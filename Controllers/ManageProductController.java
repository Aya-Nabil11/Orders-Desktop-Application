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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class ManageProductController implements Initializable {

    private Users loggedUser;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> tcname;
    @FXML
    private TableColumn<Product, Integer> tcid;
    @FXML
    private TableColumn<Product, Integer> tcquantity;
    @FXML
    private TableColumn<Product, String> tcdescription;
    @FXML
    private TableColumn<Product, String> tccategory;
    @FXML
    private TableColumn<Product, Double> tcprice;
    @FXML
    private TextField quantity;
    @FXML
    private TextArea discription;
    @FXML
    private Button viewbtn;
    @FXML
    private Button addbtn;
    @FXML
    private TextField price;
    @FXML
    private TextField name;
    @FXML
    private Button backbtn;
    @FXML
    private ComboBox<String> combobox;
    Alert alert;
    EntityManagerFactory emf;

    public ManageProductController(Users loggedUser) {
        this.loggedUser = loggedUser;
    }

    public ManageProductController() {
    }
    
      @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tccategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        //Add content to combo box
        combobox.getItems().addAll("Beauty",
                "Fashion", "Electronics",
                "Sports", "Food");

        tableView.getSelectionModel().selectedItemProperty().addListener(
                s -> {
                    Product p = tableView.getSelectionModel().getSelectedItem();
                    if (p != null) {//هاى ما بعرف لايش بس لما ما احطها واضغط على زر التعديل انا ضمنيا مستدعية زر الشوو ف بصير خطأ في زر الشوو وبعدل على الداتابيز بس بطلع ايكسبشن هان
                        name.setText(p.getName());
                        combobox.setValue(p.getCategory());
                        price.setText(p.getPrice() + "");
                        quantity.setText(p.getQuantity() + "");
                        discription.setText(p.getDescription());
                    }
                }
        );

        emf = Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
    }

    @FXML
    void addHandle(ActionEvent event) {
        // retreive input from textfields or combobox
        String pname = name.getText();
        String pcategory = combobox.getValue();
        String pprice = price.getText();
        String pquantity = quantity.getText();
        String pdescription = discription.getText();
        //validate them
        if (validate_input(pname) && validate_input(pcategory) && validate_input(pprice)
                && validate_input(pquantity) && validate_input(pdescription)
                && validateUniqueProductName(pname, "") && validate_numeric_Int_val(pquantity)
                && validate_numeric_Double_val(pprice)) {
//validateUniqueProductName(pname,"")  why??
//i checked earlier that the input feilds cant not be empty then pname !=""
            Double doubleprice = Double.parseDouble(pprice);
            Integer intQuantity = Integer.parseInt(pquantity);
            EntityManager em = emf.createEntityManager();
            Product p = new Product(pname, pcategory, doubleprice,
                    intQuantity, pdescription);

            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            viewHandle(event);
            em.close();
            alert = new Alert(Alert.AlertType.INFORMATION,
                     "The Add Process is completed successfully!",
                     ButtonType.OK
            );
            alert.setHeaderText("Success");
            alert.show();
            clearAll();
        }
    }

    @FXML
    void editHandle(ActionEvent event) {
        Product p = tableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            alert = new Alert(Alert.AlertType.ERROR,
                     "please select an item from table view",
                     ButtonType.OK
            );
            alert.setHeaderText("Error");
            alert.show();
        } else {
            String updatedName = name.getText();
            String updatedCat = combobox.getValue();
            String updatedPrice = price.getText();
            String updatedQuantity = quantity.getText();
            String updatedDiscription = discription.getText();
            if (validate_input(updatedName) && validate_input(updatedCat) && validate_input(updatedPrice)
                    && validate_input(updatedQuantity) && validate_input(updatedDiscription)
                    && validateUniqueProductName(updatedName, p.getName()) && validate_numeric_Int_val(updatedQuantity)
                    && validate_numeric_Double_val(updatedPrice)) {
                //confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to update this product?.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    EntityManager em = emf.createEntityManager();
                    em.getTransaction().begin();
                    em.createNamedQuery("Product.update")
                            .setParameter("name", updatedName)
                            .setParameter("category", updatedCat)
                            .setParameter("price", Double.parseDouble(updatedPrice))
                            .setParameter("quantity", Integer.parseInt(updatedQuantity))
                            .setParameter("description", updatedDiscription)
                            .setParameter("id", p.getId()).executeUpdate();
                    em.getTransaction().commit();
                    em.close();
                    viewHandle(event);
                    alert = new Alert(Alert.AlertType.INFORMATION,
                             "The Edit Process is completed successfully!",
                             ButtonType.OK
                    );
                    alert.setHeaderText("Success");
                    alert.show();
                    clearAll();
                }

            }
        }
    }

    @FXML
    void viewHandle(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Product> list = em.createNamedQuery("Product.findAll").getResultList();
        
        tableView.getItems().setAll(list);
    }

    @FXML
    void deleteHandle(ActionEvent event) {
        Product p=tableView.getSelectionModel().getSelectedItem();
        if(p==null){
                  selectAnItemAlert();
        }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to delete this product?.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    EntityManager em=emf.createEntityManager();
                    int checktrnncate=tableView.getItems().size();
                if(checktrnncate==1){
                    // to trancate the table if the all items deleted
//                              trancate();
                          }
                em.getTransaction().begin();
                em.createNamedQuery("Product.deletebyid")
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

    @FXML
    void resetHandle(ActionEvent event) {
        clearAll();
        tableView.getItems().clear();
    }

    @FXML
    void backHandle(ActionEvent event) throws IOException {
        Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admindashboard.fxml"));
        AdminDashboardController controller=new AdminDashboardController(loggedUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root);  
    }

    @FXML
    void searchHandle(ActionEvent event) {
        if(combobox.getValue()!=null&&!combobox.getValue().equals("")){
            // becouse when i call resetAll method i set the value to be ""
            String selectedCat=combobox.getValue();
            EntityManager em=emf.createEntityManager();
            List<Product> list=em.createNamedQuery("Product.findbycategories")
                    .setParameter("category", selectedCat)
                    .getResultList();
            tableView.getItems().setAll(list);
            em.close();
            
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty category fields");
            alert.setContentText("please select a category from comboBox!");
            alert.show();
         }
        
    }


  

    public boolean validate_numeric_Int_val(String x) {
        try {
            int quantity = Integer.parseInt(x);
            if (quantity <= 0) {
                alert = new Alert(Alert.AlertType.ERROR,
                         "Invalid Quantity",
                         ButtonType.OK
                );
                alert.show();
                return false;
            }
            return true;
        } catch (Exception ex) {
            alert = new Alert(Alert.AlertType.ERROR,
                     x + "  Invalid Quantity \nPlease make sure to enter Integer value",
                     ButtonType.OK
            );
            alert.show();
            return false;
        }
    }

    public boolean validate_numeric_Double_val(String x) {
        try {
            double price = Double.parseDouble(x);
            if (price <=0) {
                alert = new Alert(Alert.AlertType.ERROR,
                         "Invalid Price!",
                         ButtonType.OK
                );
                alert.show();
                return false;
            }
            return true;

        } catch (Exception ex) {
            alert = new Alert(Alert.AlertType.ERROR,
                     x + " No Numeric value \nPlease make sure to enter a numeric value",
                     ButtonType.OK
            );
            alert.show();
            return false;
        }
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

    public boolean validateUniqueProductName(String x, String oldName) {
        EntityManager em = emf.createEntityManager();
        List<String> list = em.createNamedQuery("Product.findNames").getResultList();
        if (!x.equalsIgnoreCase(oldName)) {
            //if the operation is add the x != "" then it will search in all names in product table
            //in edit operation if the No change made in name then return true that the name
            //still unique else if there does not equal then search in product names
            //in table
            if (list.stream().anyMatch(e -> e.equalsIgnoreCase(x))) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Duplicates Names");
                alert.setContentText("The product name you entered already exists..");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStyleClass().add("my-dialog");
                dialogPane.setStyle("-fx-background-color: rgb(218,245,254);");
                alert.show();
                return false;
            }
            return true;
        }
        return true;

    }
    
    public void clearAll(){
        name.clear();
        combobox.setValue("");
        discription.clear();
        quantity.clear();
        price.clear();
        
    }
    
    private void trancate(){
        EntityManager em= emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;TRUNCATE TABLE product;SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
        em.getTransaction().commit();
        //this error is occur
        //Cannot truncate a table referenced in a foreign key constraint
        //(`finalproject`.`orders`, CONSTRAINT `FK_ORDERS_product_id`
        //FOREIGN KEY (`product_id`) REFERENCES `finalproject`.`product` (`ID`))
    }
    private void selectAnItemAlert(){
        alert = new Alert(Alert.AlertType.ERROR,
                     "please select an item from table view",
                     ButtonType.OK
            );
            alert.setHeaderText("Error");
            alert.show();
    }
    
    
    
    
    
    
    

}
