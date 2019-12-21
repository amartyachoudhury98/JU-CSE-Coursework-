package user;
import java.util.HashMap;
public class User{
	String username;
	HashMap<String,String> info;
	boolean isManager;
	public User(String username){
		this.username = username;
		this.isManager = false;
		info = new HashMap<>();
	}
	public void grantManagerAuth(){
		this.isManager = true;
	}
	public void put(String key,String value){
		info.put(key,value);
		return;
	}
	public String get(String key){
		return info.get(key);
	}
	public String username(){
		return username;
	}
	public boolean isManager(){
		return isManager;
	}
	public HashMap<String,String> getInfo(){
		return info;
	}
}