package jp.co.mochisapo.setup;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/setup")
public class SetupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SetupService setupService = new SetupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (setupService.hasAnyAdmin()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String token = setupService.issueCsrfToken(req.getSession(true));
        req.setAttribute("csrfToken", token);

        req.getRequestDispatcher("/WEB-INF/setup/Setup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (setupService.hasAnyAdmin()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String loginId = req.getParameter("loginId");
        String password = req.getParameter("password");
        String name     = req.getParameter("name");
        String csrf     = req.getParameter("csrfToken");

        if (!setupService.verifyCsrfToken(req.getSession(false), csrf)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CSRF token");
            return;
        }

        if (isBlank(loginId) || isBlank(password) || isBlank(name)) {
            req.setAttribute("error", "すべて入力してください。");
            req.setAttribute("csrfToken", setupService.issueCsrfToken(req.getSession()));
            req.getRequestDispatcher("/WEB-INF/setup/Setup.jsp").forward(req, resp);
            return;
        }

        try {
            setupService.createInitialAdmin(loginId.trim(), password, name.trim());
            req.getSession().setAttribute("setupMessage", "初期管理者の登録が完了しました。ログインしてください。");
            resp.sendRedirect(req.getContextPath() + "/LoginHomeServlet");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "登録に失敗しました（重複IDなど）。");
            req.setAttribute("csrfToken", setupService.issueCsrfToken(req.getSession()));
            req.getRequestDispatcher("/WEB-INF/setup/Setup.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
