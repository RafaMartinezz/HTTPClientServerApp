import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

/**
 * The ClientHandler class handles client requests in a separate thread.
 * Each instance of this class is associated with a single connected client.
 */
public class ClientHandler extends Thread {
    Socket clientSocket; // Socket for the connected client.
    String solicitud; // Stores the client request (unused in the provided code).
    DataInputStream entrada; // Input stream to read data sent by the client.
    DataOutputStream salida; // Output stream to send data to the client.

    // Reference header for comparing the client request.
    String cabecerasReferencia = "GET /Contenido.html \n\r Host: localhost";

    /**
     * Constructor for the ClientHandler class.
     * Initializes input and output streams based on the client's socket.
     *
     * @param socket Socket of the connected client.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            // Initialize input and output streams for communication with the client.
            entrada = new DataInputStream(clientSocket.getInputStream());
            salida = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // Prints stack trace if an I/O exception occurs during stream initialization.
        }
    }

    /**
     * The run method is executed when the thread starts.
     * Reads the client's request, checks if it matches the reference header,
     * and if it does, sends an HTTP response with the content of an HTML file.
     */
    public void run() {
        try {
            System.out.println("Handler: Handler launched");

            // Reads the message sent by the client.
            String mensajeRecibido = entrada.readUTF();

            // Checks if the received message matches the reference request.
            if (mensajeRecibido.equals(cabecerasReferencia)) {
                // Prepares the HTTP response with the content of the HTML file.
                String httpRespuesta = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + leerArchivo().toString();

                // Sends the response to the client.
                salida.writeUTF(httpRespuesta);
                System.out.println("Handler: Response sent");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Prints stack trace for I/O exceptions.
        }
    }

    /**
     * Reads the content of an HTML file and returns it in a StringBuilder.
     *
     * @return StringBuilder containing the HTML file content.
     */
    public StringBuilder leerArchivo() {
        StringBuilder contenidoHtml = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("UD4_Practica1/src/Contenido.html"))) {
            String linea;

            // Reads the file line by line and appends each line to the StringBuilder.
            while ((linea = reader.readLine()) != null) {
                contenidoHtml.append(linea).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Improved error handling
        }
        return contenidoHtml; // Returns the HTML content as a StringBuilder.
    }
}
