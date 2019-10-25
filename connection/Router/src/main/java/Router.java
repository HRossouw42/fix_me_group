
import java.io.IOException;
import java.net.ServerSocket;

// initial -> https://www.java-samples.com/showtutorial.php?tutorialid=1167
// official Java multithreading tutorial -> https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
public class Router {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        int marketPortNumber = 5001;
        boolean listening = true;

        System.out.println("Connect at least 1 Market and 1 Broker");
        try (
                //currently requires at least these 2 to be connected before continuing
                ServerSocket marketSocket = new ServerSocket(marketPortNumber);
                ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {
            while (listening) {
                new RouterMultiThread(serverSocket.accept()).start();
                new RouterMultiThread(marketSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}