package model;

import dao.tweetDAO;

public class EditMutterLogic {

	//実行メソッド
	public void execute(Tweet tweet) {//変更
		tweetDAO dao = new tweetDAO();
		dao.update(tweet);
	}
}