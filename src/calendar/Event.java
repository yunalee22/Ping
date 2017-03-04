package calendar;

import java.awt.Image;
import java.io.Serializable;

public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String date;
	//private String time;
	private String Description;
	//private Image Icon;
	
	public Event(String date, String Description){
		this.date = date;
		this.Description = Description;
		//this.Icon = photo;
	}
	
	public String getDate(){
		return date;
	}
	
//	public String getTime(){
//		return time;
//	}
	
	public String getDescription(){
		return Description;
	}
	
	//public Image getIcon(){
	//	return Icon;
	//}
}