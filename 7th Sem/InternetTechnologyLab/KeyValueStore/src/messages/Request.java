package messages;
import java.io.Serializable;
public class Request implements Serializable{
	String username;
	public Request(String uname){
		this.username = uname;
	}
	public String getSender(){
		return username;
	}
}