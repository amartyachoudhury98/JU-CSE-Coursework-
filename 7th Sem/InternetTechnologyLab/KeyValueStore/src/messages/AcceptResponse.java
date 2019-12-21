package  messages;
public class AcceptResponse extends Response{
	String message;
	public AcceptResponse(String username,boolean userExists){
		super(username);
		if(userExists == true) this.message = "Username Exists";
		else this.message = "New User Created";
	}
	public String message(){
		return this.message;
	}
}