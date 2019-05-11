/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Classes.appointment;
import Classes.customer;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static tbro402businesscalendar.DBConnection.conn;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.id;
import static tbro402businesscalendar.Tbro402BusinessCalendar.cid;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;
import java.util.TimeZone;
import static tbro402businesscalendar.Tbro402BusinessCalendar.uID;

/**
 *
 * @author Barid
 */
public class addAppointmentViewController implements Initializable{
    
    /*private ObservableList<customer> customers;
    private ArrayList<customer> allCustomer=new ArrayList<>();*/
    int select=-1;
    
    @FXML
    private TextField title;
            
    @FXML
    private TextField desc;
            
    @FXML
    private DatePicker date;
            
    @FXML
    private TextField locat;
            
    @FXML
    private TextField cont;
    
    @FXML
    private TextField type;
            
    @FXML
    private TextField strtHour;
    
    @FXML
    private TextField strtMin;
    
    @FXML
    private TextField strtAM;
    
    @FXML
    private TextField endHour;
    
    @FXML
    private TextField endMin;
    
    @FXML
    private TextField endAM;
    
    @FXML
    private TableView clients;
    
    @FXML
    private TableColumn custName;
    
    @FXML
    private TableColumn custID;
    
    @FXML
    private TextField url;
    
    //Lambda expression used for outputting error messages supplying information on what is wrong.
    interface lamb{
        void error(String str);
    }
    
    
    //lambda expressions used for formatting the date and time in UTC and in the format that MySQL will accept in order to remove the need to perform the action on two separate ocassions. 
    interface timeLamb{
        String format(DatePicker d, TextField hour, TextField min, TextField amPm);
    }
    
    private customer c = null;
    
