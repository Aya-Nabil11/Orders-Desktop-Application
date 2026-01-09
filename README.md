Orders Management Desktop Application
Show Image
Show Image
Show Image
Show Image
A comprehensive desktop application for managing product orders with role-based access control, built using JavaFX, JPA, and MySQL database.
📋 Table of Contents

Overview
Features
Technologies
System Architecture
Database Schema
Installation
Configuration
Usage
Project Structure
Screenshots
Contributing
License
Contact

🎯 Overview
This Orders Management System is a desktop application developed as part of the Programming III Lab course (CSCI 2108) at the Islamic University of Gaza. The system provides a complete solution for managing products, orders, clients, and invoices with two distinct user roles: Admin and Client.
Course Information:

Institution: Islamic University of Gaza - Faculty of Information Technology
Course: Programming III Lab (CSCI 2108)
Instructor: Samira Mawia Silmy
Year: 2023

✨ Features
🔐 Authentication & Authorization

Secure login system with email and password validation
Client self-registration
Role-based access control (Admin/Client)
Password change functionality with validation

👤 Client Features

Profile Management

View personal profile information
Edit profile details (name, email, mobile)


Order Management

Create new orders with product selection
Edit existing orders
View order history
Search orders by ID
Delete orders
Real-time stock validation


Invoice Viewing

Access all personal invoices
View invoice details (ID, order ID, total price, date)



👨‍💼 Admin Features

Product Management

Add new products with details
Edit product information
Delete products
View all products in table format
Search products by category (minimum 5 categories)
Category-based filtering


Client Management

View all registered clients
Delete client accounts
Search clients by name


Order Management

View all system orders
Create orders on behalf of clients
Search orders by client ID
Comprehensive order tracking


Invoice Management

Generate invoices automatically
View all invoices in the system
Search invoices by order ID
Delete invoices
Automatic calculation: Total Price = Product Price × Order Quantity



🎨 UI Customization

Menu Bar with Three Menus:

File: Exit application (with icon)
Format:

Font size adjustment
Font family selection
Background color customization


Help: About application (with icon)



🛠 Technologies
TechnologyPurposeJavaFXDesktop UI frameworkFXMLUI layout and designJPA (Java Persistence API)Object-relational mappingMySQLDatabase managementMVC PatternSoftware architectureCSSStyling (if applicable)
🏗 System Architecture
The application follows the MVC (Model-View-Controller) design pattern:
┌─────────────────────────────────────┐
│           VIEW (FXML)               │
│  - Login Screen                     │
│  - Admin Dashboard                  │
│  - Client Dashboard                 │
│  - Management Forms                 │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│      CONTROLLER (JavaFX)            │
│  - User Authentication              │
│  - Business Logic                   │
│  - Event Handling                   │
│  - Data Validation                  │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│       MODEL (JPA Entities)          │
│  - User Entity                      │
│  - Product Entity                   │
│  - Order Entity                     │
│  - Invoice Entity                   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│      DATABASE (MySQL)               │
│  - Users Table                      │
│  - Products Table                   │
│  - Orders Table                     │
│  - Invoices Table                   │
└─────────────────────────────────────┘
💾 Database Schema
Users Table
sqlCREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mobile VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role INT NOT NULL COMMENT '0=Admin, 1=Client'
);
Products Table
sqlCREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    description TEXT
);
Orders Table
sqlCREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    quantity INT NOT NULL,
    date VARCHAR(50),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
Invoices Table
sqlCREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    total_price DOUBLE NOT NULL,
    date VARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
Entity Relationships

Users → Orders (One-to-Many)
Products → Orders (One-to-Many)
Orders → Invoices (One-to-One)

📥 Installation
Prerequisites
Before running the application, ensure you have the following installed:

Java Development Kit (JDK) 8 or higher
JavaFX SDK 17 or compatible version
MySQL Server 8.0 or higher
IDE (IntelliJ IDEA, Eclipse, or NetBeans)
Maven or Gradle (optional, for dependency management)

Step-by-Step Installation

Clone the Repository

bash   git clone https://github.com/yourusername/orders-management-app.git
   cd orders-management-app

Set Up MySQL Database

sql   -- Create database
   CREATE DATABASE orders_db;
   
   -- Use the database
   USE orders_db;
   
   -- Tables will be auto-generated by JPA

Configure Database Connection
Update src/main/resources/META-INF/persistence.xml:

xml   <persistence-unit name="OrdersPU">
       <properties>
           <property name="javax.persistence.jdbc.url" 
                     value="jdbc:mysql://localhost:3306/orders_db"/>
           <property name="javax.persistence.jdbc.user" 
                     value="your_mysql_username"/>
           <property name="javax.persistence.jdbc.password" 
                     value="your_mysql_password"/>
           <property name="javax.persistence.jdbc.driver" 
                     value="com.mysql.cj.jdbc.Driver"/>
           <property name="hibernate.hbm2ddl.auto" 
                     value="update"/>
           <property name="hibernate.dialect" 
                     value="org.hibernate.dialect.MySQL8Dialect"/>
       </properties>
   </persistence-unit>

Add Admin User Manually
Since there's no admin registration form, add admin via SQL:

sql   INSERT INTO users (name, email, mobile, password, role) 
   VALUES ('Admin', 'admin@example.com', '1234567890', 'admin123', 0);

Configure JavaFX in Your IDE
For IntelliJ IDEA:

Go to Run → Edit Configurations
Add VM options: --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

For Eclipse:

Right-click project → Run As → Run Configurations
Add VM arguments in the Arguments tab


Build and Run

bash   # If using Maven
   mvn clean install
   mvn javafx:run
   
   # Or run the main class from your IDE
