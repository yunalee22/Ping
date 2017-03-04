package customUI;


import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TextBoxImage extends JButton{
	private BufferedImage img = null;
	JLabel title;
	
	public TextBoxImage(String name){
		super(name);
		addname(name);
		try {
		    img = ImageIO.read(new File("res/icons/goldbut.png"));
		} catch (IOException e) {
			
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0,
				getWidth(), getHeight(), null);
	}
	
	protected void addname(String text){
		title = new JLabel(text, JLabel.CENTER);
		add(title);
	}
	
	public void setTitle(String newname){
		title.setText(newname);
	}
}
