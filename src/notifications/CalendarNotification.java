package notifications;

import java.awt.Image;

import javax.swing.ImageIcon;

import calendar.Event;
import client.User;

public class CalendarNotification implements Notification{
		private static User mSender;
		private static User mRecipient;
		private static Event mEvent;
		private String message;
		private Image icon;
		
		public CalendarNotification(User sender, User recipient, Event inEvent){
			mSender = sender;
			mRecipient = recipient;
			mEvent = inEvent;
			message = mEvent.getDescription();
			//icon = mEvent.getIcon();
		}
		
		@Override
		public String getMessage(){
			return message;
		}
		
		@Override
		public Image  getIcon(){
			return icon;
		}
		
}
