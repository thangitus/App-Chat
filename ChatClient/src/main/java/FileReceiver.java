

import java.io.*;
import java.net.Socket;

public class FileReceiver extends Thread {
   private Socket socket;
   private String host;
   private int port;
   private String folderPath;
   private String sender;
   private String receiver;
   private BufferedWriter writer;
   private BufferedReader reader;
   private final int BUFFER_SIZE = 8192;

   public FileReceiver(String sender, String receiver, int Port, String host, String filePath) {
      this.host = host;
      this.sender = sender;
      this.port = Port;
      this.receiver = receiver;
      this.folderPath = filePath;
   }

   @Override
   public void run() {
      System.out.println(folderPath);
      if (folderPath == null) {
         return;
      }
      try {
         socket = new Socket(host, port);
         writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         String command = "ReadyReceive " + receiver + " " + sender;
         writer.write(command);
         writer.newLine();
         writer.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }

      try {
         String data;
         while (!Thread.currentThread()
                       .isInterrupted()) {
            data = reader.readLine();
            System.out.println(data);
            if (data == null){
               Thread.sleep(100);
               continue;
            }
            String[] tokens = data.split(" ");

            if (tokens[0].equals("sendingFile")) {
               String sender;
               try {
                  System.out.println("Receiving...");
                  String filename = tokens[1];
                  sender = tokens[3]; // Get the Sender Username
                  System.out.println("Đang tải File....");
                  System.out.println("From: " + sender);
                  String path = folderPath + filename;

                  FileOutputStream fos = new FileOutputStream(path);
                  fos.flush();
                  InputStream input = socket.getInputStream();

                  BufferedInputStream bIS = new BufferedInputStream(input);
                  byte[] buffer = new byte[BUFFER_SIZE];
                  int count;
                  while ((count = bIS.read(buffer)) != -1) {
                     fos.write(buffer, 0, count);
                  }
                  bIS.close();
                  fos.flush();
                  fos.close();
                  this.socket.close();
                  System.out.println("File saved at: " + path);

               } catch (IOException e) {
                  e.printStackTrace();
                  try {
                     socket.close();
                  } catch (IOException ioException) {
                     ioException.printStackTrace();
                  }
               }
               break;
            }
         }
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
      }
   }

}