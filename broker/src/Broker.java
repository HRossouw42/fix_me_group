import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Broker {

    int id = 0;

    public static void main(String[] args) throws IOException {

        Broker broker = new Broker();
        broker.run();
    }

    public void run() throws IOException {
        String hostName = "localhost";
        int portNumber = 5000;

        System.out.println("Attempting connection...");
        try (
                Socket kkSocket = new Socket(hostName, portNumber); //open a socket that is connected to the server running on the specific port number
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true); //this looks at the IO of the socket
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream())) //reads from IO
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
    }
}
