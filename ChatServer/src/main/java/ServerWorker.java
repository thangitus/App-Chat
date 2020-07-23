import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread {
   private final Socket clientSocket;
   private final Server server;
   private String userName;
   private OutputStream outputStream;
   public ServerWorker(Server server, Socket clientSocket) {
      this.server = server;
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
      InputStream inputStream = clientSocket.getInputStream();
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
               handleLogin(tokens);
            else if ("register".equalsIgnoreCase(cmd))
               handleRegister(tokens);
         }
      }
      clientSocket.close();
   }
   private void handleRegister(String[] tokens) throws IOException {
      boolean isSuccess = server.register(tokens[1], tokens[2]);
      String msg = tokens[0] + " ";
      if (isSuccess)
         msg += "success";
      else
         msg += "fail";
      send(msg);
   }

   private void handleLogin(String[] tokens) throws IOException {
      boolean isSuccess = server.login(tokens);
      String msg = tokens[0] + " ";
      if (isSuccess)
         msg += "success";
      else
         msg += "fail";
      send(msg);
   }

   private void handleMessage(String[] tokensMsg) {
   }
   private void send(String msg) throws IOException {
      if (userName != null) {
         try {
            outputStream.write(msg.getBytes());
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }
   }
}
