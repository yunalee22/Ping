package mainframe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import calendar.CalendarManager;
import calendar.Event;
import client.Client;
import customUI.CPanel;
import customUI.ImageButton;
import customUI.RoundJTextField;
import customUI.TextBoxImage;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import server.Request.RequestType;
import server.Request;

public class CalendarGUI extends CPanel {

	private Vector<Event> Events;
	private JList eventlist;
	private DefaultListModel mModel;
	private TextBoxImage newEvent;
	private TextBoxImage removeEvent;
	private EventDescrip eventDescrip;
	private CardLayout cl;
	private String mdate;
	private JPanel cardContainer;
	private CPanel card2;
	UtilDateModel model;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	private Vector<Event> currentEvents;

	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/background/compbg.jpg"));
		} catch (IOException e) {

		}
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	public CalendarGUI(JPanel mCardPanel, CardLayout cl, Vector<Event> currentEvents) {
		this.currentEvents = currentEvents;
		cardContainer = mCardPanel;
		this.cl = cl;

		JPanel card1 = createCard1();
		add(card1);
		// add(cardContainer);
	}
	
	public void add(Event e) {
		currentEvents.add(e);
		mModel.clear();
		for (int i = 0; i < currentEvents.size(); i++) {
			mModel.addElement(
					currentEvents.get(i).getDate() + " " + currentEvents.get(i).getDescription().substring(0, 5));
		}
	}

	public void remove(Event e){
		int index = -1;
		for (int i = 0; i < currentEvents.size(); i++) {
			if(e.getDate().equals(currentEvents.get(i).getDate()))
				if(e.getDescription().equals(currentEvents.get(i).getDescription()))
					index = i;
		}
		System.out.println(index);
		currentEvents.remove(index);
		System.out.println(currentEvents.size());
		mModel.clear();
		for (int i = 0; i < currentEvents.size(); i++) {
			mModel.addElement(
					currentEvents.get(i).getDate() + " " + currentEvents.get(i).getDescription().substring(0, 5));
		}
	}
	private JPanel createCard1() {
		mdate = null;
		JPanel card11 = new JPanel();
		card11.setOpaque(false);
		//card11.setBackground(Color.BLACK);
		card11.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1,1,1,1);
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		
		
		
		newEvent = new TextBoxImage("New Event");
		newEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Date selectedDate = (Date) datePicker.getModel().getValue();
				if(selectedDate == null) return;
				mdate = selectedDate.toString();
				card2 = createCard2();
				cardContainer.add(card2, "Card 2");
				cl.show(cardContainer, "Card 2");
			}
		});
		
		removeEvent = new TextBoxImage("Remove Event");
		removeEvent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedindex = eventlist.getSelectedIndex();
				if(selectedindex != -1){
//					
//					String description = mModel.getElementAt(selectedindex).toString();
//					String [] splitdes = description.split(" ");
//					description = "";
//					for(int i =0 ; i < splitdes.length-1; ++i){
//						description += splitdes[i];
//					}
					Event event = currentEvents.get(selectedindex);
					CMEObject cmeObject1 = new CMEObject(Client.getUser().getUsername(), event); 
					Request request = new Request(RequestType.CalendarRemove, cmeObject1);
					Client.sendRequest(request);
					remove(event);
					//also need to send signal to delete from db
//					Client.getClient().sendRequest(new Request(RequestType.CalendarRemove, description);
				}
			}
		});
		
		mModel = new DefaultListModel();
		eventlist = new JList(mModel);
		//for(int i=0; i < vec.length; ++i)
		for(int i =0 ; i < currentEvents.size(); i++){
			mModel.addElement(currentEvents.get(i).getDate()+" "+currentEvents.get(i).getDescription().substring(0,5));
		}
		JScrollPane pane = new JScrollPane(eventlist);
		c.gridx =0;
		c.gridy =4;
		card11.add(datePanel,c);
		c.gridy = 6;
		card11.add(pane,c);
		c.gridy = 4;
		c.gridx = 1;
		card11.add(newEvent,c);
		c.gridy = 5;
		card11.add(removeEvent,c);
		return card11;
	}

	private CPanel createCard2() {
		CPanel card2 = new CPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("res/background/compbg.jpg"));
				} catch (IOException e) {

				}
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		TextBoxImage date;
		RoundJTextField datefield;
		TextBoxImage description;
		RoundJTextField descripfield;
		ImageButton addEvent;
		ImageButton backbut;

		String[] split = mdate.split(" ");
		String addToDate = null;
		if (split[1].equals("Jan")) {
			addToDate = "01" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Feb")) {
			addToDate = "02" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Mar")) {
			addToDate = "03" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Apr")) {
			addToDate = "04" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("May")) {
			addToDate = "05" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Jun")) {
			addToDate = "06" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Jul")) {
			addToDate = "07" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Aug")) {
			addToDate = "08" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Sep")) {
			addToDate = "09" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Oct")) {
			addToDate = "10" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Nov")) {
			addToDate = "11" + " " + split[2] + " " + split[5];
		} else if (split[1].equals("Dec")) {
			addToDate = "12" + " " + split[2] + " " + split[5];
		}
		String ddate = addToDate;
		card2.setLayout(new GridBagLayout());
		date = new TextBoxImage("DATE");
		datefield = new RoundJTextField(15);
		datefield.setText(addToDate);
		description = new TextBoxImage("Enter Description");
		descripfield = new RoundJTextField(15);
		descripfield.setEditable(true);
		addEvent = new ImageButton(new ImageIcon("res/icons/calendar.png")) {
			@Override
			protected void paintComponent(Graphics g) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("res/background/yellow.png"));
				} catch (IOException e) {

				}
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				ImageIcon mImage = new ImageIcon("res/icons/newEvent.png");
				Image transfer = mImage.getImage();
				img = toBufferedImage(transfer);
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		addEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(Client.getUser().getUsername());
				Event event = new Event(ddate, descripfield.getText());
				CMEObject cmeObject = new CMEObject(Client.getUser().getUsername(), event);
				add(event);
				Request request = new Request(RequestType.CalendarAdd, cmeObject);
				Client.sendRequest(request);
				cl.show(cardContainer, "Calendar");
			}
		});
		backbut = new ImageButton(new ImageIcon("res/icons/backbut.png")) {
			@Override
			protected void paintComponent(Graphics g) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("res/background/yellow.png"));
				} catch (IOException e) {

				}
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				ImageIcon mImage = new ImageIcon("res/icons/backbut.png");
				Image transfer = mImage.getImage();
				img = toBufferedImage(transfer);
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		backbut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cardContainer, "Calendar");
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		card2.add(date, c);
		card2.add(datefield, c);
		c.gridy = 1;
		card2.add(description, c);
		card2.add(descripfield, c);
		c.gridy = 2;
		card2.add(addEvent, c);
		card2.add(backbut, c);

		return card2;
	}
}