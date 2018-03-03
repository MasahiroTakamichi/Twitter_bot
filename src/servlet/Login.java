//ログイン全般をコントロールするサーブレットクラス

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.LoginLogic;
import model.TwitterUser;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@WebServlet("/SchneitLogin")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");

		// Userインスタンス（ユーザー情報）の生成
		TwitterUser user  = null;
		try {
			user = new TwitterUser(twitter.getId());
		} catch (TwitterException e) {
			System.out.println("User Create Error");
			e.printStackTrace();
		}

		// ログインロジックのインスタンス生成
		LoginLogic loginlogic = new LoginLogic();

		//IDチェック execute()
		boolean isLogin = loginlogic.execute(user);

		// ログイン成功時の処理
		if(isLogin){
			System.out.println("ログイン成功");
		}else{
			System.out.println("ユーザーデータをDBに挿入");
			UserDAO dao = new UserDAO();
			if(dao.create(user.getId())){
				System.out.println("挿入成功");
			}else{
				System.out.println("挿入失敗");
			}
		}
		System.out.println("Login.java end");

		//要る？
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", user);

		// mainにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
		dispatcher.forward(request, response);
	}
}