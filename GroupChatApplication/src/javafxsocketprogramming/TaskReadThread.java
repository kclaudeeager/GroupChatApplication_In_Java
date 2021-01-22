/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsocketprogramming;

import UsedForms.ClientHomePage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Kwizera
 * 
 * It is used to get input from server simultaneously
 */
public class TaskReadThread implements Runnable {
    //private variables
    Socket socket;
    ClientHomePage client;
    DataInputStream input;
    public Label ms;

    //constructor
    public TaskReadThread(Socket socket, ClientHomePage client) {
        this.socket = socket;
        this.client = client;
        client.setObject(client);
    }
    
 

    @Override 
    public void run() {
        //continuously loop it

        while (true) {
            try {
                //Create data input stream
                input = new DataInputStream(socket.getInputStream());

                //get input from the client
                String message = input.readUTF();
                 ms=new Label(message+"\n");
                ms.setId("messages");
                ms.setWrapText(true);
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {
                    //display the message in the textarea
                    client.messageArea.getChildren().add(ms);
                });
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
     /*  public void Searc_Message(Button bt,TextField tf)
    {
        
      bt.setOnAction(e->{
            while(true)
    {
    
        if(ms.getText().contains(tf.getText()))
        {
          ms.setId("find");
        }
    }
      });
    }*/
}
