package mainframe;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import customUI.DynamicRoundButton;
import customUI.ImageButton;

public class PingsGUI extends JPanel{
	
	DynamicRoundButton trashbut;
	DynamicRoundButton cleanbut;
	DynamicRoundButton dishesbut;
	DynamicRoundButton fridgebut;
	DynamicRoundButton volumebut;
	DynamicRoundButton restroombut;
	DynamicRoundButton billbut;
	DynamicRoundButton lockedbut;
	DynamicRoundButton toiletbut;
	
	JPanel MainFrame;
	Client client;
	
	public PingsGUI(JPanel mf, Client client){
		MainFrame = mf;
		this.client = client;
		
		setBackground(Color.WHITE);
		setLayout(new GridLayout(3,3));
		
		addButtons();
	}
	
	private void addButtons() {
		
		trashbut = new DynamicRoundButton("Trash", "res/pingicons/Trash.png");
		cleanbut = new DynamicRoundButton("Clean", "res/pingicons/Broom.png");
		dishesbut = new DynamicRoundButton("Dishes", "res/pingicons/Dishes.png");
		fridgebut = new DynamicRoundButton("Fridge", "res/pingicons/Fridge.png");
		volumebut = new DynamicRoundButton("Volume", "res/pingicons/Volume.png");
		restroombut = new DynamicRoundButton("Restroom", "res/pingicons/toilet.png");
		billbut = new DynamicRoundButton("Bills", "res/pingicons/Bill.png");
		lockedbut = new DynamicRoundButton("Lockedout", "res/pingicons/Key.png");
		toiletbut = new DynamicRoundButton("toiletpaper", "res/pingicons/ToiletPaper.png");
		
		trashbut.addActionListener(new CustomListener(trashbut.getText()));
		cleanbut.addActionListener(new CustomListener(cleanbut.getText()));
		dishesbut.addActionListener(new CustomListener(dishesbut.getText()));
		fridgebut.addActionListener(new CustomListener(fridgebut.getText()));
		volumebut.addActionListener(new CustomListener(volumebut.getText()));
		restroombut.addActionListener(new CustomListener(restroombut.getText()));
		billbut.addActionListener(new CustomListener(billbut.getText()));
		lockedbut.addActionListener(new CustomListener(lockedbut.getText()));
		toiletbut.addActionListener(new CustomListener(toiletbut.getText()));
		
		//GridBagConstraints c = new GridBagConstraints();
//		c.insets = new Insets(1,1,1,1);
		add(trashbut);
		add(cleanbut);
		add(dishesbut);
		//c.gridy = 1;
		add(fridgebut);//,c);
		add(volumebut);//,c);
		add(restroombut);//,c);
		//c.gridy = 2;
		add(billbut);//,c);
		add(lockedbut);//,c);
		add(toiletbut);//,c);
	}
	
	class CustomListener implements ActionListener {
		
		String PingType;
		
		public CustomListener(String pt) {
			PingType = pt;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			//leads to the sendping screen
			Sendping sp = new Sendping(PingType, MainFrame);
			MainFrame.add("Sendping", sp);
			CardLayout cl = (CardLayout)MainFrame.getLayout();
			cl.show(MainFrame, "Sendping");
		}
	}
}
