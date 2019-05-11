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
public class reportAppointmentTypeController implements Initializable{
    @FXML
    TableView jan;
    
    @FXML
    TableColumn janApp;
    
    @FXML
    TableColumn janCon;
    
    @FXML
    TableView feb;
    
    @FXML
    TableColumn febApp;
    
    @FXML
    TableColumn febCon;
    
    @FXML
    TableView mar;
    
    @FXML
    TableColumn marApp;
    
    @FXML
    TableColumn marCon;
    
    @FXML
    TableView apr;
    
    @FXML
    TableColumn aprApp;
    
    @FXML
    TableColumn aprCon;
    
    @FXML
    TableView may;
    
    @FXML
    TableColumn mayApp;
    
    @FXML
    TableColumn mayCon;
    
    @FXML
    TableView jun;
    
    @FXML
    TableColumn junApp;
    
    @FXML
    TableColumn junCon;
    
    @FXML
    TableView jul;
    
    @FXML
    TableColumn julApp;
    
    @FXML
    TableColumn julCon;
    
    @FXML
    TableView aug;
    
    @FXML
    TableColumn augApp;
    
    @FXML
    TableColumn augCon;
    
    @FXML
    TableView sep;
    
    @FXML
    TableColumn sepApp;
    
    @FXML
    TableColumn sepCon;
    
    @FXML
    TableView oct;
    
    @FXML
    TableColumn octApp;
    
    @FXML
    TableColumn octCon;
    
    @FXML
    TableView nov;
    
    @FXML
    TableColumn novApp;
    
    @FXML
    TableColumn novCon;
    
    @FXML
    TableView dec;
    
    @FXML
    TableColumn decApp;
    
    @FXML
    TableColumn decCon;
    
    //lambda expression used to gather the obervable list for each month in order to more easily populate the tables. 
    interface lambdaQuery{
        ObservableList<seeder> gather(int i);
    }
    
    @Override
    public void initialize(URL u, ResourceBundle rb){
        
        reportAppointmentTypeController.lambdaQuery result= (i)->{
            boolean n=true;
            
            ObservableList<seeder> count;
            ArrayList<seeder> allCount=new ArrayList<>();
            
            String sqlStatement = String.format("SELECT type, COUNT(*) AS count FROM appointment WHERE MONTH(start) = %1$d GROUP BY type;", i);
            try{
                Statement q = conn.createStatement();
                
                ResultSet r = q.executeQuery(sqlStatement);
                r.next();
                do{
                    allCount.add(new seeder(r.getString("type"), r.getInt("count")));
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
        janApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        janCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        feb.setItems(count);
        febApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        febCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        mar.setItems(count);
        marApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        marCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        apr.setItems(count);
        aprApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        aprCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        may.setItems(count);
        mayApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        mayCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        jun.setItems(count);
        junApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        junCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        jul.setItems(count);
        julApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        julCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        aug.setItems(count);
        augApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        augCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        sep.setItems(count);
        sepApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        sepCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        oct.setItems(count);
        octApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        octCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        nov.setItems(count);
        novApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        novCon.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        i++;    
        
        count = result.gather(i);
        
        dec.setItems(count);
        decApp.setCellValueFactory(new PropertyValueFactory<>("identifier"));
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
