<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	System.out.println("error");
%>
<html>
  <head><title>Error</title></head>
  <body>Failed to connect to the Twitter API<br>
  	${exception.message}
  	<h3>エラーが発生しました。<br>15分ほど経過してからか、最初のページから再実行してください。</h3>
  	<a href="/schneit/Logout">最初のページへ</a>
  </body>
</html>