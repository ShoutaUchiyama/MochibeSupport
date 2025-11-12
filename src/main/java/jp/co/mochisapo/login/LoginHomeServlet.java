package jp.co.mochisapo.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginHomeServlet")
public class LoginHomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String LOGIN_JSP = "/WEB-INF/login/Login.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToLogin(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String msg = (String) request.getSession().getAttribute("setupMessage");
        if (msg != null) {
            request.setAttribute("message", msg);
            request.getSession().removeAttribute("setupMessage");
        }
        forwardToLogin(request, response);
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_JSP);
        dispatcher.forward(request, response);
    }
}
