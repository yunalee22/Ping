package loginframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import chat.ChatMessage;
import client.Client;
import customUI.ImageButton;
import customUI.RoundJTextField;
import mainframe.ChatGUI;
import server.Request;
import server.Request.RequestType;

public class LoginGUI extends JPanel {
	
	LoginFrame loginFrame;
	RoundJTextField usernameField;
	JPasswordField passField;
	ImageButton login;
	ImageButton register;
	ImageButton offline;
	
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
			img = ImageIO.read(new File("res/Logo/Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, getWidth()/3 + 30, getHeight()/4,
				60, 75, this);
	}
	
	
	public LoginGUI(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
		
		setLayout(new GridBagLayout());
		JLabel usernameit = new JLabel("Username");
		JLabel passtit = new JLabel("Password");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4,4,4,4);
		usernameField = new RoundJTextField(7);
		passField = new JPasswordField(7){
			private Shape shape;
			protected void paintComponent(Graphics g) {
		         g.setColor(getBackground());
		         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
		         super.paintComponent(g);
		    }
		    protected void paintBorder(Graphics g) {
		         g.setColor(getForeground());
		         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
		    }
		    public boolean contains(int x, int y) {
		         if (shape == null || !shape.getBounds().equals(getBounds())) {
		             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
		         }
		         return shape.contains(x, y);
		    }
		};
		
		// Create login button
		login = new ImageButton(new ImageIcon("res/Logo/login.png"));
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Send login request to server
				Vector<String> args = new Vector<String>();
				args.add(usernameField.getText());
				args.add(new String(passField.getPassword()));
				Request request = new Request(RequestType.Login, args);
				Client.sendRequest(request);
			}
		});
		
		// Create register button
		register = new ImageButton(new ImageIcon("res/Logo/register.png"));
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginFrame.showRegister();
			}
		});
		
		offline  = new ImageButton(new ImageIcon("res/logo/offline2.png"));
		offline.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("hello");
				loginFrame.showOffline();
			}
		});
		
		add(usernameit,c);
		add(usernameField,c);
		
		c.gridy = 1;
		add(passtit,c);
		add(passField,c);
		
		c.gridwidth = 2;
		c.gridy = 2;
		login.setPreferredSize(new Dimension(150,30));
		add(login,c);
		
		c.gridwidth = 2;
		c.gridy = 3;
		register.setPreferredSize(new Dimension(150,30));
		add(register,c);
		
		c.gridy = 4;
		offline.setPreferredSize(new Dimension(100,15));
		Border emptyBorder = BorderFactory.createEmptyBorder();
		offline.setBorder(emptyBorder);
		add(offline,c);
	}
}
