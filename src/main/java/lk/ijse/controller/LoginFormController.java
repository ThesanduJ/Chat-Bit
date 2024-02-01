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

import java.io.IOException;

public class LoginFormController {
    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField userName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws Exception {
        Server serverSocket = Server.getServerSocket();
        Thread thread = new Thread(serverSocket);
        thread.start();

        String title = (String) userName.getText();
        if (!title.equals("")) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/chat_form.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(title + "'s Chat Room");
            stage.setScene(scene);
            Image image=new Image("/assets/chat Icon.png");
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can not continue this chat application without username").show();
        }
    }
}
