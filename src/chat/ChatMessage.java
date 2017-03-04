package chat;

import java.io.Serializable;
import java.sql.Timestamp;

import client.User;

public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = 1274321034757227149L;

	private String sender;
	private String message;
	Timestamp timestamp;
	
	{
		timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public ChatMessage(String sender, String message){
		this.sender = sender;
		this.message = message;
	}
	
		// Getter functions
	public String getMessage(){
		return message;
	}
	public long getTime(){
		return timestamp.getTime();
	}
	public String getName(){
		return sender;
	}
}
