package messages;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.util.Pair;
public class DictionaryRequest extends Request{
	ArrayList<Pair<String,String>> Requests;
	public DictionaryRequest(String username,ArrayList<Pair<String,String>> Requests){
		super(username);
		this.Requests = Requests;
	}
	public ArrayList<Pair<String,String>> Requests(){
		return Requests;
	}
}