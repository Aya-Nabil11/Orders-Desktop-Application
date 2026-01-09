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
public class ClientOrderManageController implements Initializable{
    Users loggedInUser;
          @FXML
    private DatePicker date;

    @FXML
    private TextField quantity;

    @FXML
    private Button viewbtn;
    @FXML
    private TableColumn<Orders, Integer> tcproductid;
    @FXML
    private TextField orderid;

    @FXML
    private Button editbtn;

    @FXML
    private TableView<Orders> tableView;

    @FXML
    private TableColumn<Orders, String> tcdate;

    @FXML
    private Button addbtn;

    @FXML
    private ListView<String> productsListView;

    @FXML
    private Button backbtn;
    EntityManagerFactory emf;
    Alert alert;
    @FXML
    private TableColumn<Orders, Integer> tcid;

    @FXML
    private TableColumn<Orders, Double> tcquantity;

    public ClientOrderManageController() {
    }

    public ClientOrderManageController(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcproductid.setCellValueFactory(new PropertyValueFactory<>("product_id"));  
        tcquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));  
        tcdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        emf=Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
       LocalDate maxDate = LocalDate.now();
       displayListViewsContent();
        date.setDayCellFactory(d ->
           new DateCell() {
               @Override public void updateItem(LocalDate item, boolean empty) {
                   super.updateItem(item, empty);
                   setDisable(item.isBefore(maxDate));
               }});
        
           tableView.getSelectionModel().selectedItemProperty().addListener(
                s -> {
                    Orders p = tableView.getSelectionModel().getSelectedItem();
                    if (p != null) {//هاى ما بعرف لايش بس لما ما احطها واضغط على زر التعديل انا ضمنيا مستدعية زر الشوو ف بصير خطأ في زر الشوو وبعدل على الداتابيز بس بطلع ايكسبشن هان
                        quantity.setText(p.getQuantity()+"");
                        String dateStr = p.getDate();
                        LocalDate local = LocalDate.parse(dateStr);
                        date.setValue(local);
                        productsListView.getSelectionModel().clearSelection();
                        productsListView.getSelectionModel().select(p.getProduct().getName());
                    }
                }
        );
    }
    
    public void displayListViewsContent(){
        EntityManager em=emf.createEntityManager();
        //Find non zero product name
        List<String> list=em.createNamedQuery("Product.findNames").getResultList();//
        productsListView.getItems().setAll(list);
        em.close();
    }
 
    @FXML
    void addHandle(ActionEvent event) {
       String selectedProductName= productsListView.getSelectionModel().getSelectedItem();
       String orderQuantity=quantity.getText();
       LocalDate orderDate=date.getValue();
       if(validate_input(selectedProductName)&&validate_input(orderQuantity)
               &&validatedate(orderDate) &&validate_numeric_val(orderQuantity)){
          EntityManager em=emf.createEntityManager();
          Product selectedProduct=(Product)em.createNamedQuery("Product.findbyname")
                   .setParameter("name", selectedProductName)
                        .getSingleResult();
           Integer intOrderQuan= Integer.parseInt(orderQuantity);
     /************Validate the quantity if it is less than the product quantity*************/
          if(intOrderQuan>selectedProduct.getQuantity()){
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("We Don't Have "+
                intOrderQuan+" "+selectedProductName+
                         " in Store\nthe max quantity of "+selectedProductName+
                       " is "+selectedProduct.getQuantity()+"\nDo you need it?");
                        Optional<ButtonType> result = alert.showAndWait();
//      /*********************Take the response from client*************************/
                     if (result.isPresent() && result.get() == ButtonType.OK) {
                       Orders order=new Orders(selectedProduct, loggedInUser, orderDate.toString(), selectedProduct.getQuantity());
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
               // if the ordered quantity is less then the product quantity
               Orders order=new Orders(selectedProduct, loggedInUser, date.getValue().toString(), intOrderQuan);
               em.getTransaction().begin();
               em.persist(order);
//               /***********************************Update Product Quantity****************************************/
               Integer newQuantity=selectedProduct.getQuantity()-intOrderQuan;
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
        EntityManager em =emf.createEntityManager();
        List<Orders> list=em.createNamedQuery("Orders.findClientOrder")
                .setParameter("id", loggedInUser.getId())
                .getResultList();
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
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(loggedInUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        editbtn.getScene().setRoot(root);  
    }

    @FXML
    void searchHandle(ActionEvent event) {
        String inputOrderid=orderid.getText();
        if(validate_input(inputOrderid)&&validate_numeric_val(inputOrderid)){
          try{      
            EntityManager em=emf.createEntityManager();
                em.getTransaction().begin();
                Orders o=(Orders)em.createNamedQuery("Orders.findClientOrderByOrderId")
                        .setParameter("id", Integer.parseInt(inputOrderid))
                        .setParameter("userid", loggedInUser.getId())
                        .getSingleResult();
                tableView.getItems().setAll(o);
                em.getTransaction().commit();
          }catch(NoResultException no){
                    alert=new Alert(Alert.AlertType.ERROR
                  ,"You don't order this order"
                       ,ButtonType.OK
                 );
                alert.show();

          }
            
            
            
        }
    }

    

    @FXML
    void editHandle(ActionEvent event) {
       String selectedProductName= productsListView.getSelectionModel().getSelectedItem();//May the same product or another product of teh edited order
       String updatedquantity=quantity.getText();
       LocalDate updatedDate=date.getValue();
       Orders updatedOrder=tableView.getSelectionModel().getSelectedItem();
       /************************* Validation *****************************************/
        System.out.println(selectedProductName);
        if(validateSelectedOrder(updatedOrder)&&validate_input(selectedProductName)
                &&validate_input(updatedquantity)&&validatedate(updatedDate)
                &&validate_numeric_val(updatedquantity)){
            //return the old order quantity to the old product
          EntityManager em=emf.createEntityManager();
          em.getTransaction().begin();
          Integer oldProductUpdatedQuantity=updatedOrder.getQuantity()+updatedOrder.getProduct().getQuantity();
          em.createNamedQuery("Product.updatequantity")
              .setParameter("quantity", oldProductUpdatedQuantity)
              .setParameter("id",updatedOrder.getProduct_id() )
              .executeUpdate();
          em.getTransaction().commit();
          Product selectedProduct=(Product)em.createNamedQuery("Product.findbyname")
                   .setParameter("name", selectedProductName)
                        .getSingleResult();
          Integer intQuantity=Integer.parseInt(updatedquantity);
          if(intQuantity>selectedProduct.getQuantity()){
             //quantity of the order greater than the needed quantity
             alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Confirmation Dialog");
             alert.setHeaderText("Confirmation");
             alert.setContentText("We Don't Have "+
             intQuantity+" "+selectedProductName+
             " in Store\nthe max quantity of "+selectedProductName+
             " is "+selectedProduct.getQuantity()+"\nDo you need it?");
             Optional<ButtonType> result = alert.showAndWait();
            /*********************Take the response from client*************************/
             if (result.isPresent() && result.get() == ButtonType.OK) {
                em.getTransaction().begin();
                //update the quantity and date and the product of the order
              em.createNamedQuery("Orders.updateOrderbyId")
                      .setParameter("product", selectedProduct)
                      .setParameter("quantity", selectedProduct.getQuantity())
                      .setParameter("date", updatedDate.toString())
                      .setParameter("id", updatedOrder.getId())
                      .executeUpdate();
                    //Put the quantity of product to zero
                    em.createNamedQuery("Product.updatequantity")
                            .setParameter("quantity", 0)
                            .setParameter("id", selectedProduct.getId())
                            .executeUpdate();
                            em.getTransaction().commit();
                            successAlert();
                            clearAll();
                            displayListViewsContent();
                            viewHandle(event);
                            }
          }else{
              // the needed quantity is less than the product quantity
                    em.getTransaction().begin();
                    em.createNamedQuery("Orders.updateOrderbyId")
                        .setParameter("product", selectedProduct)
                        .setParameter("quantity", intQuantity)
                        .setParameter("date", updatedDate.toString())
                        .setParameter("id", updatedOrder.getId())
                        .executeUpdate();
                      
                    //product quantity=old quantity-needed quantity for the order
                    em.createNamedQuery("Product.updatequantity")
                       .setParameter("quantity", selectedProduct.getQuantity()-intQuantity)
                       .setParameter("id", selectedProduct.getId())
                       .executeUpdate();
                    em.getTransaction().commit();
                    successAlert();
                    clearAll();
                    displayListViewsContent();
                     viewHandle(event);
          }
        }
    }

    @FXML
    void deleteHandle(ActionEvent event) {
       Orders order= tableView.getSelectionModel().getSelectedItem();
        if(validateSelectedOrder(order)){
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Do you sure that you want to delete the order with id = "+order.getId());
                        Optional<ButtonType> result = alert.showAndWait();
//      /*********************Take the response from client*************************/
                     if (result.isPresent() && result.get() == ButtonType.OK) {
                          EntityManager em=emf.createEntityManager();
                        em.getTransaction().begin();
                        // delete the order +return the quantity to the product
                        em.createNamedQuery("deleteById")
                                .setParameter("id", order.getId())
                                .executeUpdate();
                        
                        Integer oldProductUpdatedQuantity=order.getQuantity()+order.getProduct().getQuantity();
                         em.createNamedQuery("Product.updatequantity")
                            .setParameter("quantity", oldProductUpdatedQuantity)
                            .setParameter("id",order.getProduct_id())
                            .executeUpdate();
                        
                        em.getTransaction().commit();
                        viewHandle(event);
                        //success alert
                        alert = new Alert(Alert.AlertType.INFORMATION,
                    "The Edit Process is completed successfully!",
                       ButtonType.OK);
                      alert.setHeaderText("Success");
                      alert.show();
                      displayListViewsContent();
                         clearAll();
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
     
     private void successAlert(){
            alert = new Alert(Alert.AlertType.INFORMATION,
                "The Edit Process is completed successfully!",
                   ButtonType.OK);
            alert.setHeaderText("Success");
            alert.show();
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
        orderid.clear();
        productsListView.getSelectionModel().clearSelection();
    }
    private boolean validateSelectedOrder(Orders o){
        if(o==null){
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Order");
               alert.setContentText("please Make Sure to Select An order from Table View..");
               DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
               alert.show();

            return false;
        }
        return true;
    }
}
