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
	Panel entryPanel = new Panel();
	
	//~~~~~~~~Entry Panel Components~~~~~~~~~
	private Label screenNameLabel = new Label("Name:");
	private TextField screenName = new TextField("", 20);

	private Label serverLabel = new Label("Server:"); 
	private TextField server = new TextField("", 20);

	private Label portLabel = new Label("Port:");
	private TextField port = new TextField("", 20);

	private Button joinButton = new Button("Join");

	//~~~~~~~Main Panel Components~~~~~~~~~
	
	public ChatGUI() {
		super("Java Mailclient");

		//~~~~~~~~Entry Panel Components~~~~~~~~~
		//GridLayout grid = new GridLayout(2,1);
		//entryPanel.setLayout(grid);
		entryPanel.add(screenNameLabel);
		entryPanel.add(screenName);
		entryPanel.add(serverLabel);
		entryPanel.add(server);
		entryPanel.add(portLabel);
		entryPanel.add(port);
		entryPanel.add(joinButton);

		joinButton.addActionListener(new JoinListener());

		//~~~~~~~Main Panel Components~~~~~~~~~
		
		
		
		add(entryPanel, BorderLayout.NORTH);
		add(mainPanel,BorderLayout.SOUTH);
		pack();
		show();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//the listener for the join click
	class JoinListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if((server.getText()).equals("") || port.getText().equals("") || screenName.getText().equals("")) {
				System.out.println("All fields must be filled out.");
				return;
			}

			entryPanel.setVisible(false);
			mainPanel.setVisible(true);
			//TCPClient client = new Client(sender.getText(), recipient.getText(), subject.getText(), message.getText());

			//surrounded by a try catch in case specialized errors wanted to be implemented
			//			try {
			//				SMTP blah = new SMTP(email);
			//			} catch (Exception error) {
			//				System.out.println("Sending failed: " + error);
			//			}
		}
	}

	static public void main(String argv[]) {
		new ChatGUI();
	}
}