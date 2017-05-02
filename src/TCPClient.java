import java.io.*;
import java.net.*;
class TCPClient {
	String server;
	String port;
	
	BufferedReader inFromUser;
	Socket clientSocket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	
	String sentence;
	String modifiedSentence;
	
	public TCPClient(String server, String port) throws Exception{
		this.server = server;
		this.port = port;

		inFromUser = new BufferedReader(new InputStreamReader(System.in));

		clientSocket = new Socket(Inet4Address.getLocalHost(), 6789);

		outToServer = new DataOutputStream(clientSocket.getOutputStream());

		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			//Server Asks for Username
			modifiedSentence = inFromServer.readLine();	
			System.out.println(modifiedSentence);
			
			//Enter Username
			sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + "\n");
			
			//Server Confirms Username
			modifiedSentence = inFromServer.readLine();
			System.out.println(modifiedSentence);
			
			//Server Says to Start Typing!
			modifiedSentence = inFromServer.readLine();
			System.out.println(modifiedSentence);
			
			while(true) {
				modifiedSentence = inFromServer.readLine();
				System.out.println(modifiedSentence);
			}
	}
	
	void sendToServer(String text) throws Exception{
		outToServer.writeBytes(text + "\n");
		listenFromServer();
	}
	
	void listenFromServer() throws IOException{
		while(true) {
			modifiedSentence = inFromServer.readLine();
			System.out.println(modifiedSentence);
		}
	}
}