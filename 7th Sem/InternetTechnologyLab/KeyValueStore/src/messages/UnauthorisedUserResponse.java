package messages;
public class UnauthorisedUserResponse extends Response{
	String message;
	public UnauthorisedUserResponse(String username){
		super(username);
		this.message = "User does not have manager priviledges";
	}
	public String message(){
		return this.message;
	}
}