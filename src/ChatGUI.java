import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;


/**
 * This is a simple GUI for the user to input their information
 * @author Sasha Jouravlev
 *
 */
public class ChatGUI extends JFrame implements ActionListener{
	Panel mainPanel = new Panel();
	Panel chatPanel = new Panel();
	Panel entryPanel = new Panel();

	TCPClient client;

	//~~~~~~~~Entry Panel Components~~~~~~~~~
	private Label serverLabel = new Label("Server:"); 
	private TextField server = new TextField("localhost", 20);

	private Label portLabel = new Label("Port:");
	private TextField port = new TextField("6789", 20);

	private Button joinButton = new Button("Join");

	//~~~~~~~Main Panel Components~~~~~~~~~
	private Label chatLabel = new Label("Chat");
	private TextArea chat = new TextArea();

	private TextField message = new TextField("",50);

	private Button sendButton = new Button("Send");

	public ChatGUI() {
		super("Java WebChat");

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
		joinButton.addActionListener(this);

		//~~~~~~~Main Panel Components~~~~~~~~~
		chatPanel.add(chatLabel);
		chatPanel.add(chat);
		chatPanel.add(message);
		chatPanel.add(sendButton);
		sendButton.addActionListener(this);

		chat.setEditable(false);

		mainPanel.add(entryPanel);
		mainPanel.add(chatPanel);
		add(mainPanel);

		chatPanel.setVisible(false);
		pack();
		show();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();

		if(obj == joinButton){
			if((server.getText()).equals("") || port.getText().equals("")) {
				System.out.println("All fields must be filled out.");
				return;
			}
			
			entryPanel.setVisible(false);
			chatPanel.setVisible(true);

			try {
				client = new TCPClient(server.getText(), Integer.parseInt(port.getText()), this);
				client.start();
			} catch (Exception e) {
				
			}
			System.out.println("Client created");
		}

		if(obj == sendButton){
			try {
				client.sendToServer(message.getText());
			} catch (Exception e) {
				System.out.println();
			}

			message.setText("");
		}
	}

	void append(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}

	static public void main(String argv[]) {
		new ChatGUI();
	}
}