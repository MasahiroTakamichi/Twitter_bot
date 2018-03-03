package model;

import dao.tweetDAO;

public class PostMutterLogic {

	//実行メソッド
	public void execute(Tweet tweet) {//変更

		tweetDAO dao = new tweetDAO();
		dao.create(tweet);
	}
}