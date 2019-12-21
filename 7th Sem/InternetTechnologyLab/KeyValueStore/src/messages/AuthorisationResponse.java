package messages;
public class AuthorisationResponse extends Response{
	String message;
	public AuthorisationResponse(String username,boolean isManager){
		super(username);
		if(isManager== true) this.message = "Already Manager";
		else this.message = "User upgraded to manager ";
	}
	public String message(){
		return this.message;
	}
}