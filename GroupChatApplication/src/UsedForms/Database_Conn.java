/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsedForms;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Kwizera
 */
public class Database_Conn  {
      PreparedStatement preparedStatement;
  ResultSet result=null;
  private static final int MIN_PIXELS = 10;
     public Connection createConnection(){
    
     
        Connection  connection=null;
    
    try{
       Class.forName("com.mysql.jdbc.Driver");   //register driver

      connection=DriverManager.getConnection( "jdbc:mysql://localhost/mult_user_chat","root","");
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
      result=preparedStatement.executeQuery();
      if(result.next())
      {
          checkif=true;
         username=result.getString("username");
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
public void InsertMessages(String Sender,String Message,String date)
{
    try{
        
       int clientID=GetSender(Sender);
      preparedStatement=createConnection().prepareStatement("insert into messages(Sender_Id,Text_messages,Time) values(?,?,?)");
      preparedStatement.setInt(1, clientID);
      preparedStatement.setString(2,Message );
     preparedStatement.setString(3,date);
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
     preparedStatement=createConnection().prepareStatement("select users.username,messages.Text_messages,messages.Time from messages INNER JOIN users ON messages.Sender_Id=users.ID order by messages.messageId ASC");
    result= preparedStatement.executeQuery();
    while(result.next())
    {
        String Sender=result.getString("username");
        String message=result.getString("Text_messages");
        
   
        String time=result.getString("Time");
        Text date=new Text(time);
        //date.setFont(Font.font("verdana", FontWeight.THIN,FontPosture.ITALIC,20));
        Label lb=new Label(date.getText());
        lb.setId("time");
        Label sentms=new Label("["+Sender+"] : "+message+"   "+lb.getText());
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
 result=preparedStatement.executeQuery();
 if(result.next())
 {
    senderId=Integer.parseInt(result.getString("ID"));
    
 }
 return senderId;
}
public void InsertPhoto(String Username,InputStream photo,String date,byte[] b)
        
{
    try{
        
       int clientID=GetSender(Username);
      preparedStatement=createConnection().prepareStatement("insert into Photos(Sender_Id,photo,Time) values(?,?,?)");
      preparedStatement.setInt(1, clientID);
      preparedStatement.setBytes(2,b);
     preparedStatement.setString(3,date);
      preparedStatement.executeUpdate();
      //JOptionPane.showMessageDialog(null, "sent");
     
    }     catch (SQLException ex) {
              Logger.getLogger(Database_Conn.class.getName()).log(Level.SEVERE, null, ex);
          }
}
public ObservableList<HBox> GetImageMessages()
{
    
    ObservableList<HBox> Sentphotomessages=FXCollections.observableArrayList();
         
  try{
     preparedStatement=createConnection().prepareStatement("select users.username,photos.photo,photos.Time from photos INNER JOIN users ON photos.Sender_Id=users.ID order by photos.photoId ASC");
    result= preparedStatement.executeQuery(); 
     int size=0;

    while(result.next())
    {
                 

                 InputStream imageFile = result.getBinaryStream(2);
                Image image = new Image(imageFile);
//                int len = (int) imageFile.length();
//                byte[] buf = imageFile.getBytes(1, len);

//                    fos.write(buf, 0, len);
               ImageView imageview = new ImageView(image);
                double width = image.getWidth();
        double height = image.getHeight();
        reset(imageview, width / 2, height / 2);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
         imageview.setOnMousePressed(e -> {
            
            Point2D mousePress = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        imageview.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));
            shift(imageview, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageview, new Point2D(e.getX(), e.getY())));
        });

        imageview.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageview.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                // don't scale so that we're bigger than image dimensions:
                Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );
            
            Point2D mouse = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the image

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale 

            // we then clamp this value so the image never scrolls out
            // of the imageview:

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale, 
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale, 
                    0, height - newHeight);

            imageview.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

            imageview.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                reset(imageview, width, height);
            }
        });
               imageview.setFitWidth(400);
               imageview.setFitHeight(350);
            
           imageview.setPreserveRatio(true);
             

        
        String Sender=result.getString("username");

        String time=result.getString("Time");
        Text date=new Text(time);
        //date.setFont(Font.font("verdana", FontWeight.THIN,FontPosture.ITALIC,20));
        Label lb=new Label(date.getText());
        lb.setId("time");
        Label sender=new Label(Sender+" : ");
         HBox sentphotos=new HBox(sender,new VBox(lb,imageview) );
         if(sender.getText().startsWith(ClientHomePage.Username))
         {
             sender.setText(sender.getText().replace(ClientHomePage.Username,"YOU"));
         }
         sentphotos.setSpacing(15);
       Sentphotomessages.add(sentphotos);
       
    }   
 
     
  }       catch (SQLException ex) {     
              Logger.getLogger(Database_Conn.class.getName()).log(Level.SEVERE, null, ex);
          }

      
 return Sentphotomessages; 
}
private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }
private static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            Logger.getLogger(Database_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
private void shift(ImageView imageView, Point2D delta) {
		Rectangle2D viewport = imageView.getViewport();
		double width = imageView.getImage().getWidth();
		double height = imageView.getImage().getHeight();
		double maxX = width - viewport.getWidth();
		double maxY = height - viewport.getHeight();
		double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
		double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);
		if (minX < 0.0) {
			minX = 0.0;
		}
		if (minY < 0.0) {
			minY = 0.0;
		}
		imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
	}
    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(), 
                viewport.getMinY() + yProportion * viewport.getHeight());
    }


}

