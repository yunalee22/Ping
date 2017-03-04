package chat;

import java.io.Serializable;
import java.util.Vector;

public class ChatManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<ChatMessage> messages; 
	
	public ChatManager(Vector<ChatMessage> messages){
		this.messages = messages;
	}
	
	public void addMessage(ChatMessage chatMessage){
		messages.add(chatMessage);
	}

	public Vector<ChatMessage> getChatMessages() {
		return messages;
	}
	
}
