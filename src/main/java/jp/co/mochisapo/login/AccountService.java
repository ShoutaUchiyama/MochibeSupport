package jp.co.mochisapo.login;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.naming.NamingException;

import jp.co.mochisapo.common.PasswordHasher;
import jp.co.mochisapo.login.AccountEntity.Role;

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
	 * ロールを指定して認証する
	 * @param loginId ログインID
	 * @param rawPassword　生パスワード
	 * @param role 期待ロール
	 * @return 認証に成功したらアカウント、失敗なら Optional.empty()
	 * @throws SQLException
	 * @throws NamingException
	 */
    public Optional<AccountEntity> authenticate(String loginId, char[] rawPassword, Role role)
            throws SQLException, NamingException {
        try {
            if (loginId == null || loginId.isBlank() || role == null) {
                return Optional.empty();
            }
            switch (role) {
                case STUDENT:
                    return verifyAndReturn(accountRepository.findStudentByLoginId(loginId), rawPassword);
                case STAFF:
                    return verifyAndReturn(accountRepository.findStaffByLoginId(loginId), rawPassword);
                case ADMIN:
                    return verifyAndReturn(accountRepository.findAdministratorByLoginId(loginId), rawPassword);
                default:
                    return Optional.empty();
            }
        } finally {
            // 生パスワードを早期ワイプ
            if (rawPassword != null) Arrays.fill(rawPassword, '\0');
        }
    }

    /**
     * ロール自動判別で認証する（学生→職員→管理者の順に探索）。
     * @param loginId ログインID
     * @param rawPassword 生パスワード（呼び出し後にワイプされる）
     * @return 認証に成功したらアカウント、失敗なら Optional.empty()
     */
    public Optional<AccountEntity> authenticateAutoDetect(String loginId, char[] rawPassword)
            throws SQLException, NamingException {
        try {
            if (loginId == null || loginId.isBlank()) {
                return Optional.empty();
            }

            // 学生
            Optional<AccountEntity> ok = verifyAndReturn(
                    accountRepository.findStudentByLoginId(loginId), rawPassword);
            if (ok.isPresent()) return ok;

            // 職員
            ok = verifyAndReturn(
                    accountRepository.findStaffByLoginId(loginId), rawPassword);
            if (ok.isPresent()) return ok;

            // 管理者
            return verifyAndReturn(
                    accountRepository.findAdministratorByLoginId(loginId), rawPassword);

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