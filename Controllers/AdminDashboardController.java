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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class AdminDashboardController  implements Initializable {
    @FXML
    private Button manageOrders;

    @FXML
    private Label welcomelabel;

    @FXML
    private ImageView imageview;

    @FXML
    private Button manageProducts;
    Users loggedinUser;
    EntityManagerFactory emf;
    
    public AdminDashboardController(Users user) {
        this.loggedinUser=user;
    }
    

     @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image image=new Image(new FileInputStream(loggedinUser.getImg()));
            imageview.setImage(image);
            welcomelabel.setText("Welcome "+loggedinUser.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void manageProductsHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/manageproduct.fxml"));
        ManageProductController controller=new ManageProductController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);   
}

    

    @FXML
    void manageOrdersHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/Adminordermanage.fxml"));
        AdminOrderManageController controller=new AdminOrderManageController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);   
    }

    

    @FXML
    void ManageClientsHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/manageclient.fxml"));
        ManageClientsController controller=new ManageClientsController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);  
    }

    

    @FXML
    void ManageInvoicesHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/manageinvoices.fxml"));
        ManageInvoicesController controller=new ManageInvoicesController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);  
    }

    

    @FXML
    void ChangePasswordHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/ChangePassword.fxml"));
        ChangePasswordController controller=new ChangePasswordController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);   
    }

    

    @FXML
    void LogoutHandle(ActionEvent event) throws IOException {
         Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/loginpage.fxml"));
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        manageProducts.getScene().setRoot(root);   
        
        
    }

  

    
}
