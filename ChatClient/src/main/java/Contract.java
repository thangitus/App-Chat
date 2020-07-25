public interface Contract {
   public interface View {
      public void updateOnline(String[] tokens);

      public void updateOffline(String login);

      public void updateMsg(String userName,String msg);
   }

   public interface Controller {

      void exit();

      void sendMsg(String receiver, String msg);

      String getUserName();
   }
}
