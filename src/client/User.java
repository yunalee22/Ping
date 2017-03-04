package client;

import java.awt.Image;
import java.io.Serializable;
import java.util.Vector;

import chat.ChatManager;
import calendar.CalendarManager;
import notifications.NotificationManager;

import notifications.Notification;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String username;
	private Home home;
	
	private NotificationManager notificationManager;
	
	private ChatManager chatManager;
	private CalendarManager calendarManager;
	
	public User(String name, String username, NotificationManager notificationManager, Home home) {
		this.name = name;
		this.username = username;
		this.notificationManager = notificationManager;
		this.home = home;
		this.chatManager = home.getChatManager();
		this.calendarManager = home.getCalendarManager();
	}
	
	public void setHome(Home home) {
		this.home = home;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Home getHome() {
		return home;
	}
	
	public ChatManager getChatManager() {
		return chatManager;
	}
	
	public CalendarManager getCalendarManager() {
		return calendarManager;
	}
	
	public NotificationManager getNotificationManager() {
		return notificationManager;
	}
	
}
