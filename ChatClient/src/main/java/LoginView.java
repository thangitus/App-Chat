import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.time.chrono.IsoChronology;

public class LoginView extends javax.swing.JFrame {
   private Socket socket;
   private final int serverPort = 8911;
   public LoginView() {
      try {
         this.socket = new Socket("localhost", serverPort);
      } catch (IOException e) {
         e.printStackTrace();
      }

      initComponents();
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      Main = new javax.swing.JPanel();
      jLabel1 = new javax.swing.JLabel();
      inputUserName = new javax.swing.JTextField();
      inputPassword = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      btnLogin = new javax.swing.JButton();
      btnRegister = new javax.swing.JButton();
      textNotify = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setBackground(new java.awt.Color(255, 255, 255));

      Main.setBackground(new java.awt.Color(255, 255, 255));
      Main.setForeground(new java.awt.Color(255, 105, 218));

      jLabel1.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
      jLabel1.setForeground(new java.awt.Color(102, 102, 102));
      jLabel1.setText("Tài khoản");

      inputUserName.setBackground(new java.awt.Color(204, 204, 204));
      inputUserName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
      inputUserName.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

      inputPassword.setBackground(new java.awt.Color(204, 204, 204));
      inputPassword.setHorizontalAlignment(javax.swing.JTextField.LEFT);
      inputPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

      jLabel2.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
      jLabel2.setForeground(new java.awt.Color(102, 102, 102));
      jLabel2.setText("Mật khẩu");

      btnLogin.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
      btnLogin.setText("Đăng nhập");
      btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            btnLoginMouseClicked(evt);
         }
      });

      btnRegister.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
      btnRegister.setText("Đăng kí");
      btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            btnRegisterMouseClicked(evt);
         }
      });

      textNotify.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
      textNotify.setForeground(new java.awt.Color(255, 102, 102));

      javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
      Main.setLayout(MainLayout);
      MainLayout.setHorizontalGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainLayout.createSequentialGroup()
                                                                                                              .addContainerGap(137, Short.MAX_VALUE)
                                                                                                              .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                  .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                                                                                                                      .addComponent(inputUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                      .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                  .addComponent(jLabel1)
                                                                                                                                  .addComponent(jLabel2)
                                                                                                                                  .addGroup(MainLayout.createSequentialGroup()
                                                                                                                                                      .addGap(3, 3, 3)
                                                                                                                                                      .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                          .addComponent(textNotify, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                          .addGroup(MainLayout.createSequentialGroup()
                                                                                                                                                                                              .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                              .addGap(18, 18, 18)
                                                                                                                                                                                              .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                                                                              .addGap(90, 90, 90)));
      MainLayout.setVerticalGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(MainLayout.createSequentialGroup()
                                                                .addGap(41, 41, 41)
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(inputUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(textNotify)
                                                                .addGap(23, 23, 23)
                                                                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                    .addComponent(btnLogin)
                                                                                    .addComponent(btnRegister))
                                                                .addContainerGap(77, Short.MAX_VALUE)));

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                      .addComponent(Main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {
      String userName = inputUserName.getText();
      String password = inputPassword.getText();
      try {
         System.out.println("Client port is " + socket.getLocalPort());
         OutputStream outputStream = socket.getOutputStream();
         String msg = "login " + userName + " " + password;
         outputStream.write(msg.getBytes());
         handleResponse();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   private void handleResponse() throws IOException {
      String line;
      InputStream inputStream = socket.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      while ((line = bufferedReader.readLine()) != null) {
         String[] tokens = line.split(" ");
         if (tokens.length == 0)
            continue;
         if (tokens[1].equalsIgnoreCase("success")) {
            EventQueue.invokeLater(new Runnable() {
               public void run() {
                  new ChatView(socket,inputUserName.getText());
               }
            });
            this.dispose();
         } else if (tokens[0].equalsIgnoreCase("login"))
            textNotify.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
         else if (tokens[0].equalsIgnoreCase("register"))
            textNotify.setText("Đăng kí không thành công!");
      }
   }

   private void btnRegisterMouseClicked(java.awt.event.MouseEvent evt) {
   }

   public static void main(String args[]) {
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(LoginView.class.getName())
                                 .log(java.util.logging.Level.SEVERE, null, ex);
      }
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new LoginView().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel Main;
   private javax.swing.JButton btnLogin;
   private javax.swing.JButton btnRegister;
   private javax.swing.JTextField inputPassword;
   private javax.swing.JTextField inputUserName;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel textNotify;
   // End of variables declaration//GEN-END:variables
}
