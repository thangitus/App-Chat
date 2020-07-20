import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
   private final Socket clientSocket;
   private final ServerMain server;
   private String userName;
   private InputStream inputStream;
   private OutputStream outputStream;
   public Worker(ServerMain serverMain, Socket clientSocket) {
      this.server = serverMain;
      this.clientSocket = clientSocket;
   }

   @Override
   public void run() {
      super.run();
      try {
         handleClient();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void handleClient() throws IOException {
      inputStream = clientSocket.getInputStream();
      outputStream = clientSocket.getOutputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
         String[] tokens = line.split(" ");
         if (tokens.length > 0) {
            String cmd = tokens[0];
            if ("logout".equals(cmd) || "quit".equalsIgnoreCase(cmd)) {
               server.logout(this, userName);
               clientSocket.close();
               return;
            } else if ("login".equalsIgnoreCase(cmd))
               server.login(tokens[1], tokens[2]);
            else if ("register".equalsIgnoreCase(cmd))
               server.register(tokens[1], tokens[2]);
         }
      }
   }

   private void handleMessage(String[] tokensMsg) {
   }

}
