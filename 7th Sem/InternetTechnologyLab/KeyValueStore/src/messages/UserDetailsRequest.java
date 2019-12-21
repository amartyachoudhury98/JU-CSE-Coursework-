package  messages;
public class UserDetailsRequest extends Request{
	String guestusername;
	public UserDetailsRequest(String username,String guestusername){
		super(username);
		this.guestusername = guestusername;
	}
	public String username(){
		return guestusername;
	}
}