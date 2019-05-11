/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static tbro402businesscalendar.Tbro402BusinessCalendar.language;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import static tbro402businesscalendar.DBConnection.conn;
import javafx.stage.Stage;
import tbro402businesscalendar.Tbro402BusinessCalendar;
import static tbro402businesscalendar.Tbro402BusinessCalendar.user;
import static tbro402businesscalendar.Tbro402BusinessCalendar.uID;


/**
 *
 * @author Barid
 */
public class loginPageController implements Initializable{
    @FXML
    private CheckMenuItem languageEnglish;
    
    @FXML
    private CheckMenuItem languageSpanish;
    
    @FXML
    private CheckMenuItem languageFrench;
    
    @FXML
    private Button submit;
    
    @FXML
    private Label username;
    
    @FXML
    private Label password;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    void submitClick(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
        
        try{
            Statement q = conn.createStatement();
            String sqlStatement = "SELECT userName, password, userId FROM user WHERE userName= '" + usernameField.getText() + "'";
            user=usernameField.getText();
            ResultSet result = q.executeQuery(sqlStatement);
            result.next();
            sqlStatement = result.getString("password");
            String pass =passwordField.getText();
            if(!pass.equals(sqlStatement))
            {
                if(language==0){
                    alert.setTitle("Error");
                    alert.setContentText("Username or Password incorrect.");
                    alert.showAndWait();
                }
                if(language==1){
                    alert.setTitle("Error");
                    alert.setContentText("Nom d'utilisateur ou Mot de passe est incorrect.");
                    alert.showAndWait();
                }
                if(language==2){
                    alert.setTitle("Error");
                    alert.setContentText("Nombre de usario o Contraseña est incorrecto.");
                    alert.showAndWait();
                }
            }else{
                try
                {
                    try {
                        String savestr = "./LoginLog.txt"; 
                        File f = new File(savestr);

                        PrintWriter out = null;
                    if ( f.exists() && !f.isDirectory() ) {
                        out = new PrintWriter(new FileOutputStream(new File(savestr), true));
                    }else {
                        out = new PrintWriter(savestr);
                    }
                    out.append(LocalDateTime.now().toString());
                    out.append("\r\n");
                    out.close();
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                    uID=Integer.parseInt(result.getString("userId"));
                    Stage PrimaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene=new Scene(FXMLLoader.load(Tbro402BusinessCalendar.class.getResource("/ViewController/calendarView.fxml")));
                    PrimaryStage.setScene(scene);
                    PrimaryStage.show();
                }catch(Exception e){
                    System.out.println(e);
                }            
            }    
        }catch(Exception ex){
           if(language==0){
                alert.setTitle("Error");
                alert.setContentText("Username or Password incorrect.");
                alert.showAndWait();
            }
            if(language==1){
                alert.setTitle("Error");
                alert.setContentText("Nom d'utilisateur ou Mot de passe est incorrect.");
                alert.showAndWait();
            }
            if(language==2){
                alert.setTitle("Error");
                alert.setContentText("Nombre de usario o Contraseña est incorrecto.");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    void selectEnglish(ActionEvent event) throws IOException{
        languageSpanish.setSelected(false);
        languageFrench.setSelected(false);
        username.setText("Username:");
        password.setText("Password:");
        submit.setText("Submit");
        language=0;
    }
    
    @FXML
    void selectFrench(ActionEvent event) throws IOException{
        languageEnglish.setSelected(false);
        languageFrench.setSelected(false);
        username.setText("Nom d'utilisateur:");
        password.setText("Mot de passe:");
        submit.setText("Soumettre");
        language=1;
    }
    
    @FXML
    void selectSpanish(ActionEvent event) throws IOException{
        languageSpanish.setSelected(false);
        languageEnglish.setSelected(false);
        username.setText("Nombre de usario:");
        password.setText("Contraseña:");
        submit.setText("Enviar");
        language=2;
        
    }
    
    @FXML
    void reporting(ActionEvent event){
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
    public void initialize(URL location, ResourceBundle resources) {
        if (language==0){
            languageSpanish.setSelected(false);
            languageFrench.setSelected(false);
            languageEnglish.setSelected(true);
            username.setText("Username:");
            password.setText("Password:");
            submit.setText("Submit");
        }else if(language==1){
            languageEnglish.setSelected(false);
            languageFrench.setSelected(true);
            languageSpanish.setSelected(false);
            username.setText("Nom d'utilisateur:");
            password.setText("Mot de passe:");
            submit.setText("Soumettre");
            language=1;
        }else if(language==2){
            languageSpanish.setSelected(true);
            languageEnglish.setSelected(false);
            languageFrench.setSelected(false);
            username.setText("Nombre de usario:");
            password.setText("Contraseña:");
            submit.setText("Enviar");
        }
    }

    
}