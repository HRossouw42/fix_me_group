package Router;

import java.lang.*;
import java.io.*;
import java.net.*;

class Server {
    public static void main(String args[]){
        String data = "TACO";
        try{
            ServerSocket serve = new ServerSocket(5000);
            Socket sock = serve.accept();
            System.out.print("I'm in: " + serve.getLocalPort() +"\n");
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            System.out.print("Sending string: " + data + "\n");
            out.print(data);
            out.close();
            sock.close();
            serve.close();
        }
        catch(Exception e){
            System.out.print("NOPE");
        }
    }
}