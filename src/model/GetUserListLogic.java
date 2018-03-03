package model;

import java.util.List;

import dao.UserDAO;

public class GetUserListLogic {

	public List<TwitterUser> execute() {//変更

		UserDAO dao = new UserDAO();

		List<TwitterUser>UserList = dao.findAll();

		return UserList;
	}

}
