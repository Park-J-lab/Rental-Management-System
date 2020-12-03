# Rental-Management-System
A program to manage rentals in a complex. The manager could register a lease, add a payment, add an expense, view quarterly reports, etc. (JDBC and JavaFX are used in this project.)


# Operating Manual
Compile and run ‘src/main/Main.java’. Please use ‘85001’ as Manager ID, and ‘123’ as password for test. After logging in, you will see the main menu as below.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/1.jpg)

## Option 1: Register a new lease
The program will retrieve the IDs of the apartments that have not been leased yet from the APARTMENT table in the database.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/2.jpg)

After you select the apartment ID, the program will retrieve the rent and area of this apartment from the APARTMENT table in the database.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/3.jpg)

You can also select the start date of the lease.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/4.jpg)

After filling in all the blanks, you can click the ‘submit’ button. This new record will then be added to the LEASE table in the database.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/5.jpg)

## Option 2: Register a new payment

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/6.jpg)

The operation is similar to that in ‘Option 1’. After filling in all the blanks, you can click the ‘submit’ button. This new record will then be added to the PAYMENT table in the database.

## Option 3: Register an uncovered rent

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/7.jpg)

The operation is similar to that in ‘Option 1’. After filling in all the blanks, you can click the ‘submit’ button. This new record will then be added to the UNCOVERED_RENT table in the database.

## Option 4: Register an uncovered rent

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/8.jpg)

The operation is similar to that in ‘Option 1’. After filling in all the blanks, you can click the ‘submit’ button. This new record will then be added to the MAINTENANCE table in the database.

## Option 5: Add an expense

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/9.jpg)

The operation is similar to that in ‘Option 1’. After filling in all the blanks, you can click the ‘submit’ button. The corresponding record in the EXPENSE table will then be updated.

## Option 6: View/Print report

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/10.jpg)

Click the ‘View’ button after selecting the report type. Reports will be displayed as below.

### Lease Report

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/11.jpg)

### Payment Report

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/12.jpg)

### Maintenance Report

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/13.jpg)

### Quarterly Report

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/14.jpg)

You can click the ‘View Expenses Chart’ at the bottom to view the chart describing the expenses of this quarter.

### Expenses Chart

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/15.jpg)

### Save the report
You can save the reports by clicking the ‘Print’ button. The txt documents will then be saved in the path ‘src/report’.

The txt document looks beautiful in MacOS or Linux, while it looks a little bit disorderly in Windows. The following pictures show what the reports look like in Ubuntu.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/16.jpg)

### Databases
The database is at ‘src/database/Safehome_Student_Residences.db’, in which 8 tables are stored.

![image](https://github.com/Park-J-lab/Rental-Management-System/blob/master/src/images/17.jpg)
