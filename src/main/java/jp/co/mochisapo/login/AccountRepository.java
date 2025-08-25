package jp.co.mochisapo.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.naming.NamingException;

import jp.co.mochisapo.common.DbUtil;

/**
 * ログインIDとパスワードに一致するユーザを1件取得します。
 *
 * @param loginId  ログインID
 * @param password パスワード（ハッシュ前提ならその値）
 * @return 見つかった場合は Student、無い場合は null（Optional を推奨）
 * @throws SQLException    SQL実行時の例外
 * @throws NamingException DataSource 解決失敗
 */

public class AccountRepository {

	public Optional<AccountEntity> findStudentByLoginIdAndPassword(AccountDto accountDto) throws SQLException, NamingException {
		
		final String sql = "select id, login_id, password, class_code, name, birthday from student"
				+ " where login_id = ? and password = ? ;";
		
		try (Connection conn = DbUtil.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, accountDto.getLoginId());
			ps.setString(2, accountDto.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return null;
			
				AccountEntity e = new AccountEntity();
				e.setId(rs.getInt("id"));
				e.setLoginId(rs.getString("login_id"));
				e.setPassword(rs.getString("password"));
				e.setClassCode(rs.getString("class_code"));
				e.setName(rs.getString("name"));
				java.sql.Date bd = rs.getDate("birthday");
				e.setBirthday(bd != null ? bd.toLocalDate() : null);
				return Optional.of(e);
			}	
		}
	}
}
