<!--
	つぶやき参照用の画面
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import=" model.*,twitter4j.*, java.util.*, java.text.SimpleDateFormat " %>
<%@ page errorPage="/error.jsp"%>
<%

// セッションスコープに保存されたtwitter情報を取得
Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");

//ユーザー情報準備
User user = twitter.verifyCredentials();

//タイムライン準備
List<Status> statuses = twitter.getHomeTimeline();

//時間取得
Calendar calendar = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("HH：mm:ss");
String time = sdf.format(calendar.getTime());

//tweetList
TwitterUser loginUser = (TwitterUser)request.getSession().getAttribute("loginUser");
Tweet editTweet = (Tweet)request.getSession().getAttribute("editTweet");

StringBuffer sb = new StringBuffer();
sb.append(editTweet.getTime().toString());
int index = sb.lastIndexOf(":");
sb.replace(index, sb.length(), "");
//rate limited TwitterAPI　の制御を入れる
%>

<!DOCTYPE html>
<html>
	<style>
		tt{
			font-family:fantasy,fantasy;
			color:#FFFFFF;
			font-size:60px;
			text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
            GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
		stt{
			font-family:fantasy,fantasy;
			color:#FFFFFF;
			font-size:30px;
			text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
       	    GRAY 1px -1px 0px, GRAY -1px -1px 0px;
		}
		tm{
			 color:#FFFFFF;
			 font-size:20px;
			 text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
             GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
		usr	{
			color:#FFFFFF;
			font-size:10px;
			text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
            GRAY 1px -1px 0px, GRAY -1px -1px 0px;
		}
		body {
			background-image: url("./images/background.jpg");
			background-attachment: fixed;
			background-position: center;
			background-repeat: no-repeat
		}
	</style>
	<head>
		<meta charset="UTF-8">
		<title>Schneit Bot</title>
	</head>
	<body>
		<DIV style="text-align:right">
		<a href="/schneit/Logout" ><b>ログアウト</b></a>
		</DIV>
		<DIV style="text-align:center;">
		<tt>Schneit BOT<br>編集画面</tt>
		</DIV>
		<DIV style="text-align:right">
		<p>

			<font size = "5"><b>ユーザー情報</b></font><br>
			<img src=<%= user.getProfileImageURL() %> alt="ユーザー画像" height="64" align="right" style="margin-top:6px">
			<usr><%= user.getName() %> @<%= user.getScreenName() %><br>
			フォロー数：<%= user.getFriendsCount() %>人<br>
			フォロワー数：<%= user.getFollowersCount() %>人<br clear="left"></usr>
		</p>
		</DIV>

		<stt>ツイート編集</stt><br>
		<tm>現在時刻<%= time %></tm><br>
		<table bgcolor = "#BBBBBB" border=1>
			<tr bgcolor = "#FFFFFF"><th>ツイート内容</th><th>設定時間</th><th>実行ステータス</th><th>編集確定</th></tr>
			<tr  bgcolor = "#FFFFFF">
				<form action="/schneit/EditServlet" method="post" id = form>
					<input type="hidden" name="uId" value = <%=editTweet.getUserId()%> >
					<input type="hidden" name="tId" value = <%=editTweet.getId()%> >
					<td> <input type="text" name="text" value=<%=editTweet.getText()%>> </td>
					<td> <input type="time" name="time" value=<%=sb%>> </td>
					<td>
						<select name="flag" form ="form"required>
							<option value="">選択してください</option>
							<option value="true">実行します</option>
							<option value="false">実行しません</option>
						</select>
					</td>
					<td> <input type="submit" value="編集確定"> </td>
				</form>
			</tr>
		</table>
	</body>
</html>