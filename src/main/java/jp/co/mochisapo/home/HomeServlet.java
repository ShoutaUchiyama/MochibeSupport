package jp.co.mochisapo.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mochisapo.common.JobCategory;
import jp.co.mochisapo.session.SessionKeys;
import jp.co.mochisapo.session.SessionUser;

@WebServlet("/public/user/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
	    

	    // --- セッションから値を取得（SessionUserがある場合/ない場合の両方を吸収） ---
	    Object sessionVal = session.getAttribute(SessionKeys.LOGIN_USER);
	    
	    SessionUser user = (SessionUser) sessionVal;
	    
	    String mailAddress = safe(user::getEmailAddress);
	    String jobCode = safe(user::getJobCategoryCode);
	    
	    if (mailAddress == null || mailAddress.isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
	    
	    // --- 職業区分でホーム画面を振り分け ---
	    String destination = null;
	    JobCategory jc = JobCategory.fromCode(jobCode);
        switch (jc) {
        case STUDENT -> destination = "/WEB-INF/home/StudentHome.jsp";
        case TEACHER -> destination = "/WEB-INF/home/TeacherHome.jsp";
        case CAREER -> destination = "/WEB-INF/home/CareerConsultantHome.jsp";
        case ADMIN -> destination = "/WEB-INF/home/AdministratorHome.jsp";
        }

	    // 想定外のIDフォーマットはログインへ戻す
        if (destination == null || destination.isEmpty()) {
        	response.sendRedirect(request.getContextPath() + "/login");
        	return;
        }

	    RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
	    dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doPost(request, response);
	}

	/**
	 * nullセーフにSupplierから値を取り出すヘルパ。
	 */
	private static <T> T safe(java.util.function.Supplier<T> supplier) {
	    try {
	        return supplier == null ? null : supplier.get();
	    } catch (Exception e) {
	        return null;
	    }
	}
}
