/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsocketprogramming;

import java.io.*;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Kwizera,It represents each new connection
 */
public  class TaskClientConnection implements  Runnable,java.io.Serializable {

    Socket socket;
    ServerJavaFX server;
    OutputStream fout;
      InputStream fin;
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;
    transient File file;
 transient ObjectInputStream oistream;
  transient ObjectOutputStream oustream;
  
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
            fout= socket.getOutputStream();
            output = new DataOutputStream(
                    socket.getOutputStream());
            fin=socket.getInputStream();
//              oistream=new  ObjectInputStream(socket.getInputStream());
//              oustream=new ObjectOutputStream(socket.getOutputStream());
 //FileOutputStream fos=null;
            while (true) {
                // Get message from the client
                
                 
//                 int filesize=1022386;
//            int bytesRead;
//            int currentTot=0;
//            byte [] bytearray=new byte[filesize];
//           
//            File file=new File("Image.png");
//            fos = new FileOutputStream(file);
//            BufferedOutputStream bos=new BufferedOutputStream(fos);
//            bytesRead=fin.read(bytearray,0,bytearray.length);
//            currentTot=bytesRead;
//            do{
//                bytesRead=fin.read(bytearray, currentTot, (bytearray.length-currentTot));
//                if(bytesRead>=0)
//                    currentTot+=bytesRead;
//            }
//            while(bytesRead>-1);
//            bos.write(bytearray,0,currentTot);
//            FileInputStream fin=new FileInputStream(file);
//            bos.flush();
               //file=(File)oistream.readObject();
              //int bytes=fin.read(bytearray,0,bytearray.length);
                String message = input.readUTF();
                //server.SendUsersfiles(bytearray);
                
                 server.broadcast(message);
    
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {                    
                    server.txtAreaDisplay.appendText(message + "\n");
                    
                });
                      
            }
            


        } catch (IOException ex) {
            ex.printStackTrace();

     
        }
    }

    //send message back to client
    public void sendMessage(String message) {
          try {
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
        } 
       
    }


    void GetUsersFile(byte[] bytearray) {
       try{
          
           fout.write(bytearray,0,bytearray.length);
           fout.flush();
       } catch (IOException ex) {  
            Logger.getLogger(TaskClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

}
