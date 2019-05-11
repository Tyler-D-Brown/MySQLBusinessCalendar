/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static tbro402businesscalendar.DBConnection.conn;
import static tbro402businesscalendar.Tbro402BusinessCalendar.uID;

/**
 *
 * @author tbrown
 */
public class reportMainController {
    @FXML
    TextField ID;
    
    @FXML
    void appointmentType(ActionEvent event){
        Scene scene=null;
        try
        {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/reportAppointmentType.fxml")));
        }catch(Exception e){
            System.out.println(e);
        }
        //show the main screen
        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }
    
    @FXML
    void userSchedule(ActionEvent event){
        try{
            uID=Integer.parseInt(ID.getText());
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT userId FROM user WHERE userId = " + ID.getText() + ";";
            ResultSet result = q.executeQuery(sqlStatement);
            boolean r = result.next();
            if(r==false){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Invalid ID");
                alert.setContentText("Please enter a vaild User ID.");
                alert.showAndWait();
                return;
            }
        }catch(Exception e){
            System.out.println();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Invalid ID");
            alert.setContentText("Please enter a value in the User ID field.");
            alert.showAndWait();
            return;
        }
        
        Scene scene=null;
        try
        {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/reportUserSchedule.fxml")));
        }catch(Exception e){
            System.out.println(e);
        }
        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }
    
    @FXML
    void userAppointment(ActionEvent event){
        Scene scene=null;
        try
        {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/reportUserIDCount.fxml")));
        }catch(Exception e){
            System.out.println(e);
        }
        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }
    
    @FXML
    void exit(ActionEvent event){
        Scene scene=null;
        try
        {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/ViewController/loginPage.fxml")));
        }catch(Exception e){
            System.out.println(e);
        }
        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }
}
