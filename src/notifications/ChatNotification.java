package notifications;

import java.awt.Image;

import javax.swing.ImageIcon;

import calendar.Event;
import chat.ChatMessage;
import client.User;

public class ChatNotification implements Notification {
	private static User mSender;
	private static User mRecipient;
	private static ChatMessage chatMessage;
	private String message;
	private Image icon;
	
	public ChatNotification(User sender, User recipient, ChatMessage CM){
		mSender = sender;
		mRecipient = recipient;
		chatMessage = CM;
		message = chatMessage.getMessage();
		icon = null;	// Hardcode this image file in later
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
	@Override
	public Image getIcon(){
		return icon;
	}
	
}
