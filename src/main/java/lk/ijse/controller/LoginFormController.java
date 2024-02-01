package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Launcher;
import lk.ijse.Server.Server;
import lk.ijse.client.Client;

import java.io.IOException;

public class LoginFormController {
    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField userName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws Exception {
        Client client = new Client(userName.getText());

        Thread thread = new Thread(client);
        thread.start();

    }
}
