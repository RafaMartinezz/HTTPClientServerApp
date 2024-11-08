# Simple web server and client application

This project implements a simple client-server application where multiple clients connect to a web server to request and retrieve content (an HTML file). The server listens for client requests on a specific port and serves content as specified. Each client receives the content, processes it to remove HTTP headers, and displays it in the system's default browser. 

## Project Structure
The project contains the following main components:

### `SimpleTextWebServer`
- **Purpose**: Listens on a specified port (default: 8080) for incoming client connections and handles them in parallel by delegating each request to an instance of `ClientHandler`.
- **Process**: When a client connects, it sends an HTTP-like request for an HTML file. The server then sends the content of this file (along with HTTP headers) as a response to the client.
  
### `ClientHandler`
- **Purpose**: Each instance of this class handles communication with a single client. This includes receiving the HTTP request, verifying its validity, and responding with the HTML file contents.
- **Process**: Reads the HTML file and formats it as an HTTP response with appropriate headers, which is then sent back to the client.

### `SimpleTextWebClient`
- **Purpose**: Acts as a client that connects to the server, requests a specific HTML file, and processes the response.
- **Process**: Sends an HTTP-like request to the server, receives the response with the HTML content, and saves it locally. It then processes the file by removing HTTP headers and displays it in the system’s default web browser.
  
### `SimpleTextWebClientTask`
- **Purpose**: Manages the creation and execution of multiple client instances.
- **Process**: Launches a predefined number of client instances (e.g., 10), each sending a request to the server and processing the response.

### `MainProgram`
- **Purpose**: The entry point of the application, where the server and clients are initialized and executed.
- **Process**: Starts the server on a separate thread and then launches the client instances using `SimpleTextWebClientTask`.

## Requirements
- **Java 17** or newer.
- **Maven** (for dependency management and building the project).

### 3. Run the Server and Client
To run the application:
1. Start the `MainProgram` class, which will initialize the server and clients.
2. The server will listen for incoming connections from the clients, process requests, and respond with the HTML content.
3. Each client will open the HTML content in the system’s default browser.

## How It Works

1. **Server Initialization**: The server starts on port `8080` and waits for client connections.
2. **Client Request**: Each client connects to the server and requests a specific HTML file (`Contenido.html`).
3. **Response Handling**: The server sends the content back with HTTP headers. Each client saves the content, removes the headers, and displays it in a browser.
4. **Termination**: The server and clients run in parallel until all client tasks are complete.

## Example
Upon running the `MainProgram`, you should see console logs indicating the connection between the clients and the server, and the processed HTML file should open in the browser for each client.

## Notes
- **Port Configuration**: The default port is set to `8080`, but this can be changed in the `SimpleTextWebServer` class.
- **Concurrency**: The server handles multiple clients concurrently.
- **HTML File**: Ensure `Contenido.html` is located in the specified directory for successful operation.