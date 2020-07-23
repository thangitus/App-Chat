import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends ServerSocket {
   private ExecutorService service = Executors.newCachedThreadPool();
   private ArrayList<ServerWorker> workList;
   private List<Account> userOnline;
   private List<Account> allUser;

   public Server(int port) throws IOException {
      super(port);
      workList = new ArrayList<>();
      userOnline = new ArrayList<>();
      allUser = readUser();
      try {
         System.out.println("Start server");
         while (true) {
            System.out.println("Waiting client....");
            Socket clientSocket = this.accept();
            System.out.println("Accept client");
            ServerWorker serverWorker = new ServerWorker(this, clientSocket);
            service.submit(serverWorker);
            workList.add(serverWorker);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
   private List<Account> readUser() {
      List<Account> res = new ArrayList<>();
      try {
         ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Account.dat"));
         int size = inputStream.read();
         for (int i = 0; i < size; i++)
            res.add((Account) inputStream.readObject());
         inputStream.close();
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
      }
      return null;
   }
   public ArrayList<ServerWorker> getWorkList() {
      return workList;
   }

   public void logout(ServerWorker serverWorker, String userName) {
      workList.remove(serverWorker);
      userOnline.remove(userName);
   }

   public boolean register(String userName, String password) {
      Account acc = new Account(userName, password);
      if (allUser.contains(acc))
         return false;
      userOnline.add(acc);
      allUser.add(acc);
      try {
         ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Account.dat"));
         outputStream.write(allUser.size());
         for (Account account : allUser)
            outputStream.writeObject(account);
         outputStream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return true;
   }

   public boolean login(String[] tokens) {
      for (Account account : allUser)
         if (tokens[1].equalsIgnoreCase(account.userName) && tokens[2].equalsIgnoreCase(account.password)) {
            userOnline.add(account);
            return true;
         }
      return false;
   }
}
