/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static tbro402businesscalendar.DBConnection.conn;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.countryID;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;

/**
 *
 * @author tbrown
 */
public class addCountryController implements Initializable{
    @FXML
    TextField country;
    
    @FXML
    void submit(ActionEvent event){
        if(country.getText().isEmpty()){
            
        }else{
            try{
                String sqlStatement = "select max(countryId) From country;";
                Statement q = conn.createStatement();
            
            if(countryID==-1){
                try{
                    ResultSet result = q.executeQuery(sqlStatement);
                    result.next();
                    int i=result.getInt(1);
                    i++;
                    sqlStatement = String.format("INSERT INTO country(countryId, country, createDate, createdBy, lastUpdateBy) VALUES (%1$s, '%2$s', NOW(), '%3$s', '%4$s');", i, country.getText(), user, user);
                    q.executeUpdate(sqlStatement);            
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    try{
                        sqlStatement = String.format("UPDATE country SET country = '%1$s', lastUpdateBy='%2$s' WHERE countryId=%3$s;", country.getText(), user, countryID);
                        q.executeUpdate(sqlStatement);            
                    }catch(Exception e){
                        System.out.println(e);
                    }
                
                    countryID=-1;
                }
            }catch(Exception e){
                System.out.println(e);
            }
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
    }
    
    @FXML
    void cancel(ActionEvent event){
        countryID=-1;
        
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/addCity.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //TODO Verify Populate.
        if(countryID!=-1){
            try{
                Statement q = conn.createStatement();
                String sqlStatement = String.format("select * From country WHERE countryId=%1$s;", countryID);
                ResultSet result = q.executeQuery(sqlStatement);
                result.next();
                country.setText(result.getString("country"));
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
