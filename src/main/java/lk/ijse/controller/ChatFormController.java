package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.client.Client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ChatFormController implements Initializable {

    @FXML
    private JFXButton btnFile;

    @FXML
    private JFXButton btnSend;

    @FXML
    private AnchorPane rootNode2;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vbox;
    @FXML
    private JFXButton btnEmoji;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField txt;
    private Client client;

    private final String[] emojis={
            "U+1F44B",
            "U+1F44C",
            "U+1F90C",
            "U+270C",
            "U+1F91F",
            "U+1F918",
            "U+1F919",
            "U+1F448",
            "U+1F44D",
            "U+1F44E",
            "U+1F64F",
            "U+270D",
            "U+1F9BE",
            "U+1F91D",
            "U+1FAF6",
            "U+1F44A",
            "U+270B"
    };

    @FXML
    void btnFIleOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; -fx-alignment: center-right;");

                ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);

                hBox.getChildren().addAll(imageView);
                vbox.getChildren().add(hBox);

                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setImage(byte[] bytes, String sender) {
        HBox hBox = new HBox();
        Label messageLbl = new Label(sender);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");

        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; " + (sender.equals(client.getName()) ? "-fx-alignment: center-right;" : "-fx-alignment: center-left;"));
        // Display the image in an ImageView or any other UI component...
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setStyle("-fx-padding: 10px;");
            imageView.setFitHeight(180);
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(messageLbl, imageView);
            vbox.getChildren().add(hBox);

        });
    }
    private JFXButton createBtn(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiBtnAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }
    private void emojiBtnAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        txt.appendText(button.getText());
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
        String text=txt.getText();
        if (!text .equals("")) {
            appendTxt(text);
            client.sendMessage(text);
            txt.clear();
        }else {
            new Alert(Alert.AlertType.INFORMATION, "empty message").show();
        }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public void writeMessage(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label msgLb1 = new Label(message);
        msgLb1.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(msgLb1);
        Platform.runLater(() -> {

            vbox.getChildren().add(hBox);

        });

    }

    public void setClient(Client client) {
        this.client = client;
    }

    void appendTxt(String message) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #008011;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vbox.getChildren().add(hBox);
    }

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        pane.setVisible(!pane.isVisible());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setVisible(false);
        int index=0;
        for (int i = 0; i <4; i++) {
            for (int j = 0; j <4; j++) {
                if (index < emojis.length) {
                    String emoji=emojis[index];
                    JFXButton emojiBtn=createBtn(emoji);
                    gridPane.add(emojiBtn,i,j);
                    index++;
                }else {
                    break;
                }
            }
        }
    }
}
