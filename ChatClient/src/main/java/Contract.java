public interface Contract {
   interface View {
      void updateOnline(String[] tokens);

      void updateOffline(String login);

      void updateMsg(String userName, String msg);

      void showConfirm(String userName, String fileName);

      String getFileName();
      void setFileNameFull(String fileNameFull);

      void updateMsgGroup(String[] tokens);
   }

   interface Controller {

      void exit();

      void sendMsg(String receiver, String msg);

      void send(String msg);

      String getUserName();

      void sendFileRequest(String receiverName, String fileName);

      void acceptSendFile(String userName, String folderSaveFile);
   }
}
