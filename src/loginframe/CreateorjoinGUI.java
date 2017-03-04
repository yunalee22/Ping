package loginframe;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import customUI.ImageButton;

public class CreateorjoinGUI extends JPanel{

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
			img = ImageIO.read(new File("res/Icons/home-large.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 90, 130, 75, 75, null);
		try {
			img = ImageIO.read(new File("res/Icons/prightarrow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 0, 350, 100, 10, null);
		try {
			img = ImageIO.read(new File("res/Icons/pleftarrow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 160, 350, 100, 10, null);
	}
	
	ImageButton create;
	ImageButton join;
		public CreateorjoinGUI(){
			create = new ImageButton(new ImageIcon("res/Logo/create.png"));
			join = new ImageButton(new ImageIcon("res/Logo/join.png"));
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2,2,2,2);
			create.setPreferredSize(new Dimension(100, 50));
			add(create,c);
			c.gridy = 1;
			join.setPreferredSize(new Dimension(100,50));
			add(join,c);
			
		}
}
