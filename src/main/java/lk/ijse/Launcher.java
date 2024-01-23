package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene=new Scene(root);
        Image image=new Image("/assets/chat Icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
    }
}