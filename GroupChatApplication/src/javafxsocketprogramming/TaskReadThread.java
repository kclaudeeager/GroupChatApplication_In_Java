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
import javafx.scene.control.Label;
//import static javafxsocketprogramming.ServerJavaFX.loggedUser;

/**
 *
 * @author Kwizera
 * 
 * It is used to get input from server simultaneously
 */
public class TaskReadThread implements Runnable,java.io.Serializable {
    //private variables
    Socket socket;
    ClientHomePage client;
    DataInputStream input;
  String users;
    public Label ms;
     //ServerSocket serverS;

    //constructor
    public TaskReadThread(Socket socket, ClientHomePage client) throws IOException {
       // this.serverS = new ServerSocket(8001);
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
               // Socket ss=serverS.accept();
                input = new DataInputStream(socket.getInputStream());
                   
                    String message = input.readUTF();
                      ms=new Label(message.toString()+"\n");

               
                ms.setId("messages");
                ms.setWrapText(true);
          
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {
                     //client.OnlineUsers.getItems().add(users);
                    client.messageArea.getChildren().add(ms);
                });
                
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                break;

}
        }
    }
}
    