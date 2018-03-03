//つぶやき部分をコントロールするサーブレットクラス

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Tweet;
import model.TwitterUser;
import timer.Timer;
import twitter4j.Twitter;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//doGet()メソッド・・ログイン結果画面の「つぶやき」リンクから
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		dispatch(request,response);
	}

	//doPost()メソッド・・・つぶやきmain画面からの「登録」
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		dispatch(request,response);
	}

	private void dispatch(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		//設定時間にツイートする用////////////////////////////////////////////////////////////////////
				Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
				System.out.println("test timer start");
				Timer t = (Timer)request.getSession().getAttribute("timer");
				if(t != null){
					t.stop();
				}
				t = new Timer(twitter);
				request.getSession().getAttribute("t");
				//////////////////////////////////////////////////////////////////////////////////////////////
		// ログインしているか確認するため
		// セッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		TwitterUser loginUser = (TwitterUser)session.getAttribute("loginUser");

		if (loginUser == null) { // ログインしていない場合
		// リダイレクト
			response.sendRedirect("/docoTsubu/");
		} else {
		// ログイン済みの場合
			GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
			List<Tweet> tweetList = getMutterListLogic.execute(loginUser);
			request.getSession().setAttribute("tweetList", tweetList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			System.out.println("フォワードMain.jsp");
			dispatcher.forward(request, response);
		}
	}
}