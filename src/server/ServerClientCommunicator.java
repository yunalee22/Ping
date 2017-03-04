package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import calendar.Event;
import chat.ChatMessage;
import client.Client;
import database.Database;
import mainframe.CMEObject;
import notifications.PingNotification;
import ping.Ping;
import server.Response;
import server.Response.ResponseType;
import server.Request;
import server.Request.RequestType;

public class ServerClientCommunicator extends Thread {

	private Socket socket;
	private Server server;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ServerClientCommunicator(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.start();
	}

	public void closeSocket() {
		try {
			socket.close();
		} catch (IOException ioe) {
			System.out.println("IOE closing socket: " + ioe.getMessage());
		}
	}

	public void sendResponse(Response response) {
		try {
			oos.writeObject(response);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("IOE sending response: " + ioe.getMessage());
		}
	}

	public void run() {
		try {
			while (true) {
				Request request = (Request) ois.readObject();
				if (request.type == RequestType.Signup) {
					System.out.println("Server processing signup request");
					Vector<String> args = (Vector<String>)request.object;
					String name = args.get(0);
					String username = args.get(1);
					String password = args.get(2);
					String homename = args.get(3);
					Response r = Database.getDatabase().signup(name, username, password, homename);
					sendResponse(r);
				}
				else if (request.type == RequestType.Login) {
					System.out.println("Server processing login request");
					Vector<String> args = (Vector<String>)request.object;
					String username = args.get(0);
					String password = args.get(1);
					Response r = Database.getDatabase().login(username, password);
					sendResponse(r);
				}
				else if (request.type.equals(RequestType.GuestRequest)){
					// For guest 
					System.out.println("Guest request"); // test
					String guestName = (String) request.object;
					Response r = Database.getDatabase().getGuestUser(guestName);
					// If guest login succeeds, then return "ResponseType.GuestLoginSuccess, null"
					// If fail, then return "ResponseType.LoginFail, null"
					sendResponse(r);
				}
				else if (request.type == RequestType.Ping) {
					System.out.println("Server processing ping request");
					Ping ping = (Ping)request.object;
					Response r = Database.getDatabase().sendPingNotification(ping);
					server.sendToAllClients(this, r);
				}
				else if (request.type == RequestType.Chat) {
					CMEObject cmeObject = (CMEObject) request.object;
					ChatMessage chatMessage = (ChatMessage) cmeObject.getCMEObject();
					String senderUsername = cmeObject.getSenderName();
					Response r = Database.getDatabase().sendChatMessage(senderUsername, chatMessage);
					sendResponse(r);
					// Send event add to client
					Response r2 = new Response(ResponseType.ChatAdd, request.object);
					server.sendToAllClients(this, r2);
				}
				else if (request.type == RequestType.CalendarAdd) {
					// Add event to database

					System.out.println("Server received request to add Event to calendar");
					CMEObject cmeObject = (CMEObject)request.object;
					Event event = (Event)cmeObject.getCMEObject();
					String senderUsername = cmeObject.getSenderName();
					Response r = Database.getDatabase().addCalendarEvent(senderUsername,event);
					sendResponse(r);
					// Send event add to client
					Response r2 = new Response(ResponseType.CalendarAdd, request.object);
					server.sendToAllClients(this, r2);
				}
				else if (request.type == RequestType.CalendarRemove) {
					// Remove event from database

					System.out.println("Server received request to remove Event to calendar");
					CMEObject cmeObject = (CMEObject)request.object;
					Event event = (Event)cmeObject.getCMEObject();
					String senderUsername = cmeObject.getSenderName();
					Response r = Database.getDatabase().deleteCalendarEvent(senderUsername,event);
					sendResponse(r);
					// Send event removal to client
					Response r2 = new Response(ResponseType.CalendarRemove, request.object);
					server.sendToAllClients(this, r2);
				}
				else if (request.type == RequestType.PingNotification){
					System.out.println("Server received request to remove Notification");
					Ping pingNotification = (Ping)request.object;
					Response r = Database.getDatabase().RemovePingNotification(pingNotification);
					sendResponse(r);
					// Send event removal to client
					Response r2 = new Response(ResponseType.NotificationRemove, request.object);
					server.sendToAllClients(this, r2);
				}
				else if (request.type == RequestType.UpdateHome) {
					System.out.println("Server processing update home request");
					String username = (String)request.object;
//					System.out.println("!!!!  " + username);
					Response r = Database.getDatabase().updateHome(username);
					sendResponse(r);
				}
				else if (request.type == RequestType.UpdateUser) {
					System.out.println("Server processing update user request");
					String username = (String)request.object;
					Response r = Database.getDatabase().updateUser(username);
					sendResponse(r);
				} 
			}
		} catch (IOException ioe) {
			server.removeServerClientCommunicator(this);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("CNFE: " + cnfe.getMessage());
		}
	}
}
