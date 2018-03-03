<!--
	つぶやきアプリ デフォルトページ
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<style>
			tt	{
				color:#FFFFFF;
				font-size:60px;
				text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
             GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
			st	{
				color:#FFFFFF;
				font-size:20px;
				text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
             GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
		</style>
		<meta charset="UTF-8">
		<title>schneitBot</title>
	</head>
	<body background="./images/background.jpg">
	<br>
	<br>
	<br>
	<br>

		<DIV style="text-align:center;">
		<tt><b>Schneit BOT</b></tt>
		</DIV>
		<br>
		<br>
		<br>
		<DIV style="text-align:center">
		<st><b>Schneit BOT(シュナイトボット)とは</b></st><br>
		<st><b>Twitterで時間指定して自動ツイートをしてくれるアプリです</b></st>
		<br>
		<br>
		<br>
		<form action= "/schneit/Signin" method = "get">
			<input type = "image" src="./images/StartButton.png">
		</form>
		</DIV>
		<%System.out.println("index"); %>

	</body>
</html>