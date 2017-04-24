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

		while (true) {
			System.out.println("Please enter the text that you want to send to the server \n");

			sentence = inFromUser.readLine();

			outToServer.writeBytes(sentence + "\n");

			modifiedSentence = inFromServer.readLine();

			System.out.println("RECEIVED FROM SERVER: " + modifiedSentence);
		}
	}
}