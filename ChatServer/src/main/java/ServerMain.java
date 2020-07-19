import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
   private static final int port = 8000;
   private static final ExecutorService service = Executors.newCachedThreadPool();
   public static void main(String[] args) {
      try {
         ServerSocket serverSocket = new ServerSocket(port);
         System.out.println("Start server");
         while (true) {
            System.out.println("Waiting client....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accept client");
            Worker worker = new Worker(clientSocket);
            service.submit(worker);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

}
