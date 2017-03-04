package mainframe;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import client.Client;
import client.User;
import notifications.Notification;
import notifications.NotificationManager;
import notifications.PingNotification;
import ping.Ping;
import server.Request;
import server.Request.RequestType;

public class NotificationsGUI extends JPanel  {
	NavigationBar mNavBar;
	TabBar mTabbar;
	JPanel Notif;
	JScrollPane mScrollPane;
	private NotificationManager nm;
	
	public NotificationsGUI(NotificationManager nm){
		this.nm = nm;
		setSize(300,500);
		
		setLayout(new BorderLayout());
		
		//creating middle sections
		
		Notif = new JPanel();
		Notif.setLayout(new GridLayout(0,1));
		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
		//try==============================================
//		JPanel jp1 = new JPanel();
//		jp1.setMaximumSize(new Dimension(300,50));
//		jp1.setPreferredSize(new Dimension(300,50));
//		JLabel lb1  = new JLabel("No.1 Notification.");
//		jp1.add(lb1);
//		
//		JPanel jp2 = new JPanel();
//		jp2.setMaximumSize(new Dimension(300,50));
//		jp2.setPreferredSize(new Dimension(300,50));
//		JLabel lb2  = new JLabel("No.2 Notification.");
//		jp2.add(lb2);
//		
//		JPanel jp3 = new JPanel();
//		jp3.setMaximumSize(new Dimension(300,50));
//		jp3.setPreferredSize(new Dimension(300,50));
//		JLabel lb3  = new JLabel("No.3 Notification.");
//		jp3.add(lb3);
//		
//		JPanel jp4 = new JPanel();
//		jp4.setMaximumSize(new Dimension(300,50));
//		jp4.setPreferredSize(new Dimension(300,50));
//		JLabel lb4  = new JLabel("No.4 Notification.");
//		jp4.add(lb4);
//		
//		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
//		Notif.add(jp1);
//		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
//		Notif.add(jp2);
//		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
//		Notif.add(jp3);
//		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
//		
//		Notif.add(jp4);
//		Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
		//=================================================
		mScrollPane = new JScrollPane(Notif);
		updateGUI();
		//mScrollPane.getVerticalScrollBar().setUI(new OfficeScrollBarUI());
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(mScrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	// make a notification as a panel
	public JPanel createNotification(Ping notif){
		JPanel NT = new JPanel(new FlowLayout());
		NT.setMaximumSize(new Dimension(300,50));
		NT.setPreferredSize(new Dimension(300,50));
		String PingType = notif.getMessage();
		ImageIcon ImgIcon = new ImageIcon(ParsePingImage(PingType));
		JLabel notfmsg  = new JLabel(notif.getMessage());
		JLabel notdicon = new JLabel(ImgIcon);
		
		JButton remove = new JButton("Remove");
		remove.addActionListener(new removeNotification(notif));
		
		
		NT.add(notdicon);
		NT.add(notfmsg);
		NT.add(remove);
		
		return NT;
	}
	
	public Image ParsePingImage(String PingType) {
		if (PingType.equals("Trash")) {
			return new ImageIcon("res/Ping Icons FINAL/Trash.png").getImage();
		} else if (PingType.equals("Clean")) {
			return new ImageIcon("res/Ping Icons FINAL/Broom.png").getImage();
		} else if (PingType.equals("Dishes")) {
			return new ImageIcon("res/Ping Icons FINAL/Dishes.png").getImage();
		} else if (PingType.equals("Fridge")) {
			return new ImageIcon("res/Ping Icons FINAL/Fridge.png").getImage();
		} else if (PingType.equals("Volume")) {
			return new ImageIcon("res/Ping Icons FINAL/Volume.png").getImage();
		} else if (PingType.equals("Restroom")) {
			return new ImageIcon("res/Ping Icons FINAL/Toilet.png").getImage();
		} else if (PingType.equals("Bills")) {
			return new ImageIcon("res/Ping Icons FINAL/Bill.png").getImage();
		} else if (PingType.equals("Lockedout")) {
			return new ImageIcon("res/Ping Icons FINAL/Key.png").getImage();
		} else { // if (PingType.equalsIgnoreCase("toiletpaper")) {
			return new ImageIcon("res/Ping Icons FINAL/ToiletPaper.png").getImage();
		}
			
	}
	
	
	
	// make all the notifications in the manager into panels
	// and insert them into the Notif JPanel.
	public void listNotification(NotificationManager nm){
		if (nm.notificationManagerSize() == 0) {
			return;
		}
		
		for(int i = 0; i < nm.notificationManagerSize(); i++){
			System.out.println(nm.getNotif(i).getRecipient() +" " + nm.getNotif(i).getSender());
			Notif.add(createNotification(nm.getNotif(i)));
			Notif.add(new JSeparator(SwingConstants.HORIZONTAL));
		}
	}
	
	public void addNotification(JPanel notfPanel){
		Notif.add(notfPanel);
	}
	
	class removeNotification implements ActionListener {

		private Ping notif;
		
		public removeNotification(Ping notif)
	    {
	        this.notif = notif;
	    }
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//removes the notification from the vector
			Ping PingToDelete = new Ping( notif.getMessage(), notif.getSender(), notif.getRecipient());
			Request r = new Request(RequestType.PingNotification, PingToDelete);
			Client.sendRequest(r);
			nm.removeNotification(notif);
			//updates the currently displaying gui
			updateGUI();
		}
	}
	
	public void updateGUI() {
		//refresh the GUI
		System.out.println("refreshing");
		Notif.removeAll();
		Notif.revalidate();
		Notif.repaint();
		
		listNotification(nm);
	}
}