package model;

import dao.tweetDAO;

public class DeleteMutterLogic {

	//実行メソッド
	public void execute(Long userId, int tweetId) {//変更
		tweetDAO dao = new tweetDAO();
		dao.delete(userId, tweetId);
	}
}