import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;


/**
 * This is a simple GUI for the user to input their information
 * @author Sasha Jouravlev
 *
 */
public class ChatGUI extends JFrame {
	Panel mainPanel = new Panel();
	Panel chatPanel = new Panel();
	Panel entryPanel = new Panel();
	
	TCPClient client;

	//~~~~~~~~Entry Panel Components~~~~~~~~~
	private Label serverLabel = new Label("Server:"); 
	private TextField server = new TextField("", 20);

	private Label portLabel = new Label("Port:");
	private TextField port = new TextField("", 20);

	private Button joinButton = new Button("Join");

	//~~~~~~~Main Panel Components~~~~~~~~~
	private Label chatLabel = new Label("Chat");
	private TextArea chat = new TextArea();

	private TextField message = new TextField("",50);

	private Button sendButton = new Button("Send");

	public ChatGUI() {
		super("Java Mailclient");

		GridLayout grid = new GridLayout(2,1);
		mainPanel.setLayout(grid);
		//~~~~~~~~Entry Panel Components~~~~~~~~~
		//GridLayout grid = new GridLayout(2,1);
		//entryPanel.setLayout(grid);
		entryPanel.add(serverLabel);
		entryPanel.add(server);
		entryPanel.add(portLabel);
		entryPanel.add(port);
		entryPanel.add(joinButton);

		joinButton.addActionListener(new JoinListener());

		//~~~~~~~Main Panel Components~~~~~~~~~
		chatPanel.add(chatLabel);
		chatPanel.add(chat);
		chatPanel.add(message);
		chatPanel.add(sendButton);

		chat.setEnabled(false);

		mainPanel.add(entryPanel);
		mainPanel.add(chatPanel);
		add(mainPanel);

		chatPanel.setVisible(false);
		pack();
		show();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//the listener for the join click
	// will attempt to join chat on click
	class JoinListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
//			if((server.getText()).equals("") || port.getText().equals("")) {
//				System.out.println("All fields must be filled out.");
//				return;
//			}

			entryPanel.setVisible(false);
			chatPanel.setVisible(true);
			
			try {
				client = new TCPClient(server.getText(), port.getText());
			} catch (Exception e) {}
			System.out.println("Client created");
		}
	}

	//the listener for the send click
	// will send a message on click
	class SendListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				client.sendToServer(message.getText());
			} catch (Exception e) {}
			message.setText("");
		}
	}
	static public void main(String argv[]) {
		new ChatGUI();
	}
}