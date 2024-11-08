import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that implements a simple web server running on a separate thread.
 * This server listens on a predefined port and accepts client connections,
 * delegating each client's request to a separate instance of ClientHandler.
 */
public class SimpleTextWebServer extends Thread {
    // Port on which the server will listen for incoming connections.
    final static int PORT = 8080;
    // ServerSocket used to accept client connections.
    private ServerSocket serverSocket;

    /**
     * Constructor for SimpleTextWebServer.
     * Initializes the ServerSocket that will listen on the specified port.
     */
    public SimpleTextWebServer() {
        try {
            // Attempts to create a ServerSocket listening on the PORT constant.
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            // Prints stack trace if there's an issue starting the ServerSocket.
            e.printStackTrace();
        }
    }

    /**
     * Run method that executes when the thread starts.
     * This is where the server starts listening for incoming connections and
     * accepting them.
     */
    @Override
    public void run() {
        // Prints a message to the console indicating that the server is ready and
        // listening.
        System.out.println("Server: Listening on port " + PORT);
        // Infinite loop to continuously accept and handle client connections.
        while (true) {
            try {
                // Waits for and accepts an incoming connection from a client.
                Socket clientSocket = serverSocket.accept();
                // Prints a message indicating a client has connected.
                System.out.println("Server: Client connected");
                // Creates a new ClientHandler instance to handle the client connection.
                // Starts the ClientHandler thread to manage the connection concurrently.
                new ClientHandler(clientSocket).start();
            } catch (IOException e) {
                // Prints stack trace if there's an issue accepting connections.
                e.printStackTrace();
            }
        }
    }
}
