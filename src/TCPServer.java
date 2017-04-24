import java.io.*;
import java.net.*;

class TCPServer{

	public static void main(String argv[]) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(6789);

		System.out.println("Waiting for incoming connection Request...");

		while(true) {
			// Listen for a TCP connection request.
			// Construct an object to process the HTTP request message.
			ChatRequest request = new ChatRequest(welcomeSocket.accept());

			// Create a new thread to process the request.
			Thread thread = new Thread(request);
			// Start the thread.
			thread.start();
		}
	}
}

final class ChatRequest implements Runnable {
	Socket socket;

	//Constructor
	public ChatRequest(Socket socket) throws Exception {
		this.socket = socket;
	}

	// Implement the run() method of the Runnable interface.
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private void processRequest() throws Exception {
		String clientSentence;
		String capitalizedSentence;

		//connectionSocket.getInetAddress();

		BufferedReader inFromClient = new BufferedReader (new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

		while (true){
			clientSentence = inFromClient.readLine();
			System.out.println("RECEIVED FROM CLIENT: " + clientSentence);

			capitalizedSentence = clientSentence.toUpperCase() + "\n"; 
			outToClient.writeBytes(capitalizedSentence);
		}

		// Close streams and socket.
		//os.close();
		//br.close();
		//socket.close();
	}
}