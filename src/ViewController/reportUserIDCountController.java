/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Classes.seeder;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static tbro402businesscalendar.DBConnection.conn;
import static tbro402businesscalendar.Tbro402BusinessCalendar.uID;

/**
 *
 * @author tbrown
 */
public class reportUserIDCountController implements Initializable{
    
    @FXML
    TableView jan;
    
    @FXML
    TableColumn janID;
    
    @FXML
    TableColumn janUser;
    
    @FXML
    TableColumn janCon;
    
    @FXML
    TableView feb;
    
    @FXML
    TableColumn febID;
    
    @FXML
    TableColumn febUser;
    
    @FXML
    TableColumn febCon;
    
    @FXML
    TableView mar;
    
    @FXML
    TableColumn marID;
    
    @FXML
    TableColumn marUser;
    
    @FXML
    TableColumn marCon;
    
    @FXML
    TableView apr;
    
    @FXML
    TableColumn aprID;
    
    @FXML
    TableColumn aprUser;
    
    @FXML
    TableColumn aprCon;
    
    @FXML
    TableView may;
    
    @FXML
    TableColumn mayID;
    
    @FXML
    TableColumn mayUser;
    
    @FXML
    TableColumn mayCon;
    
    @FXML
    TableView jun;
    
    @FXML
    TableColumn junID;
    
    @FXML
    TableColumn junUser;
    
    @FXML
    TableColumn junCon;
    
    @FXML
    TableView jul;
    
    @FXML
    TableColumn julID;
    
    @FXML
    TableColumn julUser;
    
    @FXML
    TableColumn julCon;
    
    @FXML
    TableView aug;
    
    @FXML
    TableColumn augID;
    
    @FXML
    TableColumn augUser;
    
    @FXML
    TableColumn augCon;
    
    @FXML
    TableView sep;
    
    @FXML
    TableColumn sepID;
    
    @FXML
    TableColumn sepUser;
    
    @FXML
    TableColumn sepCon;
    
    @FXML
    TableView oct;
    
    @FXML
    TableColumn octID;
    
    @FXML
    TableColumn octUser;
    
    @FXML
    TableColumn octCon;
    
    @FXML
    TableView nov;
    
    @FXML
    TableColumn novID;
    
    @FXML
    TableColumn novUser;
    
    @FXML
    TableColumn novCon;
    
    @FXML
    TableView dec;
    
    @FXML
    TableColumn decID;
    
    @FXML
    TableColumn decUser;
    
    @FXML
    TableColumn decCon;
    
    //lambda expression used to gather the observable lists for the tables to avoid repeated code to get the observable list. 
    interface lambdaQuery{
        ObservableList<seeder> gather(int i);
    }
    
    @Override
    public void initialize(URL u, ResourceBundle rb){
        
        reportAppointmentTypeController.lambdaQuery result= (i)->{
            boolean n=true;
            
            ObservableList<seeder> count;
            ArrayList<seeder> allCount=new ArrayList<>();
            
            String sqlStatement = String.format("SELECT t1.userId, t2.userName, COUNT(*) AS count FROM appointment AS t1 INNER JOIN user AS t2 ON t1.userId=t2.userId WHERE MONTH(start) = %1$d GROUP BY userId;", i);
            System.out.println(sqlStatement);
            try{
                Statement q = conn.createStatement();
                
                ResultSet r = q.executeQuery(sqlStatement);
                r.next();
            
                //System.out.println(r.getString("appointmentID"));
                
                do{
                    allCount.add(new seeder(r.getString("userName"), r.getInt("count"), r.getInt("userId")));
                    n = r.next();
                }while(n==true);
            }catch(Exception e){
                System.out.println(e);
            }
            count=FXCollections.observableArrayList(allCount);
                
            return count;
        };
        
        int i=1;
        
        ObservableList<seeder> count = result.gather(i);
        
        jan.setItems(count);
        janID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        janUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        janCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        feb.setItems(count);
        febID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        febUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        febCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        mar.setItems(count);
        marID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        marUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        marCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        apr.setItems(count);
        aprID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        aprUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        aprCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        may.setItems(count);
        mayID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        mayUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        mayCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        jun.setItems(count);
        junID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        junUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        junCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        jul.setItems(count);
        julID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        julUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        julCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        aug.setItems(count);
        augID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        augUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        augCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        sep.setItems(count);
        sepID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        sepUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        sepCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        oct.setItems(count);
        octID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        octUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        octCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        nov.setItems(count);
        novID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        novUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        novCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        dec.setItems(count);
        decID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        decUser.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        decCon.setCellValueFactory(new PropertyValueFactory<>("count"));

    }   
    
    @FXML
    void exit(ActionEvent event){
        uID=-1;
        Scene scene=null;
       try
       {
       scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/reportMain.fxml")));
       }catch(Exception e){
           System.out.println(e);
       }
       //show the main screen
       Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       PrimaryStage.setScene(scene);
       PrimaryStage.show();
    }
}
