/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbro402businesscalendar;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Locale;

/**
 *
 * @author Barid
 */

public class Tbro402BusinessCalendar extends Application {
    
    public static int language=0;
    public static int id=-1;
    public static String user="";
    public static int cid=-1;
    public static int uID=-1;
    public static int cityId=-1;
    public static int addID=-1;
    public static int countryID=-1;
    
    @Override
    public void start(Stage PrimaryStage) throws ClassNotFoundException, SQLException, Exception {
        
        System.out.println(Locale.getDefault().getDisplayLanguage());
       
        File directory = new File("./");
           
        
        Locale current = Locale.getDefault();
        
        String s = current.getDisplayLanguage();
        
        //current.getCountry();
        
        
        if(s.equals("English")|s.equals("Anglais")|s.equals("Inglés")){
            language=0;
        }else if(s.equals("French")|s.equals("français")|s.equals("francés")){
            language=1;
        }else if(s.equals("Spanish")|s.equals("espanol")|s.equals("español")){
            language=2;
        }
        
        
        
        
        DBConnection.makeConnection();
       //load the mainscreen
       Scene scene=null;
       try
       {
       scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/loginPage.fxml")));
       }catch(Exception e){
           System.out.println(e);
       }
       //show the main screen
       PrimaryStage.setScene(scene);
       PrimaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
