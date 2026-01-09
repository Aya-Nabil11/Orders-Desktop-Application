/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operate;

import java.awt.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author aya
 */
public class LoginMainApp extends Application{
    
    @FXML
    void signupHandle(ActionEvent event) {

    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml=FXMLLoader.load(getClass().getResource("../View/loginpage.fxml"));
        Parent fxml1=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        VBox v=new VBox(fxml1,fxml);
        Scene s=new Scene(v);
        primaryStage.setScene(s);
        primaryStage.setTitle("login page");
        primaryStage.show();
    }
    
    
    

}
