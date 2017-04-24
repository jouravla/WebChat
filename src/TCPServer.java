import java.io.*;
import java.net.*;

class TCPServer{

	public static void main(String argv[]) throws Exception {

		String clientSentence;
		String capitalizedSentence;

		ServerSocket welcomeSocket = new ServerSocket(6789);

		System.out.println("Waiting for incoming connection Request...");

		while(true) {
			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient =new BufferedReader
					(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient =new DataOutputStream(connectionSocket.getOutputStream());

			while (true)
			{
				clientSentence = inFromClient.readLine();

				System.out.println("RECEIVED FROM CLIENT: " + clientSentence);

				capitalizedSentence = clientSentence.toUpperCase() + "\n"; 

				outToClient.writeBytes(capitalizedSentence);
			}
		}
	}
}
