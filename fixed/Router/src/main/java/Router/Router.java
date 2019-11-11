package Router;

import Market.InstrumentList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


// initial -> https://www.java-samples.com/showtutorial.php?tutorialid=1167
// official Java multithreading tutorial -> https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
public class Router {

    public Set<PrintWriter> writers = new HashSet<>();

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

//            marketList.setPotionNumber(256);
//            marketList.setPotionName("Mana");
//            marketList.addPotionNumber();

            Timer timer = new Timer();
            timer.schedule(new updateMarket(serverOutputStream, marketList), 0, 3000);

//            serverOutputStream.writeObject(marketList);

            while (listening) {
                routerCounter++; //assign IDs
                marketCounter++;
                new RouterMultiThread(serverSocket.accept(), routerCounter, writers, marketList).start();
                new RouterMultiThread(marketSocket.accept(), marketCounter, writers, marketList).start();

            }
        } catch (IOException e) {
            System.err.println("Could not listen on ports:" + brokerPortNumber + " " + marketPortNumber);
            System.exit(-1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

class updateMarket extends TimerTask {
    ObjectOutputStream serverOutputStream;
    InstrumentList marketList;

    public updateMarket(ObjectOutputStream serverOutputStream, InstrumentList marketList) {
        this.serverOutputStream = serverOutputStream;
        this.marketList = marketList;
    }

    public void run() {
        try {
            //System.out.println(marketList.getPotionNumber());
            serverOutputStream.writeObject(marketList);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}