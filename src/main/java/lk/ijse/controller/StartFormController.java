package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Server.Server;

import java.io.IOException;

public class StartFormController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        Server serverSocket = Server.getServerSocket();
        Thread thread = new Thread(serverSocket);
        thread.start();

        Parent root= FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        this.mainPane.getChildren().clear();
        this.mainPane.getChildren().add(root);
    }
}
