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
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EditMutterLogic;
import model.Tweet;
import model.TwitterUser;



@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8") ;

        TwitterUser loginUser = (TwitterUser)request.getSession().getAttribute("loginUser");


        long userId = Long.valueOf( request.getParameter("uId") );
        int tweetId = Integer.valueOf( request.getParameter("tId") );
        String text = request.getParameter("text");
        Time time = Time.valueOf( request.getParameter("time")+":00" );
        boolean flag = Boolean.valueOf(request.getParameter("flag") );
        Tweet editTweet = new Tweet(userId,tweetId,text,time,flag);

        System.out.println("EditServlet:"+userId+":"+tweetId+":"+text+":"+time+":"+flag);

        System.out.println("deleteSevlet2 :"+loginUser.getId()+"：");
        EditMutterLogic editLogic = new EditMutterLogic();

        editLogic.execute(editTweet);

        ////////////////////////Main.javaのポストに飛ぶ*******************************************************************************************
        System.out.println("postservlet Main.java");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Main");
		dispatcher.forward(request, response);
    }


}
