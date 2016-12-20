/**
 * Created by Eiger on 6/27/16.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Login extends Application {

  @FXML
  TextField usernameField;

  @FXML
  Button signInButton;

  public static void main( String[] args ) {
    launch(args);
  }

  static Stage primaryStage;
  static Parent root;

  public void onSignIn() throws IOException {
    MyCustomer customer = ShoppingMallService.getInstance().searchCustomer
            (usernameField.getText());

    if ( customer == null ) {
      customer = new MyCustomer(usernameField.getText());
      ShoppingMallService.getInstance().register(customer);
    }

    ShoppingMallService.getInstance().enter(customer);
    primaryStage.close();
    openCustomerDashboard();

  }

  void openCustomerDashboard() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("./customer_dashboard.fxml"));
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("ABC");
    stage.setScene(new Scene(root));
    stage.show();
  }


  @Override
  public void start( Stage primaryStage ) throws IOException {
    root = null;
    this.primaryStage = new Stage();
    root = FXMLLoader.load(getClass().getResource("./login.fxml"));
    this.primaryStage.setTitle("Login");
    this.primaryStage.setScene(new Scene(root));
    this.primaryStage.show();
  }


}
