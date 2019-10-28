/* This class handles the current state of the conversation between server and client */

public class RouterProtocol {
    private static final int WAITING = 0;
//    private static final int SENTKNOCKKNOCK = 1;
//    private static final int SENTCLUE = 2;
//    private static final int ANOTHER = 3;

    private static final int ISBROKER = 1;
    private static final int ISMARKET = 2;
    private static final int ANOTHER = 3;

    private static final int BROKERBUY = 4;
    private static final int BROKERSELL = 5;

    private static final int NUMJOKES = 5;
    int clientId = 0;
    private int state = WAITING;
    private int currentJoke = 0;
    private String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    private String[] answers = {"Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"};

    public RouterProtocol(int id) {
        this.clientId = id;
    }
    //TODO fetch a list of brokers and markets and add them here

    public String processInput(String theInput) {
        String theOutput = null;


        if (state == WAITING) {
            if (clientId >= 100 && clientId <= 200) {
                theOutput = "Your BrokerID is: " + clientId + " " + " Would you like to 'Buy' or 'Sell'?";
                state = ISBROKER;
            } else if (clientId >= 200) {
                theOutput = "Your MarketID is: " + clientId + " Use 'List' to see your instruments";
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

/*
    public String OLDprocessInput(String theInput) {
        String theOutput = null;


        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                state = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                        "Try again. Knock! Knock!";
            }
        } else if (state == SENTCLUE) {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = ANOTHER;
            } else {
                theOutput = "You're supposed to say \"" +
                        clues[currentJoke] +
                        " who?\"" +
                        "! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK;
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENTKNOCKKNOCK;
            } else {
                theOutput = "exit";
                state = WAITING;
            }
        }
        return theOutput;
    }
 */
}
