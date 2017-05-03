import java.util.List;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {
	// Stores the DataOutputStream for each client connected to the server
	private static List<DataOutputStream> outStreams = new ArrayList<DataOutputStream>();

	public static void main(String[] args) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("The chat server is running.");

		while (true) {
			Messenger request = new Messenger(welcomeSocket.accept());

			Thread thread = new Thread(request);
			// Start the thread.
			thread.start();
		}
	}

	/**
	 * Creates a Messenger (new thread) for each individual connection
	 */
	private static class Messenger implements Runnable {
		private String userName;
		private Socket socket;
		private DataOutputStream outToClient;
		private BufferedReader buffIn;

		public Messenger(Socket welcomeSocket) {
			System.out.println("Setting Up Socket");
			this.socket = welcomeSocket;
		}

		public void run() {
			try {
				// Get a reference to the socket's input and output streams.
				buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new DataOutputStream(socket.getOutputStream());
				
				// Ask for a username!
				outToClient.writeBytes("Enter Username: " + "\n");

				// Read in username
				userName = buffIn.readLine();
				System.out.println(userName);

				// Confirm
				outToClient.writeBytes("Hello " + userName + ". Welcome to the ChatRoom!\n");

				// Add DataOutputStream to list
				outStreams.add(outToClient);

				outToClient.writeBytes("You can now chat to everyone here!" + "\n");

				// Notify everyone that someone joined the chatroom!
				for (DataOutputStream stream : outStreams) {
					stream.writeBytes(userName + ": Has joined the chatroom!" + "\n");
				}

				// Send messages to all clients!
				while (true) {
					String clientSentence = buffIn.readLine();
					System.out.println("RECEIVED FROM: " + userName + " " + clientSentence);
					for (DataOutputStream stream : outStreams) {
						stream.writeBytes(userName + ": " + clientSentence);
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				// Remove user's outstream since he quit the chat!				
				if (!outStreams.remove(outToClient)) {
					System.out.println("Server is having trouble removing user! Server needs to be manually restarted");
				}
				
				// Try to close the socket!
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Notify the current active users that someone left the
				// chatroom!
				for (DataOutputStream stream : outStreams) {
					try {
						if (stream != null) {
							stream.writeBytes(userName + ": Has left the chatroom!" + "\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}