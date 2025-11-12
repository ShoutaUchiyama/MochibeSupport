package jp.co.mochisapo.login;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.naming.NamingException;

import jp.co.mochisapo.common.PasswordHasher;

/**
 * アカウント認証用サービス
 * ・リポジトリは loginId で候補（保存ハッシュ付き）を取得
 * ・ここで生パスワードと保存ハッシュを検証してエンティティを返す
 * @author Shota.Uchiyama
 *
 */
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	/** DI用コンストラクタ */
	public AccountService() {
		accountRepository = new AccountRepository();
	}
	/** デフォルトコンストラクタ（簡便用途）*/
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

    /**
     * ロール自動判別で認証する（学生→職員→管理者の順に探索）。
     * @param loginId ログインID
     * @param rawPassword 生パスワード（呼び出し後にワイプされる）
     * @return 認証に成功したらアカウント、失敗なら Optional.empty()
     */
    public Optional<AccountEntity> authenticateAutoDetect(String emailAddress, char[] rawPassword)
            throws SQLException, NamingException {
        try {
            if (emailAddress == null || emailAddress.isBlank()) {
                return Optional.empty();
            }

            Optional<AccountEntity> ok = verifyAndReturn(
                    accountRepository.findUser(emailAddress), rawPassword);
            return ok;

        } finally {
            if (rawPassword != null) Arrays.fill(rawPassword, '\0');
        }
    }

    // ===== helpers =====

    /**
     * 候補の保存ハッシュと生パスワードを照合し、一致すればエンティティを返す。
     */
    private static Optional<AccountEntity> verifyAndReturn(
            Optional<AccountRepository.Candidate> candidateOpt,
            char[] rawPassword) {

        if (candidateOpt.isEmpty() || rawPassword == null) return Optional.empty();

        AccountRepository.Candidate c = candidateOpt.get();
        boolean ok = PasswordHasher.verify(rawPassword, c.passwordHash()); // 定数時間比較推奨

        return ok ? Optional.of(c.entity()) : Optional.empty();
    }
}