/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

@WebServlet("/oauth")
public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	System.out.println("CallbackServlet start");
    	Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        System.out.println("verifier:  "+verifier);
        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");
/*
            System.out.println("************************試しに表示AccessToken**********************************");
            System.out.println(twitter.verifyCredentials().getId());
            System.out.println(twitter.getOAuthAccessToken());
            System.out.println("*******************************************************************************");

            User user = twitter.verifyCredentials();
            //ユーザ情報取得
            System.out.println("名前　　　　：" + user.getName());
            System.out.println("表示名　　　：" + user.getScreenName());
            System.err.println("フォロー数　：" + user.getFriendsCount());
            System.out.println("フォロワー数：" + user.getFollowersCount());

            Scanner sc = new Scanner(System.in);

            System.out.print("Text:");
            String text = sc.nextLine();

            //つぶやきの実行
            Status status = twitter.updateStatus(text);
*/
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        //response.sendRedirect(request.getContextPath() + "/");

        System.out.println("CallbackServletのリダイレクト先（WEB上のソース）："+request.getContextPath());

     // ここでLogin.javaを呼ぶ？
        RequestDispatcher dispatcher = request.getRequestDispatcher("/SchneitLogin");
		dispatcher.forward(request, response);
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		//System.out.println("フォワードMain.jsp");
		//dispatcher.forward(request, response);

    }
}
