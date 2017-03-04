package mainframe;

import java.io.Serializable;

public class CMEObject implements Serializable{
	private static final long serialVersionUID = 201;
	private String senderUsername;
	private Object CMEObject;
	public CMEObject(String username, Object object){
		senderUsername = username;
		CMEObject = object;
	}
	
	public String getSenderName(){
		return senderUsername;
	}
	public Object getCMEObject(){
		return CMEObject;
	}
}
