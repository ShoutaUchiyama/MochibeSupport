package jp.co.mochisapo.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.mochisapo.login.AccountEntity.Role;
import jp.co.mochisapo.session.SessionKeys;
import jp.co.mochisapo.session.SessionUser;
import jp.co.mochisapo.user.profile.web.form.ProfileForm;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        String loginId = trimToNull(request.getParameter("loginId"));
        String pw = request.getParameter("password");
        char[] password = (pw != null) ? pw.toCharArray() : null;

        try {
            if (loginId == null || password == null || password.length == 0) {
                toLoginPage(request, response, "ログインIDまたはパスワードを入力してください。");
                return;
            }
            if (password.length > 20) {
                toLoginPage(request, response, "パスワードは20文字以内で入力してください。");
                return;
            }

            Optional<AccountEntity> auth = accountService.authenticateAutoDetect(loginId, password);
            if (auth.isEmpty()) {
                toLoginPage(request, response, "UserID、または、Passwordが違います。");
                return;
            }

            AccountEntity e = auth.get();

            // セッション固定化対策
            try { request.changeSessionId(); } catch (Throwable ignored) {
                var old = request.getSession(false);
                if (old != null) old.invalidate();
                request.getSession(true);
            }

            // セッションへは軽量DTOを格納
            SessionUser su = new SessionUser(
                    e.getId(), e.getLoginId(), e.getName(), e.getRole(),
                    e.getClassCode(), e.getJobCategoryCode()
            );
            request.getSession().setAttribute(SessionKeys.LOGIN_USER, su);

            // 管理者へのワーニング（元の挙動を踏襲）
            if (e.getRole() == Role.ADMIN) {
                request.getSession().setAttribute("MessageForAdmin", "ユーザー情報よりパスワードの変更をお願いします");
            }

            // 初回導線（誕生日未登録）…管理者はホーム、学生/職員は登録画面へ
            if (e.getBirthday() == null) {
                if (e.getRole() == Role.ADMIN) {
                    forward(request, response, "/WEB-INF/home/AdministratorHome.jsp");
                } else {
                    ProfileForm userForm = new ProfileForm();
                    initForm(userForm);            // 下のヘルパを利用（AbstractServletを使わず共通化）
                    userForm.setGender("N");
                    request.setAttribute("UserForm", userForm);
                    request.getSession().setAttribute("FirstTime", "first");
                    forward(request, response, "/WEB-INF/userEdit/UserRegister.jsp");
                }
                return;
            }

            // 通常遷移
            switch (e.getRole()) {
                case STUDENT -> forward(request, response, "/WEB-INF/home/StudentHome.jsp");
                case STAFF -> {
                    String jc = e.getJobCategoryCode();
                    if ("TT".equals(jc)) {
                        forward(request, response, "/WEB-INF/home/TeacherHome.jsp");
                    } else if ("CC".equals(jc)) {
                        forward(request, response, "/WEB-INF/home/CareerConsultantHome.jsp");
                    } else {
                        forward(request, response, "/WEB-INF/home/StaffHome.jsp");
                    }
                }
                case ADMIN -> forward(request, response, "/WEB-INF/home/AdministratorHome.jsp");
            }

        } catch (SQLException | NamingException ex) {
            throw new ServletException("認証処理でエラーが発生しました。", ex);
        } finally {
            if (password != null) Arrays.fill(password, '\0');
        }
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }
    private void toLoginPage(HttpServletRequest req, HttpServletResponse res, String msg)
            throws ServletException, IOException {
        req.setAttribute("Message", msg);
        forward(req, res, "/WEB-INF/login/Login.jsp");
    }
    private void forward(HttpServletRequest req, HttpServletResponse res, String path)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(path);
        rd.forward(req, res);
    }
    private void initForm(ProfileForm form) {
        form.setGenderList(ProfileForm.defaultGenderList());
        // 既定値を未選択に
        form.setGender(jp.co.mochisapo.user.profile.domain.Gender.NONE.getCode());
    }
}
