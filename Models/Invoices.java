/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author aya
 */
@NamedQueries({
    @NamedQuery(name = "Invoices.findAll" ,query = "SELECT e FROM Invoices e"),
    @NamedQuery(name = "Invoices.findByOrderId" ,query = "SELECT e FROM Invoices e where e.order.id=:id"),
    @NamedQuery(name = "Invoices.deleteById" ,query = "DELETE FROM Invoices i WHERE i.id=:id"),
    @NamedQuery(name = "Invoices.findClientInvoices" ,query = "SELECT e FROM Invoices e where e.order.user.id=:id"),
    
})
@Entity
public class Invoices {
     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     @ManyToOne
     @JoinColumn(name = "order_id")
    private Orders order;
    private Double totalPrice;
    @Column(length = 25)
    private String date;

    public Invoices() {
    }

    public Invoices(Orders order, Double totalPrice, String date) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }
    public Integer getOrder_id() {
        return order.getId();
    }
    public void setOrder(Orders order) {
        this.order = order;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoices{" + "id=" + id + ", order=" + order + ", totalPrice=" + totalPrice + ", date=" + date + '}';
    }
    
    
}
