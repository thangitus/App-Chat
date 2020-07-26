import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Group extends javax.swing.JFrame {
   ChatView view;
   public Group(ChatView view) throws HeadlessException {
      this.view = view;
      initComponents();
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      inputGroup = new javax.swing.JTextField();
      btnCreate = new javax.swing.JButton();
      btnJoin = new javax.swing.JButton();
      textNotify = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setBackground(new java.awt.Color(255, 255, 255));

      jLabel1.setText("Tên Group");

      btnCreate.setText("Tạo");
      btnCreate.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            btnCreateMouseClicked(evt);
         }
      });

      btnJoin.setText("Tham gia");
      btnJoin.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            btnJoinMouseClicked(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                      .addGroup(layout.createSequentialGroup()
                                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                      .addGroup(layout.createSequentialGroup()
                                                                                      .addContainerGap()
                                                                                      .addComponent(textNotify, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                      .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                                                                 .addGap(26, 26, 26)
                                                                                                                                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                 .addGroup(layout.createSequentialGroup()
                                                                                                                                                                 .addComponent(btnCreate)
                                                                                                                                                                 .addGap(18, 18, 18)
                                                                                                                                                                 .addComponent(btnJoin))
                                                                                                                                                 .addComponent(inputGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                 .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                      .addContainerGap(30, Short.MAX_VALUE)));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                    .addGap(24, 24, 24)
                                                    .addComponent(jLabel1)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(inputGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(btnCreate)
                                                                    .addComponent(btnJoin))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(textNotify)
                                                    .addContainerGap(28, Short.MAX_VALUE)));

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnCreateMouseClicked(java.awt.event.MouseEvent evt) {
      String groupName = inputGroup.getText();
      if (groupName == null) {
         textNotify.setText("Chưa nhập tên group");
         return;
      }
      try {
         Socket socket = new Socket("localhost", 8818);
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String msg = "CreateGroup " + groupName;
         writer.write(msg);
         writer.newLine();
         writer.flush();
         String line;
         while (true) {
            line = reader.readLine();
            if (line != null)
               if (line.equalsIgnoreCase("Success")) {
                  view.addGroup(groupName);
                  this.dispose();
                  break;
               } else if (line.equalsIgnoreCase("Fail")) {
                  textNotify.setText("Group đã có");
                  break;
               }
         }

         reader.close();
         writer.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   private void btnJoinMouseClicked(java.awt.event.MouseEvent evt) {
      String groupName = inputGroup.getText();
      if (groupName == null) {
         textNotify.setText("Chưa nhập tên group");
         return;
      }
      try {
         Socket socket = new Socket("localhost", 8818);
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String msg = "JoinGroup " + groupName;
         writer.write(msg);
         writer.newLine();
         writer.flush();
         String line;
         while (true) {
            line = reader.readLine();
            if (line.equalsIgnoreCase("Success")) {
               view.addGroup(groupName);
               this.dispose();
               break;
            } else if (line.equalsIgnoreCase("Fail")) {
               textNotify.setText("Group không tồn tại");
               break;
            }
         }
         reader.close();
         writer.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   private javax.swing.JButton btnCreate;
   private javax.swing.JButton btnJoin;
   private javax.swing.JTextField inputGroup;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel textNotify;
   // End of variables declaration//GEN-END:variables
}
