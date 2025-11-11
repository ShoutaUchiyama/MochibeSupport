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
  <title>職員用ホーム画面</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/home/HomeCommon.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style/home/TeacherHome.css" />
 
<!-- ヘッダー固定 /-->
  <header>
    <a href="${pageContext.request.contextPath}/LogoutServlet"><font size="-1"><b>ログアウト　　</b></font></a>
    <a href="${pageContext.request.contextPath}/public/teach/TeacherHomeServlet"><font size="-1"><b>ホーム　　　　</b></font></a>
  </header>

</head>
<body class="role--teacher">

<div align="center">

  <p><b>ようこそ<c:out value="${Name}"/>先生</b></p>

<font size="+2" color="#ff6347"><b>News</b></font><br>
<!-- インラインフレームでnews.htmlを表示 /-->
<iframe class="t-box" id="inlineFrameExample"
    title="Inline Frame Example"
    width="60%"
    height="180"
    src="${pageContext.request.contextPath}/public/teach/NewsListForTeacherServlet">
</iframe>

<div class="box-wrp">
  <div class="box2"><a href="${pageContext.request.contextPath}/public/teach/NewsListForTeacherServlet?code=1"><font style="font-size:15px" onMouseOver="this.style.fontWeight='bold'" onMouseOut="this.style.fontWeight='normal'">お知らせ登録入力画面へ</font></a></div>
</div>



<br><br>

<!-- 各メニュー /-->
<table bgcolor="#333333" width="100%">
<tr bgcolor="#333333">
<td bgcolor="#333333" align="center">


<!--<a href="●書籍貸出一覧へのリンク●"><font size="+1" color="#ffffff"><span onmouseover="this.innerText='　書籍貸出　'" onmouseout="this.innerText='　Borrow　'">　Borrow　</span></font></a>/-->

<a href="${pageContext.request.contextPath}/public/teach/RecommendedInformationInputServlet"><font size="+1" color="#ffffff"><span onmouseover="this.innerText='　おすすめ情報　'" onmouseout="this.innerText='　Recommend　'">　Recommend　</span></font></a>

<a href="${pageContext.request.contextPath}/public/teach/QuestionListServlet"><font size="+1" color="#ffffff"><span onmouseover="this.innerText='　質問一覧　'" onmouseout="this.innerText='　Question　'">　Question　</span></font></a>

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