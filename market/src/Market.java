import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Market {

    int id = 0;

    public void run() throws IOException {
        String hostName = "localhost";
        int portNumber = 5001;

        //adding instruments
        InstrumentList health = new InstrumentList(150, "Health");
        System.out.println("Potion number before= "
                + health .getPotionNumber());
        System.out.println("Potion name before= "
                + health .getPotionName());


        System.out.println("Attempting connection...");

        try (
                Socket kkSocket = new Socket(hostName, portNumber); //open a socket that is connected to the server running on the specific port number
        ) {
            ObjectOutputStream clientOutputStream = new
                    ObjectOutputStream(kkSocket.getOutputStream());
            ObjectInputStream clientInputStream = new
                    ObjectInputStream(kkSocket.getInputStream());

            clientOutputStream.writeObject(health);

            health= (InstrumentList) clientInputStream.readObject();

            System.out.println("Potion number after= "
                    + health .getPotionNumber());
            System.out.println("Potion name after= "
                    + health .getPotionName());

//            System.out.println("CLOSING PORTS");
//            clientOutputStream.close();
//            clientInputStream.close();

            /*
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            //implement communication between client and server
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer); //server speaks first, so client must listen
                if (fromServer.equals("exit")) //if server says this, end
                    break;

                //to get id
                if (id == 0) {
                    String[] getID = fromServer.split(" ");
                    id = Integer.parseInt(getID[1]);
                }

                fromUser = stdIn.readLine(); //take input
                if (fromUser != null) {
                    System.out.println(id + ": " + fromUser);
                    out.println(fromUser);
                }
            }

             */
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e);
        }
        /*
        try (

                Socket kkSocket = new Socket(hostName, portNumber); //open a socket that is connected to the server running on the specific port number
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            //implement communication between client and server
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer); //server speaks first, so client must listen
                if (fromServer.equals("exit")) //if server says this, end
                    break;

                //to get id
                if (id == 0) {
                    String[] getID = fromServer.split(" ");
                    id = Integer.parseInt(getID[1]);
                }

                fromUser = stdIn.readLine(); //take input
                if (fromUser != null) {
                    System.out.println(id + ": " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

         */
    }

    public static void main(String[] args) throws IOException {

        Market market = new Market();
        market.run();
    }
}
