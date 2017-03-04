package notifications;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import client.User;
import ping.Ping;

public class NotificationManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static User recipient;
	private Vector<Ping> notificationList;
	
	public NotificationManager(Vector<Ping> notificationList) {
		this.notificationList = notificationList;
	}
	
	public void addNotification(Ping not){
		notificationList.add(not);
	}
	
	public void removeNotification(Ping not){
		Iterator<Ping> iterator = notificationList.iterator();
		while(iterator.hasNext())
		{
		    Ping value = iterator.next();
		    if (not.equals(value))
		    {
		        iterator.remove();
		    }
		}
	}
	
	public int notificationManagerSize(){
		return notificationList.size();
	}
	
	public Ping getNotif(int index){
		return notificationList.get(index);
	}
	
}
