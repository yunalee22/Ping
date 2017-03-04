package customUI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class ImageButton extends JButton{
	
	ImageIcon mImage;
	public ImageButton(ImageIcon myImage){
		mImage = myImage;
//		Border emptyBorder = BorderFactory.createEmptyBorder();
//		setBorder(emptyBorder);
		setBorderPainted(false);
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
		
		Image transfer = mImage.getImage();
		img = toBufferedImage(transfer);
		g.drawImage(img, 0, 0,
				getWidth(), getHeight(), this);
			
	}
	
	//http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}