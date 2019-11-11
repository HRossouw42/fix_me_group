package Router;
import java.io.PrintWriter;
import java.util.Set;

public class RouterProtocol {
    private Set<PrintWriter> writers;

    private static final int WAITING = 0;

    private static final int ISBROKER = 1;
    private static final int ISMARKET = 2;
    private static final int ANOTHER = 3;

    private static final int CONFIRM = 4;
    private static final int CONFIRMSELL = 5;

    // private static final int BROKERBUY = 6;
    // private static final int BROKERSELL = 7;

    // private static final int MARKETREJECT = 8;
    // private static final int MARKETEXEC = 9;

    // String[] Items = {"Goldium", "Carbonadium" , "Silverite", "BoonBucks"};
    // String[] Prices = {"420", "6900" , "10110", "8888"};
    // String[] Market = {"200001", "200002", "200003", "200004"};
    // String[] Number = {"1", "2", "3", "4"};
    public static int index = 0;
 
    int clientId = 0;
    private int state = WAITING;
    static boolean isExit = false;
 
    public RouterProtocol(int id, Set<PrintWriter> writers) {
        this.clientId = id;
        this.writers = writers;
    }
    //TODO fetch a list of brokers and markets and add them here

    public String processInput(String theInput) {
        if (isExit){
            index = 0;
        }
        isExit = false;
        String theOutput = null;
        if (state == WAITING) {
            if (clientId >= 100000 && clientId <= 200000) {
                theOutput = "BrokerID: " + clientId + " " + "Stock:" + index + " Would you like to 'Buy' or 'Sell'?";
                state = ISBROKER;
            } else if (clientId >= 200000) {
                theOutput = "MarketID: " + clientId + " Use 'List' to see your instruments";
                state = ISMARKET;
            }
        } else if (state == ISBROKER) {
            if (theInput.equalsIgnoreCase("Buy")) {
                theOutput = "Buy potion y/n";
                // state = BROKERBUY; //TODO check input
                state = CONFIRM;
            } else if (theInput.equalsIgnoreCase("Sell") && index !=0 ) {
                theOutput = "Sell potion y/n";
                // state = BROKERSELL; //TODO check input
                state = CONFIRMSELL;
            } else {
                if (!theInput.equalsIgnoreCase("Sell"))
                    theOutput = "You must either 'Buy' or 'Sell'";  
                else{
                    theOutput = "You have nothing to sell. Press 'Enter' to continue.";
                    state = WAITING;
                }
            }
        // } else if (state == BROKERBUY) {
        //     while(index < Items.length) {
        //         if (theInput.equalsIgnoreCase(Items[index])) {
        //             String checksum = Items[index] + Prices[index] + clientId + Number[index];
        //             theOutput = "your order is: " + checksum + ". are you sure you wish to purchase this? (Y/N)";
        //             state = CONFIRM;
        //             break;
        //         }
        //         else{
        //             theOutput = "Please choose an actual existing item - press Enter to go back";
        //             state = ISBROKER;
        //         }
        //         index++;
        //     }
        }
        else if (state == CONFIRM){
            if (theInput.equalsIgnoreCase("y")) {
            theOutput = "Item bought! Press 'Enter' to continue.";
            index++;
            state = WAITING;
            }else if (theInput.equalsIgnoreCase("n")) {
                theOutput = "exit";
                isExit = true;
                state = WAITING;
            }
        else{
                theOutput = "please enter either y or n (Y/N)";
                state = CONFIRM;
        }
    }
        // else if (state == BROKERSELL) {
        //     while(index < Items.length) {
        //         if (theInput.equalsIgnoreCase(Items[index])) {
        //             String checksum = Items[index] + Prices[index] + clientId + Number[index];
        //             theOutput = "your order is: " + checksum + ". are you sure you wish to sell this? (Y/N)";
        //             state = CONFIRMSELL;
        //             break;
        //         }
        //         else{
        //             theOutput = "Please choose an actual existing item - press Enter to go back";
        //             state = ISBROKER;
        //         }
        //         index++;
        //     }
        // }
        else if (state == CONFIRMSELL){
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Item sold! Press 'Enter' to continue.";
                // state = ANOTHER;
                index--;
                state = WAITING;
            } else if (theInput.equalsIgnoreCase("n")) {
                theOutput = "exit";
                isExit = true;
                state = WAITING;
            }
            else{
                theOutput = "please enter either y or n (Y/N)";
                state = CONFIRMSELL;
            }
        }
         else if (state == ISMARKET) {
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
                isExit = true;
                state = WAITING;
            }
        }
        return theOutput;
    }
}