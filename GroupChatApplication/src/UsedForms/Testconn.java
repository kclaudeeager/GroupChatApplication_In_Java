/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Kwizera
 */
public class Testconn {
    public static void main(String[] args)
    {
         Connection  connection=null;
    
    try{
       Class.forName("com.mysql.jdbc.Driver");   //register driver

      connection=DriverManager.getConnection( "jdbc:mysql://db4free.net:3306/mult_user_chat?zeroDateTimeBehavior=convertToNull [kwizera_claude on Default schema]","kwizera_claude","kwizeraeager14");
      if(connection!=null)
      System.out.println("Connected");
      else
         System.out.println("Not connected");  
        
    }   catch (Exception ex)
    {
     ex.getMessage();
    }
    Database_Conn db=new Database_Conn();
       System.out.println(db.createConnection()); 
    }
}
