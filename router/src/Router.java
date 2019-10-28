import java.io.IOException;
import java.net.ServerSocket;

// initial -> https://www.java-samples.com/showtutorial.php?tutorialid=1167
// official Java multithreading tutorial -> https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
public class Router {

    public static void main(String[] args) throws IOException {

        int routerCounter = 100000; //for IDs of connections
        int marketCounter = 200000;

        int marketPortNumber = 5001;
        int brokerPortNumber = 5000;

        boolean listening = true;

        System.out.println("Connect at least 1 Market and 1 Broker");
        try (
                //currently requires at least these 2 to be connected before continuing
                ServerSocket marketSocket = new ServerSocket(marketPortNumber);
                ServerSocket serverSocket = new ServerSocket(brokerPortNumber)
        ) {
            while (listening) {
                routerCounter++; //assign IDs
                marketCounter++;
                new RouterMultiThread(serverSocket.accept(), routerCounter).start();
                new RouterMultiThread(marketSocket.accept(), marketCounter).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on ports:" + brokerPortNumber + " " + marketPortNumber);
            System.exit(-1);
        }
    }
}