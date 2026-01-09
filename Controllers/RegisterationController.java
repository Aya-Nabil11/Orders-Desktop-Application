/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Models.Users;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author aya
 */
public class RegisterationController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField mobile;
   @FXML
    private PasswordField password;
    @FXML
    private Button registerbtn;
    EntityManagerFactory emf;
    Alert alert;
     @FXML
    private Button uploadbtn;
    @FXML
    private ImageView imageview;
        File selectedImg;
    @FXML
    private Button back;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
    }    

    @FXML
    private void registerHandle(ActionEvent event) throws IOException {
        String uname=name.getText();
        String uemail=email.getText();
        String umobile=mobile.getText();
        String upassword=password.getText();
        if(validate_input(uname)&&validate_input(uemail)
                  &&validate_input(umobile)&&validate_input(upassword)&&validatePassword(upassword)
                &&validate_numeric_val(umobile)&&validateUniqueEmail(uemail)
                &&validateImg()){
        Users user=new Users(uname, uemail
                , umobile, upassword, "client",selectedImg.getPath());
        EntityManager em=emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        //  No Need to success alert if the operation success then it redirect him to client dashboard
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(user);
        loader.setController(controller);
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);                
        registerbtn.getScene().setRoot(root);   
        }
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
      
       public boolean validate_numeric_val(String x){
            try{
                Long.parseLong(x);
                return true;
            }catch(Exception ex){
                 alert=new Alert(Alert.AlertType.ERROR
                  ,x+"  Not numeric value \nPlease make sure to enter numeric value"
                       ,ButtonType.OK
                 );
                alert.show();
                return false;
            }
        }
       
       public boolean validateUniqueEmail(String x){
           EntityManager em=emf.createEntityManager();
           List<String> list=em.createNamedQuery("Users.findEmails").getResultList();
           if (list.stream().anyMatch(e -> e .equalsIgnoreCase(x))) {
               alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Duplicates Email");
               alert.setContentText("The email you entered already exists..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
               return false;
            }
           //your email Must contain @ and end with .com atleast or end with hotmail.com or gmail.com
           if(x.toLowerCase().endsWith(".com")&&x.contains("@")){
               return true;
           }
           
            alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Invalid email");
               alert.setContentText("The email you entered invalid\nPlease enter avalid email..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
               return false;
       }
       public boolean validatePassword(String x){
           if(x.length()<8){
               alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Invalid password");
               alert.setContentText("The password you entered is too short..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
               return false;
           }
           return true;
       }
       
          @FXML
    void uploadHandle(ActionEvent event) {
            FileChooser fc = new FileChooser();
             File f = fc.showOpenDialog(new Window() {
             });
             selectedImg=f;
             if(f!=null){
                 try {
                     Image i = new Image(new FileInputStream(f));
                     System.out.println(f.getName());
                     imageview.setImage(i);
                     System.out.println(f.getPath());
                 } catch (FileNotFoundException ex) {
                 }
        }
    }
    
    public boolean validateImg(){
        if(selectedImg==null){
               alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Invalid Image");
               alert.setContentText("please upload your image..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
               return false;
        }else if(selectedImg.getPath().toLowerCase().endsWith("png")
                    ||selectedImg.getPath().toLowerCase().endsWith("jpg")||
                    selectedImg.getPath().toLowerCase().endsWith("jpeg")){
                    return true;
        }
         alert=new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ERROR");
               alert.setHeaderText("Invalid Image");
               alert.setContentText("please upload avalid image..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
               return false;
    }

    @FXML
    private void backHandle(ActionEvent event) throws IOException {
        Parent fxml=FXMLLoader.load(getClass().getResource("../View/loginpage.fxml"));
        Parent fxml1=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        VBox v=new VBox(fxml1,fxml);
        back.getScene().setRoot(v);
    }
    
}
