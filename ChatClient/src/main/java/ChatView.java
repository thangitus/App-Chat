import com.vdurmont.emoji.EmojiParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ChatView extends javax.swing.JFrame implements Contract.View {
   private Contract.Controller controller;
   private DefaultListModel<String> userListModel;
   private HashMap<String, String> hashMapMsg;
   private String fileNameFull;
   private String folderSaveFile;
   public ChatView(Socket socket, String userName) throws HeadlessException {
      initComponents();
      hashMapMsg = new HashMap<>();
      controller = new Client(this, userName, socket);
      setTitle("Bạn đang đăng nhập với tên " + userName);
      this.userListModel = new DefaultListModel<>();
      listOnline.setModel(userListModel);
      userListModel.removeAllElements();
      setVisible(true);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            controller.exit();
            System.out.println("Exit");
            System.exit(0);
         }
      });
      //      String str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!";
      //      String result = EmojiParser.parseToUnicode(str);
      //      textChat.setText(result);
   }

   private void initComponents() {

      jScrollPane1 = new javax.swing.JScrollPane();
      listOnline = new javax.swing.JList<>();
      jLabel1 = new javax.swing.JLabel();
      jScrollPane2 = new javax.swing.JScrollPane();
      textChat = new javax.swing.JTextArea();
      textInput = new javax.swing.JTextField();
      btnLogout = new javax.swing.JButton();
      btnSend = new javax.swing.JButton();
      btnSendFile = new javax.swing.JButton();

      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      setBackground(new java.awt.Color(204, 204, 204));

      listOnline.setModel(new javax.swing.AbstractListModel<String>() {
         String[] strings = {"Item 1", "Item 2"};
         public int getSize() { return strings.length; }
         public String getElementAt(int i) { return strings[i]; }
      });
      listOnline.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            listOnlineMouseClicked(evt);
         }
      });
      jScrollPane1.setViewportView(listOnline);

      jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
      jLabel1.setText("Danh sách online");

      textChat.setEditable(false);
      textChat.setColumns(20);
      textChat.setRows(5);
      jScrollPane2.setViewportView(textChat);

      textInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
      textInput.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            textInputActionPerformed(evt);
         }
      });

      btnLogout.setText("Thoát");
      btnLogout.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            logout();
         }
      });
      btnSend.setText("Gửi");
      btnSend.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            sendMsg();
         }
      });
      btnSend.setActionCommand("");
      btnSendFile.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            sendFile();
         }
      });
      btnSendFile.setText("Gửi file");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                      .addGroup(layout.createSequentialGroup()
                                                      .addContainerGap()
                                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                                                  .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                                  .addComponent(btnLogout)
                                                                                                                                  .addGap(29, 29, 29)
                                                                                                                                  .addComponent(jLabel1)
                                                                                                                                  .addGap(25, 25, 25))
                                                                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                                                                                                                                             .addComponent(textInput, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                             .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                                                                                                                                                  .addComponent(jScrollPane2))
                                                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                  .addComponent(btnSendFile))))));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                .addGap(0, 6, Short.MAX_VALUE)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(btnLogout))
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                                .addComponent(jScrollPane2)
                                                                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                                .addComponent(textInput)
                                                                                                                .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                                                                                                .addComponent(btnSendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                                .addGap(10, 10, 10)));

      pack();
   }
   private void logout() {
   }
   private void sendMsg() {
      if (listOnline.getSelectedIndex() == -1)
         return;
      String receiver = userListModel.get(listOnline.getSelectedIndex());
      String msg = textInput.getText();
      textInput.setText("");
      textChat.append("YOU: " + msg + "\n");
      hashMapMsg.put(receiver, textChat.getText());
      controller.sendMsg(receiver, msg);
   }
   private void sendFile() {
      if (listOnline.getSelectedIndex() == -1)
         return;
      FileDialog dialog = new FileDialog((Dialog) null, "Chọn file");
      dialog.setMode(FileDialog.LOAD);
      dialog.setVisible(true);
      String fileName = dialog.getFile();
      fileNameFull = dialog.getDirectory() + fileName;
      String receiver = userListModel.get(listOnline.getSelectedIndex());
      controller.sendFileRequest(receiver, fileName);
   }

   private void textInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textInputActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_textInputActionPerformed

   private void listOnlineMouseClicked(java.awt.event.MouseEvent evt) {
      textChat.setText(hashMapMsg.get(userListModel.get(listOnline.getSelectedIndex())));
   }//GEN-LAST:event_listOnlineMouseClicked

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnLogout;
   private javax.swing.JButton btnSend;
   private javax.swing.JButton btnSendFile;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JList<String> listOnline;
   private javax.swing.JTextArea textChat;
   private javax.swing.JTextField textInput;

   @Override
   public void updateOnline(String[] tokens) {
      userListModel.removeAllElements();
      for (int i = 1; i < tokens.length; i++)
         if (!tokens[i].equalsIgnoreCase(controller.getUserName()))
            userListModel.addElement(tokens[i]);
   }
   @Override
   public void updateOffline(String login) {
      userListModel.removeElement(login);
   }
   @Override
   public void updateMsg(String userName, String msg) {
      int index = userListModel.indexOf(userName);
      listOnline.setSelectedIndex(index);
      msg = userName.toUpperCase() + ": " + msg + "\n";
      if (hashMapMsg.containsKey(userName))
         textChat.setText(hashMapMsg.get(userName));
      textChat.append(msg);
      String receiver = listOnline.getSelectedValue();
      hashMapMsg.put(receiver, textChat.getText());
   }
   @Override
   public void showConfirm(String userNameOfSenderFile, String fileName) {
      int confirm = JOptionPane.showConfirmDialog(this, "Từ: " + userNameOfSenderFile + "\ntên file: " + fileName + "\nbạn có Chấp nhận file này không?");
      if (confirm == 0) {
         JFileChooser chooser = new JFileChooser();
         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         int open = chooser.showDialog(this, "Mở Thư Mục");
         if (open == chooser.APPROVE_OPTION) {
            folderSaveFile = chooser.getSelectedFile()
                                    .toString() + "\\";
         } else {
            folderSaveFile = "D:\\";
         }
         controller.acceptSendFile(userNameOfSenderFile, folderSaveFile);
      } else {
         String msg = "rejectSendFile " + userNameOfSenderFile;
         if (hashMapMsg.containsKey(userNameOfSenderFile))
            textChat.setText(hashMapMsg.get(userNameOfSenderFile));
         textChat.append("YOU: Từ chối nhận file\n");
         hashMapMsg.put(userNameOfSenderFile, textChat.getText());
         controller.send(msg);
      }

   }
   @Override
   public String getFileName() {
      return fileNameFull;

   }

   @Override
   public void setFileNameFull(String fileNameFull) {
      this.fileNameFull = fileNameFull;
   }
   // End of variables declaration//GEN-END:variables
}
