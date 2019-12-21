package messages;
import java.util.HashMap;
public class UserDetailsResponse extends Response{
	String guestuser;
	HashMap<String,String> details;
	public UserDetailsResponse(String username,String guestuser,HashMap<String,String> details){
		super(username);
		this.details =  new HashMap<String,String>(details);
		this.guestuser = guestuser;
	}
	public String getGuestname(){
		return guestuser;
	}
	public HashMap<String,String> getinfo(){
		return this.details;
	}
}