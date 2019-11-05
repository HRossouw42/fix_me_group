import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


// initial -> https://www.java-samples.com/showtutorial.php?tutorialid=1167
// official Java multithreading tutorial -> https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
public class Router {

    public Set<PrintWriter> writers = new HashSet<>();

    public static void marketConnection() {

        InstrumentList employee = null;

        try {

            ServerSocket socketConnection = new ServerSocket(11111);

            System.out.println("Server Waiting");

            Socket pipe = socketConnection.accept();

            ObjectInputStream serverInputStream = new
                    ObjectInputStream(pipe.getInputStream());

            ObjectOutputStream serverOutputStream = new
                    ObjectOutputStream(pipe.getOutputStream());

            employee = (InstrumentList) serverInputStream.readObject();

            employee.setPotionNumber(256);
            employee.setPotionName("John");

            serverOutputStream.writeObject(employee);

            serverInputStream.close();
            serverOutputStream.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {

        int routerCounter = 100000; //for IDs of connections
        int marketCounter = 200000;

        int marketPortNumber = 5001;
        int brokerPortNumber = 5000;

        Set<PrintWriter> writers = new HashSet<>();

        boolean listening = true;

        InstrumentList marketList = null;

        System.out.println("Connect at least 1 Market and 1 Broker");
        try (
                //currently requires at least these 2 to be connected before continuing
                ServerSocket serverSocket = new ServerSocket(brokerPortNumber);
                ServerSocket marketSocket = new ServerSocket(marketPortNumber)
        ) {
            Socket pipe = marketSocket.accept();

            ObjectInputStream serverInputStream = new
                    ObjectInputStream(pipe.getInputStream());

            ObjectOutputStream serverOutputStream = new
                    ObjectOutputStream(pipe.getOutputStream());

            marketList = (InstrumentList) serverInputStream.readObject();

            marketList.setPotionNumber(256);
            marketList.setPotionName("Mana");
            marketList.addPotionNumber();

            serverOutputStream.writeObject(marketList);

            while (listening) {
                routerCounter++; //assign IDs
                marketCounter++;
                new RouterMultiThread(serverSocket.accept(), routerCounter, writers).start();
                new RouterMultiThread(marketSocket.accept(), marketCounter, writers).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on ports:" + brokerPortNumber + " " + marketPortNumber);
            System.exit(-1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}