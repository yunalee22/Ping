package mainframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Client;
import customUI.DynamicRoundButton;
import customUI.ImageButton;
import customUI.RoundButton;

public class HomepageGUI extends JPanel {

	JPanel roommates;

	public HomepageGUI() {
		// setSize(300,500);

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		ImageIcon img = null;
		img = new ImageIcon("res/Icons/home-large-blue.png");
		add(new JLabel(img), BorderLayout.NORTH);
		roommates = new JPanel();
		roommates.setBackground(Color.white);
		roommates.setLayout(new GridBagLayout());
		BufferedImage user = null;
		try {
			user = ImageIO.read(new File("res/Icons/DefaultProfile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		Vector<String> residents = Client.getHome().getResidents();
		int i = 0;
		c.gridy = 0;
		for (String resident : residents) {
			if ((!resident.equals("guest1")) && ((!resident.equals("guest2"))) && (!resident.equals("guest3"))) {
				roommates.add(new DynamicRoundButton(resident.substring(0, 6), "res/Icons/DefaultProfile.png"));
				i++;
				if (i == 3) {
					i = 0;
					c.gridy++;
				}
			}
		}
		add(roommates, BorderLayout.CENTER);
	}

	public void update(String name) {
		roommates.add(new DynamicRoundButton(name, "res/Icons/DefaultProfile.png"));
	}
}
