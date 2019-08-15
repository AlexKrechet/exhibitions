Project option 7

Title: Calendar of exhibitions system.

Tasks:

    - List of showrooms each has a list of expositions;
	- Visitor buying tickets after choosing exposition theme;

Project requirements:

	- classes which describe entities according to the task field;
	- classes and methods should be named according to their nature and properly structured within packages;
	- related to the task field information should be stored in DB, API JDBC should be used for accesses with use of pool of connections (standard or custom). It is recommended to use MySQL;
	- application must maintain Cyrillic (including data in DB);
	- code must be documented; 
	- application must be covered with unit-tests;
	- session and filters must be used and incidents must be handled by Log4j;
	- depends on project, Pagination and Transaction should be used;
	- servlets and JSP must be used for application functions;
	- JSTL library should be used at JSP pages;
	- correct errors and exceptions handling, user must not see them at all;
	- authorization and authentication system must be implemented.

Using tips:

	- After successful registration, Calendar of exhibitions system allows visitors to search through the list of expositions and confrm tickets.
	- When tickets chose, system will represent the order with a total price to the visitor.
    - Visitor allowed to confirm or dismiss their orders.
	- After successful payment, administrator changing order status for paid.
    - On demand system administrator may change/restore clients password, name and last name.
	- Administrator allowed to block or restore after block visitors account.

Tech requirements:

    1. Apache Tomcat;

    2. Maven;

    3. MySQL Workbench;

    3.1. New database named "exhibition" (user "root", password "kA500asvB1QtaiSDXrxT")
    
    4. IntelijIDEA Ultimate 

How to run:

    1. Create a new project -> Java Enterprise -> Web Application
    
    2. Add framework support -> Maven
    
    3. Import SQL script to MySQL Workbench from src\resources -> Exhiitions DB.sql and execute
    
    4. Use git clone URL through IntelijIDEA tools or with terminal
    
    5. Set up a database connection with parameters from above and download proper JDBC driver, make  test connection
    
    6. Run panel -> Tomcat run option

