package customUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

//http://stackoverflow.com/questions/778222/make-a-button-round
public class DynamicRoundButton extends JButton {
	String imagepath;
	JLabel title;
	  public DynamicRoundButton(String label, String imagepath) {
	    super(label);
	    addname(label);
	    this.imagepath = imagepath;
	// These statements enlarge the button so that it 
	// becomes a circle rather than an oval.
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width, 
	      size.height);
	    setPreferredSize(size);

	// This call causes the JButton not to paint 
	   // the background.
	// This allows us to paint a round background.
	    setContentAreaFilled(false);
	  }

	// Paint the round background and label.
	  protected void paintComponent(Graphics g) {
	    if (getModel().isArmed()) {
	// You might want to make the highlight color 
	   // a property of the RoundButton class.	
	      g.setColor(Color.lightGray);
	    } else {
	      g.setColor(getBackground());
	    }
	    g.fillOval(0, 0, getSize().width-10, 
	      getSize().height-10);
	    BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(imagepath));
		} catch (IOException e) {
			
		}
		g.drawImage(img, 0, 0,
				getWidth()-10, getHeight()-10, this);
		
	  }

	// Paint the border of the button using a simple stroke.
	  protected void paintBorder(Graphics g) {
	    g.setColor(Color.WHITE);
	    g.drawOval(0, 0, getSize().width, 
	      getSize().height);
	  }

	// Hit detection.
	  Shape shape;
	  public boolean contains(int x, int y) {
	// If the button has changed size, 
	   // make a new shape object.
	    if (shape == null || 
	      !shape.getBounds().equals(getBounds())) {
	      shape = new Ellipse2D.Float(0, 0, 
	        getWidth(), getHeight());
	    }
	    return shape.contains(x, y);
	  }
		protected void addname(String text){
			title = new JLabel(text, JLabel.CENTER);
			add(title);
		}
		
		public void setTitle(String newname){
			title.setText(newname);
		}
}