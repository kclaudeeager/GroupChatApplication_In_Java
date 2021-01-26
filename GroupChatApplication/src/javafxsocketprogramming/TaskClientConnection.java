/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsocketprogramming;

import java.io.*;

import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author Kwizera,It represents each new connection
 */
public  class TaskClientConnection implements Runnable {

    Socket socket;
    ServerJavaFX server;
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;
//   ObjectInputStream oistream;
//   ObjectOutputStream oustream;
//   
    public TaskClientConnection(Socket socket, ServerJavaFX server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            // Create data input and output streams
            input = new DataInputStream(
                    socket.getInputStream());
            output = new DataOutputStream(
                    socket.getOutputStream());
             // oistream=new  ObjectInputStream(socket.getInputStream());
              //oustream=new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                // Get message from the client
                String message = input.readUTF();
                 server.broadcast(message);
                   // Database_Conn Db=new Database_Conn();
//                   ArrayList Online=Db.LoggedinUsers();
               
                // String online=user.readObject().toString();
//               for(int i=0;i<Online.size();i++)
//                {
//                     
//                 if(message.equals(Online.get(i)))
//                   { 
//                    server.SendUsers(message);
//                       
//                   }
//                   else
//                    
//                }
                //send message via server broadcast
                
              
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {                    
                    server.txtAreaDisplay.appendText(message + "\n");
                    
                });
                      
            }
            
//      socket.close();

        } catch (IOException ex) {
            ex.printStackTrace();
//        } catch (SQLException ex) {
//            Logger.getLogger(TaskClientConnection.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
                
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

        }

    }

    //send message back to client
    public void sendMessage(String message) {
          try {
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
       
    }
//      public void GetUsers(String message) {
//          try {
//            output.writeUTF(message);
//            output.flush();
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } 
//       
//    }

}
