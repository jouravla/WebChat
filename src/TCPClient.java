import java.io.*;
import java.net.*;

/**
 * TCP Client for WebChat
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

	//Constructor
	public TCPClient(String server, int port, ChatGUI gui) throws Exception{
		this.server = server;
		this.port = port;
		this.gui = gui;
	}

	//Method that is executed upon creation of a Client from the GUI
	public boolean start(){
		try{
			inFromUser = new BufferedReader(new InputStreamReader(System.in));

			clientSocket = new Socket(server, port);

			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			new ListenFromServer().start();
			return true;
		}
		catch(Exception e){
			return false;
		}

	}

	//Sends the message to the server
	void sendToServer(String msg) throws Exception{
		outToServer.writeBytes(msg + "\n");
	}

	//Displays the text into the GUI chat
	private void display(String msg) {
		gui.append(msg + "\n");
	}

	//Class waits for a message from the server and prints it into the GUI (via display)
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