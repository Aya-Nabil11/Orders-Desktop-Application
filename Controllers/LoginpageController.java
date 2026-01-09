/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author aya
 */
public class LoginpageController implements Initializable {
 @FXML
    private TextField email;
  @FXML
    private Button signup;
  
    EntityManagerFactory emf;
    Alert alert;
   @FXML
    private PasswordField password;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
    }    
 @FXML
    private void signinHandle(ActionEvent event) throws IOException {
        EntityManager em=emf.createEntityManager();
        List<Users> list=em.createNamedQuery("Users.findAll").getResultList();
        String upassword=password.getText();
        String uemail=email.getText();
        if(validate_input(uemail)&&validate_input(upassword)){
        
           //admindashboard
            try{
            Users loggedinUser =list.stream()
                .filter
                  (e -> uemail.equals(e.getEmail()) &&
                               upassword.equals(e.getPassword()))
                                   .findFirst().get();           //findFirst return null if no match is found then throw NoSuchElementException
            // check the role column
        if(loggedinUser.getRole().toLowerCase().equals("admin")){
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admindashboard.fxml"));
        AdminDashboardController controller=new AdminDashboardController(loggedinUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        signup.getScene().setRoot(root);   
            }else{
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(loggedinUser);
        loader.setController(controller);
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);        
        signup.getScene().setRoot(root);   
            }
              }catch(NoSuchElementException ex){
               alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Error in email or password");
               alert.setContentText("Incorrect email or password.\n Please try again.");
               alert.show();
        }
               
        }
    }
 @FXML
    private void signupHandle(ActionEvent event) throws IOException {
        Parent p=FXMLLoader.load(getClass().getResource("../View/reg.fxml"));
        Parent p1=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));        
        VBox vbox=new VBox(p1,p);
        signup.getScene().setRoot(vbox);
    }
    
    public boolean validate_input(String x){
            if(x.trim().equalsIgnoreCase("")){
               alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Empty feilds values");
               alert.setContentText("please Enter all values\n Don't Keep it Empty!");
               alert.show();
                return false;
            }
            return true;
        }

    
    
}
