package mainframe;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import chat.ChatMessage;
import client.Home;
import client.User;
import customUI.CPanel;
import loginframe.LoginFrame;

//created on offline button clicked
public class OfflineCardPanel extends CPanel{
	
	private ChatGUI mChatGUI;
	private Offlinelogin offlinelogin;
	public CardLayout cl;
	public static final String ACCOUNT_SID = "AC227b9235bff122f13b2cbf172e4b9b94";
	public static final String AUTH_TOKEN = "c18fa63d33d8ffff3bf3aa880a88ec6d";
	TwilioRestClient Tclient;
	
	public OfflineCardPanel(LoginFrame loginframe, TwilioRestClient Tclient){
	
		
		
		setLayout(new CardLayout());
		
		offlinelogin = new Offlinelogin(loginframe, Tclient);
		//mChatGUI = new ChatGUI();
		
		add(offlinelogin, "offlinelogin");
		cl = (CardLayout)loginframe.getContentPane().getLayout();
		cl.show(loginframe.getContentPane(), "offlinelogin");

	  
	}
}