    @FXML
    void submit(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        boolean valid=true;
        int hour;
        int min;
        int appId;
        LocalDate dat;
                
        //lambda to handle the outputting of error message to streamline the process.
        lamb er= (str)->{
            alert.setHeaderText("Error");
            alert.setContentText("Error in " + str + " please validate your input.");
            alert.showAndWait();
        };
        
        //lambda expression to convert the time entered by the user into a format that MySql will accept.
        timeLamb time = (d, hours, minute, amPm)->{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String formDate="";
            int ho=Integer.parseInt(hours.getText());
            int mi=Integer.parseInt(minute.getText());
            LocalDate loc = null;
            try{
                loc=d.getValue();
            }catch(Exception e){
                System.out.println(e);
            }
            
            Calendar c = Calendar.getInstance();
            
            TimeZone timezone = TimeZone.getDefault();
            int h = timezone.getRawOffset()/3600000;
            String t=amPm.getText();
            boolean b=false;
            ho=ho-h;
            ho=ho-(c.get(Calendar.DST_OFFSET)/3600000);
            if(t.equals("pm") ||t.equals("PM")){
                ho = ho+12;
                b=true;
            }
            
            if(ho<0){
                ho=24+ho;
                loc=loc.minusDays(1);
            }else if(ho>=24){
                ho=ho-24;
                loc=loc.plusDays(1);
            }
            
            Instant inst = Instant.from(loc.atStartOfDay(ZoneId.systemDefault()));
            Date date = (Date) Date.from(inst);
            
            
            String st=sdf.format(date);
            
            formDate=st + " " + String.format("%02d", ho) + ":" + String.format("%02d", mi) + ":00";
            
            return formDate;
        };
        
        if(title.getText().isEmpty()){
            er.error("Title");
            valid=false;
            return;
        }
        if(desc.getText().isEmpty()){
            er.error("Description");
            valid=false;
            return;
        }
        if(locat.getText().isEmpty()){
            er.error("Location");
            valid=false;
            return;
        }
        if(cont.getText().isEmpty()){
            er.error("Contact");
            valid=false;
            return;
        }
        try{
            hour=Integer.parseInt(strtHour.getText());
        }
        catch(Exception e)
        {
            er.error("Start Hour");
            valid=false;
            return;
        }
        try{
            min=Integer.parseInt(strtMin.getText());
        }
        catch(Exception e)
        {
            valid=false;
            er.error("Start Minutes");
            return;
        }
        try{
            dat=date.getValue();
        }
        catch(Exception e)
        {
            er.error("Date");
            valid=false;
            return;
        }
        if(hour>12|min>60|hour<1|min<0){
            valid=false;
            er.error("Start Time");
            return;
        }
        if(strtAM.getText().isEmpty()){
            er.error("Start AM/PM");
            valid=false;
            return;
        }
        
        if(strtAM.getText().equals("am") || strtAM.getText().equals("AM")){
            if(Integer.parseInt(strtHour.getText())<8 || strtHour.getText().equals("12")){
                er.error("Outside of work hours");
                return;
            }
        }else if(strtAM.getText().equals("pm") || strtAM.getText().equals("PM")){
            if(Integer.parseInt(strtHour.getText())>=5 ){
                er.error("Outside of work hours");
                return;
            }
        }
        
        String strtDate=time.format(date, strtHour, strtMin, strtAM);
        
        if(endAM.getText().isEmpty()){
            er.error("End AM/PM");
            valid=false;
            return;
        }
        try{
            min=Integer.parseInt(endMin.getText());
        }
        catch(Exception e)
        {
            valid=false;
            er.error("End Minutes");
            return;
        }
        try{
            hour=Integer.parseInt(endHour.getText());
        }
        catch(Exception e)
        {
            er.error("End Hours");
            valid=false;
            return;
        }
        if(hour>12|min>60|hour<1|min<0){
            valid=false;
            er.error("End Time");
            return;
        }
        try{
            c = new customer((customer) clients.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            er.error("Customer selection");
            valid=false;
            return;
        }
        try{
            url.getText();
        }catch(Exception e){
            er.error("URL");
            valid=false;
            return;
        }
        try{
            type.getText();
        }catch(Exception e){
            er.error("type");
            return;
        }
        
        if(endAM.getText().equals("am") || endAM.getText().equals("AM")){
            if(Integer.parseInt(endHour.getText())<8 || endHour.getText().equals("12")){
                er.error("Outside of work hours");
                return;
            }
        }else if(endAM.getText().equals("pm") || endAM.getText().equals("PM")){
            if(Integer.parseInt(endHour.getText())>=5){
                if(Integer.parseInt(endMin.getText())>0){
                        er.error("Outside of work hours");
                }
                return;
            }
        }
        
        String endDate = time.format(date, endHour, endMin, endAM);
        try{
            Statement q = conn.createStatement();
            ResultSet result = null;
            String sqlStatement = String.format("SELECT * FROM appointment WHERE start between '%1s' AND '%2s';", strtDate, endDate);
                result = q.executeQuery(sqlStatement);
                try{
                    result.next();
            
                    if(Integer.parseInt(result.getString("appointmentId"))>-1 && Integer.parseInt(result.getString("appointmentId")) != id){
                        er.error("appointment overlap");
                        return;
                    }
                } catch (Exception ex) {
                        System.out.println(ex);
                }
                
                sqlStatement = String.format("SELECT * FROM appointment WHERE end between '%1s' AND '%2s';", strtDate, endDate);
                result = q.executeQuery(sqlStatement);
                try{
                    result.next();
            
                    if(Integer.parseInt(result.getString("appointmentId"))>-1 && Integer.parseInt(result.getString("appointmentId")) != id){
                        er.error("appointment overlap");
                        return;
                    }
                } catch (Exception ex) {
                        System.out.println(ex);
                }
            if(id==-1){
                sqlStatement = "select max(appointmentId) From appointment;";
                result = q.executeQuery(sqlStatement);
                result.next();
                appId=result.getInt(1);
                appId++;
                //optional code for finding the first available ID instead of the largest ID + 1
                /*valid=false;
                for(int i=0; valid==false; i++){
                if(i==result.getInt("appointmentId")){
                appId=i;
                valid=true;
                }else if(!result.next()){
                appId=++i;
                valid=true;
                }
                }*/
                
                sqlStatement = String.format("INSERT INTO appointment (appointmentId, customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdateBy, type, userId) VALUES(%1$s, %2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s' , now(), '%10$s', '%11$s', '%12$s', %13$s); ", Integer.toString(appId), Integer.toString(c.getCustomerID()), title.getText(), desc.getText(), locat.getText(), cont.getText(), url.getText(), strtDate, endDate, user, user, type.getText(), Integer.toString(uID));
                q.executeUpdate(sqlStatement);
            }else{
                String sql = String.format("UPDATE appointment SET title = '%3$s', customerId = %2$s, description = '%4$s', location = '%5$s', contact = '%6$s', url = '%7$s', start = '%8$s', end = '%9$s', lastUpdateBy = '%10$s', type='%11$s', userId = %12$s WHERE appointmentId = %1$s;", Integer.toString(id), Integer.toString(c.getCustomerID()), title.getText(), desc.getText(), locat.getText(), cont.getText(), url.getText(), strtDate, endDate, user, type.getText(), uID);
                q.executeUpdate(sql);
                id=-1;
            }
        }
        catch(Exception e){
            System.out.println(e);
            return;
        }
        
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/calendarView.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    void addClient(ActionEvent event){
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/customer.fxml")));
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
            c = new customer((customer) clients.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Contact Selection");
            alert.setContentText("Please select a Customer for the appointment");
            alert.showAndWait();
            return;
        }
        cid=c.getCustomerID();
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/customer.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void deleteClient(ActionEvent event){
        try{
            c = new customer((customer) clients.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("error in Contact Selection");
            alert.setContentText("Please select a Customer for the appointment");
            alert.showAndWait();
            return;
        }
        cid=c.getCustomerID();
        try
        {
        Statement q = conn.createStatement();
        String statement = "DELETE FROM customer WHERE customerId = "  + cid + ";";
        q.executeUpdate(statement);
        }
        catch(Exception e){
            System.out.println(e);
        }
        refreshTables();
    }
    
    @FXML
    void cancel(ActionEvent event){
        id=-1;
        try
        {
            Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/calendarView.fxml")));
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public void initialize(URL u, ResourceBundle rb){
        if(id==-1){
            refreshTables();
        }else{
            
            try {
                
                clients.getSelectionModel().select(select);
                Statement q = conn.createStatement();
                String sqlStatement = "SELECT * FROM appointment WHERE appointmentId = " + id + ";";
                ResultSet result;
                result = q.executeQuery(sqlStatement);
                result.next();
                title.setText(result.getString("title"));
                desc.setText(result.getString("description"));
                locat.setText(result.getString("location"));
                cont.setText(result.getString("contact"));
                url.setText(result.getString("url"));
                type.setText(result.getString("type"));
                
                
                appointment a = new appointment(result.getString("start"), result.getString("end"));
                String sdt = a.getStart();
                String edt = a.getEnd();
                String sd [] = sdt.split(" ");
                String ed [] = edt.split(" ");
                String st [] = sd[1].split(":");
                String et [] = ed[1].split(":");
                int sHour=Integer.parseInt(st[0]);
                int eHour=Integer.parseInt(et[0]);
                String AM;
                
                LocalDate d = LocalDate.parse(sd[0]);
                date.setValue(d);
                
                if(sHour-12 > 0){
                    AM="PM";
                    sHour=sHour-12;
                }else{
                    AM="AM";
                }
                strtHour.setText(Integer.toString(sHour));
                strtMin.setText(st[1]);
                strtAM.setText(AM);
                
                if(eHour-12 > 0){
                    AM="PM";
                    eHour=eHour-12;
                }else{
                    AM="AM";
                }
                endHour.setText(Integer.toString(eHour));
                endMin.setText(et[1]);
                endAM.setText(AM);
                
                select = result.getInt("customerId");
                
                refreshTables();
                
                clients.getSelectionModel().select(select);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
        }   
               
    }
    
    void refreshTables(){
        try{
            ObservableList<customer> customers;
            ArrayList<customer> allCustomer=new ArrayList<>();
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT customerName, customerID FROM customer;";
            ResultSet result = q.executeQuery(sqlStatement);
            result.next();
            boolean next;
            int count=0;
            do{
                allCustomer.add(new customer(result.getInt("customerID"), result.getString("customerName")));
                //todo fix select feature
                if(select==result.getInt("customerID")){
                    select=count;
                }
                next = result.next();
                count++;
            }while(next==true);
            customers=FXCollections.observableArrayList(allCustomer);
            clients.setItems(customers);
            custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            
        }
        catch(SQLException Ex){
            System.out.println(Ex);
        }
    }
}
