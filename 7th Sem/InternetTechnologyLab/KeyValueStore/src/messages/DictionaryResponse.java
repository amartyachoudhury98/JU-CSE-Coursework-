package messages;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.util.Pair;
public class DictionaryResponse extends Response{
	ArrayList<Pair<String,String>> getResponses;
	HashMap<String,String> putResponses;
	public DictionaryResponse(String username,HashMap<String,String> putResponses,ArrayList<Pair<String,String>> getResponses){
		super(username);
		this.putResponses = putResponses;
		this.getResponses = getResponses;
	}
	public HashMap<String,String> putResponses(){
		return this.putResponses;
	}
	public ArrayList<Pair<String,String>> getResponses(){
		return this.getResponses;
	}
}