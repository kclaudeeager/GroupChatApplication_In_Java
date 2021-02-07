/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;

import static UsedForms.Forms.loggedUser;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafxsocketprogramming.TaskReadThread;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


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
        Image profileicon;
        ImageView imageview;
Label user=new Label();
 static String Username;
  public ListView OnlineUsers=new ListView();

   public VBox messageArea=new VBox();
   
    @Override
    public void start(Stage primaryStage) throws SQLException {
   File file=new File("C:\\Users\\Kwizera\\Documents\\NetBeansProjects\\GroupChatApplication_In_Java\\GroupChatApplication\\src\\UsedForms\\photos/signup.png");

  new Database_Conn().SetProfile(file);//Setting the difault profile image
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



//String profileimage=ClientHomePage.class.getResource("photos/signup.png").toExternalForm();
 
        Username = Forms.User;
user.setText(Username);
 username = user.getText();
  //profileicon=new Image(profileimage);
  profileicon=new Database_Conn().GetprofileImage(username);
ImageView profile=new ImageView(profileicon);
user.setGraphic(profile);

VBox prof=new VBox();
prof.getChildren().addAll(user);
prof.setOnContextMenuRequested(e->{
      Stage st=new Stage();
    Pane pane = new Pane();
    String profileDescription="Change your profile";
     Label profiledes=new Label();
    
      profiledes.setText(profileDescription);
     profiledes.setId("head");
      profiledes.setMinWidth(300);
    Label profileimag=new Label("Change profile image");
    profileimag.getStyleClass().add("Labels");
    imageview=new ImageView(profileicon);
    ChangeProfile changeprof=new ChangeProfile();
    imageview.setOnMousePressed(changeprof);
    
    
    Label Usernamelabel=new Label("Change the user name");
    Usernamelabel.getStyleClass().add("Labels");
    TextField newUsername=new TextField(username);
    Button update=new Button("Update profile");
    String Updateicon=ClientHomePage.class.getResource("photos/update.png").toExternalForm();
   update.setGraphic(new ImageView(new Image(Updateicon)));
   update.setOnAction(event->{
       int confirm=JOptionPane.showConfirmDialog(null," you sure you want to change profile?","Updating profileAre",JOptionPane.YES_NO_OPTION);
       if(confirm==0)
       {
            profile.setImage(profileicon);
           user.setText(newUsername.getText());
           try {
               new Database_Conn().UpdateUser(username, newUsername.getText(),changeprof.Getbytes());//Updating the profile of the user
           } catch (SQLException ex) {
               Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
           }
           Username=newUsername.getText();
         
          // st.close();
       }
   });
   VBox vb=new VBox();
   HBox profilehb=new HBox();
   profilehb.getChildren().addAll(profileimag,imageview);
   profilehb.setSpacing(15);
   HBox Usernamehb=new HBox();
   Usernamehb.getChildren().addAll(Usernamelabel,newUsername);
   Usernamehb.setSpacing(15);
   HBox functions=new HBox();//Holding button and change password enquiery label
   Label pass=new Label("Want to change password !");
 
   functions.getChildren().addAll(update,pass);
   functions.setSpacing(20);
   
   vb.getChildren().addAll( profiledes,profilehb,Usernamehb,functions); 
   pass.setId("sign");
    PasswordField currentpass=new PasswordField();
       currentpass.setPromptText("Enter the current password");
       
       PasswordField newpass=new PasswordField();
       newpass.setPromptText("Enter the new passord");
       PasswordField confirmpass=new PasswordField();
       confirmpass.setPromptText("Confirm the new password");
       Button changepass=new Button("Update password");
   pass.setOnMousePressed(add->{
      
       vb.getChildren().addAll(currentpass,newpass,confirmpass,changepass);//appending other nodes to the pannel
    
       pass.setDisable(true);
       
       
   });
      changepass.setOnAction(value->{
           
          new Database_Conn().Updatepassword(username,currentpass.getText() ,newpass.getText(), confirmpass.getText());//Updating password
           currentpass.setText(null);
           newpass.setText(null);
           confirmpass.setText(null);
           
           
           
       });
   vb.setSpacing(10);
   vb.getStyleClass().add("vbox");
   pane.setId("AllPage");
   pane.getChildren().add(vb);
    Scene scene = new Scene(pane, 430, 370);
    String css=ClientHomePage.class.getResource("LoginForm.css").toExternalForm();
    scene.getStylesheets().add(css);
  
    st.setTitle("Update the user profile");
    st.setScene(scene);
    
    st.show();
    
});
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
  Button Logout=new Button("Logout");
  Logout.setId("sign");
  Logout.setOnAction(action->{
     int confirm= JOptionPane.showConfirmDialog(null, "Do you realy want to logout ?","Logging out",JOptionPane.YES_NO_OPTION);
     if(confirm==0)
     {
         
             if(Forms.loggedUser.contains(username))
             {
                 Forms.loggedUser.remove(user); 
             }
         
        
       primaryStage.close();
       new LoginForm().start(primaryStage);
       
     }
  });
nav.getChildren().addAll(Logout,dateLabel,new MainMenu(),search,searching,prof);//Adding all itemes to navigation bar

homepage.setTop(nav);
homepage.setCenter( centeralpane);
homepage.setLeft(Leftpane);
HBox messageText=new HBox();

writemessage.setPromptText("Write the message");
writemessage.setTooltip(new Tooltip("message"));
Button Send=new Button("Send");
ButtonListener buttonListener = new ButtonListener();
writemessage.setOnKeyPressed((KeyEvent e) -> {
    if(e.getCode()==KeyCode.ENTER)
    {
       ButtonListener buttListener = new ButtonListener();
    }
});
 
  
  Send.setOnAction(buttonListener);
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
primaryStage.setTitle("Chat appliction ");
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
  byte[] file_image=null;
  byte[] buf =null;
        @Override
        public void handle(ActionEvent event) {
             //To change body of generated methods, choose Tools | Templates.
        
             try{
  
  
   configureFileChooser(fileChooser);
   Stage stage=new Stage();
                
                     File file=   fileChooser.showOpenDialog(stage);
                   
      

    fin=new FileInputStream(file);
    ByteArrayOutputStream bos= new ByteArrayOutputStream();
                 buf = new byte[1024];
    for(int readnum;(readnum=fin.read(buf))!=-1; ){
    bos.write(buf,0,readnum);
}
                file_image = bos.toByteArray();
}    catch (FileNotFoundException ex) {
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) { 
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     }
                  try {
          ByteArrayInputStream bis= new ByteArrayInputStream(file_image);
         BufferedImage bim=ImageIO.read(bis);
         profileicon=SwingFXUtils.toFXImage(bim, null );
     } catch (IOException ex) 
     {
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     }
                            messageArea.getChildren().addAll(new ImageView(profileicon));
//                           byte[]bytearray=new byte [(int)file.length()];
//            BufferedInputStream bin=new BufferedInputStream(fin);
               String d=Dformat.format(date)+" "+Dtformat.format(now);
     
            new Database_Conn().InsertPhoto(username,file_image,d);

     } 
                    }
     private class ChangeProfile implements EventHandler<MouseEvent>,java.io.Serializable {
 final FileChooser fileChooser=new FileChooser();
  InputStream fin=null;
  byte[] file_image=null;
  byte[] buf =null;
        @Override
        public void handle(MouseEvent event) {
             //To change body of generated methods, choose Tools | Templates.
        
             try{
  
  
   configureFileChooser(fileChooser);
   Stage stage=new Stage();
                
                     File file=   fileChooser.showOpenDialog(stage);
                   
      

    fin=new FileInputStream(file);
    ByteArrayOutputStream bos= new ByteArrayOutputStream();
                 buf = new byte[1024];
    for(int readnum;(readnum=fin.read(buf))!=-1; ){
    bos.write(buf,0,readnum);
}
                file_image = bos.toByteArray();
}    catch (FileNotFoundException ex) {
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) { 
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
         
     }
             
     try {
          ByteArrayInputStream bis= new ByteArrayInputStream(file_image);
         BufferedImage bim=ImageIO.read(bis);
         profileicon=SwingFXUtils.toFXImage(bim, null );
     } catch (IOException ex) {
         Logger.getLogger(ClientHomePage.class.getName()).log(Level.SEVERE, null, ex);
     }
             
             //imageview=new ImageView(profileicon);
               imageview.setImage(profileicon);
                          //  messageArea.getChildren().addAll(new ImageView(new Image(fin)));
//                           byte[]bytearray=new byte [(int)file.length()];
//            BufferedInputStream bin=new BufferedInputStream(fin);
//               String d=Dformat.format(date)+" "+Dtformat.format(now);
//     
//            new Database_Conn().InsertPhoto(username,file_image,d);

                        }
        //returning the bytes of the selected image
        public byte[] Getbytes()
        {
            return file_image;
        }
        
                    }
                    
                
               
}  
        
      
        



        
    
            
     
     

