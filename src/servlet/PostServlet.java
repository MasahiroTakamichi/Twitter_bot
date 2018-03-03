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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostMutterLogic;
import model.Tweet;
import model.TwitterUser;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        /*前回までの既に使わなくなったソース：import注意！
        String text = request.getParameter("text");
        String time= request.getParameter("time");
        System.out.println(time);

        Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");
        try {
              twitter.updateStatus(text);

        } catch (TwitterException e) {
            throw new ServletException(e);
        }

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		System.out.println("PostServletからフォワードMain.jsp");
		dispatcher.forward(request, response);
		*/
    	System.out.println("Post Point1");
    	TwitterUser loginUser = (TwitterUser)request.getSession().getAttribute("loginUser");
        List<Tweet> tweetList = (List<Tweet>)request.getSession().getAttribute("tweetList");
        System.out.println("Post Point2");
        String text = request.getParameter("text");
        System.out.println("Post Point3:"+text);
        String time = request.getParameter("time")+":00";
        System.out.println("Post Point4:"+ time);
        System.out.println("Postsevlet :"+text+""+time);
        System.out.println("Post Point5");


        System.out.println("Postsevlet2 :"+loginUser.getId()+"："+ (getMax(tweetList) + 1)+"："+text+"："+time);
        Tweet tweet = new Tweet(loginUser.getId(), getMax(tweetList) + 1,text,time);
        PostMutterLogic postLogic = new PostMutterLogic();

        postLogic.execute(tweet);

        ////////////////////////Main.javaのポストに飛ぶ*******************************************************************************************
        System.out.println("postservlet Main.java");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
		dispatcher.forward(request, response);
    }

    private int getMax(List<Tweet> tweetList ){
    	int max = 0;
    	for(Tweet t: tweetList){
    		if ( t.getId() >= max ) {
    			max = t.getId();
    		}
    	}
    	return max;
    }
}
