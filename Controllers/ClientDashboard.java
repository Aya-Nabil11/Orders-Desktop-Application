/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Users;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author aya
 */
public class ClientDashboard implements Initializable{
    Users loggedinUser;
    
    @FXML
    private Label welcomelabel;

    @FXML
    private Button profile;

    @FXML
    private ImageView imageview;

    @FXML
    private Button viewInvoices;

   public ClientDashboard(Users user) {
        this.loggedinUser=user;
    }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image image=new Image(new FileInputStream(loggedinUser.getImg()));
            imageview.setImage(image);
            welcomelabel.setText("Welcome "+loggedinUser.getName());
        } catch (FileNotFoundException ex) {
        }
    }

    @FXML
    void profileHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/profile.fxml"));
        ProfileController controller=new ProfileController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        profile.getScene().setRoot(root);   
     }

    

    @FXML
    void viewInvoicesHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/ClientViewInvoices.fxml"));
        ClientViewInvoicesController controller=new ClientViewInvoicesController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        profile.getScene().setRoot(root);   
  
        
        
        
        
        
        
        
    }


    @FXML
    void manageOrdersHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/ClientManageOrder.fxml"));
        ClientOrderManageController controller=new ClientOrderManageController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        profile.getScene().setRoot(root);   
        
        

    }

    

    @FXML
    void changePasswordHandle(ActionEvent event) throws IOException {
         Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/ChangePassword.fxml"));
        ChangePasswordController controller=new ChangePasswordController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        profile.getScene().setRoot(root);   
    }

  
    @FXML
    void LogoutHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/loginpage.fxml"));
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        profile.getScene().setRoot(root);   
    }


    

    
    
    
    
}
