package notifications;

import java.awt.Image;
import java.io.Serializable;

public interface Notification extends Serializable{
	//private static User sender;
	//private static User recipient;
	abstract String getMessage();
	abstract Image getIcon();
}
