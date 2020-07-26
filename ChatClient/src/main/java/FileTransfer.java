import java.io.*;
import java.net.Socket;

public class FileTransfer extends Thread {
   private Socket socket;
   private String host;
   private int port;
   private String fileName;
   private String senderUser;
   private String receiverUser;
   private BufferedWriter writer;
   private final int BUFFER_SIZE = 8192;

   public FileTransfer(String sender, String receiver, String host, int port, String fileName) {
      this.host = host;
      this.senderUser = sender;
      this.port = port;
      this.receiverUser = receiver;
      this.fileName = fileName;
   }

   @Override
   public void run() {
      try {
         socket = new Socket(host, port);
         writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

         File file = new File(fileName);
         int len = (int) file.length();
         int filesize = (int) Math.ceil(len / BUFFER_SIZE);
         String clean_filename = file.getName();

         String command = "SendFile " + clean_filename.replace(" ", "_") + " " + filesize + " " + receiverUser + " " + senderUser;
         writer.write(command);
         writer.newLine();
         writer.flush();

         InputStream input = new FileInputStream(file);
         OutputStream output = socket.getOutputStream();
         BufferedInputStream bis = new BufferedInputStream(input);

         byte[] buffer = new byte[BUFFER_SIZE];
         int count;
         while ((count = bis.read(buffer)) > 0) {
            output.write(buffer, 0, count);
         }

         output.flush();
         input.close();
         output.close();
         socket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
