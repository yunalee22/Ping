package calendar;

import java.io.Serializable;
import java.util.Vector;

public class CalendarManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//private Client client;
	private Vector<Event> events;
	
	public CalendarManager(Vector<Event> events) {
		this.events = events;
	}

	public void addToCalendar(Event e) {
		events.add(e);
	}
	
	public void removeFromCalendar(Event e){
		events.remove(e);
	}
	
	public Vector<Event> getEvents(){
		return events;
	}
}
