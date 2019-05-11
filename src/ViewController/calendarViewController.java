/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import Classes.appointment;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import static tbro402businesscalendar.DBConnection.conn;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cid;
import static tbro402businesscalendar.Tbro402BusinessCalendar.id;
import static tbro402businesscalendar.Tbro402BusinessCalendar.uID;
/**
 *
 * @author Barid
 */
public class calendarViewController implements Initializable{
    
    @FXML
    private TableColumn mAppID;
    
    @FXML
    private TableColumn mTitle;
    
    @FXML
    private TableColumn mLoc;
    
    @FXML
    private TableColumn mCon;
    
    @FXML
    private TableColumn mStrt;
    
    @FXML
    private TableColumn mEnd;
    
    @FXML
    private TableColumn wAppID;
    
    @FXML
    private TableColumn wTitle;
    
    @FXML
    private TableColumn wLoc;
    
    @FXML
    private TableColumn wCon;
    
    @FXML
    private TableColumn wStrt;
    
    @FXML
    private TableColumn wEnd;
    
    @FXML
    private TableView month;
    
    @FXML
    private TableView week;
    
    @FXML
    void wAdd(ActionEvent event){
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void wEdit(ActionEvent event){
        try
        {
            try{
                appointment ap=new appointment((appointment) week.getSelectionModel().getSelectedItem());
                id=ap.getID();
            }
            catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("error in Selection");
                alert.setContentText("Please select an appointment");
                alert.showAndWait();
            }
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void wDelete(ActionEvent event){
        appointment c=null;
        try{
            c = new appointment((appointment) week.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Contact Selection");
            alert.setContentText("Please select a Customer for the appointment");
            alert.showAndWait();
            return;
        }
        cid=c.getID();
        try
        {
        Statement q = conn.createStatement();
        String statement = "DELETE FROM appointment WHERE appointmentId = "  + cid + ";";
        q.executeUpdate(statement);
        }
        catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void mAdd(ActionEvent event){
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void mEdit(ActionEvent event){
        try
        {
            try{
                appointment ap=new appointment((appointment) month.getSelectionModel().getSelectedItem());
                id=ap.getID();
            }
            catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("error in Selection");
                alert.setContentText("Please select an appointment");
                alert.showAndWait();
            }
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void mDelete(ActionEvent event){
        appointment c=null;
        try{
            c = new appointment((appointment) month.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Contact Selection");
            alert.setContentText("Please select a Customer for the appointment");
            alert.showAndWait();
            return;
        }
        cid=c.getID();
        try
        {
        Statement q = conn.createStatement();
        String statement = "DELETE FROM appointment WHERE appointmentId = "  + cid + ";";
        q.executeUpdate(statement);
        }
        catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        refreshTables();
        
        try {
            Statement q = conn.createStatement();
            TimeZone t = TimeZone.getDefault();
            Calendar c = new GregorianCalendar();
            c.setTimeZone(t);
            
            String state = "SELECT * FROM appointment WHERE  start BETWEEN NOW() AND (NOW() + INTERVAL 15 MINUTE);";
            ResultSet r = q.executeQuery(state);
            
            if(r.first()){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Appointment soon");
                alert.setContentText("You have an appointment within 15 minutes of now.");
                alert.showAndWait();
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    void refreshTables(){
        try{
            ObservableList<appointment> appointments;
            ArrayList<appointment> allAppointments=new ArrayList<>();
            Statement q = conn.createStatement();
        
            String sqlStatement = "";
            ResultSet result = null;
            
            boolean n;
            try{            
                sqlStatement = "SELECT * FROM appointment WHERE  DATE(start) >= DATE(NOW()) AND DATE(START) < DATE(NOW()) + INTERVAL 7 DAY AND userId = " + uID + " ORDER BY start;";
                result = q.executeQuery(sqlStatement);
                result.next();
                
                do{
                    allAppointments.add(new appointment(result.getInt("appointmentID"), result.getString("title"), result.getString("location"), result.getString("contact"), result.getString("start"), result.getString("end")));
                    n = result.next();
                }while(n==true);
            
                appointments=FXCollections.observableArrayList(allAppointments);
            
                week.setItems(appointments);
                wAppID.setCellValueFactory(new PropertyValueFactory<>("ID"));
                wTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                wLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
                wCon.setCellValueFactory(new PropertyValueFactory<>("contact"));
                wStrt.setCellValueFactory(new PropertyValueFactory<>("start"));
                wEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            }
            catch(Exception e){
                System.out.println(e);
            }
    
            try{
                sqlStatement = "SELECT * FROM appointment WHERE  Month(start) = Month(now()) AND userId = " + uID + " ORDER BY start;";
                result = q.executeQuery(sqlStatement);
                
                allAppointments=new ArrayList<>();
                
                result.next();
                
                do{
                    allAppointments.add(new appointment(result.getInt("appointmentID"), result.getString("title"), result.getString("location"), result.getString("contact"), result.getString("start"), result.getString("end")));
                    n = result.next();
                }while(n==true);
                
                
                appointments=FXCollections.observableArrayList(allAppointments);
            
                month.setItems(appointments);
                mAppID.setCellValueFactory(new PropertyValueFactory<>("ID"));
                mTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                mLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
                mCon.setCellValueFactory(new PropertyValueFactory<>("contact"));
                mStrt.setCellValueFactory(new PropertyValueFactory<>("start"));
                mEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            }
            catch(Exception e){
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
