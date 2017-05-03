import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/**
 * This is a GUI
 * @author Sasha Jouravlev
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

	public ChatGUI() {
		super("Java WebChat");

		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		//~~~~~~~~Entry Panel Components~~~~~~~~~
		entryPanel.add(serverLabel);
		entryPanel.add(server);
		entryPanel.add(portLabel);
		entryPanel.add(port);
		entryPanel.add(joinButton);
		joinButton.addActionListener(this);

		//~~~~~~~Main Panel Components~~~~~~~~~
		chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));
		chatSubPanel1.add(chat);
		chatSubPanel2.add(message);
		chatSubPanel2.add(sendButton);
		chatPanel.add(chatSubPanel1);
		chatPanel.add(chatSubPanel2);

		message.addActionListener(this);
		sendButton.addActionListener(this);

		chat.setEditable(false);

		mainPanel.add(entryPanel);
		mainPanel.add(chatPanel);
		add(mainPanel);

		chatPanel.setEnabled(false);
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

			entryPanel.setEnabled(false);
			chatPanel.setEnabled(true);
			message.requestFocus();

			try {
				client = new TCPClient(server.getText(), Integer.parseInt(port.getText()), this);
				client.start();
			} catch (Exception e) {

			}
			System.out.println("Client created");
		}

		if(obj == sendButton || obj == message){
			if(message.getText().equals("")){
				return;
			}

			try {
				client.sendToServer(message.getText());
			} catch (Exception e) {}

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