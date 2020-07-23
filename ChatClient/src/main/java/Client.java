import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
   private final String serverName;
   private final int serverPort;
   private Socket socket;
   private InputStream serverIn;
   private OutputStream serverOut;
   private BufferedReader bufferedIn;

   public Client(String serverName, int serverPort) {
      this.serverName = serverName;
      this.serverPort = serverPort;
   }

   public static void main(String[] args) {
      Client client = new Client("localhost", 8911);
      if (!client.connect()) {
         System.err.println("Connect failed.");
      } else {
         System.out.println("Connect successful");
      }
   }
   private boolean connect() {
      try {
         this.socket = new Socket(serverName, serverPort);
         this.serverOut = socket.getOutputStream();
         this.serverIn = socket.getInputStream();
         this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
         return true;
      } catch (IOException e) {
         e.printStackTrace();
      }
      return false;
   }
}
