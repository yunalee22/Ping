package mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.Client;
import customUI.CPanel;
import ping.Ping;
import server.Request;
import server.Request.RequestType;

public class Sendping extends CPanel {
	
	private static final long serialVersionUID = 1L;
	
	JList roommates;
	String PingType;
	JPanel mf;
	Image i;
	Vector<String> roommateList;
	ImageIcon icon;
	
	//fetch images based on pingtype
	
	public Sendping(String pt, JPanel mf){
		PingType = pt;
		ParsePingImage(); //sets the image needed
		
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JPanel imagepan = new JPanel();
		imagepan.setBackground(Color.WHITE);
		ImageIcon img = null;
	    img = new ImageIcon(i); //use the image set
	    imagepan.add(new JLabel(img));
	    add(imagepan, BorderLayout.NORTH);
		
//		roommates.setBackground(Color.white);
//		roommates.setLayout(new GridBagLayout());
//		roommates.setLayout(new GridLayout(3, 2));
		//3 columns, 2 rows
		
		icon = new ImageIcon("res/Icons/DefaultProfile.png");
		
//		GridBagConstraints c = new GridBagConstraints();
//		c.insets = new Insets(2,2,2,2);
//		roommates.add(new RoundButton("Jason"));
//		roommates.add(new RoundButton("Jason"));
//		roommates.add(new RoundButton("Jason"));
//		c.gridy = 1;
//		roommates.add(new RoundButton("Jason"),c);
//		roommates.add(new RoundButton("Jason"),c);
		
		roommateList = Client.getUser().getHome().getResidents();
//		String[] array = new String[];
		int ignorethis = roommateList.indexOf(Client.getUser().getUsername());
		roommateList.remove(ignorethis);
		roommateList.toArray(new String[0]);
		roommates = new JList(roommateList.toArray(new String[0]));
		roommates.setCellRenderer(new ListRenderer());
		JScrollPane scroll = new JScrollPane(roommates);

//		for (int i=0; i<roommateList.size(); i++) {
//			//display all roommates in order
//			System.out.println(roommateList.get(i));
//			JLabel r = new JLabel(roommateList.get(i)); //currently just displays the names
//			r.setIcon(icon);
//			roommates.add(r);
//		}
		
		add(scroll, BorderLayout.CENTER);
		
		JButton send = new JButton("Send ping");
		send.addActionListener(new CustomListener());
		
		img = new ImageIcon("res/background/green.png");
		add(send, BorderLayout.SOUTH);
	}
	
	public void ParsePingImage() {
		if (PingType.equals("Trash")) {
			i = new ImageIcon("res/Ping Icons FINAL/Trash.png").getImage();
		} else if (PingType.equals("Clean")) {
			i = new ImageIcon("res/Ping Icons FINAL/Broom.png").getImage();
		} else if (PingType.equals("Dishes")) {
			i = new ImageIcon("res/Ping Icons FINAL/Dishes.png").getImage();
		} else if (PingType.equals("Fridge")) {
			i = new ImageIcon("res/Ping Icons FINAL/Fridge.png").getImage();
		} else if (PingType.equals("Volume")) {
			i = new ImageIcon("res/Ping Icons FINAL/Volume.png").getImage();
		} else if (PingType.equals("Restroom")) {
			i = new ImageIcon("res/Ping Icons FINAL/Toilet.png").getImage();
		} else if (PingType.equals("Bills")) {
			i = new ImageIcon("res/Ping Icons FINAL/Bill.png").getImage();
		} else if (PingType.equals("Lockedout")) {
			i = new ImageIcon("res/Ping Icons FINAL/Key.png").getImage();
		} else { // if (PingType.equalsIgnoreCase("toiletpaper")) {
			i = new ImageIcon("res/Ping Icons FINAL/ToiletPaper.png").getImage();
		}
			
	}
	
	public class ListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(icon);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }

	class CustomListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			// Send a ping from client to each selected roommate
			for(int i = 0; i < roommateList.size(); i++) {
				if (roommates.isSelectedIndex(i)) {
					Ping PingToSend = new Ping(PingType, Client.getUser().getUsername(), roommateList.get(i));
					System.out.println("In sendping.java: created ping from " + Client.getUser().getUsername() + " to " + roommateList.get(i));
					Request r = new Request(RequestType.Ping, PingToSend);
					Client.sendRequest(r);
				}
			}
			
			//return back to the main frame afterwards
//			CardLayout cl = (CardLayout)mf.getLayout();
//			cl.show(mf, "Homepage");
		}
	}
}
