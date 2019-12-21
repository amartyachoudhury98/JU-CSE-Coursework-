package com.achoudhury;

import java.util.ArrayList;

public abstract class DAOClient {
	
	abstract public Boolean createUser(String firstname,String lastname,String mobileNo,
			String userid,String password,String sex);
	
	abstract public Boolean validateUser(String userid,String password);
	
	abstract public String getPreference(String username);
	
	abstract public ArrayList<Item> getItemsForUser(String username,String searchStr,String preference);
}
