/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Product;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class ProfileController implements Initializable{
    Users loggerInUser;
           @FXML
    private TableColumn<Users, String> tcname;

    @FXML
    private Button editbtn;

    @FXML
    private ImageView imageview;

    @FXML
    private TextField name;

    @FXML
    private TextField mobile;

    @FXML
    private TableColumn<Users, String> tcemail;

    @FXML
    private TableColumn<Users, String> tcmobile;

    @FXML
    private TableView<Users> tableview;

    @FXML
    private TextField email;
    File selectedImg;
    @FXML
    private Label nameLabel;
    EntityManagerFactory emf;
    Alert alert;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcmobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
         emf = Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");

         /*****************************Display client image***************************************/
         try {
            Image image=new Image(new FileInputStream(loggerInUser.getImg()));
            imageview.setImage(image);
            nameLabel.setText("Name: "+loggerInUser.getName());
        } catch (FileNotFoundException ex) {
        }
         
         /*************************Display Client Information into textFileds***********************************/
         name.setText(loggerInUser.getName());
         email.setText(loggerInUser.getEmail());
         mobile.setText(loggerInUser.getMobile());
         selectedImg=new File(loggerInUser.getImg());
         
         
           tableview.getSelectionModel().selectedItemProperty().addListener(
                s -> {
                    Users p = tableview.getSelectionModel().getSelectedItem();
                    if (p != null) {//هاى ما بعرف لايش بس لما ما احطها واضغط على زر التعديل انا ضمنيا مستدعية زر الشوو ف بصير خطأ في زر الشوو وبعدل على الداتابيز بس بطلع ايكسبشن هان
                        name.setText(p.getName());
                        email.setText(p.getEmail()+ "");
                        mobile.setText(p.getMobile());
                    }
                }
        );
    }

    public ProfileController() {
    }

    public ProfileController(Users loggerInUser) {
        this.loggerInUser = loggerInUser;
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
    

    @FXML
    void backHandle(ActionEvent event) throws IOException {
         Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(loggerInUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        editbtn.getScene().setRoot(root);  

    }

    @FXML
    void editHandle(ActionEvent event) {
       String cname= name.getText();
       String cemail= email.getText();
       String cmobile= mobile.getText();
       /**************************Validate Input***************************/
       if(validate_input(cname)&&validate_input(cemail)&&validate_input(cmobile)
               &&validateUniqueEmail(cemail)&&validate_numeric_val(cmobile)
               &&validateImg()){
           EntityManager em=emf.createEntityManager();
          em.getTransaction().begin();
           em.createNamedQuery("Users.updateClientprofile")
                   .setParameter("name", cname)
                   .setParameter("email", cemail)
                   .setParameter("mobile", cmobile)
                   .setParameter("img", selectedImg.getPath())
                   .setParameter("id", loggerInUser.getId())
                   .executeUpdate();
           em.getTransaction().commit();
           em.close();
           loggerInUser.setEmail(cemail);
           loggerInUser.setImg(selectedImg.getPath());
           loggerInUser.setMobile(cmobile);
           loggerInUser.setName(cname);
           nameLabel.setText("Name: "+cname);
            alert = new Alert(Alert.AlertType.INFORMATION,
             "The Edit Process is completed successfully!",
                        ButtonType.OK
               );
               alert.setHeaderText("Success");
               alert.show();

           viewHandle(event);
       }
     }

    @FXML
    void viewHandle(ActionEvent event) {
        tableview.getItems().setAll(loggerInUser); 
    }
    
    
    
    

       public boolean validateImg(){
        if(selectedImg.getPath().toLowerCase().endsWith("png")
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

       public boolean validateUniqueEmail(String x){
          if(!x.equalsIgnoreCase(loggerInUser.getEmail())){
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
      
       public boolean validate_numeric_val(String x){
            try{
                Long.parseLong(x);
                if(x.length()!=10){  // i guess that the telephone numbers in palestine have 10 digit
                    alert=new Alert(Alert.AlertType.ERROR
                  ,x+" Invalid Phone Number \nPlease make sure to enter valid phone number"
                       ,ButtonType.OK
                 );
                alert.show();
                return false;
                }
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
      
       
        @FXML
    void resetHandle(ActionEvent event) {
        name.clear();
        email.clear();
        mobile.clear();
        tableview.getItems().clear();
        
    }
}
