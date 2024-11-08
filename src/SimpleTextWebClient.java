import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Class representing a simple text client that connects to a server,
 * sends an HTTP request, and processes the received response.
 */
public class SimpleTextWebClient {
    private int numeroCliente; // Unique identifier for the client.
    private String httpSolicitude; // HTTP request to send to the server.
    private DataInputStream entrada; // Input stream to receive data from the server.
    private DataOutputStream salida; // Output stream to send data to the server.
    private Socket socket; // Socket for server connection.
    private String host; // Host address the client will connect to.

    /**
     * Constructor for SimpleTextWebClient.
     * 
     * @param httpSolicitude The HTTP request to be sent.
     * @param numeroCliente  The unique identifier for this client instance.
     */
    public SimpleTextWebClient(String httpSolicitude, int numeroCliente) {
        this.httpSolicitude = httpSolicitude;
        this.host = "localhost"; // Default connection to localhost.
        this.numeroCliente = numeroCliente;
    }

    /**
     * Connects to the server, sends the HTTP request, receives the response,
     * and processes the received HTML content.
     */
    public void connect() {
        try {
            // Establish connection to the server on the specified port.
            this.socket = new Socket(host, SimpleTextWebServer.PORT);
            System.out.println("Client " + numeroCliente + ": Created output socket");

            // Initialize input and output streams for the socket.
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            // Send HTTP request to the server.
            salida.writeUTF(httpSolicitude);
            System.out.println("Client " + numeroCliente + ": Sent request");

            // Prepare an HTML file to store the server response.
            File paginaHtml = new File("Client" + numeroCliente + "_page.html");

            // Read server response and write it into the HTML file.
            FileWriter writer = new FileWriter(paginaHtml);
            System.out.println("Client " + numeroCliente + ": Response read");
            writer.write(entrada.readUTF());
            writer.close();

            // Remove HTTP headers if present in the response file.
            comprobarEliminarCabecera(paginaHtml);

            System.out.println("Client " + numeroCliente + ": File ready to be read");

            // Open the HTML file in the system's default browser.
            abrirArchivo(paginaHtml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the specified file in the system's default web browser.
     * 
     * @param file The HTML file to be opened.
     */
    public void abrirArchivo(File file) {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder pb;

            // Determine the command based on the OS.
            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd", "/c", "start", file.getPath());
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", file.getPath());
            } else if (os.contains("nix") || os.contains("nux")) {
                pb = new ProcessBuilder("xdg-open", file.getPath());
            } else {
                throw new UnsupportedOperationException("Unsupported OS: " + os);
            }

            // Execute the command to open the file in a browser.
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the specified file, removes HTTP headers if present,
     * and rewrites the modified content back to the same file.
     * 
     * @param file The HTML file to process.
     */
    public void comprobarEliminarCabecera(File file) {
        StringBuilder contenidoHtml = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            // Read file line by line and exclude HTTP headers.
            while ((linea = reader.readLine()) != null) {
                if (!linea.equals("HTTP/1.1 200 OK") && !linea.equals("Content-Type: text/html")) {
                    contenidoHtml.append(linea).append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions if they occur.
        }

        // Write the modified content back to the file.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(contenidoHtml.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
