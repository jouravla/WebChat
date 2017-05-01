import java.io.*
import java.net.ServerSocket;
import java.net.Socket;
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
        	welcomeSocket.accept();
            new Messenger(welcomeSocket.accept()).start();
        }
    }

    /**
     * Creates a Messenger (new thread) for each individual connection
     */
    private static class Messenger extends Thread {
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
                // Create input and output streams for the socket.
                buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        		outToClient = new DataOutputStream(socket.getOutputStream());	

        		//Ask for a username!
                while (true) {
                    userName = buffIn.readLine();

                	System.out.print("PrintName");
                    outToClient.writeBytes("Enter Unsername: ");
            		outToClient.writeBytes("CRLF");

                    userName = buffIn.readLine();
                    break;
                }
                
                outToClient.writeBytes("Nice Username!");
                
                //Add DataOutputStream to list
                outStreams.add(outToClient);

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