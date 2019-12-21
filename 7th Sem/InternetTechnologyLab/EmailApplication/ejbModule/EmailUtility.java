import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.UIDFolder.FetchProfileItem;
import javax.mail.internet.*;
import java.util.Properties;
import com.sun.mail.pop3.POP3Store; 
import com.sun.mail.imap.*;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import com.sun.mail.dsn.*;

public class EmailUtility {
	private static EmailUtility instance;
	private IMAPStore emailStore;
	private Folder inboxFolder,sentFolder;
	private EmailUtility() {
		
	}
	public static EmailUtility getInstance() {
		if(instance == null) instance = new EmailUtility();
		return instance;
	}
	public void send(String from,String password,String to,String sub,String msg) {
		Properties props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.port","465");
		
		Session session = Session.getInstance(props,    
		           new javax.mail.Authenticator() {    
		           protected PasswordAuthentication getPasswordAuthentication() {    
		           return new PasswordAuthentication(from,password);  
		           }    
		          });
		
		try {    
	           MimeMessage message = new MimeMessage(session);    
	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	           message.setSubject(sub);    
	           message.setText(msg);
	           message.setHeader("Disposition-Notification-To",from);
	           //send message
	           Transport.send(message);    
	           System.out.println("message sent successfully");    
	          } 
		catch (MessagingException e) {
			throw new RuntimeException(e);
			}    
	    catch(Exception e) {
	    	
	    	throw new RuntimeException(e);
	    }
        return;
	}
	public Message[] recieve(String user,String password,int start,int end) {
		 	Message[] messages = null;
		 	try {  
			   Properties properties = new Properties();  
			   properties.put("mail.imap.host", "imap.gmail.com");  
			   properties.put("mail.imap.ssl.enable", "true");
			   properties.put("mail.imap.port", "993");
			   Session emailSession = Session.getInstance(properties,    
			           new javax.mail.Authenticator() {    
			           protected PasswordAuthentication getPasswordAuthentication() {    
			           return new PasswordAuthentication(user,password);  
			           }    
			          });  
			     
			   emailStore = (IMAPStore) emailSession.getStore("imap");  
			   emailStore.connect(user,password);
			   inboxFolder = emailStore.getFolder("INBOX");  
			   inboxFolder.open(Folder.READ_ONLY);  
			   System.out.println(inboxFolder.getMessageCount());
			   messages = inboxFolder.getMessages(inboxFolder.getMessageCount() -end + 1,inboxFolder.getMessageCount() - start + 1);  
			   FetchProfile profile = new FetchProfile();
			   profile.add(FetchProfileItem.ENVELOPE);
			   profile.add(FetchProfileItem.FLAGS);
			   profile.add(FetchProfileItem.CONTENT_INFO);
			   profile.add("X-mailer");
			   inboxFolder.fetch(messages, profile);
			   System.out.println("fetching done");
			   inboxFolder.close(false);  
			   //emailStore.close();  
			  
			  } 
		 	  catch (NoSuchProviderException e) {e.printStackTrace();}   
			  catch (MessagingException e) {e.printStackTrace();}  
			  catch (Exception e) {e.printStackTrace();} 
		  
			return messages;
	}
	public String getCompleteMessage(Message msg,String from,String password) {
		String email = "";
		try {
			email += "---------------------------------------------------------------------------------------------------------------------------------------------------\n";
			email += "From :" + msg.getFrom()[0]+"\n";
			email += "To :" + msg.getRecipients(Message.RecipientType.TO)[0] +"\n";
			email += "Date :" +  msg.getSentDate().toString() + "\n";
			email += "Subject :" + msg.getSubject() + "\n";
			email += "---------------------------------------------------------------------------------------------------------------------------------------------------\n";
			
			inboxFolder.open(Folder.READ_WRITE);
			System.out.println(msg.getContentType());
			if (msg.isMimeType("text/plain")){
				email += msg.getContent().toString();
				if(msg.getHeader("Disposition-Notification-To") != null) {
		        	System.out.println(msg.getHeader("Disposition-Notification-To")[0]);
		        	Properties props = new Properties();
		    		props.put("mail.smtp.host","smtp.gmail.com");
		    		props.put("mail.smtp.auth","true");
		    		props.put("mail.smtp.socketFactory.port", "465");    
		            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); 
		    		props.put("mail.smtp.port","465");
		    		
		    		Session session = Session.getInstance(props,    
		    		           new javax.mail.Authenticator() {    
		    		           protected PasswordAuthentication getPasswordAuthentication() {    
		    		           return new PasswordAuthentication(from,password);  
		    		           }    
		    		          });
		    		

		        	DispositionNotification dn = new DispositionNotification();
		        	MultipartReport mr = new MultipartReport("---------------Received your mail :" +msg.getSubject() + "--------------",dn);
		        	
		        	try {    
		 	           MimeMessage message = new MimeMessage(session);    
		 	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(msg.getHeader("Disposition-Notification-To")[0]));        
		 	           message.setContent(mr);
		 	           message.setSubject("DISPOSITION-NOTIFICATION");
		 	           Transport.send(message);    
		 	           System.out.println("mdn sent successfully");    
		 	          } 
		 		catch (MessagingException e) {
		 			throw new RuntimeException(e);
		 			}    
		 	    catch(Exception e) {
		 	    	
		 	    	throw new RuntimeException(e);
		 	    }
		        }
		    }
			else if (msg.isMimeType("multipart/*")) {
		        String result = "";
		        if(msg.isMimeType("multipart/report")) {
		        	MultipartReport report = (MultipartReport) msg.getContent();
		        	int count = report.getCount();
		        	for (int i = 0; i < count; i ++){
		        		BodyPart bodyPart = report.getBodyPart(i);
		        		if (bodyPart.isMimeType("text/plain")){
		        			result = result + "\n" + bodyPart.getContent().toString();
		        			break;  
		        		} 
		     
		        	}
		        	email += result;
		        }	
		        else {	
		        	MimeMultipart mimeMultipart = (MimeMultipart)msg.getContent();
		        	int count = mimeMultipart.getCount();
		        	for (int i = 0; i < count; i ++){
		        		BodyPart bodyPart = mimeMultipart.getBodyPart(i);
		        		if (bodyPart.isMimeType("text/plain")){
		        			result = result + "\n" + bodyPart.getContent().toString();
		        			break;  
		        		} 
		     
		        	}
		        	email += result;
		        if(msg.getHeader("Disposition-Notification-To") != null) {
		        	System.out.println(msg.getHeader("Disposition-Notification-To")[0]);
		        	Properties props = new Properties();
		    		props.put("mail.smtp.host","smtp.gmail.com");
		    		props.put("mail.smtp.auth","true");
		    		props.put("mail.smtp.socketFactory.port", "465");    
		            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); 
		    		props.put("mail.smtp.port","465");
		    		
		    		Session session = Session.getInstance(props,    
		    		           new javax.mail.Authenticator() {    
		    		           protected PasswordAuthentication getPasswordAuthentication() {    
		    		           return new PasswordAuthentication(from,password);  
		    		           }    
		    		          });
		    		

		        	DispositionNotification dn = new DispositionNotification();
		        	MultipartReport mr = new MultipartReport("---------------Received your mail :" +msg.getSubject() + "--------------",dn);
		        	
		        	try {    
		 	           MimeMessage message = new MimeMessage(session);    
		 	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(msg.getHeader("Disposition-Notification-To")[0]));        
		 	           message.setContent(mr);
		 	           message.setSubject("DISPOSITION-NOTIFICATION");
		 	           Transport.send(message);    
		 	           System.out.println("mdn sent successfully");    
		 	          } 
		 		catch (MessagingException e) {
		 			throw new RuntimeException(e);
		 			}    
		 	    catch(Exception e) {
		 	    	
		 	    	throw new RuntimeException(e);
		 	    }
		        }
		        }
		    }			
			inboxFolder.close(false);
		} 
		catch (MessagingException e) {e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace();}
		return email;
	}
	
	public String getCompleteSentMessage(Message msg) {
		String email = "";
		try {
			email += "---------------------------------------------------------------------------------------------------------------------------------------------------\n";
			email += "From :" + msg.getFrom()[0]+"\n";
			email += "To :" + msg.getRecipients(Message.RecipientType.TO)[0] +"\n";
			email += "Date :" +  msg.getSentDate().toString() + "\n";
			email += "Subject :" + msg.getSubject() + "\n";
			email += "---------------------------------------------------------------------------------------------------------------------------------------------------\n";
			
			sentFolder.open(Folder.READ_ONLY);
			System.out.println(msg.getContentType());
			if (msg.isMimeType("text/plain")){
				email += msg.getContent().toString();
		    }
			else if (msg.isMimeType("multipart/*")) {
		        String result = "";
		        MimeMultipart mimeMultipart = (MimeMultipart)msg.getContent();
		        int count = mimeMultipart.getCount();
		        for (int i = 0; i < count; i ++){
		            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
		            if (bodyPart.isMimeType("text/plain")){
		                result = result + "\n" + bodyPart.getContent().toString();
		                break;  
		            } 
		     
		        }
		        email += result;
		    }			
			sentFolder.close(false);
		} 
		catch (MessagingException e) {e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace();}
		return email;
	}
	
	public Message[] prefetchSent(String user,String password,int start,int end) {
		Message[] sent = null;
		try {  
			   Properties properties = new Properties();  
			   properties.put("mail.imap.host", "imap.gmail.com");  
			   properties.put("mail.imap.ssl.enable", "true");
			   Session emailSession = Session.getInstance(properties,    
			           new javax.mail.Authenticator() {    
			           protected PasswordAuthentication getPasswordAuthentication() {    
			           return new PasswordAuthentication(user,password);  
			           }    
			          });  
			     
			   emailStore = (IMAPStore) emailSession.getStore("imap");  
			   emailStore.connect(user, password);  
			   sentFolder = emailStore.getFolder("[Gmail]").getFolder("Sent Mail");  
			   sentFolder.open(Folder.READ_ONLY);  
			   System.out.println(sentFolder.getMessageCount());
			   sent = sentFolder.getMessages(sentFolder.getMessageCount() -end + 1,sentFolder.getMessageCount() - start + 1);  
			   FetchProfile profile = new FetchProfile();
			   profile.add(FetchProfileItem.ENVELOPE);
			   profile.add(FetchProfileItem.FLAGS);
			   profile.add(FetchProfileItem.CONTENT_INFO);
			   profile.add("X-mailer");
			   sentFolder.fetch(sent, profile);
			   System.out.println("fetching done");
			   sentFolder.close(false);   
			  
			  } 
		 	  catch (NoSuchProviderException e) {e.printStackTrace();}   
			  catch (MessagingException e) {e.printStackTrace();}  
			  catch (Exception e) {e.printStackTrace();} 
		return sent;
	}
}
