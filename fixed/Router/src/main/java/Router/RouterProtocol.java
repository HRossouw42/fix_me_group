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

    public static int index = 0;
    public static int stock = 100;
    
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
                theOutput = "BrokerID: " + clientId + " " + "Stock: " + stock + ". Would you like to 'Buy' or 'Sell'? You can exit at any point if you want";
                state = ISBROKER;
            } else if (clientId >= 200000) {
                theOutput = "MarketID: " + clientId + ". Say 'List' to see your instruments";
                state = ISMARKET;
            }
        } 
        else if(state >= 0 && theInput.equalsIgnoreCase("exit")) {     
            theOutput = "Exiting now...";
            isExit = true;
            state = WAITING;
    }
        else if (state == ISBROKER) {
            if (theInput.equalsIgnoreCase("Buy")) {
                theOutput = "You are about to buy a potion, yes or no (Y/N)?";
                state = CONFIRM;
            } else if (theInput.equalsIgnoreCase("Sell") && index !=0 ) {
                theOutput = "You are about to sell a potion, yes or no (Y/N)";
                state = CONFIRMSELL;
            } else {
                if (!theInput.equalsIgnoreCase("Sell"))
                    theOutput = "You can only 'Buy' or 'Sell' ";  
                else{
                    theOutput = "You have nothing to sell. Press 'Enter' to continue. ";
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
            if (theInput.equalsIgnoreCase("y") || theInput.equalsIgnoreCase("yes")) {
            theOutput = "Item bought! Press 'Enter' to continue.";
            index++;
            stock--;
            state = WAITING;
            }else if (theInput.equalsIgnoreCase("n") || theInput.equalsIgnoreCase("no")) {
                theOutput = "Press 'Enter' to continue.";
                state = WAITING;
            }
        else{
                theOutput = "please enter either yes or no (Y/N)";
                state = CONFIRM;
        }
    }
        else if (state == CONFIRMSELL){
            if (theInput.equalsIgnoreCase("y") || theInput.equalsIgnoreCase("yes")) {
                theOutput = "Item sold! Press 'Enter' to continue.";
                index--;
                stock++;
                state = WAITING;
            } else if (theInput.equalsIgnoreCase("n") || theInput.equalsIgnoreCase("no")) {
                theOutput = "Press 'Enter' to continue.";                
                state = WAITING;
            }
            else{
                theOutput = "please enter either yes or no (Y/N)";
                state = CONFIRMSELL;
            }
        }
         else if (state == ISMARKET) {
            if (theInput.equalsIgnoreCase("List")) {
                theOutput = "POTIONS: " + stock;
                state = WAITING;
            } else {
                theOutput = "You must type 'List' to see your instruments";
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y") || theInput.equalsIgnoreCase("yes")) {
                theOutput = "Press ENTER to continue";
                state = WAITING;
            } else if (theInput.equalsIgnoreCase("n") || theInput.equalsIgnoreCase("no")) {
                theOutput = "Exiting now...";
                isExit = true;
                state = WAITING;
            }
            else {
                theOutput = "please enter either yes or no (Y/N)";
                state = ANOTHER;
            }
        }
        return theOutput;
    }
}