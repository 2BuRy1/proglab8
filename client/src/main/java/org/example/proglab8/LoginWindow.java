package org.example.proglab8;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Commands.Login;
import Managers.RegisterStatus;
import Managers.Shake;
import Network.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindow {

    Client client = new Client("localhost", 2448, 5000, 5);
    public static User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button RegisterButton;

    @FXML
    void initialize() {


       LoginButton.setOnAction(actionEvent -> {
           String login = LoginField.getText();
           String password = PasswordField.getText();

               try {
                   var answer = login(login, password);
                   if(answer.getLoginError() == LoginError.LOGIN_ERROR || login.isEmpty() || password.isEmpty()){
                       Shake shake = new Shake(LoginField);
                       Shake shake2 = new Shake(PasswordField);
                   }else{
                       RegisterStatus.isLoggin = true;
                       LoginButton.getScene().getWindow().hide();
                       FXMLLoader fxmlLoader = new FXMLLoader();
                       fxmlLoader.setLocation(LoginWindow.class.getResource("Main.fxml"));
                       try {
                           fxmlLoader.load();
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                       Parent root = fxmlLoader.getRoot();
                       Scene scene= new Scene(root);
                       Stage stage = new Stage();
                       stage.setScene(scene);
                       stage.setResizable(false);
                       stage.show();

                   }


               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }


       });



       RegisterButton.setOnAction(actionEvent -> {
           RegisterButton.getScene().getWindow().hide();
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(LoginWindow.class.getResource("Register.fxml"));
           try {
               fxmlLoader.load();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           Parent root = fxmlLoader.getRoot();
           Scene scene= new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.setResizable(false);
           stage.showAndWait();


       });
    }


    private Response login(String login, String password) throws InterruptedException {

        user = new User(login, password);
        var answer = client.sendRequest(new Request(new Login(), user));
        return answer;
    }


}
