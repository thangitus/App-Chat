import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
   private final int serverPort;
   private ExecutorService service = Executors.newCachedThreadPool();
   private ArrayList<ServerWorker> workList;
   private List<Account> userOnline;
   private List<Account> allUser;
   @Override
   public void run() {
      allUser = readUser();
      System.out.println("All user");
      for (Account account : allUser)
         System.out.println(account.userName);
      try {
         ServerSocket serverSocket = new ServerSocket(serverPort);
         while (true) {
            System.out.println("About to accept client connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            ServerWorker worker = new ServerWorker(this, clientSocket);
            workList.add(worker);
            worker.start();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   public Server(int serverPort) throws IOException {
      this.serverPort = serverPort;
      workList = new ArrayList<>();
      userOnline = new ArrayList<>();
   }

   private List<Account> readUser() {
      List<Account> res = new ArrayList<>();
      try {
         ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Account.txt"));
         int size = inputStream.read();
         for (int i = 0; i < size; i++)
            res.add((Account) inputStream.readObject());
         inputStream.close();
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
      }
      return res;
   }
   public ArrayList<ServerWorker> getWorkList() {
      return workList;
   }

   public void logout(ServerWorker serverWorker, String userName) throws IOException {
      workList.remove(serverWorker);
      for (int i = 0; i < userOnline.size(); i++)
         if (userOnline.get(i).userName.equalsIgnoreCase(userName))
            userOnline.remove(i);
      String msg = "offline " + userName;
      System.out.println(msg);
      for (ServerWorker worker : workList)
         worker.send(msg);
   }

   public boolean register(String userName, String password) throws IOException {
      Account acc = new Account(userName, password);
      for (Account account : allUser)
         if (acc.password.equalsIgnoreCase(account.password) && acc.userName.equalsIgnoreCase(account.userName))
            return false;
      userOnline.add(acc);
      allUser.add(acc);
      try {
         ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Account.txt"));
         outputStream.write(allUser.size());
         for (Account account : allUser)
            outputStream.writeObject(account);
         outputStream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      String msg = "login " + userName;
      for (ServerWorker serverWorker : workList)
         serverWorker.send(msg);
      return true;
   }

   public boolean login(String[] tokens) throws IOException {
      for (Account account : allUser)
         if (tokens[1].equalsIgnoreCase(account.userName) && tokens[2].equalsIgnoreCase(account.password)) {
            userOnline.add(account);
            return true;
         }
      return false;
   }
   public void notifyUserOnline() {
      try {
         Thread.sleep(1000);
         StringBuilder msg = new StringBuilder("online ");
         for (Account account : userOnline)
            msg.append(account.userName)
               .append(" ");
         for (ServerWorker serverWorker : workList)
            serverWorker.send(msg.toString());
      } catch (InterruptedException | IOException e) {
         e.printStackTrace();
      }
   }
}
