<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>モチサポ ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/login/Login.css">
</head>
<body>
    <div class="login-container">
        <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="モチサポロゴ" class="login-logo">
        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>
        
        <div class="login-title">ログイン</div>

        <form class="login-form" action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <input type="text" name="emailAddress" placeholder="メールアドレス" required>
            <input type="password" name="password" placeholder="パスワード" required>
            <button type="submit" class="login-button">ログイン</button>
        </form>
    </div>
</body>
</html>
