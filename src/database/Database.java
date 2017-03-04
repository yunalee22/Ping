package database;

import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import calendar.CalendarManager;
import calendar.Event;
import chat.ChatManager;
import chat.ChatMessage;
import client.Home;
import client.User;
import notifications.NotificationManager;
import notifications.PingNotification;
import ping.Ping;
import server.Response;
import server.Response.ResponseType;

public class Database {

	private static Database database;
	private Connection connection;

	static {
		database = new Database();
	}

	public Database() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Ping?user=root&password=J0k3r765&useSSL=false");

		} catch (SQLException e) {
			System.out.println("SQLException getting connection: " + e.getMessage());
		}
	}

	public static Database getDatabase() {
		return database;
	}

	public Response signup(String name, String username, String password, String homename) {
		try {
			// Check for conflicting user
			PreparedStatement ps = connection.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Response(ResponseType.SignupFail, null);
			}
			
			// Check for the existence of home
			PreparedStatement ps2 = connection
					.prepareStatement("SELECT Homes.HomeID FROM Homes WHERE Homes.Homename=?");
			ps2.setString(1, homename);
			ResultSet rs1 = ps2.executeQuery();
			int homeID;
			if (rs1.next()) {
				homeID = rs1.getInt("HomeID");
			} else {
				return new Response(ResponseType.SignupFail, null);
			}
			
			// Register the new user
			PreparedStatement ps1 = connection
					.prepareStatement("INSERT INTO Users(name, username, password_, homeID) VALUES(?,?,?,?)");
			ps1.setString(1, name);
			ps1.setString(2, username);
			ps1.setString(3, password);
			ps1.setInt(4, homeID);
			ps1.executeUpdate();
			return new Response(ResponseType.SignupSuccess, username);
			
		} catch (SQLException e) {
			System.out.println("SQLException in signup: " + e.getMessage());
			return new Response(ResponseType.SignupFail, null);
		}
	}

	public Response login(String username, String password) {
		try {
			// Check for existing user
			PreparedStatement ps = connection
					.prepareStatement("SELECT Users.password_ FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && password.equals(rs.getString("password_"))) {
				return new Response(ResponseType.LoginSuccess, username);
			} else {
				System.out.println("Failed to find user");
				return new Response(ResponseType.LoginFail, null);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in login: " + e.getMessage());
			return new Response(ResponseType.LoginFail, null);
		}
	}
	
	public Response getGuestUser(String guestName){
		
			// Get the guest user ( name: Guest; username: guest; password_: guest; userID: 1 ).
			// There is a guest user in every Home and its userID is always 1.
		try{
			String password = "guest";
			PreparedStatement ps = connection
					.prepareStatement("SELECT Users.password_ FROM Users WHERE Users.username=?");
			ps.setString(1, guestName);
			ResultSet rs = ps.executeQuery();
			if ( rs.next() && rs.getString("password_").equals(password) ) {
				return new Response(ResponseType.GuestLoginSuccess, guestName);
			} else {
				System.out.println("Failed to find user");
				return new Response(ResponseType.LoginFail, null);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in login: " + e.getMessage());
			return new Response(ResponseType.LoginFail, null);
		}
	}

	public Response addCalendarEvent(String username, Event e) {
		try {
			// Get home ID
			PreparedStatement ps = connection.prepareStatement("SELECT Users.homeID FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			int homeID;
			if (rs.next()) {
				homeID = rs.getInt("homeID");
			} else {
				System.out.println("Failed to retrieve home ID.");
				return new Response(ResponseType.CalendarAddFail, null);
			}
			// Add event to database
			PreparedStatement ps1 = connection
					.prepareStatement("INSERT INTO Events_(date_, description, homeID) VALUES(?,?,?)");
			ps1.setString(1, e.getDate());
			ps1.setString(2, e.getDescription());
			ps1.setInt(3, homeID);
			ps1.executeUpdate();
			return new Response(ResponseType.CalendarAddSuccess, null);
		} catch (SQLException sqle) {
			System.out.println("SQLException in adding calendar event: " + sqle.getMessage());
			return new Response(ResponseType.CalendarAddFail, null);
		}
	}

	public Response deleteCalendarEvent(String username, Event e) {
		try {
			// Get home ID
			PreparedStatement ps = connection.prepareStatement("SELECT Users.homeID FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			int homeID;
			if (rs.next()) {
				homeID = rs.getInt("homeID");
			} else {
				System.out.println("Failed to retrieve home ID.");
				return new Response(ResponseType.CalendarDeleteFail, null);
			}
			// Add event to database
			PreparedStatement ps1 = connection
					.prepareStatement("DELETE FROM Events_ WHERE date_=? AND  description=? AND homeID=? LIMIT 1");
			ps1.setString(1, e.getDate());
			ps1.setString(2, e.getDescription());
			ps1.setInt(3, homeID);
			ps1.executeUpdate();
			return new Response(ResponseType.CalendarDeleteSuccess, null);
		} catch (SQLException sqle) {
			System.out.println("SQLException in deleting calendar event: " + sqle.getMessage());
			return new Response(ResponseType.CalendarDeleteFail, null);
		}
	}

	public Response sendChatMessage(String username, ChatMessage message) {
		try {
			// Get home ID
			PreparedStatement ps = connection.prepareStatement("SELECT Users.homeID FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			int homeID;
			if (rs.next()) {
				homeID = rs.getInt("homeID");
			} else {
				System.out.println("Failed to retrieve home ID.");
				return new Response(ResponseType.SendChatFail, null);
			}

			// Get user ID
			PreparedStatement ps1 = connection
					.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps1.setString(1, username);
			ResultSet rs1 = ps1.executeQuery();
			int userID;
			if (rs1.next()) {
				userID = rs1.getInt("userID");
			} else {
				System.out.println("Failed to retrieve user ID.");
				return new Response(ResponseType.SendChatFail, null);
			}
			// Add chatMessage to database
			PreparedStatement ps2 = connection
					.prepareStatement("INSERT INTO Chats(message, senderID, homeID) VALUES(?,?,?) ");
			ps2.setString(1, message.getMessage());
			ps2.setInt(2, userID);
			ps2.setInt(3, homeID);
			ps2.executeUpdate();
			return new Response(ResponseType.SendChatSuccess, null);
		} catch (SQLException sqle) {
			System.out.println("SQLException in adding chat Message: " + sqle.getMessage());
			return new Response(ResponseType.SendChatFail, null);
		}
	}

	public Response updateHome(String username) {
		Home home = getHome(username);
		if (home == null) {
			return new Response(ResponseType.UpdateFail, null);
		} else {
			return new Response(ResponseType.HomeUpdate, home);
		}
	}

	public Response updateUser(String username) {
		User user;
		String name;
		NotificationManager notificationManager;
		Home home = getHome(username);
		if (home == null) {
			return new Response(ResponseType.UpdateFail, null);
		} else {

			try {
				// Get name
				PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Users WHERE Users.username=?");
				ps1.setString(1, username);
				ResultSet rs1 = ps1.executeQuery();
				int userID;
				if (rs1.next()) {
					userID = rs1.getInt("userID");
					name = rs1.getString("name");
				} else {
					return new Response(ResponseType.UpdateFail, null);
				}

				// Create notification manager
				Vector<Ping> arr = new Vector<Ping>();
				PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Pings WHERE Pings.recipientID=?");
				ps2.setInt(1, userID);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					String message = rs2.getString("message");
					int test = rs2.getInt("senderID");
					System.out.println(test);
					String senderID = getUserName(test);
					String recipientID = getUserName(rs2.getInt("recipientID"));
//					System.out.println(sender + " " + recipient + " " + message);
					Ping pn = new Ping(message, senderID, recipientID);
					arr.add(pn);
				}
				notificationManager = new NotificationManager(arr);
				user = new User(name, username, notificationManager, home);
				return new Response(ResponseType.UserUpdate, user);

			} catch (SQLException e) {
				System.out.println("SQLE: " + e.getMessage());
				return new Response(ResponseType.UpdateFail, null);
			}
		}
	}

	private String getUserName(int userID) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT Users.username FROM Users WHERE Users.userID=?");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("username");
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("SQLE "+e.getMessage());
			return null;
		}
	}

	private int getHomeID(String username) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT Users.homeID FROM Users WHERE Users.username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			int homeID;
			if (rs.next()) {
				homeID = rs.getInt("HomeID");
				return homeID;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			return -1;
		}
	}

	private Home getHome(String username) {
		Home home = null;
		String homename;
		String address;
		Vector<String> residents;
		ChatManager chatManager;
		CalendarManager calendarManager;

		try {
			// Get homeID
			int homeID = getHomeID(username);
			System.out.println("HOMEID"+homeID);
			// Get homename
			PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Homes WHERE Homes.homeID=?");
			ps1.setInt(1, homeID);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				homename = rs1.getString("homename");
				address = rs1.getString("address");
			} else {
				return null;
			}

			// Get residents
			residents = new Vector<String>();
			PreparedStatement ps2 = connection
					.prepareStatement("SELECT Users.username FROM Users WHERE Users.homeID=?");
			ps2.setInt(1, homeID);
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				residents.add(rs2.getString("username"));
			}
			System.out.println("FIRST RESIDENT"+residents.get(0));
			// Create chat manager
			Vector<ChatMessage> vec = new Vector<ChatMessage>();
			PreparedStatement ps3 = connection.prepareStatement("SELECT * FROM Chats WHERE Chats.homeID=?");
			ps3.setInt(1, homeID);
			ResultSet rs3 = ps3.executeQuery();
			while (rs3.next()) {
				System.out.println(rs3.getInt("senderID"));
				String sender = getUserName(rs3.getInt("senderID"));
				String message = rs3.getString("message");
				ChatMessage chatmessage = new ChatMessage(sender, message);
				vec.add(chatmessage);
			}
			chatManager = new ChatManager(vec);

			// Create calendar manager
			Vector<Event> vec1 = new Vector<Event>();
			PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM Events_ WHERE Events_.homeID=?");
			ps4.setInt(1, homeID);
			ResultSet rs4 = ps4.executeQuery();
			while (rs4.next()) {
				String date = rs4.getString("date_");
				String description = rs4.getString("description");
				Event event = new Event(date, description);
				vec1.add(event);
			}
			calendarManager = new CalendarManager(vec1);

			home = new Home(homename, address, residents, chatManager, calendarManager);
		} catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		return home;
	}
	
	public Response sendPingNotification(Ping ping){
		String Message = ping.getMessage();
//		Image icon = ping.getIcon();
		String sender = ping.getSender();
		String recipient = ping.getRecipient();
		
//		int size = roommatelist.size();
		
		try {
			// Get recipient ID
			PreparedStatement ps1 = connection
					.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps1.setString(1, recipient);
			ResultSet rs1 = ps1.executeQuery();
			int userID;
			if (rs1.next()) {
				userID = rs1.getInt("userID");
			} else {
				return new Response(ResponseType.PingAddFail, null);
			}
			
			//get sender ID
			PreparedStatement ps2 = connection.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps2.setString(1, sender);
			ResultSet rs2 = ps2.executeQuery();
			int SenderID;
			if (rs2.next()) {
				SenderID = rs2.getInt("userID");
			} else {
				return new Response(ResponseType.PingAddFail, null);
			}
		
			//add ping into database
			PreparedStatement ps3 = connection.prepareStatement("INSERT INTO Pings(message, senderID, recipientID) VALUES(?,?,?) ");
			ps3.setString(1, Message);
			ps3.setInt(2, SenderID);
			ps3.setInt(3, userID);
			ps3.executeUpdate();
			
			return new Response(ResponseType.PingAddSuccess, ping);
			
		} catch (SQLException sqle) {
			System.out.println("SQLException in adding ping Message: " + sqle.getMessage());
			return new Response(ResponseType.PingAddFail, null);
		}
		
	}
	
	public Response RemovePingNotification(Ping pingnotification){
		String Message = pingnotification.getMessage();
		Image icon = pingnotification.getIcon();
		String sender = pingnotification.getSender();
		String user = pingnotification.getRecipient();
		System.out.println(sender);
		System.out.println(user);
		try {
			// Get recipient ID
			PreparedStatement ps1 = connection
					.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps1.setString(1, user);
			ResultSet rs1 = ps1.executeQuery();
			int userID = -1;
			if (rs1.next()) {
				userID = rs1.getInt("userID");
			} else {
				System.out.println("Failed to retrieve recipient ID.");
			}
			
			//get sender ID
			PreparedStatement ps2 = connection.prepareStatement("SELECT Users.userID FROM Users WHERE Users.username=?");
			ps2.setString(1, sender);
			ResultSet rs2 = ps2.executeQuery();
			int SenderID = -1;
			if (rs2.next()) {
				SenderID = rs2.getInt("userID");
			} else {
				System.out.println("Failed to retrieve sender ID.");
			}
			
			
			PreparedStatement ps3 = connection
					.prepareStatement("DELETE FROM Pings WHERE message=? AND  senderID=? AND recipientID=? LIMIT 1");
			ps3.setString(1, Message);
			ps3.setInt(2, SenderID);
			ps3.setInt(3, userID);
			ps3.executeUpdate();
			return new Response(ResponseType.PingDeleteSuccess, null);
		} catch (SQLException sqle) {
			System.out.println("SQLException in deleting calendar event: " + sqle.getMessage());
			return new Response(ResponseType.PingDeleteFail, null);
		}
	}
	
	
}

	