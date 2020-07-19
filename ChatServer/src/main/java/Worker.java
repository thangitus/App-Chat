import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
   private final Socket clientSocket;

   public Worker(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }
   @Override
   public void run() {
      super.run();
      handleClient();
   }
   private void handleClient() {
      InputStream inputStream = null;
      try {
         inputStream = clientSocket.getInputStream();
         OutputStream outputStream = clientSocket.getOutputStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            if (line.equalsIgnoreCase("quit"))
               break;
            String msg = "You: " + line + "\n";
            outputStream.write(msg.getBytes());
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
