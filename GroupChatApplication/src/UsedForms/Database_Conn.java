/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;

import java.sql.*;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;

/**
 *
 * @author Kwizera
 */
public class Database_Conn  {
      PreparedStatement preparedStatement;
  ResultSet resultSet=null;
     public Connection createConnection(){
    
     
        Connection  connection=null;
    
    try{
       Class.forName("com.mysql.jdbc.Driver");   //register driver

      connection=DriverManager.getConnection( "jdbc:mysql://db4free/mult_user_chat","kwizera_claude","kwizeraeager@14");
      System.out.println("Connected");
        
    }   catch (Exception ex)
    {
     ex.getMessage();
    }
  return connection;
  
  }
     public void addData(String fn,String ln,String email,String username,String password){

     try{
  
         preparedStatement=createConnection().prepareStatement("insert into Users(firstname,lastname,Email,username,password) values(?,?,?,?,?)");
            
            preparedStatement.setString(1, fn);
            preparedStatement.setString(2, ln);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,username);
            preparedStatement.setString(5,password);
            preparedStatement.executeUpdate();
           // JOptionPane.showMessageDialog(null,"Well Inserted !");
        
     }
     catch(Exception e)
     {
      JOptionPane.showMessageDialog(null, "The account was taken !");
     }
  }
public String GetData(String username,String password)
        
{
    boolean checkif=false;
    try{
       preparedStatement=createConnection().prepareStatement("select username,password from users where username=? and password=?");
       preparedStatement.setString(1, username);
       preparedStatement.setString(2, password);
      resultSet=preparedStatement.executeQuery();
      if(resultSet.next())
      {
          checkif=true;
         username=resultSet.getString("username");
         return username;
      }
      else
      {
          checkif=false;
          JOptionPane.showMessageDialog(null, "Invalid username or password");
      }
    }
    catch(Exception ex)
    {
        
    }
    return null;
}
//public ArrayList<String> LoggedinUsers() throws SQLException
//{
//   ArrayList users=new ArrayList<String>();
//     preparedStatement=createConnection().prepareStatement("select username from users");
//     
//      resultSet=preparedStatement.executeQuery();
//      while(resultSet.next())
//      {
//          users.add(resultSet.getString("username"));
//          
//      }
//    
//      return users;
//}
public void InsertMessages(String Sender,String Message)
{
    try{
        
       int clientID=GetSender(Sender);
      preparedStatement=createConnection().prepareStatement("insert into messages(Sender_Id,Text_messages) values(?,?)");
      preparedStatement.setInt(1, clientID);
      preparedStatement.setString(2,Message );
     //  preparedStatement.setString(3,time );
      preparedStatement.executeUpdate();
      //JOptionPane.showMessageDialog(null, "sent");
     
    }     catch (SQLException ex) {
              Logger.getLogger(Database_Conn.class.getName()).log(Level.SEVERE, null, ex);
          }
    
}
public ArrayList<Label> GetMessages()
{
    ArrayList<Label> Sentmessages=new ArrayList<Label>();
  try{
     preparedStatement=createConnection().prepareStatement("select users.username,messages.Text_messages,messages.Time from messages INNER JOIN users ON messages.Sender_Id=users.ID order by messages.sender_Id DESC");
    resultSet= preparedStatement.executeQuery();
    while(resultSet.next())
    {
        String Sender=resultSet.getString("username");
        String message=resultSet.getString("Text_messages");
        String time=resultSet.getString("Time");
        Label sentms=new Label("["+Sender+"] : "+message+"         (" +time+ ")");
        Sentmessages.add(sentms);
        
    }
 
     
  } 
  catch(SQLException e)
  {
  }
      
 return Sentmessages; 
}
       
     
private int GetSender(String Sender) throws SQLException
{
int senderId = 0;
     preparedStatement=createConnection().prepareStatement("select ID from users where username=?");
preparedStatement.setString(1, Sender);
 resultSet=preparedStatement.executeQuery();
 if(resultSet.next())
 {
    senderId=Integer.parseInt(resultSet.getString("ID"));
    
 }
 return senderId;
}

}

