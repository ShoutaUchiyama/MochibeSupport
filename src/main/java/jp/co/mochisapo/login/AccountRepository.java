package jp.co.mochisapo.login;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.naming.NamingException;

import jp.co.mochisapo.common.DbUtil;
import jp.co.mochisapo.login.AccountEntity.Role;

/**
 * ログインIDとパスワードに一致するユーザを1件取得します。
 * @param accountDto ログインID/パスワードを保持するDTO(パスワードはハッシュを想定）
 * @return 見つかった場合は Student、無い場合は Optional.empty()
 * @throws SQLException    SQL実行時の例外
 * @throws NamingException DataSource解決失敗
 * @author Shota.Uchiyama
 */

public final class AccountRepository {
	
	private static final String SQL_GET_STUDENT_BY_LOGIN_ID  = """
			SELECT id, login_id, class, name, birthday, password AS password_hash 
			FROM student WHERE login_id = ? LIMIT 1
			""";
	
	private static final String SQL_GET_STAFF_BY_LOGIN_ID  = """
			SELECT id, login_id, class, name, job_category, birthday, password AS password_hash 
			FROM staff WHERE login_id = ? LIMIT 1 
			""";
	
	private static final String SQL_GET_ADMIN_BY_LOGIN_ID  = """ 
			SELECT id, login_id, name, password AS password_hash 
			FROM administrator WHERE login_id = ? LIMIT 1 
			""";
	// loginIdから候補を取って返却する（パスワード照合はサービス層で）
	public Optional<Candidate> findStudentByLoginId(String loginId) 
			throws SQLException, NamingException {
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_GET_STUDENT_BY_LOGIN_ID)) {
			
			ps.setString(1, loginId);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				return Optional.of(mapStudentCandidate(rs));
			}	
		}
	}
	
	public Optional<Candidate> findStaffByLoginId(String loginId) 
			throws SQLException, NamingException {
		
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_GET_STAFF_BY_LOGIN_ID)) {
			
			ps.setString(1, loginId);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				
				return Optional.of(mapStaffCandidate(rs));
			}
		}
	}
	
	public Optional<Candidate> findAdministratorByLoginId(String loginId) 
			throws SQLException, NamingException {
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_GET_ADMIN_BY_LOGIN_ID)) {
			
			ps.setString(1, loginId);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				return Optional.of(mapAdminCandidate(rs));
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
	private static Candidate mapStudentCandidate(ResultSet rs) throws SQLException {
		AccountEntity e = new AccountEntity();
		e.setRole(Role.STUDENT);
		e.setId(rs.getInt("id"));
		e.setLoginId(rs.getString("login_id"));
		e.setClassCode(rs.getString("class"));
		e.setName(rs.getString("name"));
		Date bd = rs.getDate("birthday");
		e.setBirthday(bd != null ? bd.toLocalDate() : null);
		return new Candidate(e, rs.getString("password_hash"));

	}
	
	private static Candidate mapStaffCandidate(ResultSet rs) throws SQLException {
		Candidate c = mapStudentCandidate(rs);
		c.entity().setRole(Role.STAFF);
		c.entity().setJobCategoryCode(rs.getString("job_category"));
		return new Candidate(c.entity(), rs.getString("password_hash"));
	}
	
	private static Candidate mapAdminCandidate(ResultSet rs) throws SQLException {
		AccountEntity e = new AccountEntity();
		e.setRole(Role.ADMIN);
		e.setId(rs.getInt("id"));
		e.setLoginId(rs.getString("login_id"));
		e.setName(rs.getString("name"));
		return new Candidate(e, rs.getString("password_hash"));
	}
}
