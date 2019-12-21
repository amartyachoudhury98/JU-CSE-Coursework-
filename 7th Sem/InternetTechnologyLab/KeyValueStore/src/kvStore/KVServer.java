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
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.util.Pair;
import java.io.*;


class Service extends Thread{
	Socket clientSocket;
	User client;
	ArrayList<User> clientList; 
	ObjectOutputStream os;
	ObjectInputStream is;
	public Service(Socket sc,ObjectOutputStream os,ObjectInputStream is,User user,ArrayList<User> userList){
		this.clientSocket = sc;
		this.client = user;
		this.clientList = userList;
		this.os=os;
		this.is =is;
	}
	private User findUser(String username){
		for(User u:clientList){
			if( username.equals(u.username())){
				return u;
			}
		}
		return null;
	}
	private Response serviceRequest(Request rq){
		Response rs =null;
		if(rq instanceof DictionaryRequest){
			System.out.println("Dictionary Request from " + client.username());
			ArrayList<Pair<String,String>> Reqs = ((DictionaryRequest)rq).Requests();
			HashMap<String,String> putResp = new HashMap<>();
			ArrayList<Pair<String,String>> getResp = new ArrayList<>();
			for(Pair<String,String> pair:Reqs){
				if(pair.getKey().equals("Get")) getResp.add(new Pair<String,String>(pair.getValue(),client.get(pair.getValue())));
				else client.put(pair.getKey(),pair.getValue());
			}
			rs = new DictionaryResponse(client.username(),putResp,getResp);
		}
		else if(rq instanceof AuthorisationRequest){
			System.out.println("Authorisation Request from " + client.username());
			if( client.isManager()){
				rs = new AuthorisationResponse(client.username(),true);
			}
			else{
				rs = new AuthorisationResponse(client.username(),false);
				client.grantManagerAuth();
			}
		}
		else if(rq instanceof UserDetailsRequest){
			System.out.println("Request from " + client.username() + " for " + ((UserDetailsRequest)rq).username() + " details");
			if(client.isManager()){
				String guestName = ((UserDetailsRequest)rq).username();
				User guest = findUser(guestName);
				if(guest == null) rs = new UserNotFoundResponse(client.username());
				else rs = new UserDetailsResponse(client.username(),guestName,guest.getInfo());
			}
			else{
				rs = new UnauthorisedUserResponse(client.username());
			}
		}
		return rs;
	}

	public void run(){
		for(;;){
			try{
			Request rq = (Request)is.readObject();
			if(rq instanceof CloseConnectionRequest){
				clientSocket.close();
				System.out.println("Closing Session with " + client.username());
				break;
			}
			Response rs = serviceRequest(rq);
			os.writeObject(rs);
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			System.out.println("------------------------------------------------------");
		}
	}
}
public class KVServer{
	public final int PORT_NO = 8000;
	public ServerSocket serversocket;
	ArrayList<User> kvUsers;
	private boolean userExists(User user){
		for(User u : kvUsers){
			if(u.username().equals(user.username())) return true;
		}
		return false;
	}
	private User searchUser(String name){
		User user=null;
		for(User u: kvUsers){
			if(u.username().equals(name)){
				user = u;
				break;
			}
		}
		return user;
	}
	public KVServer(){
		this.kvUsers = new ArrayList<User>();
	}	
	public void startServer(){
		try{
			serversocket = new ServerSocket(PORT_NO);
			System.out.println("Server started , listening on port  8000 for incoming connections");
			System.out.println("-----------------------------------------------------------------");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		while(true){
			try{
			System.out.println("Waiting for new connection ..");
			Socket clientSocket = serversocket.accept();
			ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
			Request rq = (Request)is.readObject();
			String username = rq.getSender();
			User existingUser = searchUser(username);
			Response rs = null;
			if( existingUser == null){
				rs = new AcceptResponse(username,false);
				os.writeObject(rs);
				User newUser = new User(username);
				kvUsers.add(newUser);
				new  Service(clientSocket,os,is,newUser,kvUsers).start();
				System.out.println("Connected to new client  " + username);
				}
			else{
				rs = new AcceptResponse(username,true);
				os.writeObject(rs);
				System.out.println("Connected to existing client  " + username);
				new  Service(clientSocket,os,is,existingUser,kvUsers).start();
				}
			System.out.println("------------------------------------------------------");
			}
			catch(IOException e){ 
				e.printStackTrace();
			}
			catch(ClassNotFoundException e){ 
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		KVServer kvServer = new KVServer();
		kvServer.startServer();
	}
}