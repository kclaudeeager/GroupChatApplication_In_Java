/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;

import static UsedForms.Forms.loggedUser;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafxsocketprogramming.ConnectionUtil;
import javafxsocketprogramming.ServerJavaFX;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafxsocketprogramming.TaskReadThread;


/**
 *
 * @author Kwizera
 */
public class ClientHomePage extends Application  implements java.io.Serializable  {
     private final Desktop desktop = Desktop.getDesktop();
    transient OutputStream fout=null;
      Socket socket;
    ClientHomePage client;
    ServerJavaFX server=new ServerJavaFX ();
    TextField writemessage=new TextField();
     DataOutputStream output = null;
     DataInputStream Users=null;
    public TextField search=new TextField();
     public Button searching=new Button("Search");
      SimpleDateFormat Dformat=new  SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
       // jLabel5.setText(Dformat.format(date));
        DateTimeFormatter Dtformat=DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        public  String username ;
Label user=new Label();
 static String Username;
  public ListView OnlineUsers=new ListView();

   public VBox messageArea=new VBox();
   
    @Override
    public void start(Stage primaryStage) {
BorderPane homepage=new BorderPane();

search.setTooltip(new Tooltip("Search here"));
search.setPromptText("Search");
search.setOnKeyPressed(e->{
    if(e.getCode()==KeyCode.ENTER)
    {
      SearchText();  
    }
});
String image1=ClientHomePage.class.getResource("photos/search.png").toExternalForm();
Image icon=new Image(image1);
searching.setGraphic(new ImageView(icon));
searching.setOnAction(e->{
 SearchText();
});




        Username = Forms.User;
user.setText(Username);
 username = user.getText();
ImageView profile=new ImageView();
VBox prof=new VBox();
prof.getChildren().addAll(user);
HBox nav=new HBox();
Label dateLabel=new Label();

        dateLabel.setText(Dformat.format(date)+" "+Dtformat.format(now));
nav.setSpacing(20);
nav.setId("menu");
  Label messages=new Label("Messages you have");
  messages.getStyleClass().add("Lables");
 
  ScrollPane sp=new ScrollPane();
 messageArea.setSpacing(10);
  
  Database_Conn DC=new Database_Conn();
  ArrayList<Label> AvailableMessages= new ArrayList<>();
  AvailableMessages=DC.GetMessages();
  
  for(Label SentMessages:AvailableMessages)
  {
     SentMessages.setId("messages");
   messageArea.getChildren().add(SentMessages);
  }
 
  messageArea.setId("messagearea");
  ObservableList<HBox> Images=FXCollections.observableArrayList();
  Images=DC.GetImageMessages();
  for(HBox hb:Images)
  {
      messageArea.getChildren().add(hb);
  
  Images.addListener((ListChangeListener.Change<? extends HBox> c) ->
  {
      
        messageArea.getChildren().add(hb);
 
  });
  }
  
  sp.setContent(messageArea);
   VBox centeralpane=new VBox();
  centeralpane.getChildren().addAll(messages,sp);
    VBox Leftpane=new VBox();
  

  Label online=new Label("Online people");
  online.getStyleClass().add("Lables");
  Leftpane.getChildren().addAll(online,  OnlineUsers);

  Leftpane.setId("Left");
nav.getChildren().addAll(dateLabel,new MainMenu(),search,searching,prof);
homepage.setTop(nav);
homepage.setCenter( centeralpane);
homepage.setLeft(Leftpane);
HBox messageText=new HBox();

writemessage.setPromptText("Write the message");
writemessage.setTooltip(new Tooltip("message"));
Button Send=new Button("Send");
writemessage.setOnKeyPressed((KeyEvent e) -> {
    if(e.getCode()==KeyCode.ENTER)
    {
        ButtonListener buttonListener;
        buttonListener = new ButtonListener();
    }
});
  
  Send.setOnAction(new ButtonListener());
  String upload=ClientHomePage.class.getResource("photos/upload.png").toExternalForm();

 Button sendfile=new Button("Upload file");
 sendfile.setGraphic(new ImageView(new Image(upload)));
 
messageText.getChildren().addAll(sendfile,writemessage,Send);

sendfile.setOnAction(new UploadFile());
SetUserStyle();
 HBox.setHgrow(writemessage, Priority.ALWAYS); 
homepage.setBottom(messageText);
messageText.setId("bottom");
messageText.setSpacing(10);
  centeralpane.setId("center");

Scene scene=new Scene(homepage,1200,700);
String css=LoginForm.class.getResource("LoginForm.css").toExternalForm();
        scene.getStylesheets().clear();
      scene.getStylesheets().add(css);

primaryStage.setScene(scene);

        primaryStage.show();
        
   
        
        try {
            // Create a socket to connect to the server
            socket = new Socket(ConnectionUtil.host, ConnectionUtil.port);

            //Connection successful
            //Label lb=new Label("Connected. \n");
        //  messageArea.getChildren().add(lb);
          
            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());
            
            
          // Users=new DataInputStream(socket.getInputStream());
           //OnlineUsers.getItems().add(Users.readUTF());
            
            //create a thread in order to read message from server continuously
            TaskReadThread task = new TaskReadThread(socket, this);
            Thread thread = new Thread(task);
            //task.Searc_Message(searching, search);
          
            thread.start();
          
        } catch (IOException ex) {
          //  messageArea.getChildren().add(new Label(ex.toString() + '\n'));
            
        }
       

                     
  for (String user : loggedUser) {
                        // online.writeUTF(user);
                        System.out.println(user + '\n');
                    }


loggedUser.addListener((ListChangeListener.Change<? extends String> c) -> {
    loggedUser.stream().filter((java.lang.String user1) -> (!OnlineUsers.getItems().contains(user1) && user1 != null)).forEachOrdered((java.lang.String user2) -> {
        OnlineUsers.getItems().add(user2);
    });
});
    }
       
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
     private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            try {
                //get username and message
                
              username = user.getText().trim();
                //String username = "kwizera".trim();
                // String username = User.trim();
                String message =writemessage.getText().trim();

                //if username is empty set it to 'Unknown' 
                if (username.length() == 0) {
                    username = "Unknown";
                }
                //if message is empty, just return : don't send the message
                if (message.length() == 0) {
                    return;
                }
                Date date=new Date();
                String time=date.toString();    

                //send message to server
                String d=Dformat.format(date)+" "+Dtformat.format(now);
                output.writeUTF("[" + username + "]: " + message + "     "+d);
                output.flush();
                

                new Database_Conn().InsertMessages(username, message,d);
            
    
 
      

                //clear the textfield
                writemessage.clear();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    
}
     public void setObject(ClientHomePage client)
     {
       this.client=client;  
     }
     private void SearchText()
     {
         //boolean got=true;
         for(int i=0;i<messageArea.getChildren().size();i++)
{
    Label Text=(Label)messageArea.getChildren().get(i);
    
    if(Text.getText().toUpperCase().contains(search.getText().toUpperCase()))
    {
//        got=true;
//        if(got)
        Text.setId("find");
    }
    else
          Text.setId("message");
  }  
  //SetUserStyle();
    
 
//         if(got==false)
//             JOptionPane.showMessageDialog(null, "Not found!");
     }  
 
    private  void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
