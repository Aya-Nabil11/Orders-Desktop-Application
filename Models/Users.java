/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author aya
 */
@NamedQueries({
    @NamedQuery(name = "Users.findEmails",query = "select e.email from Users e"),
    @NamedQuery(name = "Users.findclientEmails",query = "select e.email from Users e WHERE e.role='client'"),
    @NamedQuery(name = "Users.findAll",query = "select e from Users e"),
    @NamedQuery(name = "Users.findAllclients",query = "select e from Users e WHERE e.role='client'"),
    @NamedQuery(name = "Users.deletebyid",query = "DELETE FROM Users e WHERE e.id=:id"),
    @NamedQuery(name = "Users.findbyname",query = "select e from Users e WHERE e.name=:name and e.role='client'"),
    @NamedQuery(name = "Users.findbyemail",query = "select e FROM Users e where e.email=:email"),//fint client by its email that slected by admin to make to him an order
    @NamedQuery(name = "Users.changepassword",query = "UPDATE Users u SET u.password=:password WHERE u.id=:id"),
    @NamedQuery(name = "Users.updateClientprofile",query = "UPDATE Users u SET u.name=:name , u.email=:email, u.mobile=:mobile ,u.img=:img where u.id=:id")

})
@Entity
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 25)
    private String name;
    @Column(unique=true,length = 25)
    private String email;
    @Column(length = 25)
    private String mobile;
    @Column(length = 25)
    private String password;
    @Column(length = 25)
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Orders> list;
    @Column(length = 100)
    private String img;
    public Users() {
    }

    public Users(String name, String email, String mobile, String password, String role, String img) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", password=" + password + ", role=" + role + ", img=" + img + '}';
    }

  

    public List<Orders> getList() {
        return list;
    }

    public void setList(List<Orders> list) {
        this.list = list;
    }
    
    
    
    
    
}
