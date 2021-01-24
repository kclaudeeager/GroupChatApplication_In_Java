/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsocketprogramming;

import UsedForms.ClientHomePage;
import UsedForms.Database_Conn;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

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
              // user= new ObjectInputStream(socket.getInputStream());
              
                //user=new DataInputStream(ss.getInputStream());
                //get input from the client
                Database_Conn Db=new Database_Conn();
              ArrayList Online=Db.LoggedinUsers();
               
                // String online=user.readObject().toString();
               for(int i=0;i<Online.size();i++)
                {
                     
                    String message = input.readUTF();
                   if(message.equals(Online.get(i)))
                   { 
                        users=message;
                         Platform.runLater(() -> {
                     client.OnlineUsers.getItems().add(users);
                 });
                   }
                   else
                   {
                     ms=new Label(message.toString()+"\n");
                     
                      
                   }
                }
               
                ms.setId("messages");
                ms.setWrapText(true);
              
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {
                     client.OnlineUsers.getItems().add(users);
                    client.messageArea.getChildren().add(ms);
                });
                
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            } catch (SQLException ex) {
                Logger.getLogger(TaskReadThread.class.getName()).log(Level.SEVERE, null, ex);
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
