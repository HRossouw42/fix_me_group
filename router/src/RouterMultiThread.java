import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class RouterMultiThread extends Thread {
    Broker broker;
    int clientId = 0;
    private Socket socket = null;
    private Set<PrintWriter> writers;

    public RouterMultiThread(Socket socket, int counter, Set<PrintWriter> writers) {
        super("RouterMultiThread");
        this.socket = socket;
        this.clientId = counter;
        this.writers = writers;
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
            System.out.println(writers);
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
//                out.println(outputLine);
                if (outputLine.equals("exit"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}