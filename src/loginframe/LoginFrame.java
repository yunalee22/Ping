package loginframe;

import java.awt.CardLayout;

import javax.swing.JFrame;

import com.twilio.sdk.TwilioRestClient;

import mainframe.OfflineCardPanel;
import mainframe.Offlinelogin;

public class LoginFrame extends JFrame {
	
	LoginGUI loginGUI;
	RegistrationGUI registrationGUI;
	CreateorjoinGUI createorjoinGUI;
	OfflineCardPanel offlinepaneGUI;
	Offlinelogin offlineloginGUI;
	
	public LoginFrame(TwilioRestClient Tclient) {
		super("Ping");
		setSize(500,800);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Set card layout
		getContentPane().setLayout(new CardLayout());
		loginGUI = new LoginGUI(this);
		add(loginGUI, "Login");
		registrationGUI = new RegistrationGUI();
		add(registrationGUI, "Register");
		createorjoinGUI = new CreateorjoinGUI();
		add(createorjoinGUI, "Createorjoin");
		offlinepaneGUI = new OfflineCardPanel(this, Tclient);
		add(offlinepaneGUI, "Offlinepan");
//		offlineloginGUI = new Offlinelogin(this);
//		add(offlineloginGUI, "Offlinelogin");
		showLogin();
	}
	
	public LoginGUI getLoginGUI(){
		return loginGUI;
	}
	
	public RegistrationGUI getRegistrationGUI() {
		return registrationGUI;
	}
	
	public CreateorjoinGUI getCreateorjoinGUI() {
		return createorjoinGUI;
	}
	
	public void showOfflinelogin(){
		CardLayout cl = (CardLayout)getContentPane().getLayout();
		cl.show(getContentPane(), "Offlinelogin");
	}
	public void showLogin() {
		CardLayout cl = (CardLayout)getContentPane().getLayout();
		cl.show(getContentPane(), "Login");
	}
	
	public void showRegister() {
		CardLayout cl = (CardLayout)getContentPane().getLayout();
		cl.show(getContentPane(), "Register");
	}
	
	public void showOffline(){
		CardLayout cl = (CardLayout)getContentPane().getLayout();
		cl.show(getContentPane(), "Offlinepan");
	}
	public void showCreateorjoin() {
		CardLayout cl = (CardLayout)getContentPane().getLayout();
		cl.show(getContentPane(), "Createorjoin");
	}
}
