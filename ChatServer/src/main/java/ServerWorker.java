import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorker extends Thread {
   private final Socket clientSocket;
   private final Server server;
   private final int BUFFER_SIZE = 8192;
   private BufferedWriter outputStream;
   private String senderName;
   private String receiver;
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
      DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
      String line;
      while (true) {
         line = bufferedReader.readLine();
         //         line = dataInputStream.readUTF();
         System.out.println(line);
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
            else if (tokens[0].equals("ReadyReceive")) {
               handleReadyReceiverFile(tokens);
               break;
            } else if (tokens[0].equals("SendFile")) {
               sendFile(tokens);
               break;
            }
         }
      }
   }

   private void sendFile(String[] tokens) throws IOException {
      String fileName = tokens[1];
      String size = tokens[2];
      String receiver = tokens[3];
      String sender = tokens[4];
      ServerWorker workerReceiver = server.getWorkerBySenderReceiver(sender, receiver);
      if (workerReceiver != null) {
         try {
            System.out.println("Sending.......");
            String cmd = "sendingFile " + fileName + " " + size + " " + sender;
            System.out.println(cmd);
            workerReceiver.send(cmd);

            InputStream input = clientSocket.getInputStream();
            OutputStream sendFile = workerReceiver.getClientSocket()
                                                  .getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int cnt;
            int test = 0;
            while ((cnt = input.read(buffer)) > 0) {
               test++;
               sendFile.write(buffer, 0, cnt);
            }
            System.out.println("test " + test);
            sendFile.flush();
            sendFile.close();
            //this.socket.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   private void handleReadyReceiverFile(String[] tokens) throws IOException {
      List<ServerWorker> workerList = server.getWorkList();
      senderName = tokens[2];
      receiver = tokens[1];
      for (ServerWorker serverWorker : workerList)
         if (serverWorker.getSenderName()
                         .equalsIgnoreCase(senderName)) {
            String msg = tokens[0] + " " + receiver;
            serverWorker.send(msg);
            break;
         }
   }
   private void handleReject(String[] tokens) {
      String receiver = tokens[1];
      ArrayList<ServerWorker> serverWorkers = server.getWorkList();
      for (ServerWorker serverWorker : serverWorkers) {
         if (serverWorker.getSenderName()
                         .equalsIgnoreCase(receiver)) {
            try {
               serverWorker.send("rejectSendFile " + senderName);
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
         if (serverWorker.getSenderName()
                         .equalsIgnoreCase(receiver)) {
            try {
               serverWorker.send("requestSendFile " + senderName + " " + tokens[2]);
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
         senderName = tokens[1];
      } else
         msg += "fail";
      send(msg);
   }

   private void handleLogin(String[] tokens) throws IOException {
      boolean isSuccess = server.login(tokens);
      String msg = tokens[0] + " ";
      if (isSuccess) {
         msg += "success";
         senderName = tokens[1];
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
         if (serverWorker.getSenderName()
                         .equalsIgnoreCase(tokens[1])) {
            String msg = "msg " + tokens[2] + " " + tokens[3];
            System.out.println(msg);
            serverWorker.send(msg);
            break;
         }
   }

   public String getSenderName() {
      return senderName;
   }
   public String getReceiver() {
      return receiver;
   }
   public Socket getClientSocket() {
      return clientSocket;
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
