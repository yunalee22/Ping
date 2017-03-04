package server;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum ResponseType {
		LoginSuccess,
		LoginFail,
		SignupSuccess,
		SignupFail,
		HomeUpdate,
		UserUpdate,
		UpdateFail,
		CalendarAddSuccess,
		CalendarAddFail,
		CalendarDeleteSuccess,
		CalendarDeleteFail,
		PingAddSuccess,
		PingAddFail,
		SendChatSuccess,
		SendChatFail,
		ChatAdd,
		CalendarAdd,
		CalendarRemove,
		NotificationRemove,
		PingDeleteSuccess,
		PingDeleteFail,
		
		// For guest
		GuestLoginSuccess,
	}
	
	public ResponseType type;
	public Object object;
	
	public Response(ResponseType type, Object object) {
		this.type = type;
		this.object = object;
	}
	
}