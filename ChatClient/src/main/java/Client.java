import java.io.*;
import java.net.Socket;

public class Client implements Contract.Controller {
   Contract.View view;
   private String userName;
   private Socket socket;
   private BufferedWriter writer;
   private BufferedReader reader;

   public Client(Contract.View view, String userName, Socket socket) {
      this.view = view;
      this.userName = userName;
      this.socket = socket;
      try {
         writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      } catch (IOException e) {
         e.printStackTrace();
      }
      startMessageReader();
   }

   private void startMessageReader() {
      Thread t = new Thread() {
         @Override
         public void run() {
            readMessageLoop();
         }
      };
      t.start();
   }

   private void readMessageLoop() {
      try {
         String line;
         while (true) {
            line = reader.readLine();
            String[] tokens = line.split(" ");
            if (tokens.length == 0) {
               Thread.sleep(100);
               continue;
            }
            String cmd = tokens[0];
            if ("online".equalsIgnoreCase(cmd)) {
               handleOnline(tokens);
            } else if ("offline".equalsIgnoreCase(cmd)) {
               handleOffline(tokens);
            } else if ("msg".equalsIgnoreCase(cmd)) {
               tokens = line.split(" ", 3);
               handleMessage(tokens);
            } else if (tokens[0].equalsIgnoreCase("requestSendFile"))
               handleFileRequest(tokens[1], tokens[2]);
            else if (tokens[0].equalsIgnoreCase("rejectSendFile"))
               handleRejectSendFile(tokens);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         try {
            socket.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   private void handleRejectSendFile(String[] tokens) {
      view.updateMsg(tokens[1], "Từ chối nhận file");
   }
   private void handleFileRequest(String userName, String fileName) {
      view.showConfirm(userName, fileName);
   }
   private void handleMessage(String[] tokensMsg) {
      String login = tokensMsg[1];
      String msgBody = tokensMsg[2];
      view.updateMsg(login, msgBody);
   }

   private void handleOffline(String[] tokens) {
      view.updateOffline(tokens[1]);
   }

   private void handleOnline(String[] tokens) {
      view.updateOnline(tokens);
   }
   @Override
   public void exit() {
      String msg = "offline " + userName;
      try {
         writer.write(msg);
         writer.newLine();
         writer.flush();
         Thread.sleep(1000);
         writer.close();
         reader.close();
         socket.close();
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void sendMsg(String receiver, String msg) {
      if (msg != null) {
         msg = "msg " + receiver + " " + userName + " " + msg;
         try {
            writer.write(msg);
            writer.newLine();
            writer.flush();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   @Override
   public void send(String msg) {
      if (msg != null) {
         try {
            writer.write(msg);
            writer.newLine();
            writer.flush();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   @Override
   public String getUserName() {
      return userName;
   }

   @Override
   public void sendFileRequest(String receiverName, String fileName) {
      String msg = "requestSendFile " + receiverName + " " + fileName;
      try {
         writer.write(msg);
         writer.newLine();
         writer.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void acceptSendFile(String userName, String folderSaveFile) {
      FileReceiver fileReceiver = new FileReceiver(userName, this.userName, socket.getPort(), "localhost", folderSaveFile);
      fileReceiver.run();
   }

}
