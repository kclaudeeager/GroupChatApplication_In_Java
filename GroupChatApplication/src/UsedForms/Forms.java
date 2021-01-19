/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsocketprogramming.ServerJavaFX;
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
  
    VBox Login(Stage st)
    {
        VBox loginpane=new VBox(5);
        loginpane.getStyleClass().add("vbox");
        loginpane.prefWidthProperty().bind(Bindings.add(st.widthProperty(), -50));
        loginpane.setPadding(new Insets(10,2, 1,0));
        loginpane.setId("login");
        loginpane.setSpacing(10);
        Label Username=new Label("Username");
        TextField name=new TextField();
        name.setPromptText("Enter the username");
   name.setMaxWidth(400);
      
        name.setTooltip(new Tooltip("Username"));
        Label password=new Label("Password");
        PasswordField pass=new PasswordField();
        pass.setMinWidth(400);
        pass.setPromptText("Enter your password");
        pass.setTooltip(new Tooltip("paasword"));
        Button submit=new Button("Login");
        Button signup=new Button("Signup");
        Label message=new Label("Not have an account?");
        HBox hb=new HBox();
        hb.setSpacing(10);
        hb.getChildren().addAll(pass,submit);
        loginpane.getChildren().addAll(Username,name,password,hb,message,signup);
        signup.setOnAction(e->{
        loginpane.getChildren().add(Signup());
        signup.setDisable(true);
        });
        submit.setOnAction(e->{
      
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
        Fnamefield.setMaxWidth(500);
        Fnamefield.setPromptText("Enter the first name");
        Label Lname=new Label("Sur name");
        TextField Lnamefield=new TextField();
        Lnamefield.setPromptText("Enter the Last name");
        Label gender=new Label("Gender");
        ComboBox select=new ComboBox();
        ObservableList<String> options =FXCollections.observableArrayList("Male","Female","Other");
        select.setItems(options);
        select.setValue("Male");
        Label Email=new Label("Email");
        TextField Emailfield=new TextField();
         Label UserName=new Label("Username");
        TextField User=new TextField();
        User.setPromptText("Enter the user name");
        Emailfield.setPromptText("Enter your email address");
         Label password=new Label("Password");
        PasswordField pass=new PasswordField();
        pass.setPromptText("Enter the password");
           Label confirm=new Label("Confirm Password");
        PasswordField confirmpass=new PasswordField();
        confirmpass.setPromptText("confirm your password");
        
         signUpPane.setPrefSize(700,400);
      signUpPane.setMaxWidth(500);
      
        //GridPane.setHalignment(sign, HPos.RIGHT);
        Label register =new Label("Register here:");
        register.getStyleClass().add("title");
        Button signup=new Button("Signup");
        
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
        hbox.getChildren().addAll(select,signup);
        signUpPane.getChildren().addAll(register,Fname, Fnamefield, Lname, Lnamefield, Email, Emailfield,UserName,User,password,pass,confirm,confirmpass, gender,
                hbox);
        return  signUpPane;
        
    }
}
