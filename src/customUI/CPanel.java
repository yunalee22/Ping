package customUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CPanel extends JPanel{

	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("res/background/green.png"));
		} catch (IOException e) {
			
		}
		g.drawImage(img, 0, 0,
				getWidth(), getHeight(), this);
	}
}
