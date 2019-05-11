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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.addID;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cid;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;
import static tbro402businesscalendar.DBConnection.conn;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cityId;

/**
 *
 * @author tbrown
 */
public class addAddressViewController implements Initializable{
    
    int select=-1;
    
    @FXML
    TextField address;
    
    @FXML
    TextField address2;
    
    @FXML
    TextField postalCode;
    
    @FXML
    TextField phone;
    
    @FXML
    TableView city;
    
    @FXML
    TableColumn cityID;
    
    @FXML
    TableColumn cityName;
    
    @FXML
    void submit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        seeder s = null;
        if(address.getText().isEmpty()){
            alert.setHeaderText("error in address");
            alert.setContentText("Invalid address entry");
            alert.showAndWait();
            return;
        }else if(address2.getText().isEmpty()){
            alert.setHeaderText("error in address");
            alert.setContentText("Invalid address 2 entry");
            alert.showAndWait();
            return;
        }else if(postalCode.getText().isEmpty()){
            alert.setHeaderText("error in postal code");
            alert.setContentText("Invalid postal code entry");
            alert.showAndWait();
            return;
        }else if(phone.getText().isEmpty()){
            alert.setHeaderText("error in phone");
            alert.setContentText("Invalid Phone entry");
            alert.showAndWait();
            return;
        }
        try{
            s = new seeder((seeder) city.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            alert.setHeaderText("error in city");
            alert.setContentText("Invalid city selection");
            alert.showAndWait();
            return;
        }
        String sqlStatement = "select max(addressId) From address;";
        
        
        if(addID==-1){        
            try{
                Statement q = conn.createStatement();
                ResultSet result = q.executeQuery(sqlStatement);
                result.next();
                int i=result.getInt(1);
                i++;
                sqlStatement = String.format("INSERT INTO address(addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES (%1$s, '%2$s', '%3$s', %4$s, '%5$s', '%6$s', NOW(), '%7$s', '%8$s');", i, address.getText(), address2.getText(), s.getCount(), postalCode.getText(), phone.getText(), user, user);
                q.executeUpdate(sqlStatement);            
            }catch(Exception e){
                System.out.println(e);
            }
            try
            {
                Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/customer.fxml")));
                PrimaryStage.setScene(scene);
                PrimaryStage.show();
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            try{
                Statement q = conn.createStatement();
                sqlStatement = String.format("UPDATE address SET address = '%1$s', address2 = '%2$s', cityId = %3$s, postalCode = '%4$s', phone = '%5$s', lastUpdateBy = '%6$s' WHERE addressId = %7$s;", address.getText(), address2.getText(), s.getCount(), postalCode.getText(), phone.getText(), user, addID);
                q.executeUpdate(sqlStatement);            
            }catch(Exception e){
                System.out.println(e);
            }
            try
            {
                addID=-1;
                Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/customer.fxml")));
                PrimaryStage.setScene(scene);
                PrimaryStage.show();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    @FXML
    void cancel(ActionEvent event){
        addID=-1;
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/customer.fxml")));
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
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addCity.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void edit(ActionEvent event){
        try{
            seeder a = new seeder((seeder) city.getSelectionModel().getSelectedItem());
            cityId=a.getCount();
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
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addCity.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }        
        refreshTables();
    }
    
    @FXML
    void delete(ActionEvent event){
        try{
            seeder a = new seeder((seeder) city.getSelectionModel().getSelectedItem());
            cityId=a.getCount();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in city selection");
            alert.setContentText("Invalid please select a city entry");
            alert.showAndWait();
            return;
        }
        try{
            String sql=String.format("DELETE FROM city WHERE cityId = %1$s;", cityId);
            Statement q = conn.createStatement();
            q.executeUpdate(sql);
            cityId=-1;
        }catch(Exception e){
            System.out.println(e);
        }
        
        refreshTables();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(addID!=-1){
            try{
                refreshTables();
                Statement q = conn.createStatement();
                String sqlStatement = String.format("select * From address WHERE addressId=%1$s;", addID);
                ResultSet result = q.executeQuery(sqlStatement);
                result.next();
                address.setText(result.getString("address"));
                address2.setText(result.getString("address2"));
                postalCode.setText(result.getString("postalCode"));
                phone.setText(result.getString("phone"));
                
                select = result.getInt("cityId");
                
                refreshTables();
                
                city.getSelectionModel().select(select);
                
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            refreshTables();
        }
    }
    
    void refreshTables(){
        try{
            
            ObservableList<seeder> citys = null;
            ArrayList<seeder> allCitys=new ArrayList<>();
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT city, cityId FROM city;";
            ResultSet result = q.executeQuery(sqlStatement);
            
            result.next();
            boolean next;
            int count=0;
            do{
                if(select==result.getInt("cityId")){
                    select=count;
                }
                count++;
                
                allCitys.add(new seeder(result.getString("city"), result.getInt("cityId")));
                next = result.next();
            }while(next==true);
            citys=FXCollections.observableArrayList(allCitys);
            city.setItems(citys);
            cityName.setCellValueFactory(new PropertyValueFactory<>("identifier"));
            cityID.setCellValueFactory(new PropertyValueFactory<>("count"));
            
            
        }
        catch(SQLException Ex){
            System.out.println(Ex);            
        }
    }
}
