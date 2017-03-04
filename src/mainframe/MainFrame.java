package mainframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import calendar.Event;
import chat.ChatManager;
import chat.ChatMessage;
import client.Client;
import loginframe.CreateorjoinGUI;
import loginframe.LoginGUI;
import loginframe.RegistrationGUI;
import notifications.NotificationManager;

public class MainFrame extends JFrame{
	
	private Color bgcolor;
	private NavigationBar mNavBar;
	private TabBar mTabbar;
	private JPanel mCardpanel;
	
	private CalendarGUI calendarGUI;
	private ChatGUI chatGUI;
	private HomepageGUI homepageGUI;
	private NotificationsGUI notificationsGUI;
	private EventDescrip eventGUI;
	private PingsGUI pingGUI;
	public CardLayout cl;
	
	public NavigationBar getNavBar(){
		return mNavBar;
	}
	
	public TabBar getTabBar(){
		return mTabbar;
	}
	public JPanel getCardpanel(){
		return mCardpanel;
	}
	
	public MainFrame(){
		super("Ping");
		setSize(600,800);
		bgcolor = new Color(88,196,198);
		getContentPane().setBackground(bgcolor);
		setLayout(new BorderLayout());
		mCardpanel = new JPanel(new CardLayout());
		cl = (CardLayout)mCardpanel.getLayout();
		
//		//initializing all panels
		homepageGUI = new HomepageGUI();
		setChatGUI();
		setCalendarGUI();
		//notificationsGUI = new NotificationsGUI();
		//eventGUI = new EventDescrip();
		pingGUI = new PingsGUI(mCardpanel, Client.getClient());
		
		//creating NavBar, which also creates the notification pane
		//so we need to also pass in the user and his information
		mNavBar = new NavigationBar(mCardpanel, Client.getUser());
		this.add(mNavBar, BorderLayout.NORTH);
		
		//creating middle sections
		mCardpanel.setPreferredSize(new Dimension(mCardpanel.getWidth(), 100));
		
		//home page gui
		mCardpanel.add(homepageGUI, "Homepage");
		
		//ping gui
		mCardpanel.add(pingGUI, "Ping");
		
		//chat gui
		mCardpanel.add(chatGUI, "Chat");
		
		//calendar gui
		mCardpanel.add(calendarGUI, "Calendar");
		
		//event gui
//		eventGUI = new EventDescrip();
//		mCardpanel.add(eventGUI, "EventGUI");
		
		cl.show(mCardpanel, "Homepage");
		
//		CardLayout cl = (CardLayout)mCardpanel.getLayout();
//		cl.show(mCardpanel, "CalendarGUI");
		
//		eventGUI = new EventDescrip();
//		mCardpanel.add("addevent", eventGUI);
//		CardLayout c2 = (CardLayout)mCardpanel.getLayout();
//		c2.show(mCardpanel, "addevent");
		
		add(mCardpanel, BorderLayout.CENTER);
		
		//creating tabbar
		mTabbar = new TabBar(mCardpanel, calendarGUI, chatGUI, homepageGUI, pingGUI);
		mTabbar.setPreferredSize(new Dimension(mTabbar.getWidth(), 30));
		add(mTabbar, BorderLayout.SOUTH);
		
		
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setCalendarGUI() {
		Vector<Event> events = Client.getUser().getCalendarManager().getEvents();
		Vector<Event> currentEvents = new Vector<Event>();
		String timeStamp = new SimpleDateFormat("yyyy MM dd HH mm ss").format(new Timestamp(System.currentTimeMillis()));
		String[] split = timeStamp.split(" ");
		System.out.println(split[0]);
		for (Event event : events) {
			System.out.println(event.getDate());
			String[] time = event.getDate().split(" ");
			System.out.println(time[0]);
			if (Integer.parseInt(time[0]) > Integer.parseInt(split[2])) {
				currentEvents.add(event);
			} else if (Integer.parseInt(time[0]) == Integer.parseInt(split[2])) {
				if (Integer.parseInt(time[1]) > Integer.parseInt(split[0])) {
					currentEvents.add(event);
				} else if (Integer.parseInt(time[1]) == Integer.parseInt(split[0])) {
					if (Integer.parseInt(time[2]) >= Integer.parseInt(split[1])) {
						currentEvents.add(event);
					}
				}
			}
		}
		calendarGUI = new CalendarGUI(mCardpanel, cl, currentEvents);
	}
	
	public CalendarGUI getCalendarGUI(){
		return calendarGUI;
	}

	public ChatGUI getChatGUI() {
		return chatGUI;
	}
	
	public HomepageGUI getHomepageGUI(){
		return homepageGUI;
	}
	
	public void setChatGUI() {
		Vector<ChatMessage> messages = Client.getUser().getChatManager().getChatMessages();
		chatGUI = new ChatGUI(messages);
	}
}
