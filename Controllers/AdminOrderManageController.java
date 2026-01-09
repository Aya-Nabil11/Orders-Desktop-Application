/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Orders;
import Models.Product;
import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SelectionMode;
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
public class AdminOrderManageController implements Initializable{
    Users loggedinAdmin;
    @FXML
    private DatePicker date;

    @FXML
    private ListView<String> clientsListView;

    @FXML
    private TextField clientid;

    @FXML
    private Button viewbtn;

    @FXML
    private ListView<String> productListView;

    @FXML
    private Button backbtn;

    @FXML
    private TableView<Orders> tableView;

    @FXML
    private TableColumn<Orders, Integer> tcuserid;

    @FXML
    private TableColumn<Orders, String> tcdate;

    @FXML
    private TableColumn<Orders, Integer> tcproductid;

    @FXML
    private TableColumn<Orders, Integer> tcid;
     @FXML
    private TableColumn<Orders, Integer> tcquantity;

    @FXML
    private Button addbtn;
     @FXML
    private TextField quantity;
    EntityManagerFactory emf;
    Alert alert;
    public AdminOrderManageController(Users loggedinAdmin) {
        this.loggedinAdmin = loggedinAdmin;
    }

    public AdminOrderManageController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcuserid.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcproductid.setCellValueFactory(new PropertyValueFactory<>("product_id"));  
        tcquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));  
        emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
        displayListViewsContent();
       LocalDate maxDate = LocalDate.now();
        date.setDayCellFactory(d ->
           new DateCell() {
               @Override public void updateItem(LocalDate item, boolean empty) {
                   super.updateItem(item, empty);
                   setDisable(item.isBefore(maxDate));
               }});
    }
    
    
    public void displayListViewsContent(){
        EntityManager em=emf.createEntityManager();
        List<String> list=em.createNamedQuery("Product.findNames").getResultList();
        productListView.getItems().setAll(list);
        List<String> list1=em.createNamedQuery("Users.findclientEmails").getResultList();
        clientsListView.getItems().setAll(list1);
        em.close();
        
    }
    @FXML
    void addHandle(ActionEvent event) {
        String selectedclientEmail=clientsListView.getSelectionModel().getSelectedItem();//null if no selected
        String selectedProductName=productListView.getSelectionModel().getSelectedItem();//null if no selected
        LocalDate odate=date.getValue();
        String quan=quantity.getText();
        // we take the inputs 
       /******************************  validate the inputs  *******************************/
        if(validate_input(selectedclientEmail)&&validate_input(selectedProductName)
               &&validatedate(odate)&&validate_input(quan)&&validate_numeric_val(quan)){
            
            EntityManager em=emf.createEntityManager();
            Product selectedProduct=(Product)em.createNamedQuery("Product.findbyname").setParameter("name", selectedProductName)
                        .getSingleResult();
            
                Users selectedClient=(Users)em.createNamedQuery("Users.findbyemail").//selected client
                   setParameter("email", selectedclientEmail)
                  .getSingleResult();
                
                if(Integer.parseInt(quan)>selectedProduct.getQuantity()){
                     alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Confirmation");
                        alert.setContentText("We Don't Have "+
                           Integer.parseInt(quan)+" "+selectedProductName+
                         " in Store\nthe max quantity of "+selectedProductName+
                       " is "+selectedProduct.getQuantity()+"\nDo you need it?");
                        Optional<ButtonType> result = alert.showAndWait();
                        
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            Orders order=new Orders(selectedProduct, selectedClient, odate.toString(), selectedProduct.getQuantity());
                            em.getTransaction().begin();
                            em.persist(order);
             /********************************Update Product Quantity**********************************/
                        em.createNamedQuery("Product.updatequantity")
                                .setParameter("quantity", 0)
                                .setParameter("id", selectedProduct.getId())
                                .executeUpdate();
                            em.getTransaction().commit();
                            em.close();
                            clearAll();
                             viewHandle(event);
                            alert = new Alert(Alert.AlertType.INFORMATION,
                                     "The Add Process is completed successfully!",
                                     ButtonType.OK
                            );
                            alert.setHeaderText("Success");
                            alert.show();
                             displayListViewsContent();
                        }
                           
                }else{
               Orders order=new Orders(selectedProduct, selectedClient, odate.toString(), Integer.parseInt(quan));
               em.getTransaction().begin();
               em.persist(order);
               /***********************************Update Product Quantity****************************************/
               Integer newQuantity=selectedProduct.getQuantity()-Integer.parseInt(quan);
               em.createNamedQuery("Product.updatequantity")
                                .setParameter("quantity", newQuantity)
                                .setParameter("id", selectedProduct.getId())
                                .executeUpdate();               
               em.getTransaction().commit();
               em.close();
               
               clearAll();
               viewHandle(event);
               alert = new Alert(Alert.AlertType.INFORMATION,
                        "The Add Process is completed successfully!",
                        ButtonType.OK
               );
               alert.setHeaderText("Success");
               alert.show();
               displayListViewsContent();
                }
                } 
    }
    
    
    @FXML
    void viewHandle(ActionEvent event) {
        EntityManager em=emf.createEntityManager();
        List<Orders> list=em.createNamedQuery("Orders.findAll").getResultList();
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
        String cid=clientid.getText();
        if(validate_input(cid)&&validate_numeric_val(cid)){
            try{
            Integer intcid=Integer.parseInt(cid);
           EntityManager em=emf.createEntityManager();
           List<Orders> list= em.createNamedQuery("Orders.findOrderByClientid")
                    .setParameter("id", intcid)
                    .getResultList();
           if(list.size()==0){
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid inputs");
               alert.setContentText("the client id does not exsit or this client does not order any order..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();

           }
            tableView.getItems().setAll(list);
            em.close();
            }catch(Exception s){
                System.out.println("This cleint id does not exsit");
            }
        }
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
                long result=Long.parseLong(x);
                if(result<=0){
                     alert=new Alert(Alert.AlertType.ERROR
                  ,"Invalid Quantity"
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
        quantity.clear();
        clientid.clear();
        productListView.getSelectionModel().clearSelection();
        clientsListView.getSelectionModel().clearSelection();
    }
}
