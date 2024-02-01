package lk.ijse.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.controller.ChatFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import javafx.scene.image.Image;

public class Client implements Runnable, Serializable {
    private final String name;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private ChatFormController chat;

    public Client(String name) throws IOException {
        this.name = name;

        socket = new Socket("localhost", 3000);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(name);
        outputStream.flush();
        try {
            loadScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF("-New Member Joined to Chat-");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                String message = inputStream.readUTF();
                if (message.equals("image")) {
                    receiveImage();
                } else {
                    chat.writeMessage(message);
                }
            } catch (IOException e) {
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException ignored) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        outputStream.writeUTF(msg);
        outputStream.flush();

    }

    public void sendImage(byte[] bytes) throws IOException {
        outputStream.writeUTF("image");
        outputStream.writeInt(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
    }

    private void loadScene() throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chat_form.fxml"));
        Parent parent = loader.load();
        chat = loader.getController();
        chat.setClient(this);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.setTitle(name + "'s Chat");
        Image image=new Image("/assets/chat Icon.png");
        stage.getIcons().add(image);
        stage.show();

        stage.setOnCloseRequest(event->{
            try {
                inputStream.close();
                outputStream.close();

                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        new Alert(Alert.AlertType.INFORMATION, "Server connected").show();

        stage.setOnCloseRequest(event -> {
            try {
                outputStream.writeUTF(" Has left! \uD83D\uDE13 ");
                outputStream.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void receiveImage() throws IOException {
        String utf = inputStream.readUTF();
        int size = inputStream.readInt();
        byte[] bytes = new byte[size];
        inputStream.readFully(bytes);

        chat.setImage(bytes, utf);
    }

    public String getName() {
        return name;
    }
}
