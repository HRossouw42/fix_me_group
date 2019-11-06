package Router;

import java.io.PrintWriter;
import java.util.Set;

public class RouterProtocol {
    private Set<PrintWriter> writers;

    private static final int WAITING = 0;
//    private static final int SENTKNOCKKNOCK = 1;
//    private static final int SENTCLUE = 2;
//    private static final int ANOTHER = 3;

    private static final int ISBROKER = 1;
    private static final int ISMARKET = 2;
    private static final int ANOTHER = 3;

    private static final int BROKERBUY = 4;
    private static final int BROKERSELL = 5;

    int clientId = 0;
    private int state = WAITING;

    public RouterProtocol(int id, Set<PrintWriter> writers) {
        this.clientId = id;
        this.writers = writers;
    }
    //TODO fetch a list of brokers and markets and add them here

    public String processInput(String theInput) {
        String theOutput = null;


        if (state == WAITING) {
            if (clientId >= 100000 && clientId <= 200000) {
                theOutput = "BrokerID: " + clientId + " " + " Would you like to 'Buy' or 'Sell'?";
                state = ISBROKER;
            } else if (clientId >= 200000) {
                theOutput = "MarketID: " + clientId + " Use 'List' to see your instruments";
                state = ISMARKET;
            }
        } else if (state == ISBROKER) {
            if (theInput.equalsIgnoreCase("Buy")) {
                theOutput = "[TODO LIST OF STUFF YOU CAN BUY]";
                state = BROKERBUY; //TODO check input
            } else if (theInput.equalsIgnoreCase("Sell")) {
                theOutput = "[TODO LIST OF STUFF YOU CAN SELL]";
                state = BROKERSELL; //TODO check input
            } else {
                theOutput = "You must either 'Buy' or 'Sell'";
            }
        } else if (state == BROKERBUY) {
            theOutput = "Item bought! Continue? (Y/N)";
            state = ANOTHER;
        } else if (state == BROKERSELL) {
            theOutput = "Item sold! Continue? (Y/N)";
            state = ANOTHER;
        } else if (state == ISMARKET) {
            if (theInput.equalsIgnoreCase("List")) {
                theOutput = "[TODO LIST MARKET INSTRUMENTS]";
                state = WAITING;
            } else {
                theOutput = "You must type 'List' to see your instruments";
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Press ENTER to continue";
                state = WAITING;
            } else {
                theOutput = "exit";
                state = WAITING;
            }
        }
        return theOutput;
    }
}