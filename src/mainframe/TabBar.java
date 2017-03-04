package mainframe;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import customUI.CPanel;
import customUI.ImageButton;

public class TabBar extends CPanel{

	ImageButton houseImage;
	ImageButton speechImage;
	ImageButton calendarImage;
	ImageButton pingImage;
	
	//need to pass in the panels / the cardlayoutmanager
	public TabBar(JPanel mCardpanel, JPanel calendarGUI, JPanel chatGUI, JPanel homepageGUI, JPanel pingGUI){
		setLayout(new GridBagLayout());
		ImageIcon mhouseImage = new ImageIcon("res/Icons/Tab Bar/home.png");
		houseImage = new ImageButton(mhouseImage);
		ImageIcon mspeechImage = new ImageIcon("res/Icons/Tab Bar/ping.png");
		speechImage = new ImageButton(mspeechImage);
		ImageIcon mcalendarImage = new ImageIcon("res/Icons/calcal.png");
		calendarImage = new ImageButton(mcalendarImage);
		ImageIcon mpingimage = new ImageIcon("res/Icons/pingpan.png");
		pingImage = new ImageButton(mpingimage);
				
		
		
		houseImage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)mCardpanel.getLayout();
				cl.show(mCardpanel, "Homepage");
			}
		});
		
		speechImage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CardLayout cl = (CardLayout)mCardpanel.getLayout();
				cl.show(mCardpanel, "Chat");
			}
		});
		
		
		calendarImage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CardLayout cl = (CardLayout)mCardpanel.getLayout();
				cl.show(mCardpanel, "Calendar");
			}
		});
		
		pingImage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CardLayout cl = (CardLayout)mCardpanel.getLayout();
				cl.show(mCardpanel, "Ping");
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(25,25,25,25);
		c.gridx = 0;
		c.gridy = 0;
		add(houseImage,c);
		c.gridx = 2;
		c.gridwidth = 1;
		add(speechImage, c);
		c.gridx = 4;
		add(calendarImage,c);
		c.gridx = 5;
		add(pingImage,c);
	}
}
