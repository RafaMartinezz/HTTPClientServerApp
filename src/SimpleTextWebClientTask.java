/**
 * Class that manages the task of launching multiple simple web clients.
 * This class initializes and runs a set of clients that connect to a server
 * and request a specific resource.
 */
public class SimpleTextWebClientTask {
    // Array to store client instances.
    private SimpleTextWebClient[] clientes;

    /**
     * Constructor for SimpleTextWebClientTask.
     * Initializes an array of simple web clients, each configured with a specific
     * HTTP request and a unique numeric identifier.
     */
    public SimpleTextWebClientTask() {
        // Initializes the array to hold space for 10 clients.
        clientes = new SimpleTextWebClient[10];
        // Creates and stores 10 instances of SimpleTextWebClient.
        for (int i = 0; i < 10; i++) {
            // Each client is configured with the same HTTP request and a unique identifier.
            clientes[i] = new SimpleTextWebClient("GET /Contenido.html \n\r Host: localhost", i);
        }
    }

    /**
     * Launches the connection of all clients stored in the array.
     * Each client will attempt to connect to the server and send its configured
     * HTTP request.
     */
    public void lanzarClientes() {
        // Iterates over the client array and calls each one's connect method.
        for (int i = 0; i < 10; i++) {
            clientes[i].connect();
        }
    }
}
