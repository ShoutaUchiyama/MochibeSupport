package jp.co.mochisapo.common;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PBKDF2(HMAC-SHA256) によるパスワードハッシュ/検証ユーティリティ。
 * 保存形式: "iterations:saltBase64:hashBase64"
 */
public final class PasswordHasher {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 120_000; // 目標コスト（将来引き上げ可）
    private static final int KEY_LENGTH = 256;     // bits
    private static final int SALT_LENGTH = 16;     // bytes

    private static final SecureRandom RNG = new SecureRandom();

    private PasswordHasher() {}

    /** パスワードをハッシュ化してDB保存用の文字列を返す。呼び出し元で raw のワイプは別途行ってください。 */
    public static String hash(char[] password) {
        byte[] salt = new byte[SALT_LENGTH];
        RNG.nextBytes(salt);
        return hashWithSalt(password, salt, ITERATIONS);
    }

    /** 保存済みハッシュ（iterations:salt:hash）と生パスワードを照合する。 */
    public static boolean verify(char[] rawPassword, String stored) {
        // 形式チェック
        String[] parts = (stored != null) ? stored.split(":", 3) : null;
        if (parts == null || parts.length != 3) {
            fakeVerify(rawPassword); // タイミング耐性
            return false;
        }

        int iterations;
        try {
            iterations = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            fakeVerify(rawPassword);
            return false;
        }

        byte[] salt;
        byte[] expected;
        try {
            salt = Base64.getDecoder().decode(parts[1]);
            expected = Base64.getDecoder().decode(parts[2]);
        } catch (IllegalArgumentException e) {
            fakeVerify(rawPassword);
            return false;
        }

        byte[] actual = null;
        // 既存ハッシュの長さに合わせる（将来 KEY_LENGTH を変えても検証可能）
        PBEKeySpec spec = new PBEKeySpec(rawPassword != null ? rawPassword : new char[0],
                                         salt, iterations, expected.length * 8);
        try {
            actual = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
            return MessageDigest.isEqual(actual, expected); // 定数時間比較
        } catch (Exception e) {
            fakeVerify(rawPassword);
            return false;
        } finally {
            spec.clearPassword();
            if (actual != null) Arrays.fill(actual, (byte) 0);
            Arrays.fill(salt, (byte) 0);
            Arrays.fill(expected, (byte) 0);
        }
    }

    /**
     * ストア形式が古い（iterations が現在値より小さい等）場合に true。
     * 認証成功時、true なら再ハッシュして更新すると良い。
     */
    public static boolean needsRehash(String stored) {
        String[] parts = (stored != null) ? stored.split(":", 3) : null;
        if (parts == null || parts.length != 3) return true;
        try {
            int it = Integer.parseInt(parts[0]);
            return it < ITERATIONS;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    // ===== 内部実装 =====

    private static String hashWithSalt(char[] password, byte[] salt, int iterations) {
        byte[] dk = null;
        PBEKeySpec spec = new PBEKeySpec(password != null ? password : new char[0],
                                         salt, iterations, KEY_LENGTH);
        try {
            dk = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
            String saltB64 = Base64.getEncoder().encodeToString(salt);
            String hashB64 = Base64.getEncoder().encodeToString(dk);
            return iterations + ":" + saltB64 + ":" + hashB64;
        } catch (Exception e) {
            throw new RuntimeException("ハッシュ化失敗", e);
        } finally {
            spec.clearPassword();
            if (dk != null) Arrays.fill(dk, (byte) 0);
            Arrays.fill(salt, (byte) 0);
        }
    }

    /**
     * パース失敗や例外時にも計算を行い、応答時間の差を小さくするダミー検証。
     * 戻り値は使わず、副作用（計算時間消費）のみを意図。
     */
    public static void fakeVerify(char[] rawPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        RNG.nextBytes(salt);
        byte[] dk = null;
        PBEKeySpec spec = new PBEKeySpec(rawPassword != null ? rawPassword : new char[0],
                                         salt, ITERATIONS, KEY_LENGTH);
        try {
            dk = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
        } catch (Exception ignored) {
        } finally {
            spec.clearPassword();
            if (dk != null) Arrays.fill(dk, (byte) 0);
            Arrays.fill(salt, (byte) 0);
        }
    }
}
