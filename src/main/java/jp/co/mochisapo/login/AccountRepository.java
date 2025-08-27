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

public class AccountRepository {
	
	private static final String SQL_FIND_STUDENT = """
			SELECT id, login_id, class_code, name, birthday 
			FROM student
			WHERE login_id = ? AND password = ?
			LIMIT 1
			""";
	
	private static final String SQL_FIND_STAFF = """
			SELECT id, login_id, class_code, name, job_category_code, birthday 
			FROM staff 
			WHERE login_id = ? AND password = ? 
			LIMIT 1 
			""";
	
	private static final String SQL_FIND_ADMINISTRATOR = """ 
			SELECT id, login_id, name 
			FROM administrator 
			WHERE login_id = ? AND password = ?
			LIMIT 1 
			""";

	public Optional<AccountEntity> findStudentByLoginIdAndPassword(AccountDto accountDto) 
			throws SQLException, NamingException {
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_FIND_STUDENT)) {
			
			ps.setString(1, accountDto.getLoginId());
			ps.setString(2, accountDto.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
			
				return Optional.of(mapStudent(rs));
			}	
		}
	}
	
	public Optional<AccountEntity> findStaffByLoginIdAndPassword(AccountDto accountDto) 
			throws SQLException, NamingException {
		
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_FIND_STAFF)) {
			
			ps.setString(1, accountDto.getLoginId());
			ps.setString(2, accountDto.getPassword());
			
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				
				return Optional.of(mapStaff(rs));
			}
		}
	}
	
	public Optional<AccountEntity> findAdministratorByLoginIdAndPassword(AccountDto accountDto) 
			throws SQLException, NamingException {
		
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_FIND_ADMINISTRATOR)) {
			
			ps.setString(1,  accountDto.getLoginId());
			ps.setString(2, accountDto.getPassword());
			
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return Optional.empty();
				
				return Optional.of(mapAdministrator(rs));
			}
		}
	}
	
	// --- mappers ---
	
	private static AccountEntity mapStudent(ResultSet rs) throws SQLException {
		AccountEntity e = new AccountEntity();
		e.setId(rs.getInt("id"));
		e.setLoginId(rs.getString("login_id"));
		e.setClassCode(rs.getString("class_code"));
		e.setName(rs.getString("name"));
		Date bd = rs.getDate("birthday");
		e.setBirthday(bd != null ? bd.toLocalDate() : null);
		return e;

	}
	
	private static AccountEntity mapStaff(ResultSet rs) throws SQLException {
		AccountEntity e = mapStudent(rs);
		e.setJobCategoryCode(rs.getString("job_category_code"));
		return e;
	}
	
	private static AccountEntity mapAdministrator(ResultSet rs) throws SQLException {
		AccountEntity e = new AccountEntity();
		e.setId(rs.getInt("id"));
		e.setLoginId(rs.getString("login_id"));
		e.setName(rs.getString("name"));
		return e;
	}
}
