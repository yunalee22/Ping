package mainframe;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.User;
import customUI.CPanel;
import customUI.ImageButton;
import notifications.NotificationManager;

public class NavigationBar extends CPanel{
	JPanel mf;
	ImageButton notImage;
	private static NotificationsGUI notificationsGUI;
	private Boolean isShown;
	User user;
	
	public NavigationBar(JPanel mf, User u) {
		this.mf = mf;
		this.user = u;
		NotificationManager nm = u.getNotificationManager();
		
		setSize(500, 30);
		setLayout(new GridLayout(1,5));
		ImageIcon mNotImage = new ImageIcon("res/Icons/Navigation Bar/notifications.png");
		ImageButton mNButton = new ImageButton(mNotImage);
		
		isShown = false;
		
		mNButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isShown.equals(false)) {
					notificationsGUI = new NotificationsGUI(nm);
					mf.add("NotificationGUI", notificationsGUI);
					CardLayout cl = (CardLayout)mf.getLayout();
					cl.show(mf, "NotificationGUI");
					isShown = true;
				}
				else {
					//always return to homescreen after the notification panel
					CardLayout cl = (CardLayout)mf.getLayout();
					cl.show(mf, "Homepage");
					isShown = false;
				}
				
			}
		});
		
		add(new CPanel());
		add(new CPanel());
		add(new CPanel());
		add(new CPanel());
		add(new CPanel());
		add(mNButton); //this is the bell
	}
}
