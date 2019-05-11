/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Classes.seeder;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static tbro402businesscalendar.DBConnection.conn;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cid;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.addID;
import static tbro402businesscalendar.Tbro402BusinessCalendar.id;
/**
 *
 * @author tbrown
 */
public class customerViewController implements Initializable{
    int select=-1;
    
    @FXML
    TextField name;
    
    @FXML
    TextField active;
    
    @FXML
    TableView addressTable;
    
    @FXML
    TableColumn address;
    
    @FXML
    TableColumn addressID;
    
    @FXML
    void submit(ActionEvent event){
        boolean valid=true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(name.getText().isEmpty()){
            alert.setHeaderText("error in Name");
            alert.setContentText("Invalid Name entry");
            alert.showAndWait();
            valid=false;
            return;
        }else if(active.getText().isEmpty() || (!active.getText().equals("False") && !active.getText().equals("True"))){
            alert.setHeaderText("error in active");
            alert.setContentText("Please enter True or False");
            alert.showAndWait();
            valid=false;
            return;
        }else{
            int act=0;
            if(active.getText().equals("True")){
                act=1;
            }else{
                act=0;
            }
            seeder s = null;
            try{
            s = new seeder((seeder) addressTable.getSelectionModel().getSelectedItem());
            }
            catch(Exception e){
                alert.setHeaderText("error in Address ID");
                alert.setContentText("Invalid Address ID entry");
                alert.showAndWait();
                return;
            }
            try{
                Statement q = conn.createStatement();
                if(cid==-1){
                    int custId=0;
                    String sqlStatement = "select max(customerId) From customer;";
                    ResultSet result = q.executeQuery(sqlStatement);
                    result.next();
                    custId=result.getInt(1);
                    custId++;
                    sqlStatement = String.format("INSERT INTO customer (customerId , customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES(%1$s, '%2$s', %3$s, %4$s, NOW(), '%5$s', '%6$s');", custId, name.getText(), s.getCount(), act, user, user);
                    q.executeUpdate(sqlStatement);
                    try{
                        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
                        PrimaryStage.setScene(scene);
                        PrimaryStage.show();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    
                    String sqlStatement = String.format("UPDATE customer SET customerName = '%1$s', addressId = %2$s, active = %3$s, lastUpdateBy = '%4$s' WHERE customerId = %5$s;", name.getText(), s.getCount(), act, user, cid);
                    q.executeUpdate(sqlStatement);
                    try{
                        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
                        PrimaryStage.setScene(scene);
                        PrimaryStage.show();
                    }catch(Exception e){
                        System.out.println(e);
                    }    
                }   
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    
    @FXML
    void cancel(ActionEvent event){
        cid=-1;
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAppointment.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML 
    void add(ActionEvent event){
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAddress.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
            refreshTables();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML 
    void edit(ActionEvent event){
        try{
            seeder a = new seeder((seeder) addressTable.getSelectionModel().getSelectedItem());
            addID=a.getCount();
        }
        catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Contact Selection");
            alert.setContentText("Please select a Customer for the appointment");
            alert.showAndWait();
            return;
        }
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAddress.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML 
    void delete(ActionEvent event){
        //TODO test Delete
        try{
            seeder a = new seeder((seeder) addressTable.getSelectionModel().getSelectedItem());
            addID=a.getCount();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Address selection");
            alert.setContentText("Invalid please select an address entry");
            alert.showAndWait();
            return;
        }
        try{
            String sql=String.format("DELETE FROM address WHERE addressId = %1$s;", addID);
            Statement q = conn.createStatement();
            q.executeUpdate(sql);
            addID=-1;
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if(cid!=-1){
            try{
                refreshTables();
                Statement q = conn.createStatement();
                String sqlStatement = String.format("select * From customer WHERE customerId=%1$s;", cid);
                ResultSet result = q.executeQuery(sqlStatement);
                result.next();
                name.setText(result.getString("customerName"));
                
                select = result.getInt("addressId");
                
                refreshTables();
                
                addressTable.getSelectionModel().select(select);
                
                if(result.getString("active").equals("1")){
                    sqlStatement="True";
                }else{
                    sqlStatement="False";
                }
                active.setText(sqlStatement);
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            refreshTables();
        }
    }
    
    void refreshTables(){
        try{
            ObservableList<seeder> addresses = null;
            ArrayList<seeder> allAddresses=new ArrayList<>();
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT address, addressId FROM address;";
            ResultSet result = q.executeQuery(sqlStatement);
            result.next();
            boolean next;
            int count=0;
            do{
                
                if(select==result.getInt("addressId")){
                    select=count;
                }
                count++;
                allAddresses.add(new seeder(result.getString("address"), result.getInt("addressId")));
                next = result.next();
                
            }while(next==true);
            addresses=FXCollections.observableArrayList(allAddresses);
            addressTable.setItems(addresses);
            address.setCellValueFactory(new PropertyValueFactory<>("identifier"));
            addressID.setCellValueFactory(new PropertyValueFactory<>("count"));            
        }
        catch(SQLException Ex){
            System.out.println(Ex);
            addressTable=new TableView();
        }
    }
}
