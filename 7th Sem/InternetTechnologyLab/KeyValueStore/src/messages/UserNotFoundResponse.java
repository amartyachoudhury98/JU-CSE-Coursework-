package messages;
public class UserNotFoundResponse extends Response{
	String message;
	public UserNotFoundResponse(String username){
		super(username);
		this.message = "User does not exist ";
	}
	public String message(){
		return this.message;
	}
}