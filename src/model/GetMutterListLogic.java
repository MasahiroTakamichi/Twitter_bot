package model;

import java.util.List;

import dao.tweetDAO;

public class GetMutterListLogic {

	public List<Tweet> execute(TwitterUser loginUser) {//変更

		tweetDAO dao = new tweetDAO();

		List<Tweet>mutterList = dao.findAll(loginUser.getId());

		return mutterList;
	}
	public List<Tweet> execute() {//変更

		tweetDAO dao = new tweetDAO();

		List<Tweet>TweetList = dao.findAll();

		return TweetList;
	}

}
