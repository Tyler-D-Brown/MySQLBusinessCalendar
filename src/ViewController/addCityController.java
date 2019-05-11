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
import static tbro402businesscalendar.DBConnection.conn;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.addID;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cid;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cityId;
import static tbro402businesscalendar.Tbro402BusinessCalendar.countryID;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;

/**
 *
 * @author tbrown
 */
public class addCityController implements Initializable{
    int select=-1;
    
    @FXML
    TextField city;
    
    @FXML
    TableView country;
    
    @FXML
    TableColumn countryId;
    
    @FXML
    TableColumn countryName;
    
    @FXML
    void submit(ActionEvent event){
        if(city.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in City Name");
            alert.setContentText("Invalid City name");
            alert.showAndWait();
            return;
        }else{
            seeder s = null;
            try{
                s = new seeder((seeder) country.getSelectionModel().getSelectedItem());
            }catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("error in country Selection");
                alert.setContentText("Invalid country Selection");
                alert.showAndWait();
                return;
            }
            if(cityId==-1){
                String sqlStatement = "select max(cityId) From city;";
                try{
                    Statement q = conn.createStatement();
                    ResultSet result = q.executeQuery(sqlStatement);
                    result.next();
                    int i=result.getInt(1);
                    i++;
                    sqlStatement = String.format("INSERT INTO city(cityId, city, countryId, createDate, createdBy, lastUpdateBy) VALUES (%1$s, '%2$s', %3$s, NOW(), '%4$s', '%5$s');", i, city.getText() ,s.getCount(), user, user);
                    q.executeUpdate(sqlStatement);            
                    
                    try{
                        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAddress.fxml")));
                        PrimaryStage.setScene(scene);
                        PrimaryStage.show();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }else{                
                try{
                    Statement q = conn.createStatement();
                    String sqlStatement = String.format("UPDATE city SET city = '%1$s', countryId = %2$s, lastUpdateBy = '%3$s' WHERE cityId = %4$s;", city.getText() ,s.getCount(), user, cityId);
                    System.out.println(sqlStatement);
                    q.executeUpdate(sqlStatement); 
                    cityId=-1;
                    
                    try
                    {
                        Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAddress.fxml")));
                        PrimaryStage.setScene(scene);
                        PrimaryStage.show();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }
    
    @FXML
    void cancel(ActionEvent event){
        try{
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addAddress.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
            cityId=-1;
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    void add(ActionEvent event){
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addCountry.fxml")));
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
            seeder s = new seeder((seeder) country.getSelectionModel().getSelectedItem());
            countryID= s.getCount();
            
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addCountry.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void delete(ActionEvent event){
        //TODO test
        try{
            seeder a = new seeder((seeder) country.getSelectionModel().getSelectedItem());
            countryID=a.getCount();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in country selection");
            alert.setContentText("Invalid please select a country entry");
            alert.showAndWait();
            return;
        }
        try{
            String sql=String.format("DELETE FROM country WHERE countryId = %1$s;", addID);
            Statement q = conn.createStatement();
            q.executeUpdate(sql);
            countryID=-1;
        }catch(Exception e){
            System.out.println(e);
        }
                
        refreshTables();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(cityId!=-1){
            try{
                refreshTables();
                Statement q = conn.createStatement();
                String sqlStatement = String.format("select * From city WHERE cityId=%1$s;", cityId);
                ResultSet result = q.executeQuery(sqlStatement);
                result.next();
                city.setText(result.getString("city"));
                
                select = result.getInt("countryId");
                
                refreshTables();
                
                country.getSelectionModel().select(select);
                
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            refreshTables();
        }
    }
    
    void refreshTables(){
        try{
            ObservableList<seeder> countries = null;
            ArrayList<seeder> allCountries=new ArrayList<>();
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT country, countryId FROM country;";
            ResultSet result = q.executeQuery(sqlStatement);
            result.next();
            boolean next;
            int count=0;
            do{
                if(select==result.getInt("cityId")){
                    select=count;
                }
                count++;
                
                allCountries.add(new seeder(result.getString("country"), result.getInt("countryId")));
                next = result.next();
            }while(next==true);
            countries=FXCollections.observableArrayList(allCountries);
            country.setItems(countries);
            countryName.setCellValueFactory(new PropertyValueFactory<>("identifier"));
            countryId.setCellValueFactory(new PropertyValueFactory<>("count"));
            
        }
        catch(SQLException Ex){
            System.out.println(Ex);
        }
    }
}
