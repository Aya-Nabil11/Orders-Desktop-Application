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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author aya
 */
@NamedQueries({
    @NamedQuery(name = "Product.findNames",query = "select  p.name  from Product p where p.quantity!=0"),
    @NamedQuery(name = "Product.findAll",query = "select  p  from Product p"),
    @NamedQuery(name = "Product.update",
            query = "update Product p set p.name=:name , p.category=:category ,p.price=:price,p.quantity=:quantity,p.description=:description where p.id=:id"),
    @NamedQuery(name = "Product.findbycategories",query = "select  p  from Product p where p.category=:category"),
    @NamedQuery(name = "Product.deletebyid",query = "DELETE FROM Product p WHERE p.id=:id"),
    @NamedQuery(name = "Product.findbyname",query = "SELECT e from Product e where e.name=:name"),
    @NamedQuery(name ="Product.updatequantity" ,query = "update Product p set p.quantity=:quantity where p.id=:id")
})
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true,length = 25)
    private String name;
    @Column(length = 25)
    private String category;
    private Double price;
    private Integer quantity;
    @Column(length = 100)
    private String description;
    @OneToMany(mappedBy = "product")
    private List<Orders> list;

    public Product() {
    }

    public Product(String name, String category, Double price, Integer quantity, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", quantity=" + quantity + ", description=" + description + '}';
    }

    public List<Orders> getList() {
        return list;
    }

    public void setList(List<Orders> list) {
        this.list = list;
    }

   
    
    
    
}
