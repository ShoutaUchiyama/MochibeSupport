package jp.co.mochisapo.home;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	    String loginId = null;
	    String jobCode = null; // "TT" なら教師、それ以外はキャリコン扱い

	    Object sessionVal = session.getAttribute(SessionKeys.LOGIN_USER);
	    
	    SessionUser user = (SessionUser) sessionVal;
	    
	    loginId = safe(user::getLoginId);
	    jobCode = safe(user::getJobCategoryCode);
	    
	    if (loginId == null || loginId.isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
	    
	    // --- ID桁数でホーム画面を振り分け ---
	    String destination;
	    destination = "/WEB-INF/home/StudentHome.jsp";
	    
	    int len = loginId.length();

	    if (len == 4) {
	        destination = "/WEB-INF/home/StudentHome.jsp";

	    } else if (len == 6) {
	        // 6桁はSTAFF想定：jobCode が "TT" なら教師、それ以外はキャリコン
	        if ("TT".equals(jobCode)) {
	            destination = "/WEB-INF/home/TeacherHome.jsp";
	        } else {
	            destination = "/WEB-INF/home/CareerConsultantHome.jsp";
	        }

	    } else if (len == 8) {
	        destination = "/WEB-INF/home/AdministratorHome.jsp";

	    } else {
	        // 想定外のIDフォーマットはログインへ戻す
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
