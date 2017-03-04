package server;

import java.io.Serializable;

public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum RequestType {
		Signup,
		Login,
		UpdateHome,
		UpdateUser,
		Updatenm,
		Ping,
		Chat,
		CalendarAdd,
		CalendarRemove,
		PingNotification,
		// For guest
		GuestRequest
	}
	
	public RequestType type;
	public Object object;
	
	public Request(RequestType type, Object object) {
		this.type = type;
		this.object = object;
	}
}
