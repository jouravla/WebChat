import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class TCPServer {
    /**
     * Stores the DataOutputStream for each client connected to the server
     */
	private static HashSet<DataOutputStream> outStreams = new HashSet<DataOutputStream>();

    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("The chat server is running.");

        while (true) {
        	System.out.println("In loop");
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
            	//Get a reference to the socket's input and output streams.
        		InputStream is = socket.getInputStream(); 
        		DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

        		//Set up input stream filters.
        		BufferedReader buffIn = new BufferedReader(new InputStreamReader(is));

        		//Ask for a username!
                outToClient.writeBytes("Enter Unsername: " + "\n");
                userName = buffIn.readLine();
                System.out.println(userName);
                
                outToClient.writeBytes("Hello " + userName + " " + "Welcome to the ChatRoom!");
                
                //Add DataOutputStream to list
                outStreams.add(outToClient);
                
                outToClient.writeBytes("You can now chat to everyone here!");
                
                //Notify everyone that someone joined the chatroom!
                for (DataOutputStream stream : outStreams) {
                    stream.writeBytes(userName + ": Has joined the chatroom!");
                }

                // Send messages to all clients!
                while (true) {
                    String clientSentence = buffIn.readLine();
                    System.out.println("RECEIVED FROM: "+ userName + " " + clientSentence);
                    for (DataOutputStream stream : outStreams) {
                        stream.writeBytes(userName + ": " + clientSentence);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } 
        }
    }
}