package com.example.localchat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HelloController {

    private boolean isAuthorized;

    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    Button button;

    @FXML
    ListView<String> clientList;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button enter;

    @FXML
    HBox upperPanel;
    @FXML
    HBox bottomPanel;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    String IP_ADDRESS = "localhost";
    int PORT = 8189;

    @FXML
    public void keyListener(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            sendMessage();
        }
    }

    public void setActive(boolean isAuthorized){
        this.isAuthorized = isAuthorized;

        if(!isAuthorized){
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    public void sendMessage() {
        //textArea.appendText(textField.getText() + "\n");
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            try{
                                String str = in.readUTF();
                                if(str.startsWith("/authok")){
                                    setActive(true);
                                    textArea.appendText(str + "\n");
                                    break;
                                } else {
                                    textArea.appendText(str + "\n");
                                }
                            } catch (SocketException e){
                                System.out.println("Server don't callback");
                                break;
                            }
                        }
                        while (true) {
                            try{
                                String str = in.readUTF();
                                if(str.startsWith("/")){
                                    if(str.startsWith("/show")){
                                        String[] nicknames = str.split(" ");

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                clientList.getItems().clear();

                                                for (int i = 1; i < nicknames.length; i++) {
                                                    clientList.getItems().add(nicknames[i]);
                                                }
                                            }
                                        });
                                    }
                                    if(str.equals("/end")){
                                        break;
                                    }
                                } else {
                                    textArea.appendText(str + "\n");
                                }

                            } catch (SocketException e){
                                System.out.println("Server don't callback");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                            in.close();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            System.out.println("Error");;
        }
    }

    public void auth(){
        if(loginField.getText().isBlank() || passwordField.getText().isBlank()){
            textArea.appendText("Input Login/Password\n");
            return;
        }
        if (socket == null || socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}