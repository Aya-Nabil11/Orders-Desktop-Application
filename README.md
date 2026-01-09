Orders Desktop Application
Project Overview
A comprehensive desktop application for managing product orders, built with JavaFX and MySQL. The system supports two user roles: Admin and Client, each with specific functionalities for managing products, orders, clients, and invoices.
Course: Programming III Lab (CSCI 2108)
Institution: Islamic University of Gaza - Faculty of Information Technology
Instructor: Samira Mawia Silmy
Technologies Used

JavaFX - User interface framework
FXML - UI layout and design
JPA (Java Persistence API) - Object-relational mapping
MySQL - Database management system
MVC Pattern - Software architecture (Model-View-Controller)

System Architecture
The application follows the MVC (Model-View-Controller) design pattern:

Model: JPA entities representing database tables
View: FXML files for UI layout
Controller: JavaFX controllers handling business logic and user interactions

Database Schema
Tables Structure
Users Table
ColumnTypeConstraintsidINTAuto Increment, Primary KeynameVARCHAR-emailVARCHARUniquemobileVARCHAR-passwordVARCHAR-roleINT0=Admin, 1=Client
Products Table
ColumnTypeConstraintsidINTAuto Increment, Primary KeynameVARCHARUniquecategoryVARCHAR-priceDOUBLE-quantityINT-descriptionTEXT-
Orders Table
ColumnTypeConstraintsidINTAuto Increment, Primary Keyproduct_idINTForeign Key → Productsuser_idINTForeign Key → UsersquantityINT-dateVARCHAR-
Invoices Table
ColumnTypeConstraintsidINTAuto Increment, Primary Keyorder_idINTForeign Key → Orderstotal_priceDOUBLE-dateVARCHAR-
Features
Authentication System

Login: Email and password validation
Registration: Client self-registration
Role-based Access: Separate dashboards for Admin and Client

Client Features

Profile Management

View profile information
Edit profile (name, email, mobile)


Order Management

Add new orders (select product, quantity, date)
Edit existing orders
View all personal orders
Search orders by ID
Delete orders


Invoice Viewing

View all personal invoices with details


Account Settings

Change password



Admin Features

Product Management

Add new products
Edit product details
Delete products
View all products
Search products by category


Client Management

View all registered clients
Delete clients
Search clients by name


Order Management

View all orders from all clients
Add orders on behalf of clients
Search orders by client ID


Invoice Management

Generate invoices (calculates: product price × quantity)
View all invoices
Search invoices by order ID
Delete invoices


Account Settings

Change password



Menu Bar Features

File Menu: Exit application (with icon)
Format Menu:

Font size customization
Font family selection
Background color options


Help Menu: About application (with icon)

Installation & Setup
Prerequisites

Java Development Kit (JDK) 8 or higher
MySQL Server
JavaFX SDK
IDE (Eclipse, IntelliJ IDEA, or NetBeans)

Database Setup

Create a MySQL database:

sqlCREATE DATABASE orders_db;

Configure database connection in persistence.xml:

xml<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/orders_db"/>
<property name="javax.persistence.jdbc.user" value="your_username"/>
<property name="javax.persistence.jdbc.password" value="your_password"/>

Add admin user manually (since no admin registration form exists):

sqlINSERT INTO users (name, email, mobile, password, role) 
VALUES ('Admin', 'admin@example.com', '1234567890', 'admin123', 0);
```

### Running the Application
1. Clone or download the project
2. Import the project into your IDE
3. Configure JavaFX libraries
4. Set up JPA dependencies
5. Update database credentials in configuration files
6. Run the main application class

## Project Structure
```
orders-app/
├── src/
│   ├── model/           # JPA Entity classes
│   ├── view/            # FXML files
│   ├── controller/      # JavaFX Controllers
│   ├── dao/             # Data Access Objects
│   └── util/            # Utility classes
├── resources/
│   ├── META-INF/
│   │   └── persistence.xml
│   └── images/          # Application icons
└── lib/                 # External libraries
Validation Rules

Email must be unique in the system
Product names must be unique
Order quantity cannot exceed available product quantity
Password change requires old password verification
All form fields are validated before submission

Business Logic

Invoice Calculation: Total Price = Product Price × Order Quantity
Stock Management: Product quantity is checked before order placement
Role Verification: Each user action is validated against their role

Default Admin Credentials
Note: Admin must be added manually via database
Example:

Email: admin@example.com
Password: admin123

Future Enhancements (Bonus Features)

Profile picture upload and storage
Multiple product ordering in single transaction
Enhanced reporting features
Export invoices to PDF

Troubleshooting

Database Connection Error: Verify MySQL service is running and credentials are correct
JPA Configuration: Ensure persistence.xml is properly configured
JavaFX Issues: Check that JavaFX libraries are correctly linked to the project

License
This project is developed for educational purposes as part of the Programming III Lab course.
Contact
For questions or issues, please contact the course instructor: Samira Mawia Silmy

Development Year: 2023
Project Type: Desktop Application
Framework: JavaFX with JPA/MySQL
