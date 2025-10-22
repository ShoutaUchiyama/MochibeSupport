<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>モチサポ ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/Login.css">
</head>
<body>
    <div class="login-container">
        <img src="${pageContext.request.contextPath}/static/img/logo.png" alt="モチサポロゴ" class="login-logo">
        <div class="login-title">ログイン</div>

        <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
            <input type="text" name="userId" placeholder="ユーザーID" required>
            <input type="password" name="password" placeholder="パスワード" required>
            <button type="submit" class="login-button">ログイン</button>
        </form>
    </div>
</body>
</html>
