/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafxsocketprogramming.ServerJavaFX;
//import javafxsocketprogramming.ServerJavaFX;

/**
 *
 * @author Kwizera
 */
  
public class LoginForm extends Application {
    
     BorderPane Allpage=new BorderPane();
    @Override
    public void start(Stage primaryStage) {
      Forms Login=new Forms();
      Label Ta=new Label();
      String header="Welcome to the multi-user Chat Please Login to get started or create one !";
      Ta.setText(header);
     Ta.setId("header");
      Ta.setMaxHeight(15);
   
      Allpage.setTop(Ta);
      Allpage.setCenter(Login.Login(primaryStage));
      //Allpage.setRight(Login.Signup(primaryStage));
      Scene scene=new Scene(Allpage,1200,700);
        
        String css=LoginForm.class.getResource("LoginForm.css").toExternalForm();
        scene.getStylesheets().clear();
      scene.getStylesheets().add(css);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Multi_user_chat");
      new ServerJavaFX().start(new Stage());
      primaryStage.show();
     Stage st=new Stage();
  // ServerJavaFX Server= new ServerJavaFX();
  // Server.start(st);
        
    }
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