//                      new FileChooser.ExtensionFilter("PDF", "*.pdf"),
//                new FileChooser.ExtensionFilter("PPT", "*.ppt"),
//                  new FileChooser.ExtensionFilter("DOC", "*.docx")
               
            );
    }
            
 
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
         
        }
    }
    public void SetUserStyle()
    {
         for(int i=0;i<messageArea.getChildren().size();i++)
{
    if(messageArea.getChildren().get(i) instanceof Label){
        
    
    Label Text=(Label)messageArea.getChildren().get(i);
    
    if(Text.getText().startsWith("[" + username + "]"))
    {
        Text.setText(Text.getText().replace("[" + username + "]", "[ You ]"));
        Text.setId("user");
    }
      else
   {
       Text.setId("other");
   }
      
}
    }
    }
    private class UploadFile implements EventHandler<ActionEvent>,java.io.Serializable {
 final FileChooser fileChooser=new FileChooser();
  FileInputStream fin=null;
  byte[] person_image;
        @Override
        public void handle(ActionEvent event) {
             //To change body of generated methods, choose Tools | Templates.
        
             try{
      final FileChooser fileChooser=new FileChooser();
  
   configureFileChooser(fileChooser);
   Stage stage=new Stage();
                
                     File file=   fileChooser.showOpenDialog(stage);
                   
                       
                            
                           // openFile(file);
                          
                           //fin = new FileInputStream(file);
                              //File image=new File (fin);
    fin=new FileInputStream(file);
    ByteArrayOutputStream bos= new ByteArrayOutputStream();
                 byte[] buf = new byte[1024];
    for(int readnum;(readnum=fin.read(buf))!=-1; ){
    bos.write(buf,0,readnum);
}
                 person_image = bos.toByteArray();
}    catch (FileNotFoundException ex) {
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) { 
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     }
                            messageArea.getChildren().addAll(new ImageView(new Image(fin)));
//                           byte[]bytearray=new byte [(int)file.length()];
//            BufferedInputStream bin=new BufferedInputStream(fin);
               String d=Dformat.format(date)+" "+Dtformat.format(now);
     
            new Database_Conn().InsertPhoto(username,fin,d,person_image);
//            BufferedOutputStream out=null;
//            bin.read(bytearray,0,bytearray.length);
//            fout=socket.getOutputStream();
           
            //out=new BufferedOutputStream(socket.getOutputStream());
            
            //System.out.println("sending Files.......");
           // fout.write(bytearray,0,bytearray.length);
            
            //JOptionPane.showMessageDialog(null,"File transfer completed ");
            //fout.flush();
                        } 
                    }
                    
                
               
}  
        
      
        



        
    
            
     
     

