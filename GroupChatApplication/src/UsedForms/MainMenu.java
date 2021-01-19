package UsedForms;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public final class MainMenu extends MenuBar{
    
    
    // file menu
    Menu fileMenu = new Menu("file");
    
    //file menu items
    MenuItem exit = new MenuItem("Exit");
    
    // view menu
    Menu viewMenu = new Menu("view");
    
    // view menu items
    MenuItem zoomIn = new MenuItem("zoom in");
    MenuItem zoomOut = new MenuItem("zoom out ");
    
    // help menu
    Menu help = new Menu("help");
    
    // about us 
    Menu aboutUs = new Menu("about Us"); 
    public MainMenu(){
        addItems();
        boolean addAll = getMenus().addAll(fileMenu, viewMenu, aboutUs, help);
        setStyle("-fx-font-size:13px");
        
        zoomIn.setOnAction(eh ->{
            System.out.println("zoon in clicked");
        });
        exit.setOnAction(eh ->{
            System.out.print("exit");
        });
    }
    public void addItems(){
        fileMenu.getItems().addAll(exit);
        viewMenu.getItems().addAll(zoomIn, zoomOut);
        
    }
    
    
}