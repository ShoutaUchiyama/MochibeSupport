<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<!-- クロムで開いた時に「日本語に翻訳しますか？」を出さない /-->
<html lang="ja">
<head>
<meta charset="UTF-8">
  <title>キャリコン用ホーム画面</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/home/HomeCommon.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/home/CareerConsultantHome.css" />
  
<!-- ヘッダー固定 /-->
  <header>
    <a href="${pageContext.request.contextPath}/LogoutServlet"><font size="-1"><b>ログアウト　　</b></font></a>
    <a href="${pageContext.request.contextPath}/public/support/CareerConsultantHomeServlet"><font size="-1"><b>ホーム　　　　</b></font></a>
  </header>


</head>
<body class="role--consultant">

<div align="center">

  <p><b>ようこそ<c:out value="${Name}"/></b></p>


<br><br>

<!-- 各メニュー /-->
<table bgcolor="#333333" width="100%">
<tr bgcolor="#333333">
<td bgcolor="#333333" align="center">

<a href="${pageContext.request.contextPath}/public/support/InterviewConfirmedListServlet"><font size="+1" color="#ffffff">
<span onmouseover="this.innerText='　面談予約状況　'" onmouseout="this.innerText='　Reserve　'">　Reserve　</span>
</font></a>

<a href="${pageContext.request.contextPath}/public/user/UserInfoServlet"><font size="+1" color="#ffffff"><span onmouseover="this.innerText='　ユーザー情報　'" onmouseout="this.innerText='　My Page　'">　My Page　</span></font></a>

</td>
</tr>
</table>


<br><br><br><br><br>

</div>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/hoge/javascript/link.js"></script>
</body>
</html>