```

## ⚙️ Configuration

### Database Configuration Files

**persistence.xml** location: `src/main/resources/META-INF/persistence.xml`

### Application Properties
You may need to configure:
- Database connection pool settings
- JPA logging levels
- Application window size and settings

### Sample Categories
Ensure you have at least 5 product categories:
- Electronics
- Clothing
- Food & Beverages
- Books
- Home & Garden

## 🚀 Usage

### First-Time Login

**Admin Credentials:**
```
Email: admin@example.com
Password: admin123
```
*(Change these after first login)*

**Client Registration:**
1. Click "Register" on login screen
2. Fill in: Name, Email, Mobile, Password
3. Click "Register" button
4. Login with your credentials

### Admin Workflow

1. **Login** as admin
2. **Dashboard** displays management options
3. **Manage Products:**
   - Add products with category, price, quantity
   - Search by category
   - Edit/Delete products
4. **Manage Orders:**
   - View all orders
   - Add orders for clients
   - Search by client ID
5. **Generate Invoices:**
   - Click "Generate Invoices" to calculate all pending invoices
   - View/Search/Delete invoices
6. **Manage Clients:**
   - View all registered clients
   - Search by name
   - Delete client accounts

### Client Workflow

1. **Login** as client
2. **Dashboard** displays:
   - Profile
   - Orders
   - Invoices
   - Settings
3. **Create Order:**
   - Select product from dropdown
   - Enter quantity
   - Select date
   - Click "Add Order"
4. **View Profile:**
   - See your information
   - Edit details
5. **Change Password:**
   - Enter old password
   - Enter new password
   - Confirm change

### Business Rules

- ✅ Product quantity validation before order placement
- ✅ Unique email addresses
- ✅ Unique product names
- ✅ One product per order (quantity can vary)
- ✅ Password verification for changes
- ✅ Automatic invoice calculation

## 📁 Project Structure
```
orders-management-app/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/              # JPA Entity Classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Order.java
│   │   │   │   └── Invoice.java
│   │   │   │
│   │   │   ├── controller/         # JavaFX Controllers
│   │   │   │   ├── LoginController.java
│   │   │   │   ├── AdminDashboardController.java
│   │   │   │   ├── ClientDashboardController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── InvoiceController.java
│   │   │   │
│   │   │   ├── dao/                # Data Access Objects
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── ProductDAO.java
│   │   │   │   ├── OrderDAO.java
│   │   │   │   └── InvoiceDAO.java
│   │   │   │
│   │   │   ├── util/               # Utility Classes
│   │   │   │   ├── JPAUtil.java
│   │   │   │   ├── ValidationUtil.java
│   │   │   │   └── SessionManager.java
│   │   │   │
│   │   │   └── Main.java           # Application Entry Point
│   │   │
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml  # JPA Configuration
│   │       │
│   │       ├── view/                # FXML Files
│   │       │   ├── login.fxml
│   │       │   ├── register.fxml
│   │       │   ├── admin-dashboard.fxml
│   │       │   ├── client-dashboard.fxml
│   │       │   ├── product-management.fxml
│   │       │   ├── order-management.fxml
│   │       │   └── invoice-management.fxml
│   │       │
│   │       ├── css/                 # Stylesheets
│   │       │   └── styles.css
│   │       │
│   │       └── images/              # Icons and Images
│   │           ├── exit-icon.png
│   │           └── about-icon.png
│   │
│   └── test/                        # Unit Tests (if applicable)
│
├── lib/                             # External Libraries
│
├── pom.xml                          # Maven Configuration
├── README.md                        # This File
└── LICENSE                          # License Information
📸 Screenshots
Login Screen

Email and password input fields
Login and Register buttons
Form validation

Admin Dashboard

Product Management panel
Order Management panel
Client Management panel
Invoice Management panel
Settings and Logout

Client Dashboard

Profile section
My Orders section
My Invoices section
Account settings

Product Management (Admin)

Add/Edit product forms
Product table view
Category-based search
Delete functionality

Order Management

Order creation form
Order list/table view
Search functionality
Edit/Delete options

Invoice Management (Admin)

Invoice generation button
Invoice table view
Search by order ID
Delete functionality

🔒 Security Features

Password hashing (recommended to implement)
Role-based access control
Session management
Input validation and sanitization
SQL injection prevention through JPA

🐛 Known Issues & Limitations

Admin must be added manually via database
One product per order limitation
Password stored in plain text (recommend hashing)
No email verification on registration
Date stored as VARCHAR (consider using DATE type)

🎁 Bonus Features (Optional Enhancements)

 Profile picture upload and storage
 Multiple products in single order
 Export invoices to PDF
 Email notifications
 Advanced reporting and analytics
 Data export (CSV, Excel)
 Audit trail/logging system

🧪 Testing
Manual Testing Checklist

 User authentication (login/register)
 Admin product CRUD operations
 Client order creation
 Invoice generation
 Search functionalities
 Data validation
 Role-based access control

Test Scenarios

Invalid Login: Test with wrong credentials
Duplicate Email: Try registering with existing email
Out of Stock: Order quantity exceeds available stock
Password Change: Verify old password validation
Search Functions: Test all search features

🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository
Create a feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request

📝 License
This project is licensed under the MIT License - see the LICENSE file for details.
📞 Contact
Course Instructor: Samira Mawia Silmy
Institution: Islamic University of Gaza - Faculty of Information Technology
Project Maintainer: [Your Name]

Email: your.email@example.com
GitHub: @yourusername

🙏 Acknowledgments

Islamic University of Gaza - Faculty of Information Technology
Programming III Lab Course (CSCI 2108)
Instructor: Samira Mawia Silmy
All contributors and testers


⭐ If you found this project helpful, please give it a star!
