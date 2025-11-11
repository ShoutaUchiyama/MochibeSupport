<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>初期管理者登録</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/setup/Setup.css">
</head>
<body>
  <div class="setup-container">
    <h1>初期管理者登録</h1>

    <c:if test="${not empty error}">
      <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/setup" method="post" autocomplete="off">
      <input type="hidden" name="csrfToken" value="${csrfToken}">

      <label for="loginId">ログインID</label>
      <input type="text" id="loginId" name="loginId" required>

      <label for="password">パスワード</label>
      <input type="password" id="password" name="password" required>

      <label for="name">表示名</label>
      <input type="text" id="name" name="name" required>

      <button type="submit">登録</button>
    </form>
  </div>
</body>
</html>
