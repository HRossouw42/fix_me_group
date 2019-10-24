import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

// initial -> https://www.java-samples.com/showtutorial.php?tutorialid=1167
// official Java multithreading tutorial -> https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
public class Router {

    public static void main(String[] args) throws IOException {

//        if (args.length != 1) {
//            System.err.println("Usage: java KnockKnockServer <port number>");
//            System.exit(1);
//        }

        //int portNumber = Integer.parseInt(args[0]);
        int portNumber = 8000;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber); // create socket object that listens on portNumber
                Socket clientSocket = serverSocket.accept(); // accept connection from client

                // get socket's input/output stream and opens readers and writers on them
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;

            // Initiate conversation with client
            RouterProtocol kkp = new RouterProtocol(); // protocol object keeps track of states
            outputLine = kkp.processInput(null); //runs protocol's process input function
            out.println(outputLine);

            //as long as client/server are connected, the server reads/writes to socket
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("exit")) //ends when client sends this message
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}