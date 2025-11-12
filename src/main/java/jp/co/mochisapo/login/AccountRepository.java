package jp.co.mochisapo.login;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.naming.NamingException;

import jp.co.mochisapo.common.DbUtil;

/**
 * ログインIDとパスワードに一致するユーザを1件取得します。
 * @param accountDto ログインID/パスワードを保持するDTO(パスワードはハッシュを想定）
 * @return 見つかった場合は Student、無い場合は Optional.empty()
 * @throws SQLException    SQL実行時の例外
 * @throws NamingException DataSource解決失敗
 * @author Shota.Uchiyama
 */

public final class AccountRepository {
	
	private static final String SQL_GET_USER  = """
			SELECT id, email_address, class_code, name, birthday, job_category_code, password AS password_hash 
			FROM users WHERE email_address = ? LIMIT 1
			""";
	
	// emailAddressから候補を取って返却する（パスワード照合はサービス層で）
	public Optional<Candidate> findUser(String emailAddress) 
			throws SQLException, NamingException {
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_GET_USER)) {
			
			ps.setString(1, emailAddress);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				return Optional.of(mapUserCandidate(rs));
			}	
		}
	}
	
	// 認証前の一時入れ物（エンティティ + 保存ハッシュ）
	public static final class Candidate {
		private final AccountEntity entity;
		private final String passwordHash; //保存済みハッシュ
		public Candidate(AccountEntity entity, String passwordHash) {
			this.entity = entity;
			this.passwordHash = passwordHash;
		}
		public AccountEntity entity() { return entity; }
		public String passwordHash() { return passwordHash; }
	}
	
	// --- mappers ---
	private static Candidate mapUserCandidate(ResultSet rs) throws SQLException {
		AccountEntity e = new AccountEntity();
		e.setId(rs.getInt("id"));
		e.setEmailAddress(rs.getString("email_address"));
		e.setClassCode(rs.getString("class_code"));
		e.setName(rs.getString("name"));
		Date bd = rs.getDate("birthday");
		e.setJobCategoryCode(rs.getString("job_category_code"));
		e.setBirthday(bd != null ? bd.toLocalDate() : null);
		return new Candidate(e, rs.getString("password_hash"));

	}
}
