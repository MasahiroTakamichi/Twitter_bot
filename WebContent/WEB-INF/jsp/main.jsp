<!--
	つぶやき参照用の画面
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="twitter4j.*, java.util.*, java.text.SimpleDateFormat, model.*" %>
<%//@ page errorPage="/error.jsp"%>
<%
	// セッションスコープに保存されたtwitter情報を取得
Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");

//long id = twitter.getId();

//System.out.println("ID:"+id);

User user = twitter.verifyCredentials();

List<Status> statuses = twitter.getHomeTimeline();

//時間取得
Calendar calendar = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("HH：mm:ss");
String time = sdf.format(calendar.getTime());

//ツイート番号
int cnt = 1;

//tweetList
TwitterUser loginUser = (TwitterUser)request.getSession().getAttribute("loginUser");
List<Tweet> tweetList = (List<Tweet>)request.getSession().getAttribute("tweetList");

//boolean listFlg = false;
//if(tweetList!=null){
//	listFlg = true;
//}

//rate limited TwitterAPI　の制御を入れる
%>

<!DOCTYPE html>
<html>
	<head>
		<style>
			tt	{
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
			usr	{
				color:#FFFFFF;
				font-size:10px;
				text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
             GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
			tm{
				color:#FFFFFF;
				font-size:20px;
				text-shadow: GRAY 1px 1px 0px, GRAY -1px 1px 0px,
             GRAY 1px -1px 0px, GRAY -1px -1px 0px;
			}
			/*スクロール用*/
			thead.scrollHead,tbody.scrollBody{
 				 display:block;
			}
			tbody.scrollBody{
 				 overflow-y:scroll;
 				 height:310px;
			}
			/*幅調整*/
			uname{
 				width:84px;
			}
			twt{
 				 width:400px;
			}
			body {
				background-image: url("./images/background.jpg");
				background-attachment: fixed;
				background-position: center;
				background-repeat: no-repeat
			}
			td,th{
			 	table-layout:fixed;
			}
			.id{
				width:90px;
			}
			.twt{
				 width:400px;
			}
			.time{
				width:75px;
			}
			.status{
				width:115px;
			}
			.button{
				width:80px;
			}
		</style>
		<meta charset="UTF-8">
		<title>Schneit Bot</title>
	</head>
	<body>
		<DIV style="text-align:right">
		<a href="/schneit/Logout"><b>ログアウト</b></a>
		</DIV>
		<DIV style="text-align:center;">
		<tt>Schneit BOT</tt>
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
		<table bgcolor = "#BBBBBB" border=1>
			<tr bgcolor = "#FFFFFF"><th>ツイート内容</th><th>設定時間</th><th>確定</th></tr>
			<tr  bgcolor = "#FFFFFF">
		<form action="/schneit/PostServlet" method="post">
			<td><input type="text" name="text"></td>
			<td><input type="time" name="time" value="00:00"></td>
			<td><input type="image" src="./images/addButton.png"></td>
		</form></tr>
		</table>
		<a href="/schneit/Main"><img src="./images/reload.png"alt="ページ更新"></a><tm>PC時間<%= time %></tm><br>
		<table  bgcolor = "#BBBBBB" border=1 >
		 	<thead class="scrollHead">
		 		<tr bgcolor = "#FFFFFF"><th>Name</th> <th>Tweet</th></tr>
		 	</thead>
		 	<tbody class="scrollBody">
				<% for(Status status : statuses) { %>
					<tr bgcolor = "#FFFFFF"><td><%= status.getUser().getName() %></td><td><%= status.getText() %></td></tr>
				<%} %>
			</tbody>
		</table>
		<br>
		<stt>追加済みツイートリスト</stt><br>
		<table bgcolor = "#BBBBBB" border=1>
			<thead class="scrollHead">
				<tr bgcolor = "#FFFFFF"><th class = "id">ツイートID</th><th class = "twt">ツイート内容</th><th class = "time">ツイート設定時間</th><th class = "status">実行ステータス</th><th class = "button">編集/削除</th></tr>
			</thead>
			<tbody class="scrollBody">
				<%for(Tweet tweet : tweetList) { %>
					 <tr bgcolor = "#FFFFFF">
					 	 <td class = "id"><%=tweet.getId() %></td>
						 <td class = "twt"><%=tweet.getText() %></td>
						 <td class = "time"><%=tweet.getTime() %></td>
						 <td class = "status"><% if( tweet.getFlag() ){%>
						 			実行します
						 		<%}else{%>
						 			実行しません
						 		 <%}%>
						 </td>
						 <td class = "button">
							<form action="/schneit/EditForwardServlet" method="post">
								<input type="hidden" name="uId" value=<%=tweet.getUserId()%>>
								<input type="hidden" name="tId" value=<%=tweet.getId()%>>
								<input type="hidden" name="text" value=<%=tweet.getText()%>>
								<input type="hidden" name="time" value=<%=tweet.getTime()%>>
								<input type="hidden" name="flag" value=<%=tweet.getFlag()%>>
								<input type="submit" value="編集">
							</form>
							<form action="/schneit/DeleteServlet" method="post">
								<input type="hidden" name="uId" value=<%=tweet.getUserId()%>>
								<input type="hidden" name="tId" value=<%=tweet.getId()%>>
								<input type="hidden" name="text" value=<%=tweet.getText()%>>
								<input type="hidden" name="time" value=<%=tweet.getTime()%>>
								<input type="hidden" name="flag" value=<%=tweet.getFlag()%>>
								<input type="image" src = "./images/delButton.png" alt="削除">
							</form>
						</td>
					</tr>
				<%}%>
			</tbody>
		</table>
	</body>
</html>