import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain extends ServerSocket {
   private ExecutorService service = Executors.newCachedThreadPool();
   private ArrayList<Worker> workList;
   private List<Account> userOnline;
   private List<Account> allUser;
   private HashMap<String, Socket> hashMapUser;

   public ServerMain(int port) throws IOException {
      super(port);
      hashMapUser = new HashMap<>();
      workList = new ArrayList<>();
      userOnline = new ArrayList<>();
      allUser = readUser();
      try {
         System.out.println("Start server");
         while (true) {
            System.out.println("Waiting client....");
            Socket clientSocket = this.accept();
            System.out.println("Accept client");
            Worker worker = new Worker(this, clientSocket);
            service.submit(worker);
            workList.add(worker);
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
   public ArrayList<Worker> getWorkList() {
      return workList;
   }

   public void logout(Worker worker, String userName) {
      workList.remove(worker);
      hashMapUser.remove(userName);
      userOnline.remove(userName);
   }
   public void register(String userName, String password) {
      Account acc = new Account(userName, password);
      userOnline.add(acc);
      try {
         ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Account.dat"));
         outputStream.write(allUser.size());
         for (Account account : allUser)
            outputStream.writeObject(account);
         outputStream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) throws IOException {
      int port = 8911;
      new ServerMain(port);
   }
   public void login(String userName, String password) {

   }
}
