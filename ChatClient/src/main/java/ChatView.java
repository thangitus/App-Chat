import java.awt.*;
import java.net.Socket;

public class ChatView extends javax.swing.JFrame {
   private String userName;
   private Socket client;
   public ChatView() {
      initComponents();
   }
   public ChatView(Socket socket, String text) throws HeadlessException {
      this.client = socket;
      this.userName = text;
      initComponents();
      setVisible(true);
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

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

      btnSend.setText("Gửi");
      btnSend.setActionCommand("");

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

   private void textInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textInputActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_textInputActionPerformed

   private void listOnlineMouseClicked(java.awt.event.MouseEvent evt) {
      System.out.println(listOnline.getSelectedIndex());
   }//GEN-LAST:event_listOnlineMouseClicked

   /**
    * @param args
    *         the command line arguments
    */
   public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
      /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
       */
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(ChatView.class.getName())
                                 .log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(ChatView.class.getName())
                                 .log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(ChatView.class.getName())
                                 .log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(ChatView.class.getName())
                                 .log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new ChatView().setVisible(true);
         }
      });
   }

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
   // End of variables declaration//GEN-END:variables
}
