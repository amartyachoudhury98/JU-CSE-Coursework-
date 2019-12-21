package messages;
import java.io.Serializable;
public class Response implements Serializable{
	String username;
	public Response(String username){
		this.username =username;
	}
}