/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class ChangePasswordController implements Initializable{
    Users loggedInUser;
    @FXML
    private PasswordField confirm;

    @FXML
    private Button changebtn;

    @FXML
    private Button backbtn;
      @FXML
    private Label errorMessage;
    @FXML
    private PasswordField oldpassword;
    boolean validPasswordLength=false;
    @FXML
    private PasswordField newpassword;
    Alert alert;
    EntityManagerFactory emf;
    public ChangePasswordController() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
    }

    public ChangePasswordController(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        if(loggedInUser.getRole().toLowerCase().equals("admin")){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admindashboard.fxml"));
        AdminDashboardController controller=new AdminDashboardController(loggedInUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root); 
        }else{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(loggedInUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root);             
         }
        
    }

    

    @FXML
    void changeHandle(ActionEvent event) {
            String newp=newpassword.getText();
            String oldp=oldpassword.getText();
            String confirmp=confirm.getText();
        if(validate_input(oldp)&&validate_input(newp)&&validate_input(confirmp)){
            String loggedInUserPassword=loggedInUser.getPassword();
            validPasswordLength=true;
            if(newp.equalsIgnoreCase(confirmp)&&validatePassword(newp)){
               if(oldp.equals(loggedInUserPassword)){
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to change the password?.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    EntityManager em=emf.createEntityManager();
                    em.getTransaction().begin();
                    em.createNamedQuery("Users.changepassword")
                            .setParameter("password", newp)
                            .setParameter("id", loggedInUser.getId())
                            .executeUpdate();
                    em.getTransaction().commit();
                    em.close();
                    /***************************Success Alert****************************/
                        alert = new Alert(Alert.AlertType.INFORMATION,
                    "The Edit Process is completed successfully!",
                       ButtonType.OK
                   );
                    alert.setHeaderText("Success");
                    alert.show();
                    cleaAll();
                    loggedInUser.setPassword(newp);
                    System.out.println(loggedInUser);
                }
                }else{
                   // new password does equal to old password
                   errorMessage.setText("incorrect password please try again");
               } 
            }else{
                //if new Don't equal to confirm password or the password length is less than 8
                if(!validPasswordLength){
                errorMessage.setText("Invalid password Length");
                }else{
                errorMessage.setText("New & confirmation password doesn't match");
                }
              
            }
        }
    }

    
     public boolean validatePassword(String x){
           if(x.length()<8){
               validPasswordLength=false;
               return false;
           }
           validPasswordLength=true;
           return true;
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
       
       public void cleaAll(){
           newpassword.clear();
           oldpassword.clear();
           confirm.clear();
           errorMessage.setText("");
       }
    
}
