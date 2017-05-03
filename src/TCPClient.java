import java.io.*;
import java.net.*;

/**
 * TCP Client
 * 
 * @author Sasha Jouravlev & Jake Beley
 */
class TCPClient {
	String server;
	int port;

	BufferedReader inFromUser;
	Socket clientSocket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;

	String sentence;
	String modifiedSentence;

	ChatGUI gui;

	public TCPClient(String server, int port, ChatGUI gui) throws Exception{
		this.server = server;
		this.port = port;
		this.gui = gui;
	}

	public void start() throws Exception{
		inFromUser = new BufferedReader(new InputStreamReader(System.in));

		clientSocket = new Socket(server, port);

		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		new ListenFromServer().start();
	}

	// Sends the message to the server
	void sendToServer(String msg) throws Exception{
		outToServer.writeBytes(msg + "\n");
	}

	// Displays the text into the gui chat window
	private void display(String msg) {
		gui.append(msg + "\n");
	}

	// Class waits for a message from the server and prints it into the gui (via display)
	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					String msg = inFromServer.readLine();
					display(msg);
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}