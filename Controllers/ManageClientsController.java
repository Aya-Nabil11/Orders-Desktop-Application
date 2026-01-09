/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Product;
import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class ManageClientsController implements Initializable{
    private Users loggedInAdmin;
     @FXML
    private TableColumn<Users, String> tcname;
    @FXML
    private TextField name;
    @FXML
    private Button backbtn;
    @FXML
    private TableView<Users> tableView;
    @FXML
    private TableColumn<Users, String> tcemail;
    @FXML
    private TableColumn<Users, String> tcmobile;
    @FXML
    private TableColumn<Users, Integer> tcid;
    Alert alert;
    EntityManagerFactory emf;
    public ManageClientsController() {
    }

    public ManageClientsController(Users loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcmobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
         emf = Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
        
    }
    
   

    @FXML
    void deleteHandle(ActionEvent event) {
         Users p=tableView.getSelectionModel().getSelectedItem();
        if(p==null){
                    alert = new Alert(Alert.AlertType.ERROR,
                     "please select an item from table view",
                     ButtonType.OK
            );
            alert.setHeaderText("Error");
            alert.show();
        }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to delete this product?.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                EntityManager em=emf.createEntityManager();
                em.getTransaction().begin();
                em.createNamedQuery("Users.deletebyid")
                        .setParameter("id", p.getId())
                        .executeUpdate();
                em.getTransaction().commit();
                em.close();
                    viewHandle(event);
                    name.clear();
                     alert = new Alert(Alert.AlertType.INFORMATION,
                                     "The delete Process is completed successfully!",
                                     ButtonType.OK
                            );
                            alert.setHeaderText("Success");
                            alert.show();
                    
                    
                }
            }
    }

    @FXML
    void resetHandle(ActionEvent event) {
        name.clear();
        tableView.getItems().clear();
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
         Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admindashboard.fxml"));
        AdminDashboardController controller=new AdminDashboardController(loggedInAdmin);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root);  
    }

    @FXML
    void searchHandle(ActionEvent event) {
        String searchedName= name.getText();
        if(validate_input(searchedName)){
            EntityManager em=emf.createEntityManager();
            List<Users> list=em.createNamedQuery("Users.findbyname")
                    .setParameter("name", searchedName)
                    .getResultList();
                       if(list.size()==0){
            alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid name");
            alert.setHeaderText("This name does not exsit");
               alert.setContentText("!!");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();

           }
            tableView.getItems().setAll(list);
            em.close();
            
            
        }
     }
    
    @FXML
    void viewHandle(ActionEvent event) {
        EntityManager em=emf.createEntityManager();
        List<Users> list=em.createNamedQuery("Users.findAllclients").getResultList();
        tableView.getItems().setAll(list);
        em.close();
    }
      public boolean validate_input(String x) {
        if (x == null || x.trim().equalsIgnoreCase("")) {//null for combobox value
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty feilds values");
            alert.setContentText("please Enter all values\n Don't Keep it Empty!");
            alert.show();
            return false;
        }
        return true;
    }
   
}
