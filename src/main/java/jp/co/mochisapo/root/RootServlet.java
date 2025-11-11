package jp.co.mochisapo.root;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.mochisapo.setup.SetupService;

@WebServlet(name = "RootServlet", urlPatterns = { "" })
public class RootServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SetupService setupService = new SetupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // administrator が0件なら /setup へ、1件以上なら /LoginServlet へ
        final String ctx = req.getContextPath();
        if (!setupService.hasAnyAdmin()) {
            resp.sendRedirect(ctx + "/setup");
        } else {
            resp.sendRedirect(ctx + "/LoginHomeServlet");
        }
    }
}
