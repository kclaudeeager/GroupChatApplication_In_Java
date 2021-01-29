/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;


import java.awt.Font;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;

/**
 *
 * @author Kwizera
 */
public class Forms {
    Database_Conn DC=new Database_Conn();  
public static String User;
public static ObservableList<String> loggedUser = FXCollections.observableArrayList();
TextField name=new TextField();
 PasswordField pass=new PasswordField();
    static boolean isValid(String email) {
      boolean result=true;

      try{
          InternetAddress emailAddress=new InternetAddress(email);
          emailAddress.validate();
      }
      catch(AddressException ex)
      {
          result=false;
      }
      return result;
   }
  Stage st=new Stage();
    VBox Login(Stage st)
    {
       Label Ta=new Label();
       String header="Login";
      Ta.setText(header);
     Ta.setId("header");
      Ta.setMinWidth(300);
      
        this.st=st;
        VBox loginpane=new VBox(5);
        loginpane.setMaxHeight(500);
        loginpane.setMaxWidth(70);
        loginpane.getStyleClass().add("vbox");
        loginpane.prefWidthProperty().bind(Bindings.add(st.widthProperty(), -50));
        loginpane.setPadding(new Insets(10,2, 1,0));
        loginpane.setId("login");
        loginpane.setSpacing(10);
        loginpane.setId("Loginpage");
        Label Username=new Label("Username");
        Username.setTextFill(Color.WHITE);
        
        name.setPromptText("Enter the username");
   name.setMaxWidth(300);
      
        name.setTooltip(new Tooltip("Username"));
        Label password=new Label("Password");
       password.setTextFill(Color.WHITE);
        pass.setMinWidth(300);
        pass.setPromptText("Enter your password");
        pass.setTooltip(new Tooltip("paasword"));
        
        Button submit=new Button("Login");
         String log=ClientHomePage.class.getResource("photos/login.png").toExternalForm();
           String p=ClientHomePage.class.getResource("photos/password.png").toExternalForm();
           
        submit.setGraphic(new ImageView(new Image(log)));
        //Button signup=new Button("Signup");
        Label message=new Label("Not have an account?");
        HBox hb=new HBox();
        hb.setSpacing(1);
        
        hb.getChildren().addAll(new ImageView(new Image(p)),pass);
         message.setTextFill(Color.RED);     
         message.setId("sign");
           Label haveaccount=new Label("Arlead have an account?");
         haveaccount.setTextFill(Color.RED);
         haveaccount.setId("sign");
        loginpane.getChildren().addAll(Username,name,password,hb,submit,message);
        message.setOnMousePressed(e->{
            loginpane.getChildren().removeAll(Username,name,password,hb,submit,message);
         loginpane.setMinWidth(400);
         VBox signingup=Signup();
        loginpane.getChildren().addAll(signingup,haveaccount);
       // message.setDisable(true);
 
        haveaccount.setOnMousePressed(a->{
            
            loginpane.getChildren().removeAll(signingup,haveaccount); 
             loginpane.getChildren().addAll(Username,name,password,hb,submit,message);
             message.setDisable(false);
         });
        
        });
          
        
        pass.setOnKeyPressed(e->
        {
            if(e.getCode()==KeyCode.ENTER)
            {
                 logingin();
                    if(!loggedUser.contains(User)&& User!=null)
                       loggedUser.add(User);
            }
        });
        submit.setOnAction(e->{
      
       logingin();
       if(!loggedUser.contains(User)&& User!=null)
       loggedUser.add(User);
               });
        
        return loginpane;
        
    }
    
  
      BorderPane SetSignupform(VBox vb)
    {
     BorderPane bp=new BorderPane();
     bp.setRight(vb);
     return bp;
    }
    VBox Signup()
    {
               VBox signUpPane=new VBox();
      
      
        signUpPane.setPadding(new Insets(11.5, 12.5, 13.5,5));
    
        Label Fname=new Label("First name");
        TextField Fnamefield=new TextField();
        Fname.setTextFill(Color.WHITE);
          
        Fnamefield.setPromptText("Enter the first name");
        Label Lname=new Label("Sur name");
         Lname.setTextFill(Color.WHITE);
        TextField Lnamefield=new TextField();
        Lnamefield.setPromptText("Enter the Last name");
        Label gender=new Label("Gender");
         gender.setTextFill(Color.WHITE);
        ComboBox select=new ComboBox();
        
        ObservableList<String> options =FXCollections.observableArrayList("Male","Female","Other");
        select.setItems(options);
        select.setValue("Male");
        Label Email=new Label("Email");
         Email.setTextFill(Color.WHITE);
        TextField Emailfield=new TextField();
         Label UserName=new Label("Username");
          UserName.setTextFill(Color.WHITE);
        TextField User=new TextField();
        User.setPromptText("Enter the user name");
        Emailfield.setPromptText("Enter your email address");
         Label password=new Label("Password");
          password.setTextFill(Color.WHITE);
        PasswordField pass=new PasswordField();
        pass.setPromptText("Enter the password");
           Label confirm=new Label("Confirm Password");
            confirm.setTextFill(Color.WHITE);
        PasswordField confirmpass=new PasswordField();
        confirmpass.setPromptText("confirm your password");
        
         signUpPane.setPrefSize(700,400);
      signUpPane.setMaxWidth(300);
      
        //GridPane.setHalignment(sign, HPos.RIGHT);
        Label register =new Label("Register here:");
         register.setTextFill(Color.WHITE);
        register.getStyleClass().add("title");
        Button signup=new Button("Signup");
         String sup=ClientHomePage.class.getResource("photos/signup.png").toExternalForm();
         signup.setGraphic(new ImageView(new Image(sup)));
        
        signup.setOnAction(new EventHandler<ActionEvent>() {
                   @Override
                   public void handle(ActionEvent e) {
                       boolean check=false;
                       if(!Lnamefield.getText().isEmpty()&&!Fnamefield.getText().isEmpty()&&!Emailfield.getText().isEmpty()
                               &&!User.getText().isEmpty()&&!pass.getText().isEmpty()&&!confirmpass.getText().isEmpty())
                       {
                           check=true;
                       }
                       
                       else
                       {
                           check=false;
                           JOptionPane.showMessageDialog(null,"pleas fill all the fields!");
                       }
                       if(!isValid(Emailfield.getText()))
                       {
                           check=false;
                           JOptionPane.showMessageDialog(null,"please fill the right email");
                       }
                       if(!pass.getText().equals(confirmpass.getText()))
                       {
                           check=false;
                           JOptionPane.showMessageDialog(null,"password did not match!");
                           
                       }
                       if(check==true)
                       {
                           
                           DC.addData(Fnamefield.getText(), Lnamefield.getText(), Emailfield.getText(), User.getText(), pass.getText());
                           
                       }
                       Fnamefield.clear();
                       Lnamefield.clear();
                       Emailfield.clear();
                       User.clear();
                       confirmpass.clear();
                       pass.clear();
                       select.setValue("Male");
                   }
               });
        HBox hbox=new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(select,signup);
      
      
        signUpPane.getChildren().addAll(register,Fname, Fnamefield, Lname, Lnamefield, Email, Emailfield,UserName,User,password,pass,confirm,confirmpass, gender,
                hbox); 
     
        return  signUpPane;
        
    }
    private void logingin()
    {
         Database_Conn DC=new Database_Conn(); 
        User = DC.GetData(name.getText(), pass.getText()); 
          if(User!=null)
     {
          Stage stage=new Stage();
     
          
          ClientHomePage client=new ClientHomePage();
         // client.user.setText(User);
          client.start(stage);
         
     
          name.clear();
          pass.clear();
          //st.close();
          
     }
        
    }
}
