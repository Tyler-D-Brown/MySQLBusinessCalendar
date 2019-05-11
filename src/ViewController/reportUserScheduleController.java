/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Classes.appointment;
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
public class reportUserScheduleController implements Initializable{
    @FXML
    TableView month;
    
    @FXML
    TableColumn appID;
    
    @FXML
    TableColumn title;
    
    @FXML
    TableColumn location;
    
    @FXML
    TableColumn contact;
    
    @FXML
    TableColumn strt;
    
    @FXML
    TableColumn end;
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        refreshTable();
    }
    
    public void refreshTable(){
        try{
            ObservableList<appointment> appointments;
            ArrayList<appointment> allAppointments=new ArrayList<>();
            Statement q = conn.createStatement();
        
            String sqlStatement = "";
            ResultSet result = null;
            
            boolean n;
            try{            
                sqlStatement = "SELECT * FROM appointment WHERE  userID = " + uID + " ORDER BY start;";
                result = q.executeQuery(sqlStatement);
                result.next();
                
                do{
                    allAppointments.add(new appointment(result.getInt("appointmentID"), result.getString("title"), result.getString("location"), result.getString("contact"), result.getString("start"), result.getString("end")));
                    n = result.next();
                }while(n==true);
            
                appointments=FXCollections.observableArrayList(allAppointments);
            
                month.setItems(appointments);
                appID.setCellValueFactory(new PropertyValueFactory<>("ID"));
                title.setCellValueFactory(new PropertyValueFactory<>("title"));
                location.setCellValueFactory(new PropertyValueFactory<>("location"));
                contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
                strt.setCellValueFactory(new PropertyValueFactory<>("start"));
                end.setCellValueFactory(new PropertyValueFactory<>("end"));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
