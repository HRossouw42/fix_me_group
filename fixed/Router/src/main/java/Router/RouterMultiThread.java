package Router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

import Broker.Broker;
import Market.InstrumentList;

public class RouterMultiThread extends Thread {
    Broker broker;
    int clientId = 0;
    private Socket socket = null;
    private Set<PrintWriter> writers;
    private InstrumentList marketList = null;

    public RouterMultiThread(Socket socket, int counter, Set<PrintWriter> writers, InstrumentList marketList) {
        super("RouterMultiThread");
        this.socket = socket;
        this.clientId = counter;
        this.writers = writers;
        this.marketList = marketList;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            writers.add(out);
//            System.out.println(writers);
            RouterProtocol kkp = new RouterProtocol(clientId, writers);
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            //TODO fix this printwriter

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                if (outputLine.equalsIgnoreCase("List")){
                    System.out.println("REEEEEEE");
                    for (PrintWriter writer : writers) {
                        writer.println(outputLine);
                    }
                }
                else {
                    out.println(outputLine);
                }
                if (outputLine.equals("exit"))
                {
//                     System.out.println("@@@@@@@@@@@@@@@@@@@@@@" + RouterProtocol.index);
                     if (RouterProtocol.index >= 0 && marketList.getPotionNumber() > 0) {
                         marketList.setPotionNumber(marketList.getPotionNumber() - RouterProtocol.index);
                         System.out.println("Market: ACCEPTED. Stock:" + marketList.getPotionNumber());
                     }
                     else {
                         System.out.println("Market: REJECTED.");
                     }
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}