package mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.ChatMessage;
import client.Client;
import customUI.CPanel;
import server.Request;
import server.Request.RequestType;

//http://codereview.stackexchange.com/questions/25461/simple-chat-room-swing-gui
public class ChatGUI extends JPanel {
	String appName = "Colt Chat v0.1";
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;
	JTextField usernameChooser;

	public ChatGUI(Vector<ChatMessage> history) {

		setLayout(new BorderLayout());
		JPanel southPanel = new CPanel();

		// southPanel.setBackground(Color.BLUE);
		southPanel.setLayout(new GridBagLayout());

		messageBox = new JTextField(30);
		messageBox.requestFocusInWindow();

		sendMessage = new JButton("Send Message");
		sendMessage.addActionListener(new sendMessageButtonListener());

		chatBox = new JTextArea();
		for(ChatMessage cm : history)
			chatBox.append("<" + cm.getName() + ">:  " + cm.getMessage() + "\n");
		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);

		add(new JScrollPane(chatBox), BorderLayout.CENTER);

		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.LINE_START;
		left.fill = GridBagConstraints.HORIZONTAL;
		left.weightx = 512.0D;
		left.weighty = 1.0D;

		GridBagConstraints right = new GridBagConstraints();
		right.insets = new Insets(0, 10, 0, 0);
		right.anchor = GridBagConstraints.LINE_END;
		right.fill = GridBagConstraints.NONE;
		right.weightx = 1.0D;
		right.weighty = 1.0D;

		southPanel.add(messageBox, left);
		southPanel.add(sendMessage, right);

		add(BorderLayout.SOUTH, southPanel);

	}

	class sendMessageButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (messageBox.getText().length() < 1) {
				// do nothing
			} else if (messageBox.getText().equals(".clear")) {
				chatBox.setText("Cleared all messages\n");
				messageBox.setText("");
			} else {
				chatBox.append("<" + Client.getUser().getUsername() + ">:  " + messageBox.getText() + "\n");
				ChatMessage cm = new ChatMessage(Client.getUser().getUsername(), messageBox.getText());
				CMEObject cmeObject = new CMEObject(Client.getUser().getUsername(), cm);
				Request r = new Request(RequestType.Chat, cmeObject);
				Client.sendRequest(r);
				messageBox.setText("");
			}
			messageBox.requestFocusInWindow();
		}
	}

	public void add(ChatMessage chatMessage) {
		chatBox.append("<" + chatMessage.getName() + ">:  " + chatMessage.getMessage() + "\n");
	}
}
