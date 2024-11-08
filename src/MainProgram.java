/**
 * Main class that starts the server and clients.
 * This class is responsible for launching the server in a separate thread and
 * then launching client tasks that will connect to the server.
 */
public class MainProgram {

    /**
     * Main entry point for the program.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // Instance of the simple web server
        SimpleTextWebServer server = new SimpleTextWebServer();

        // Instance of the client task that simulates multiple clients connecting to the
        // server
        SimpleTextWebClientTask tareas = new SimpleTextWebClientTask();

        // Starts the server in a new thread
        server.start();
        System.out.println("Main: Server launched as an independent process");

        // Launches client tasks that will connect to the server
        tareas.lanzarClientes();
    }
}
