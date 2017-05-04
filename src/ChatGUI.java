import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * This is a simple GUI for WebChat. It consists of a server/port entry field, a chat window, and a 
 * message line.
 * 
 * @author Sasha Jouravlev & Jake Beley
 */
public class ChatGUI extends JFrame implements ActionListener{
	Panel mainPanel = new Panel();
	Panel chatPanel = new Panel();
	Panel chatSubPanel1 = new Panel();
	Panel chatSubPanel2 = new Panel();
	Panel entryPanel = new Panel();

	TCPClient client;

	//~~~~~~~~Entry Panel Components~~~~~~~~~
	private Label serverLabel = new Label("Server:"); 
	private TextField server = new TextField("localhost", 20);

	private Label portLabel = new Label("Port:");
	private TextField port = new TextField("6789", 20);

	private Button joinButton = new Button("Join");

	//~~~~~~~Main Panel Components~~~~~~~~~
	private TextArea chat = new TextArea(10,67);
	private TextField message = new TextField("",60);
	private Button sendButton = new Button("Send");

	//Constructor
	public ChatGUI() {
		super("WebChat");
		
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		//~~~~~~~~Entry Panel Components~~~~~~~~~
		entryPanel.add(serverLabel);
		entryPanel.add(server);
		entryPanel.add(portLabel);
		entryPanel.add(port);
		entryPanel.add(joinButton);

		//~~~~~~~Main Panel Components~~~~~~~~~
		chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));
		chatSubPanel1.add(chat);
		chatSubPanel2.add(message);
		chatSubPanel2.add(sendButton);
		chatPanel.add(chatSubPanel1);
		chatPanel.add(chatSubPanel2);

		//~~~~~~~Action Listeners~~~~~~~
		joinButton.addActionListener(this);
		message.addActionListener(this);
		sendButton.addActionListener(this);

		chat.setEditable(false);		
		chatPanel.setEnabled(false);

		mainPanel.add(entryPanel);
		mainPanel.add(chatPanel);
		add(mainPanel);

		pack();
		show();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Action listeners for the Send and Join functionalities 
	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();

		//Join Action
		if(obj == joinButton){
			//In case of missing connection information
			if((server.getText()).equals("") || port.getText().equals("")) {
				chat.setText("Server and Port fields must be filled out.");
				return;
			}

			//Create a client
			try {
				client = new TCPClient(server.getText(), Integer.parseInt(port.getText()), this);
				if(!client.start()){
					chat.setText("Failed to establish server connection.");
					return;
				}
				System.out.println("Client created");
			} catch (Exception e) {
				chat.setText("Failed to establish client connection.");
				return;
			}

			//Reset chat, enable bottom, disable top
			chat.setText("");
			entryPanel.setEnabled(false);
			chatPanel.setEnabled(true);
			message.requestFocus();
		}

		//Send Action
		if(obj == sendButton || obj == message){
			//Don't send if text is empty
			if(message.getText().equals("")){
				return;
			}

			//Send message to server
			try {
				client.sendToServer(message.getText());
				message.setText("");
			} catch (Exception e) {
				chat.append("Failed to send message.");
			}	
		}
	}

	//Append method utilized by the client
	void append(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}

	static public void main(String argv[]) {
		new ChatGUI();
	}
}