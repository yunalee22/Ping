package client;

import java.awt.CardLayout;
import java.awt.Container;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import calendar.Event;
import chat.ChatMessage;
import loginframe.LoginFrame;
import mainframe.CMEObject;
import mainframe.CalendarGUI;
import mainframe.ChatGUI;
import mainframe.MainFrame;
import mainframe.TabBar;
import notifications.PingNotification;
import ping.Ping;
import server.Request;
import server.Response;
import server.Request.RequestType;
import server.Response.ResponseType;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {

	public static final String ACCOUNT_SID = "AC227b9235bff122f13b2cbf172e4b9b94";
	public static final String AUTH_TOKEN = "c18fa63d33d8ffff3bf3aa880a88ec6d";
	  
	private static Client client;
	private static Socket socket;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;

	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;
	//private static ChatGUI chatGUI;
	
	private static Home home;
	private static User user;
	private String username;
	public TwilioRestClient Tclient;
	
	public Client(String hostname, int port) {
		//text message 
		Tclient = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		
		// Initialize frames
		loginFrame = new LoginFrame(Tclient);
		loginFrame.setVisible(true);

		
		// Open client socket and streams
		try {
			socket = new Socket(hostname, port);
			System.out.println("Connected!");
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			start();
		} catch (IOException ioe) {
			System.out.println("IOE in Client constructor: " + ioe.getMessage());
		}
	}

	public static Client getClient() {
		return client;
	}
	
	public void setUser(User user) {
		Client.user = user;
	}

	public static User getUser() {
		return Client.user;
	}

	public static Home getHome() {
		return Client.home;
	}

	public static CalendarGUI getCalendarGUI(){
		return mainFrame.getCalendarGUI();
	}
	

	
	public static void sendRequest(Request request) {
		try {
			oos.writeObject(request);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("IOE sending request: " + ioe.getMessage());
		}
	}

	private void updateUser(String Username) {
		// Send home update request to server
		Request request = new Request(RequestType.UpdateHome, Username);
		sendRequest(request);

		// Send user update request to server
		Request request1 = new Request(RequestType.UpdateUser, Username);
		sendRequest(request1);
	}
	
	// checks if user is in recipient list
	private Boolean shouldReceivePing(Response r) {
		Ping p = (Ping) r.object;
		if (Client.getUser().getUsername().equals(p.getRecipient())) {
			return true;
		}
		else {
			return false;
		}
	}

	// check if user is in the same chat home
//	private Boolean shouldReceiveChat(Response r) {
//		ChatMessage c = (ChatMessage) r.object;
//		User sender = c.getName();
//		Vector<User> Residents = sender.getHome().getResidents();
//		Boolean shouldReceive = false;
//		for (int i = 0; i < Residents.size(); i++) {
//			if (Residents.get(i).equals(Client.getUser())) {
//				shouldReceive = true;
//			}
//		}
//		return shouldReceive;
//	}

	public void run() {
		while (true) {
			Response response = null;
			try {
				response = (Response) ois.readObject();
				if (response.type == ResponseType.LoginSuccess) {
					JOptionPane.showMessageDialog(null, "Successfully logged in!");
					updateUser((String) response.object);
					loginFrame.setVisible(false);
				}
				else if (response.type == ResponseType.LoginFail) {
					JOptionPane.showMessageDialog(null, "Failed to log in");
				}
				else if (response.type == ResponseType.SignupSuccess) {
					JOptionPane.showMessageDialog(null, "Successfully signed up!");
					updateUser((String) response.object);
					loginFrame.setVisible(false);
				}
				else if (response.type == ResponseType.SignupFail) {
					JOptionPane.showMessageDialog(null, "Failed to sign up");
				}
				else if (response.type.equals(ResponseType.GuestLoginSuccess)){
					// Process the GUEST functionality
//					List<NameValuePair> params = new ArrayList<NameValuePair>();
//				    params.add(new BasicNameValuePair("Body", "GS32gd"));
//				    params.add(new BasicNameValuePair("To", "+16266366010"));
//				    params.add(new BasicNameValuePair("From", "+13235249046"));
//				    
//			
//				    MessageFactory messageFactory = Tclient.getAccount().getMessageFactory();
//				    try {
//				      Message message = messageFactory.create(params);
//				    } catch (TwilioRestException e) {
//				      System.out.println(e.getErrorMessage());
//				    }
					JOptionPane.showMessageDialog(null, "Guest successfully logged in!\n (Limited use...)");
					updateUser((String) response.object);
					//System.out.println((String) response.object);
				}
				else if (response.type == ResponseType.HomeUpdate) {
					home = (Home) response.object;
				}
				else if (response.type == ResponseType.UserUpdate) {
					setUser((User) response.object);
					mainFrame = new MainFrame();
					User newuser = (User) response.object;
					
					mainFrame.getHomepageGUI().update(newuser.getUsername());
					mainFrame.setVisible(true);
					if(user.getUsername().equals("guest1")||user.getUsername().equals("guest2")||user.getUsername().equals("guest3")){
						
						mainFrame.remove(mainFrame.getNavBar());
						mainFrame.remove(mainFrame.getTabBar());
						JPanel cardpan = mainFrame.getCardpanel();
						CardLayout c2 = (CardLayout)cardpan.getLayout();
						c2.show(cardpan, "Chat");
						
						
						//						JFrame guestFrame = new JFrame();
//						guestFrame.setSize(300, 300);
//						System.out.println(mainFrame.getChatGUI());
//						guestFrame.add(mainFrame.getChatGUI());
//						guestFrame.setVisible(true);
//						mainFrame.setVisible(false);
//						CardLayout cl = (CardLayout)mainFrame.getLayout();
//						cl.show(mainFrame.getCardpanel(), "Chat");
					}
						
						
				}
				else if (response.type == ResponseType.UpdateFail) {
					JOptionPane.showMessageDialog(null, "Failed to update client information");
				}
				else if (response.type == ResponseType.PingAddSuccess) {
					if (shouldReceivePing(response)) {
						Ping pn = (Ping)response.object;
						System.out.println(pn.getRecipient() + " " + user.getUsername());
						if(pn.getRecipient().equals(user.getUsername())){ //put the txt msg here 
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("Body", "Ping Received!"));
						    params.add(new BasicNameValuePair("To", "+16266366010"));
						    params.add(new BasicNameValuePair("From", "+13235249046"));

						    MessageFactory messageFactory = Tclient.getAccount().getMessageFactory();
						    try {
						      Message message = messageFactory.create(params);
						    } catch (TwilioRestException e) {
						      System.out.println(e.getErrorMessage());
						    }
						  }
							
							System.out.println("done2");
							user.getNotificationManager().addNotification((Ping) response.object);
							JOptionPane.showMessageDialog(null, "Ping successfully sent!");
						}
				}
				
				else if (response.type == ResponseType.PingAddFail) {
					JOptionPane.showMessageDialog(null, "Ping failed to send");
				}
//				 else if (response.type == ResponseType.ChatNotification) {
//				 // Process the chat
//				 if (shouldReceiveChat(response)) {
//				 user.getChatManager();
//				 }
				else if(response.type == ResponseType.PingDeleteSuccess){
					JOptionPane.showMessageDialog(null, "Ping delete successfully!");
				}
				else if(response.type == ResponseType.PingDeleteSuccess){
					JOptionPane.showMessageDialog(null, "Ping delete failed. Shame on me!");
				}
				else if (response.type == ResponseType.ChatAdd) {
					// Process the chat add
					CMEObject cmeObject = (CMEObject)response.object;
					user.getChatManager().addMessage((ChatMessage)cmeObject.getCMEObject());
					mainFrame.getChatGUI().add((ChatMessage) cmeObject.getCMEObject());
				} else if (response.type == ResponseType.CalendarAdd) {
					System.out.println(user.getUsername()+"receive add event");
					// Process the calendar add
					CMEObject cmeObject = (CMEObject)response.object;
					user.getCalendarManager().addToCalendar((Event) cmeObject.getCMEObject());
					mainFrame.getCalendarGUI().add((Event) cmeObject.getCMEObject());
				} else if (response.type == ResponseType.CalendarRemove) {
					// Process the calendar remove
					CMEObject cmeObject = (CMEObject)response.object;
					user.getCalendarManager().removeFromCalendar((Event) cmeObject.getCMEObject());
					mainFrame.getCalendarGUI().remove((Event)cmeObject.getCMEObject());
				} else if (response.type == ResponseType.NotificationRemove) {
					System.out.println("Notification removed successfully");
				} 
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException reading response: " + e.getMessage());
				e.printStackTrace();
				break;
			} catch (IOException e) {
				System.out.println("IOE reading response: " + e.getMessage());
				e.printStackTrace();
				break;
			}
		}
	}
	


	public static void main(String[] args) {
		client = new Client("localhost", 6788);
	}
}
