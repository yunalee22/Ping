package mainframe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import chat.ChatMessage;
import client.Client;
import customUI.CPanel;
import customUI.ImageButton;
import customUI.RoundJTextField;
import loginframe.LoginFrame;
import server.Request;
import server.Request.RequestType;

public class Offlinelogin extends CPanel{
	
	private RoundJTextField codefield;
	private JLabel requestinfo;
	private ImageButton mButton;
	private JComboBox<String> houselist;
	
	public Offlinelogin(LoginFrame loginframe, TwilioRestClient Tclient){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", "GS32gd"));
	    params.add(new BasicNameValuePair("To", "+16266366010"));
	    params.add(new BasicNameValuePair("From", "+13235249046"));
	    

	    MessageFactory messageFactory = Tclient.getAccount().getMessageFactory();
	    try {
	      Message message = messageFactory.create(params);
	    } catch (TwilioRestException e) {
	      System.out.println(e.getErrorMessage());
	    }
		
		mButton = new ImageButton(new ImageIcon("res/logo/confirm.png"));
		mButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//send me to the cardlayout
				
				// get the home name
				
				String homeName = houselist.getSelectedItem().toString();
				String guestName = null;
				if(homeName == "Icon 101") { guestName = "guest1";}
				else if(homeName == "Icon 102") { guestName = "guest2";}
				else if(homeName == "Icon 103") { guestName = "guest3";}
				
				Request guestRequest = new Request(RequestType.GuestRequest, guestName);
				Client.sendRequest(guestRequest);
				loginframe.setVisible(false);
			}
		});
		
		
		requestinfo = new JLabel("Please Enter Code Confirmation");
		codefield = new RoundJTextField(10);
		String [] houses = {"Icon 101" , "Icon 102", "Icon 103"};
		houselist = new JComboBox<String>(houses);
		add(requestinfo);
		add(codefield);
		
		c.gridy = 1;
		c.gridx = 0;
		add(mButton,c);
		
		c.gridy = 2;
		add(houselist);
		
	}
}
