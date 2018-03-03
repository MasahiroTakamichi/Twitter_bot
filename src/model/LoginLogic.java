//ログインのチェック処理

package model;

import java.util.ArrayList;
import java.util.List;

public class LoginLogic {

	public boolean execute(TwitterUser user) {

		List<TwitterUser> userList = new ArrayList<>();

		GetUserListLogic g = new GetUserListLogic();
		userList = g.execute();

		for(TwitterUser u : userList){
			if( u.getId() == user.getId() ){
				return true;
			}
		}
		return false;
	}
}