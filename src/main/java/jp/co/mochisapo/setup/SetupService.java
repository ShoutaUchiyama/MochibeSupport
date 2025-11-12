package jp.co.mochisapo.setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.co.mochisapo.common.JobCategory;
import jp.co.mochisapo.common.PasswordHasher; // 添付のクラスのパッケージに合わせて

public class SetupService {

    private final DataSource ds;

    public SetupService() {
        try {
            Context c = new InitialContext();
            this.ds = (DataSource) c.lookup("java:comp/env/jdbc/mochisapo");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    /** administrator に1件でもレコードがあるか */
    public boolean hasAnyAdmin() {
        final String sql = "SELECT COUNT(*) FROM users";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 初期管理者の作成（passwordにハッシュを保存） */
    public void createInitialAdmin(String emailAddress, String rawPassword, String name) throws SQLException {
        final String hash = hashWithPasswordHasher(rawPassword);

        final String sql = "INSERT INTO users (email_address, password, name, job_category_code) VALUES (?, ?, ?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, emailAddress);
            ps.setString(2, hash);
            ps.setString(3, name);
            ps.setString(4, JobCategory.ADMIN.getJobCode());
            ps.executeUpdate();
        }
    }

    // ===== CSRF（簡易）=====
    public String issueCsrfToken(javax.servlet.http.HttpSession session) {
        String token = UUID.randomUUID().toString();
        session.setAttribute("setup_csrf", token);
        return token;
    }

    public boolean verifyCsrfToken(javax.servlet.http.HttpSession session, String token) {
        if (session == null || token == null) return false;
        Object v = session.getAttribute("setup_csrf");
        boolean ok = token.equals(v);
        if (ok) session.removeAttribute("setup_csrf");
        return ok;
    }

    private String hashWithPasswordHasher(String raw) {
        return PasswordHasher.hash(raw.toCharArray());
    }
}
