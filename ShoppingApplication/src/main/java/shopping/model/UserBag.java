package shopping.model;

import java.util.TreeMap;

public class UserBag { // log of users that admins can access to view each's data
	TreeMap<String, User> users;

	public UserBag() {
		super();
		this.users = new TreeMap<String, User>();
	}

	public TreeMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(TreeMap<String, User> users) {
		this.users = users;
	}

}
