/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Invoices;
import Models.Orders;
import Models.Product;
import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class ManageInvoicesController implements Initializable{
    private Users loggedinAdmin;
    Alert alert;
    @FXML
    private DatePicker date;
    @FXML
    private Button viewbtn;

    @FXML
    private TextField orderid;

    @FXML
    private TableColumn<Invoices, Double> tctotalprice;

    @FXML
    private Button backbtn;

    @FXML
    private TableView<Invoices> tableView;

    @FXML
    private TableColumn<Invoices, Integer> tcorderid;

    @FXML
    private TableColumn<Invoices, String> tcdate;

    @FXML
    private TableColumn<Invoices, Integer> tcid;
    EntityManagerFactory emf;
    public ManageInvoicesController() {
    }

    public ManageInvoicesController(Users loggedinAdmin) {
        this.loggedinAdmin = loggedinAdmin;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
       tcorderid.setCellValueFactory(new PropertyValueFactory<>("order_id"));
       tcdate.setCellValueFactory(new PropertyValueFactory<>("date"));
       tctotalprice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));  
       emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
       LocalDate today = LocalDate.now(); 
        //Allow only the today's date
       date.setDayCellFactory(d ->
        new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || !item.isEqual(today));
            }
        });



    }
       @FXML
    void generateInvoicesHandle(ActionEvent event) {
           if(validatedate(date.getValue())){
           EntityManager em=emf.createEntityManager();
           List<Orders> list=em.createNamedQuery("Orders.findOrdersThatDoesNotHaveInvoices")
                   .getResultList();
           System.out.println(list);
           if(list.size()>0){
           em.getTransaction().begin();
           list.stream().forEach(e->{
              Double orderTotalPrice=e.getQuantity()*e.getProduct().getPrice();
               Invoices invoice=new Invoices(e, orderTotalPrice, date.getValue().toString());
               em.persist(invoice);
           });
           em.getTransaction().commit();
           em.close();
               viewHandle(event);
           }else{
               alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("All orders invoices are generated");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
           }
    }
    
    }

    @FXML
    void viewHandle(ActionEvent event) {
        EntityManager em=emf.createEntityManager();
        List <Invoices> list=em.createNamedQuery("Invoices.findAll").getResultList();
        tableView.getItems().setAll(list);
        em.close();
    }

    @FXML
    void resetHandle(ActionEvent event) {
        clearAll();
        tableView.getItems().clear();
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admindashboard.fxml"));
        AdminDashboardController controller=new AdminDashboardController(loggedinAdmin);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root);  
    }

    @FXML
    void searchHandle(ActionEvent event) {
      String oid=  orderid.getText();
      if(validate_input(oid)&&validate_numeric_val(oid)){
          Integer order_id=Integer.parseInt(oid);
          EntityManager em=emf.createEntityManager();
          try{
          Invoices inv=(Invoices) em.createNamedQuery("Invoices.findByOrderId")
                  .setParameter("id", order_id)
                  .getSingleResult();
          tableView.getItems().setAll(inv);
          }catch(NoResultException ec){
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid inputs");
            alert.setContentText("No Result Is Found");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
          }
          em.close();
      }

    }

     private boolean validatedate(LocalDate x){
       if(x==null){
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid inputs");
               alert.setContentText("please Make Sure to enter all inputs..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
           return false;
       }
         return true;
   }
    
    private void clearAll(){
        date.setValue(null);
        orderid.clear();
        
    }

     private boolean validate_input(String x){
       if(x==null||x.trim().equals("")){
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid inputs");
               alert.setContentText("please Make Sure to enter all inputs..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();
           return false;
       }
         return true;
   }
     
     public boolean validate_numeric_val(String x){
            try{
                Integer.parseInt(x);
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
    void deleteHandle(ActionEvent event) {
         
         Invoices p=tableView.getSelectionModel().getSelectedItem();
        if(p==null){
                    alert = new Alert(Alert.AlertType.ERROR,
                     "please select an item from table view",
                     ButtonType.OK
            );
            alert.setHeaderText("Error");
            alert.show();
        }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to delete this order Invoice?.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                EntityManager em=emf.createEntityManager();
                em.getTransaction().begin();
                em.createNamedQuery("Invoices.deleteById")
                        .setParameter("id", p.getId())
                        .executeUpdate();
                em.getTransaction().commit();
                em.close();
                    viewHandle(event);
                     alert = new Alert(Alert.AlertType.INFORMATION,
                             "The delete Process is completed successfully!",
                             ButtonType.OK
                    );
                    alert.setHeaderText("Success");
                    alert.show();
                    clearAll();
                }
         }
     }
    
}
