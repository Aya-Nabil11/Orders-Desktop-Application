/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author aya
 */
@Entity
@NamedQueries({
    @NamedQuery(name ="Orders.findAll" ,query = "SELECT e FROM Orders e"),
    @NamedQuery(name ="Orders.findClientOrderByOrderId" ,query = "SELECT e FROM Orders e where e.id=:id AND e.user.id=:userid"),
    @NamedQuery(name ="Orders.findClientOrder" ,query = "SELECT e FROM Orders e WHERE e.user.id=:id"),
    @NamedQuery(name ="Orders.findOrderByClientid" ,query = "SELECT e FROM Orders e WHERE e.user.id=:id"),
    @NamedQuery(name ="Orders.findOrdersThatDoesNotHaveInvoices" 
            ,query = "SELECT e FROM Orders e where e.id not in(SELECT  i.order.id from Invoices i)"),
    @NamedQuery(name ="Orders.updateOrderbyId"
     ,query = "update Orders o set o.product=:product , o.quantity=:quantity ,o.date=:date where o.id=:id"),
    @NamedQuery(name = "deleteById",query ="DELETE FROM Orders o where o.id=:id" )

})
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private Users user;
    @Column(length = 25)
    private String date;
    private Integer quantity;

    public Orders() {
    }

    public Orders(Product product, Users user, String date, Integer quantity) {
        this.product = product;
        this.user = user;
        this.date = date;
        this.quantity = quantity;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }
    public Integer getUser_id() {
        return user.getId();
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Orders{" + "id=" + id + ", user=" + user + ", date=" + date + '}';
    }

    public Product getProduct() {
        return product;
    }
    public Integer getProduct_id() {
        return product.getId();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    

}
