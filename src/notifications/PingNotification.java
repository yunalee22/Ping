package notifications;

import java.awt.Image;

import javax.swing.ImageIcon;

import client.User;
import ping.Ping;

public class PingNotification implements Notification{
	private static final long serialVersionUID = 1L;
	private String mMessage;
	private String mSender;
	private String mRecipient;
	
	public PingNotification(String message, String senderUsername, String recipientUsername) {
		mMessage = message;
		mSender = senderUsername;
		mRecipient = recipientUsername;
	}

	@Override
	public String getMessage() {
		return mMessage;
	}

	@Override
	public Image getIcon() {
		return null;
	}
	
	public String getSender(){
		return mSender;
	}
	
	public String getRecipient(){
		return mRecipient;
	}
	
}
