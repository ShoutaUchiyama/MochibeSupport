<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Chromeで開いたときに「日本語に翻訳しますか？」を出さない -->
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/hoge/css/login/top.css">
<title>ログイン</title>
</head>
<body>
  <c:if test="${empty Message}">
    <div id="splash">
      <div id="splash-logo">モチベサポート<br>mochisapo.com</div>
    </div>
    <div class="splashbg"></div><!-- 画面遷移用 -->
 </c:if>
 <canvas id="canvas"></canvas>
 <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
   
   <div align="center">
     <font size="+2"><b>モチベサポート</b></font><br><br>
   </div>
   
   <c:if test="${not empty param.message}">
     <div align="center">
       <h5><font color="#ff000"><c:out value="${param.message}"/></font></h5>
     </div>	
   </c:if>
   <c:if test="${not empty Message}">
     <div align="center">
       <h5><font color="#ff0000"><c:out value="${Message}"/></font></h5>
     </div>
   </c:if>
   
   <table rules="none" style="width: 320px; height: 150px";
     cellspacing="100" align="center">
     
       <tbody>
         <tr align="left">
           <td align="right">UserID: </td>
           <td><input class="t-box" type="text" name="loginId" placeholder="ユーザーIDを入力してください"</td>
           <span id="buttonEye" class="fa fa-eye" onclick="pushHideButton()"></span>
         </tr>
       </tbody>
       
       <tbody>
         <tr align="center">
           <td align="center" colspan="2">
             <button type="submit" class="button"> Login </button>
           </td>
         </tr>
       </tbody>
   </table>
 </form>
 
 <c:if test="${empty Message}">
 <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
 <script src="${pageContext.request.contextPath}/hoge/css/login/top.js"></script>
 <script language="javascript">
   function pushHideButton() {
     var txtPass = document.getElementById("textPassword");
     var btnEye = document.getElementById("buttonEye");
     if (txtPass.type === "text") {
       txtPass.type = "password";
       btnEye.className = "fa fa-eye";
     } else {
       txtPass.type = "text";
       btnEye.className = "fa fa-eye-slash";
    }
  }
  </script>
  
</body>
</html>