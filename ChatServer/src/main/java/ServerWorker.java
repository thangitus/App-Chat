import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorker extends Thread {
   private final Socket clientSocket;
   private final Server server;
   private BufferedWriter outputStream;
   private String userName;
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
      outputStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
      InputStream inputStream = clientSocket.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while (true) {
         line = bufferedReader.readLine();
         String[] tokens = line.split(" ");
         if (tokens.length > 0) {
            String cmd = tokens[0];
            if ("offline".equals(cmd)) {
               server.logout(this, tokens[1]);
               clientSocket.close();
               break;
            } else if ("login".equalsIgnoreCase(cmd))
               handleLogin(tokens);
            else if ("register".equalsIgnoreCase(cmd))
               handleRegister(tokens);
            else if ("msg".equalsIgnoreCase(cmd))
               handleMessage(line);
            else if (tokens[0].equals("requestSendFile"))
               handleRequestTransferFile(tokens);
            else if (tokens[0].equals("rejectSendFile"))
               handleReject(tokens);
         }
      }
      clientSocket.close();
   }
   private void handleReject(String[] tokens) {
      String receiver = tokens[1];
      ArrayList<ServerWorker> serverWorkers = server.getWorkList();
      for (ServerWorker serverWorker : serverWorkers) {
         if (serverWorker.getUserName()
                         .equalsIgnoreCase(receiver)) {
            try {
               serverWorker.send("rejectSendFile " + userName);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
   private void handleRequestTransferFile(String[] tokens) {
      String receiver = tokens[1];
      ArrayList<ServerWorker> serverWorkers = server.getWorkList();
      for (ServerWorker serverWorker : serverWorkers) {
         if (serverWorker.getUserName()
                         .equalsIgnoreCase(receiver)) {
            try {
               serverWorker.send("requestSendFile " + userName + " " + tokens[2]);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
   private void handleRegister(String[] tokens) throws IOException {
      boolean isSuccess = server.register(tokens[1], tokens[2]);
      String msg = "register ";
      if (isSuccess) {
         msg += "success";
         userName = tokens[1];
      } else
         msg += "fail";
      send(msg);
   }

   private void handleLogin(String[] tokens) throws IOException {
      boolean isSuccess = server.login(tokens);
      String msg = tokens[0] + " ";
      if (isSuccess) {
         msg += "success";
         userName = tokens[1];
      } else
         msg += "fail";
      send(msg);
      if (isSuccess)
         server.notifyUserOnline();
   }

   private void handleMessage(String line) throws IOException {
      String[] tokens = line.split(" ", 4);
      List<ServerWorker> serverWorkers = server.getWorkList();
      for (ServerWorker serverWorker : serverWorkers)
         if (serverWorker.getUserName()
                         .equalsIgnoreCase(tokens[1])) {
            String msg = "msg " + tokens[2] + " " + tokens[3];
            System.out.println(msg);
            serverWorker.send(msg);
            break;
         }
   }
   public String getUserName() {
      return userName;
   }
   public void send(String msg) throws IOException {
      try {
         outputStream.write(msg);
         outputStream.newLine();
         outputStream.flush();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
