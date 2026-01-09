/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aya
 */
public class LongmenubarController implements Initializable {
    private String selectedfontSize="15";
    private String selectedfontfamily="arial";
    private String selectedBgc="#eee";
    @FXML
    private MenuBar menubar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void exsitHandle(ActionEvent event) {
       ((Stage) menubar.getScene().getWindow()).close();
    }

    @FXML
    private void handle14(ActionEvent event) {
        selectedfontSize="14";
        menubar.getScene().getRoot().setStyle("-fx-font-size:14px;-fx-font-family:"+selectedfontfamily+";-fx-background-color:"+selectedBgc);
    
    }

    @FXML
    private void handle15(ActionEvent event) {
        selectedfontSize="15";
        menubar.getScene().getRoot().setStyle("-fx-font-size:15px;-fx-font-family:"+selectedfontfamily+";-fx-background-color:"+selectedBgc);
    }

    @FXML
    private void handle16(ActionEvent event) {
        selectedfontSize="16";
        menubar.getScene().getRoot().setStyle("-fx-font-size:16px;-fx-font-family:"+selectedfontfamily+";-fx-background-color:"+selectedBgc);
    }

    @FXML
    private void handle17(ActionEvent event) {
        selectedfontSize="17";
        menubar.getScene().getRoot().setStyle("-fx-font-size:17px;-fx-font-family:"+selectedfontfamily+";-fx-background-color:"+selectedBgc);
    }

    @FXML
    private void handle18(ActionEvent event) {
        selectedfontSize="18";
        menubar.getScene().getRoot().setStyle("-fx-font-size:18px;-fx-font-family:"+selectedfontfamily+";-fx-background-color:"+selectedBgc);
    }

    @FXML
    private void arialHandle(ActionEvent event) {
        selectedfontfamily="arial";
        menubar.getScene().getRoot().setStyle("-fx-font-family:arial;-fx-font-size:"+selectedfontSize+"px;-fx-background-color:"+selectedBgc);
    }

    @FXML
    private void centaurHandle(ActionEvent event) {
        selectedfontfamily="centaur";
        menubar.getScene().getRoot().setStyle("-fx-font-family:centaur;-fx-font-size:"+selectedfontSize+"px;-fx-background-color:"+selectedBgc);

    }

    @FXML
    private void SansSerifHandle(ActionEvent event) {
         selectedfontfamily="SansSerif";
        menubar.getScene().getRoot().setStyle("-fx-font-family:SansSerif;-fx-font-size:"+selectedfontSize+"px;-fx-background-color:"+selectedBgc);

    }

    @FXML
    private void arialNarrowHandle(ActionEvent event) {
        selectedfontfamily="arialNarrow";
        menubar.getScene().getRoot().setStyle("-fx-font-family:arialNarrow;-fx-font-size:"+selectedfontSize+"px;-fx-background-color:"+selectedBgc);

    }

    @FXML
    private void orangeHandle(ActionEvent event) {
        selectedBgc="#e3aca3";
        menubar.getScene().getRoot().setStyle("-fx-background-color:#e3aca3;-fx-font-family:"+selectedfontfamily+";-fx-font-size:"+selectedfontSize);
    }

    @FXML
    private void roseHandle(ActionEvent event) {
        selectedBgc="#e3aab9";
        menubar.getScene().getRoot().setStyle("-fx-background-color:#e3aab9;-fx-font-family:"+selectedfontfamily+";-fx-font-size:"+selectedfontSize);
    }

    @FXML
    private void shadowHandle(ActionEvent event) {
       selectedBgc="#424040";
        menubar.getScene().getRoot().setStyle("-fx-background-color:#424040;-fx-font-family:"+selectedfontfamily+";-fx-font-size:"+selectedfontSize);
    }

    @FXML
    private void skyHandle(ActionEvent event) {
       selectedBgc="#bdc1c7";
        menubar.getScene().getRoot().setStyle("-fx-background-color:#bdc1c7;-fx-font-family:"+selectedfontfamily+";-fx-font-size:"+selectedfontSize);
    }

    @FXML
    private void tealHandle(ActionEvent event) {
       selectedBgc="#86b9ba";
        menubar.getScene().getRoot().setStyle("-fx-background-color:#86b9ba;-fx-font-family:"+selectedfontfamily+";-fx-font-size:"+selectedfontSize);

    }

    @FXML
    private void aboutappHandle(ActionEvent event) {
               Alert alert=new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Orders Desktop Application");
               alert.setHeaderText("Welcome to our \nOrders Desktop Application");
               alert.setContentText("Streamline your order management\nwith our Orders Desktop ApplicationTwo user roles: admin and client.\nHassle-free product ordering for clients\nand efficient process management for admins.\"!");
               alert.show();
    }
    
}
