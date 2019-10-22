import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//https://www.java-samples.com/showtutorial.php?tutorialid=1167
public class router{

    public static void main(String args[]){
        // data to be sent
        String data = "ACCEPTED! You may totally purchase your legal goods!";
        //starting router server
        try {
            //setup server
            ServerSocket server = new ServerSocket(5000);
            Socket socket = server.accept();
            System.out.println("Router has connected!\n");

            //PrintWriter prints formatted representations of objects to a text-output stream
            //https://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html#PrintWriter(java.io.OutputStream)
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Sending string: ["+data + "]\n");
            out.print(data);

            //end server
            out.close();
            socket.close();
            server.close();
        }
        catch (Exception e) {
            System.out.println("Whoops! Router failed!\n");
        }
    }
}