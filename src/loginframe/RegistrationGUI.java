package loginframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import client.Client;
import customUI.ImageButton;
import customUI.RoundJTextField;
import server.Request;
import server.Request.RequestType;

public class RegistrationGUI extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JPasswordField passField;
	RoundJTextField nameField;
	RoundJTextField usernameField;
	JPasswordField verifyfield;
	JComboBox<String> homenameComboBox;
	ImageButton register;
	
	String [] houses = {"Icon 101", "Icon 102", "Icon 103"};
	
	public RegistrationGUI() {
		setLayout(new GridBagLayout());
		JLabel nameit = new JLabel("Name");
		JLabel userit = new JLabel("UserName");
		JLabel passtit = new JLabel("Password");
		JLabel verifyit = new JLabel("Verify Password");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4,4,4,4);
		usernameField = new RoundJTextField(7);
		passField = new JPasswordField(7);
		nameField = new  RoundJTextField(7);
		verifyfield = new JPasswordField(7);
		homenameComboBox = new JComboBox<String>(houses);
		
		register = new ImageButton(new ImageIcon("res/Logo/createaccount.png"));
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String username = usernameField.getText();
				String password = new String(passField.getPassword());
				String repeat = new String(verifyfield.getPassword());
				String homename = (String)homenameComboBox.getSelectedItem();
				
				// Username is invalid
				if (username == "") {
					JOptionPane.showMessageDialog(null, "Invalid username", 
							"Sign-up Failed", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// Password is invalid
				else if (password == "") {
					JOptionPane.showMessageDialog(null,
							"Password must contain at least\n 1 number and 1 uppercase letter", 
							"Sign-up Failed", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// Passwords do not match
				else if (!password.equals(repeat)) {
					JOptionPane.showMessageDialog(null, "Passwords do not match", 
							"Sign-up Failed", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				// Send a signup request to server
				Vector<String> args = new Vector<String>();
				args.add(name);
				args.add(username);
				args.add(password);
				args.add(homename);
				Request request = new Request(RequestType.Signup, args);
				Client.sendRequest(request);
			}
		});
		
		c.gridy = 0;
		add(nameit,c);
		add(nameField,c);
		
		c.gridy = 1;
		add(userit,c);
		add(usernameField,c);
		
		c.gridy = 2;
		add(passtit, c);
		add(passField,c);
		
		c.gridy = 3;
		add(verifyit, c);
		add(verifyfield, c);
		
		c.gridy = 4;
		c.gridwidth = 3;
		add(homenameComboBox,c);
		
		c.gridy = 5;
		c.gridwidth = 3;
		register.setPreferredSize(new Dimension(150,30));
		add(register,c);
		
		setVisible(true);
	}
	
	public String getName(){
		return nameField.getName();
	}
	
	public String passField(){
		return new String(passField.getPassword());
	}
	
	public String verifyField(){
		return new String(verifyfield.getPassword());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("res/background/green.png"));
		} catch (IOException e) {
			
		}
		g.drawImage(img, 0, 0,
				getWidth(), getHeight(), this);
		try {
			img = ImageIO.read(new File("res/Logo/register.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, getWidth()/3, getHeight()/4,
				150, 50, this);
	}
	
	
}
