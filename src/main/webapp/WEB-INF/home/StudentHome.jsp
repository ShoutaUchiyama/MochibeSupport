<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>生徒用ホーム画面</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/home/HomeCommon.css">
</head>

<body class="ms-body">
  <!-- ← headerはbody直下へ -->
  <header class="ms-header">
    <div class="ms-header__inner">
      <nav class="ms-nav">
        <a href="${pageContext.request.contextPath}/LogoutServlet">ログアウト</a>
        <a href="${pageContext.request.contextPath}/public/learn/StudentHomeServlet">ホーム</a>
      </nav>
    </div>
  </header>

  <div class="ms-container" align="center">
    <section class="ms-welcome">
      <div>
        <div class="ms-welcome__title">
          ようこそ <c:out value="${Name}"/> さん
        </div>
        <div class="ms-welcome__sub">学内サービスへアクセスできます</div>
      </div>
    </section>

    <div class="ms-grid">
      <!-- Newsカード -->
      <section class="ms-card ms-news">
        <div class="ms-card__head">
          <div class="ms-card__title">News</div>
        </div>
        <div class="ms-card__body">
          <iframe
            id="inlineFrameExample"
            title="Inline Frame Example"
            width="100%"
            height="220"
            src="${pageContext.request.contextPath}/public/learn/NewsListForStudentServlet">
          </iframe>
        </div>
      </section>

      <section class="ms-card">
        <div class="ms-card__head">
          <div class="ms-card__title">メニュー</div>
        </div>
        <div class="ms-card__body">
          <div class="ms-menu">
            <a href="${pageContext.request.contextPath}/public/learn/SelectStaffServlet">
              <span class="ms-menu__label">
                <span class="jp">面談予約</span><span class="en">Reserve</span>
              </span>
            </a>
            <a href="${pageContext.request.contextPath}/public/learn/BookListForStudentServlet">
              <span class="ms-menu__label">
                <span class="jp">書籍貸出</span><span class="en">Borrow</span>
              </span>
            </a>
            <a href="${pageContext.request.contextPath}/public/clazz/RecommendedListServlet">
              <span class="ms-menu__label">
                <span class="jp">おすすめ情報</span><span class="en">Recommend</span>
              </span>
            </a>
            <a href="${pageContext.request.contextPath}/public/user/UserInfoServlet">
              <span class="ms-menu__label">
                <span class="jp">ユーザー情報</span><span class="en">My Page</span>
              </span>
            </a>
          </div>
        </div>
      </section>
    </div>

    <!-- 問い合わせフォーム -->
    <section class="ms-card ms-ask mt-16">
      <div class="ms-card__head">
        <div class="ms-card__title">講師への質問</div>
      </div>
      <div class="ms-card__body">
        <form action="${pageContext.request.contextPath}/public/learn/QuestionInsertServlet" method="POST">
          <c:if test="${not empty Message}">
            <div class="ms-error"><c:out value="${Message}"/></div>
          </c:if>

          <label class="visually-hidden" for="text">質問内容</label>
          <textarea id="text" name="content" placeholder="全角200文字以内で記入して下さい"><c:out value="${Content}"/></textarea>

          <div class="ms-actions">
            <dialog id="doneDialog">
              <div class="ms-modal__head">
                <div class="ms-modal__title">送信完了</div>
              </div>
              <div class="ms-modal__body">
                送信が完了しました。
              </div>
              <div class="ms-modal__foot">
                <button type="submit" id="close" class="btn btn--primary">閉じる</button>
              </div>
            </dialog>
            <button id="open" type="button" class="btn btn--primary">送信</button>
          </div>
        </form>
      </div>
    </section>
  </div>

  <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
  <script src="${pageContext.request.contextPath}/hoge/javascript/link.js"></script>
  <script src="${pageContext.request.contextPath}/static/css/interview/StudentHome.js"></script>
</body>
</html>