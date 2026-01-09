/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Invoices;
import Models.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aya
 */
public class ClientViewInvoicesController implements Initializable{
    Users loggedInUser;

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
    public ClientViewInvoicesController() {
    }

    public ClientViewInvoicesController(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcorderid.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tctotalprice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("date"));
         emf = Persistence.createEntityManagerFactory("Programming3_FinalProjectPU");
         EntityManager em=emf.createEntityManager();
         List<Invoices> list=em.createNamedQuery("Invoices.findClientInvoices")
                 .setParameter("id", loggedInUser.getId())
                 .getResultList();
    
         tableView.getItems().setAll(list);
         em.close();
    }
    
    

    @FXML
    void backHandle(ActionEvent event) throws IOException {
         Parent menubar=FXMLLoader.load(getClass().getResource("../View/longmenubar.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/clientdashboard.fxml"));
        ClientDashboard controller=new ClientDashboard(loggedInUser);
        loader.setController(controller); // i need to set the controller with controller buffer becouse it have information about logged admin
        Parent parent=loader.load();
        VBox root=new VBox(menubar,parent);
        backbtn.getScene().setRoot(root);  

    }

   
    
}
