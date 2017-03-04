package ping;

import java.awt.Image;
import java.io.Serializable;

public class Ping implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String message;
	String sender;
	String recipient;

	public Ping(String message, String sender, String recipient) {
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Image getIcon() {
		// return image according to ping type
		
		
		
		return null;
	}
	public String getRecipient(){
		return recipient;
	}
	public String getSender(){
		return sender;
	}
}