import java.io.*;
import java.net.*;
class TCPClient {

	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket(Inet4Address.getLocalHost(), 6789);

		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
}