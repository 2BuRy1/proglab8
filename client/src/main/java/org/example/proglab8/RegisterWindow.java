package org.example.proglab8;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Commands.Login;
import Commands.Register;
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


public class RegisterWindow {
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
    private PasswordField PasswordField1;

    @FXML
    private PasswordField PasswordField2;

    @FXML
    private Button RegisterButton;

    @FXML
    void initialize() {

        RegisterButton.setOnAction(actionEvent -> {
            String login = LoginField.getText();
            String password = PasswordField1.getText();
            String password1 = PasswordField2.getText();
            if (!password1.equals(password) || password.isEmpty() || login.isEmpty()) {
                Shake shake = new Shake(PasswordField1);

                Shake shake2 = new Shake(PasswordField2);

                Shake shake1 = new Shake(LoginField);

            } else {
                try {
                    var answer = register(login, password);
                    if (answer.getLoginError() == LoginError.LOGIN_ERROR) {
                        Shake shake = new Shake(LoginField);
                        Shake shake2 = new Shake(PasswordField2);
                        Shake shake1 = new Shake(PasswordField1);

                    } else {
                        RegisterStatus.isLoggin = false;
                        RegisterButton.getScene().getWindow().hide();
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
            }
            });


        LoginButton.setOnAction(actionEvent1 -> {
            LoginButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(LoginWindow.class.getResource("Login.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = fxmlLoader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();


        });
    }






    private Response register(String login, String password) throws InterruptedException {
        user = new User(login , password);
        var answer = client.sendRequest(new Request(new Register(), user));
        return answer;
    }
}

