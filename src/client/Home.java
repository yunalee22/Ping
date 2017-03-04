package client;

import java.io.Serializable;
import java.util.Vector;

import calendar.CalendarManager;
import chat.ChatManager;

public class Home implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String homename;
	private String address;
	private Vector<String> residents;
	private ChatManager chatManager;
	private CalendarManager calendarManager;
	
	public Home(String homename, String address, Vector<String> residents,
			ChatManager chatManager, CalendarManager calendarManager) {
		this.homename = homename;
		this.address = address;
		this.residents = residents;
		this.chatManager = chatManager;
		this.calendarManager = calendarManager;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Vector<String> getResidents() {
		return residents;
	}
	
	public void addResident(User resident) {
		
		// Fill this in later
		
	}
	
	public ChatManager getChatManager() {
		return chatManager;
	}
	
	public CalendarManager getCalendarManager(){
		return calendarManager;
	}
	
	public String getHomeName(){
		return homename;
	}
}
