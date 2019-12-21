/*
*
*@author Amartya Choudhury amartyachowdhury98@gmail.com 
*
*/
package kvStore;
import messages.*;
import user.User;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.*;
import javafx.util.Pair;
import java.util.NoSuchElementException;

class InvalidInputException extends Exception{
	public InvalidInputException(String message){
		super(message);
	}
}
class UsernameExistsException extends Exception{
	public UsernameExistsException(String message){
		super(message);
	}
}
public class KVClient{
	
	String clientUsername;
	String serverInetAddress;
	int serverPort;
	Socket sc;
	ObjectOutputStream os;
	ObjectInputStream is;

	//Create a new connection to the kvstore Server and request authorisation , print response 
	public KVClient(String serverInetAddress,int serverPort,String clientUsername) throws UsernameExistsException{
		this.serverInetAddress = serverInetAddress;
		this.serverPort = serverPort;
		this.clientUsername = clientUsername;
		try{
			this.sc = new Socket(serverInetAddress,serverPort);
			this.os = new ObjectOutputStream(sc.getOutputStream());
			this.is = new ObjectInputStream(sc.getInputStream());
				Request rq= new AcceptRequest(clientUsername);
				os.writeObject(rq);
				Response rs = (Response)is.readObject();
				String responseMessage = ((AcceptResponse)rs).message(); 
			 	System.out.println(responseMessage);
			}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}	
		return ;
	}

	//parse the input into the  corresponding Request type
	public Request parse(String input) throws InvalidInputException,NoSuchElementException{
		Request rq = null;
		if(input.equals("")) return rq;
		StringTokenizer st = new StringTokenizer(input," ");
		String startsWith = st.nextToken();

		//if it is a get , put input , then it is a "Dictionary Request" 
		if(startsWith.equals("Get") || startsWith.equals("Put")){
			ArrayList<Pair<String,String>> Req = new ArrayList<>();
			String key = st.nextToken();
			if(startsWith.equals("Get")){
				Req.add(new Pair<String,String>("Get",key));
			}
			else{
				String val = st.nextToken();
				Req.add(new Pair<String,String>(key,val));
			}
			while(st.hasMoreTokens()){
				String type = st.nextToken();
				if(type.equals("Get")){
					key = st.nextToken();
					Req.add(new Pair<String,String>("Get",key));
				}
				else if(type.equals("Put")){
					key = st.nextToken(); 
					String val = st.nextToken();
					Req.add(new Pair<String,String>(key,val));
				}
				else throw new InvalidInputException("Invalid input");
			}
			rq = new DictionaryRequest(clientUsername,Req);
		}

		//Promote to Manager request
		else if(startsWith.equals("Request")){
			if(st.nextToken().equals("Authorisation")) rq = new AuthorisationRequest(clientUsername);
			else throw new InvalidInputException("Invalid input");
		}

		// View details of another client
		else if(startsWith.equals("Show")){
			if(st.nextToken().equals("Details")) {
				String guest = st.nextToken();
				rq = new UserDetailsRequest(clientUsername,guest);
			}
			else throw new InvalidInputException("Invalid input");
		}
		else throw new InvalidInputException("Invalid Input");
		return rq;
	}

	//Print server Response
	public void print(Response rs){
		
		if(rs instanceof AcceptResponse){
			System.out.println(((AcceptResponse)rs).message());
		}
		
		else if(rs instanceof DictionaryResponse){
			HashMap<String,String> putResponses = ((DictionaryResponse)rs).putResponses();
			ArrayList<Pair<String,String>> getResponses = ((DictionaryResponse)rs).getResponses();
			if(!getResponses.isEmpty()) System.out.println("Response: " + getResponses);
		}
		
		else if(rs instanceof AuthorisationResponse){
			System.out.println(((AuthorisationResponse)rs).message());
		}
		
		else if(rs instanceof UserDetailsResponse){
			HashMap<String,String> info = ((UserDetailsResponse)rs).getinfo();
			String guestname = ((UserDetailsResponse)rs).getGuestname();
			System.out.println(guestname + " : " +info);
		}
		else if(rs instanceof UnauthorisedUserResponse){
			System.out.println(((UnauthorisedUserResponse)rs).message());
		}
		else if(rs instanceof UserNotFoundResponse){
			System.out.println(((UserNotFoundResponse)rs).message());
		}
	}

	//send request to server 
	public void send(Request rq){
		try{
			os.writeObject(rq);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return;
	}

	//recieve response from server
	public Response recieve(){
		Response rs= null;
		try{ 
			rs=(Response)is.readObject();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return rs;
	} 

	//close connection with server
	public void closeConnection(){
		try{
		Request rq = new CloseConnectionRequest(clientUsername);
		os.writeObject(rq);
		sc.close();
		}
		catch(IOException e){e.printStackTrace();}
		return;
	}


	public static void main(String args[]){
		//fetch server credentials
		String serverInetAddress = args[0];
		int serverPort =Integer.parseInt(args[1]);
		//fetch client username
		String clientUsername = args[2];
		
		KVClient client =null;
		
		//create new client
		try{
			client = new KVClient(serverInetAddress,serverPort,clientUsername);
		}
		catch(UsernameExistsException e){ 
			e.printStackTrace();
			return ;
			}		
		Scanner sc = new Scanner(System.in);
		//until connection closed
		for(;;){
			String input = sc.nextLine();
			if(input.equals("Exit")){
				client.closeConnection();
				break;
			}
			//parse input
			Request rq = null;
			try{
				rq = client.parse(input);
				
			}
			catch(InvalidInputException e){
				e.printStackTrace();
			}
			catch(NoSuchElementException e){
				e.printStackTrace();
			}
			//send request
			client.send(rq);
			//print response
			client.print(client.recieve());
			System.out.println("------------------------------------------------------");

		}
	}
}


