import java.io.Serializable;

public class Account implements Serializable {
   String userName, password;
   public Account(String userName, String password) {
      this.userName = userName;
      this.password = password;
   }
}
