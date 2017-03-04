package mainframe;

import java.awt.ActiveEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import calendar.Event;
import client.Client;
import customUI.CPanel;
import customUI.DynamicRoundButton;
import customUI.ImageButton;
import customUI.RoundJTextField;
import customUI.TextBoxImage;
import server.Request;
import server.Request.RequestType;

public class EventDescrip extends CPanel{
	
	private TextBoxImage date;
	private RoundJTextField datefield;
	private TextBoxImage description;
	private RoundJTextField descripfield;
	private ImageButton addEvent;
	private ImageButton backbut;
	
	public EventDescrip(String Date){
		String[] split = Date.split(" ");
		System.out.print(split[1]+split[2]+split[5]);
		String addToDate = split[1]+split[2]+split[5];
		String numDate = "";
		if(split[1].equals("Jan")){
			numDate += "01";
		}
		else if(split[1].equals("Feb")){
			numDate += "02";
		}
		else if(split[1].equals("Mar")){
			numDate += "03";
		}
		else if(split[1].equals("Apr")){
			numDate += "04";
		}
		else if(split[1].equals("May")){
			numDate += "05";
		}
		else if(split[1].equals("Jun")){
			numDate += "06";
		}
		else if(split[1].equals("Jul")){
			numDate += "07";
		}
		else if(split[1].equals("Aug")){
			numDate += "08";
		}
		else if(split[1].equals("Sep")){
			numDate += "09";
		}
		else if(split[1].equals("Oct")){
			numDate += "10";
		}
		else if(split[1].equals("Nov")){
			numDate += "11";
		}
		else if(split[1].equals("Dec")){
			numDate += "12";
		}
		numDate += " "+split[2];
		numDate += " "+split[5];
		System.out.println(numDate);
		setLayout(new GridBagLayout());
		date = new TextBoxImage("DATE");
		datefield = new RoundJTextField(15);
		datefield.setText(addToDate);
		description = new TextBoxImage("Enter Description");
		descripfield = new RoundJTextField(15);
		descripfield.setEditable(true);
		addEvent = new ImageButton(new ImageIcon("res/icons/calendar.png"));
		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event = new Event(addToDate, descripfield.getText());
				Request request = new Request(RequestType.CalendarAdd, event);
				Client.sendRequest(request);
			}
		});
		backbut = new ImageButton(new ImageIcon("res/icons/backbut.png"));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		add(date,c);
		add(datefield,c);
		c.gridy = 1;
		add(description,c);
		add(descripfield,c);
		c.gridy = 2;
		add(addEvent,c);
		add(backbut,c);
	}
}
