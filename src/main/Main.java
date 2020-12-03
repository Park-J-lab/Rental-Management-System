package main;

import DAO.ReportDaoImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ReportUtils.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main extends Application {

    private String currentComplexID = ""; /* the complex ID corresponding to the current user, which is retrieved from
    the MANAGER table in the database according to the managerID and password that he or she inputs */
    private String currentMangerID = ""; // the manager ID input by the user
    private String currentPassword = ""; // the password input by the user
    private final Calendar date = Calendar.getInstance(); // get current date
    private DatePicker datePicker; // get date
    private final String datePattern = "yyyy-MM-dd"; // set date format
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern); // set date format
    private final ReportDaoImpl dao = new ReportDaoImpl(); // implement sql operations

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    // present the login page
    private void logIn(Stage stage){
        stage.setTitle("Login");

        // set the grid
        GridPane logInGrid = new GridPane();
        logInGrid.setAlignment(Pos.CENTER);
        logInGrid.setHgap(10);
        logInGrid.setVgap(10);
        logInGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene logInScene = new Scene(logInGrid, 800, 500);
        logInScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(logInScene);

        // set title
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 40));
        logInGrid.add(sceneTitle, 0, 0, 2, 1);

        // set the 'Manager ID' label
        Label userName = new Label("Manager ID:");
        userName.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        logInGrid.add(userName, 0, 2);
        TextField userTextField = new TextField();
        userTextField.setText(currentMangerID);
        logInGrid.add(userTextField, 1, 2);

        // set the 'Password' label
        Label pw = new Label("Password:");
        pw.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        logInGrid.add(pw, 0, 3);
        PasswordField pwBox = new PasswordField();
        pwBox.setText(currentPassword);
        logInGrid.add(pwBox, 1, 3);

        // Log in button
        Button logInBtn = new Button("Log in");
        HBox logInBox = new HBox();
        logInBox.setAlignment(Pos.BOTTOM_RIGHT);
        logInBox.getChildren().add(logInBtn);
        logInGrid.add(logInBox, 1, 5);

        // Quit the program
        Button quitBtn = new Button("Quit");
        HBox quitBox = new HBox();
        quitBox.setAlignment(Pos.BOTTOM_LEFT);
        quitBox.getChildren().add(quitBtn);
        logInGrid.add(quitBox, 0, 5);

        // Prompt to log in
        Text promptText = new Text( "For your convenience, please use '85001' as manager ID,\nand '123' as password.");
        promptText.setFill(Paint.valueOf("Gray"));
        logInGrid.add(promptText, 0, 10,2,1);

        // Error prompt
        Text errorText = new Text();
        logInGrid.add(errorText, 0, 8,4,1);

        // define the action of clicking the login button
        logInBtn.setOnAction(e -> {
            currentMangerID = userTextField.getText().trim();
            currentPassword = pwBox.getText().trim();
            // very the user status
            if (verifyStatus(currentMangerID, currentPassword)) {
                currentComplexID = dao.getComplexID(currentMangerID);
                stage.close();
                mainMenu(stage);
            } else {
                errorText.setFill(Color.FIREBRICK);
                errorText.setText("Invalid user or wrong password.");
            }
        });

        // define the action of clicking the quit button
        quitBtn.setOnAction(e -> {
            System.out.println("\nGoodbye!");
            stage.close();
        });

        stage.setWidth(800);
        stage.setHeight(500);
        stage.show();
    }

    // verify whether the manager ID and the password are valid
    public boolean verifyStatus(String managerID, String providedPwd) {
        return dao.verifyStatus(managerID, providedPwd);
    }

    // present the main menu page
    private void mainMenu(Stage stage) {
        stage.setTitle("Main Menu");

        // set the grid
        GridPane mainMenuGrid = new GridPane();
        mainMenuGrid.setAlignment(Pos.CENTER);
        mainMenuGrid.setHgap(10);
        mainMenuGrid.setVgap(20);
        mainMenuGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene mainMenuScene = new Scene(mainMenuGrid, 800, 500);
        mainMenuScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(mainMenuScene);

        // set title
        Text menuTitle = new Text("Main Menu");
        menuTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 40));
        mainMenuGrid.add(menuTitle, 0, 0, 6, 1);

        // set subtitle 'Lease'
        Text leaseTitle = new Text("Lease");
        leaseTitle.setFont(Font.font("Gabriola", FontWeight.LIGHT, 30));
        mainMenuGrid.add(leaseTitle, 0, 1, 2, 1);

        // set subtitle 'Rent'
        Text paymentTitle = new Text("Rent");
        paymentTitle.setFont(Font.font("Gabriola", FontWeight.NORMAL, 30));
        mainMenuGrid.add(paymentTitle, 4, 1, 2, 1);

        // set subtitle 'Maintenance'
        Text maintenanceTitle = new Text("Maintenance");
        maintenanceTitle.setFont(Font.font("Gabriola", FontWeight.NORMAL, 30));
        mainMenuGrid.add(maintenanceTitle, 8, 1, 1, 1);

        // set subtitle 'Expenses'
        Text expenseTitle = new Text("Expenses");
        expenseTitle.setFont(Font.font("Gabriola", FontWeight.NORMAL, 30));
        mainMenuGrid.add(expenseTitle, 0, 5, 2, 1);

        // set subtitle 'Report'
        Text reportTitle = new Text("Report");
        reportTitle.setFont(Font.font("Gabriola", FontWeight.NORMAL, 30));
        mainMenuGrid.add(reportTitle, 4, 5, 2, 1);

        // define buttons that corresponding to different operations
        Button addLease = new Button ("Register a new lease");
        Button addPayment = new Button("Register a new payment");
        Button addUncoveredRent = new Button("Register an uncovered rent");
        Button addMaintenanceRequest = new Button("Register a new request");
        Button addExpense = new Button("Add an expense");
        Button showReport = new Button("View/Print Report");

        // add the buttons to the grid
        mainMenuGrid.add(addLease, 0, 2);
        mainMenuGrid.add(addPayment, 4, 2);
        mainMenuGrid.add(addUncoveredRent, 4, 3);
        mainMenuGrid.add(addMaintenanceRequest, 8, 2,1,1);
        mainMenuGrid.add(addExpense, 0, 6);
        mainMenuGrid.add(showReport, 4, 6);

        // Help doc
        Button helpBtn = new Button("Help");
        // Back to login page
        Button backBtn = new Button("Back");
        // Quit the program
        Button quitBtn = new Button("Quit");
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(helpBtn, backBtn, quitBtn);
        mainMenuGrid.add(hBox, 8, 6);

        // define the action when clicking the 'register a new lease' button
        addLease.setOnAction(e -> {
            stage.close();
            addLease(stage);
        });

        // define the action when clicking the 'register a new payment' button
        addPayment.setOnAction(e -> {
            stage.close();
            addPayment(stage);
        });

        // define the action when clicking the 'register an uncovered rent' button
        addUncoveredRent.setOnAction(e -> {
            stage.close();
            addUncoveredRent(stage);
        });

        // define the action when clicking the 'register a new request' button
        addMaintenanceRequest.setOnAction(e -> {
            stage.close();
            addMaintenance(stage);
        });

        // define the action when clicking the 'add an expense' button
        addExpense.setOnAction(e -> {
            stage.close();
            addExpense(stage);
        });

        // define the action when clicking the 'show/print report' button
        showReport.setOnAction(e -> {
            stage.close();
            reportMenu(stage);
        });

        // define the action when clicking the 'help' button
        helpBtn.setOnAction(e -> {
            confirm(stage, "Please refer to README.pdf.", true);
        });

        // define the action when clicking the 'back' button
        backBtn.setOnAction(e -> {
            stage.close();
            logIn(stage);
        });

        // define the action when clicking the 'quit' button
        quitBtn.setOnAction(e -> {
            System.out.println("\nGoodbye!");
            stage.close();
        });

        stage.show();
    }

    // present the Lease Registration page
    private void addLease(Stage stage) {
        stage.setTitle("Lease Registration");

        // set the grid
        GridPane leaseGrid = new GridPane();
        leaseGrid.setAlignment(Pos.CENTER);
        leaseGrid.setHgap(10);
        leaseGrid.setVgap(10);
        leaseGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene leaseScene = new Scene(leaseGrid, 800, 500);
        leaseScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(leaseScene);

        // set title
        Text menuTitle = new Text("Lease Registration");
        menuTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        leaseGrid.add(menuTitle, 0, 0, 6, 1);

        // Show complex ID, this ID is retrieved from the MANAGER table in the database, according to the user's ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        leaseGrid.add(complexIDText, 2, 3, 1, 1);

        // Show available room id list
        Label aptIDLabel = new Label("Available Room:");
        aptIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(aptIDLabel, 0, 4, 2, 1);

        /* The list is retrieved from the APARTMENT table in the database,
        only those which have not been leased will be displayed in the list. */
        List<String> aptIDs = dao.getAvailableApt(currentComplexID);
        ObservableList<String> aptObList = FXCollections.observableArrayList(aptIDs);
        ComboBox<String> aptIDBox = new ComboBox<>(aptObList);
        leaseGrid.add(aptIDBox, 2, 4);
        Label aptIDPrompt = new Label("Please select apartment ID.");
        aptIDPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        leaseGrid.add(aptIDPrompt, 4, 4, 5,1);

        // get the apartment ID selected by the user
        Text aptIDText = new Text();

        // Show rent amount
        Label rentLabel = new Label("Rent Amount:");
        rentLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(rentLabel, 0, 5, 2, 1);
        Text rentText = new Text("         -");
        leaseGrid.add(rentText, 2, 5, 2, 1);

        // Show total area
        Label areaLabel = new Label("Total Area:");
        areaLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(areaLabel, 0, 6, 2, 1);
        Text areaText = new Text("         -");
        leaseGrid.add(areaText, 2, 6, 2, 1);

        // Select start date
        Label dateLabel = new Label("Start Date:");
        dateLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(dateLabel, 0, 7, 2, 1);

        // get the date selected by the user
        Text dateText = new Text();

        // define the action when clicking on the date
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    dateText.setText(dateFormatter.format(date));
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker = new DatePicker();
        datePicker.setConverter(converter);
        datePicker.setPromptText("                     click here ->");
        leaseGrid.add(datePicker, 2, 7, 2, 1);

        // select lease period
        Label periodLabel = new Label("Lease Period:");
        periodLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(periodLabel, 0, 8, 2, 1);
        List<String> periods = Arrays.asList("6 month", "12 month");
        ObservableList<String> periodList = FXCollections.observableArrayList(periods);
        ComboBox<String> periodBox = new ComboBox<>(periodList);
        leaseGrid.add(periodBox, 2, 8);
        Label periodPrompt = new Label("Please select lease period.");
        periodPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        leaseGrid.add(periodPrompt, 4, 8, 5,1);
        Text periodText = new Text();

        // Input lessee name
        Label nameLabel = new Label("Lessee Name:");
        nameLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(nameLabel, 0, 9, 2, 1);
        TextField nameField = new TextField();
        leaseGrid.add(nameField, 2, 9, 2, 1);

        // Input deposit
        Label depositLabel = new Label("Deposit:");
        depositLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        leaseGrid.add(depositLabel, 0, 10, 2, 1);
        TextField depositField = new TextField();
        leaseGrid.add(depositField, 2, 10, 2, 1);

        // Back to main menu
        Button cancelBtn = new Button("Back");
        leaseGrid.add(cancelBtn, 0, 14);

        // Submit the request
        Button submitBtn = new Button("Submit");
        leaseGrid.add(submitBtn, 3, 14);

        // Error Prompt
        Text submitPrompt = new Text();
        submitPrompt.setFill(Paint.valueOf("FIREBRICK"));
        leaseGrid.add(submitPrompt, 0, 16, 3, 1);

        /* After the user selected the apartment ID, the program will automatically retrieve
        the information such as rent, area of this particular apartment from the APARTMENT table in
        the database and display them to the user. */
        aptIDBox.valueProperty().addListener((ov, t, t1) -> {
            aptIDPrompt.setText("");
            aptIDText.setText(t1);
            rentText.setText("$" + dao.getAptRent(currentComplexID, t1) + " / month");
            areaText.setText(dao.getAptArea(currentComplexID, t1) + " square meters");
        });

        // define the action after selecting the lease period
        periodBox.valueProperty().addListener((ov, t, t1) -> {
            periodPrompt.setText("");
            periodText.setText(t1);
        });

        /* Define the action when clicking the submit button. After the user fill an all blanks and
        click the 'submit' button, a new lease record will be added to the LEASE table in the database. */
        submitBtn.setOnAction(e -> {
            if (aptIDText.getText().equals("") ||
                    periodText.getText().equals("") ||
                    dateText.getText().equals("") ||
                    nameField.getText().trim().equals("") ||
                    depositField.getText().trim().equals("")) {
                submitPrompt.setText("Please fill in all the blanks."); // must fill in all blanks before submitting
            } else {
                submitPrompt.setText("Submitting...");
                submitPrompt.setFill(Paint.valueOf("Green"));
                // add a lease record to the LEASE table in the database
                LeaseDetail detail = new LeaseDetail(currentComplexID,
                        aptIDText.getText(),
                        nameField.getText().trim(),
                        dateText.getText(),
                        periodText.getText(),
                        dao.getAptRent(currentComplexID, aptIDText.getText()),
                        Double.parseDouble(depositField.getText().trim()));
                dao.addLease(detail);
                dao.setLeasedStatusToYes(currentComplexID,aptIDText.getText());
                confirm(stage, "Successfully registered!", true);
            }
        });

        // define the action when clicking the cancel button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // present the Lease Registration page
    private void addPayment(Stage stage) {
        stage.setTitle("Payment Registration");

        // set the grid
        GridPane paymentGrid = new GridPane();
        paymentGrid.setAlignment(Pos.CENTER);
        paymentGrid.setHgap(10);
        paymentGrid.setVgap(10);
        paymentGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene paymentScene = new Scene(paymentGrid, 800, 500);
        paymentScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(paymentScene);

        // set title
        Text menuTitle = new Text("Payment Registration");
        menuTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        paymentGrid.add(menuTitle, 0, 0, 5, 1);

        // show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        paymentGrid.add(complexIDText, 2, 3, 1, 1);

        // select apartment ID list
        Label aptIDLabel = new Label("Apartment ID:");
        aptIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(aptIDLabel, 0, 4, 2, 1);
        // the list is retrieved from the LEASE table in the database
        List<String> aptIDList = dao.getLeasedAptIDs(currentComplexID);
        ObservableList<String> aptObList = FXCollections.observableArrayList(aptIDList);
        ComboBox<String> aptIDBox = new ComboBox<>(aptObList);
        paymentGrid.add(aptIDBox, 2, 4);
        Label aptIDPrompt = new Label("Please select apartment ID.");
        aptIDPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        paymentGrid.add(aptIDPrompt, 5, 4, 5,1);
        Text aptIDText = new Text();

        // select lease ID
        Label leaseLabel = new Label("Lease ID:");
        leaseLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(leaseLabel, 0, 5, 2, 1);
        // the lease ID will be displayed to the user once he or she selecting the apartment ID
        List<String> leaseIDs = new ArrayList<>();
        ObservableList<String> leaseObList = FXCollections.observableArrayList(leaseIDs);
        ComboBox<String> leaseIDBox = new ComboBox<>(leaseObList);
        paymentGrid.add(leaseIDBox, 2, 5);
        Label leaseIDPrompt = new Label("Please select lease ID.");
        leaseIDPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        paymentGrid.add(leaseIDPrompt, 5, 5, 5,1);
        Text leaseIDText = new Text();

        // select whether it is a late payment or not
        Label lateOrNotLabel = new Label("Late Or Not:");
        lateOrNotLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(lateOrNotLabel, 0, 6, 2, 1);
        List<String> judges = Arrays.asList("Yes", "No");
        ObservableList<String> judgeList = FXCollections.observableArrayList(judges);
        ComboBox<String> judgeBox = new ComboBox<>(judgeList);
        paymentGrid.add(judgeBox, 2, 6);
        Label judgePrompt = new Label("Please specify whether it is a late payment.");
        judgePrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        paymentGrid.add(judgePrompt, 5, 6, 5,1);
        Text judgeText = new Text();

        // input lessee name
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(nameLabel, 0, 7, 2, 1);
        TextField nameField = new TextField();
        paymentGrid.add(nameField, 2, 7, 3, 1);

        // input the payment amount
        Label amountLabel = new Label("Amount Paid:");
        amountLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        paymentGrid.add(amountLabel, 0, 8, 2, 1);
        TextField amountField = new TextField();
        paymentGrid.add(amountField, 2, 8, 3, 1);

        // Back to main menu
        Button cancelBtn = new Button("Back");
        paymentGrid.add(cancelBtn, 0, 13);

        // Submit the request
        Button submitBtn = new Button("Submit");
        paymentGrid.add(submitBtn, 4, 13);

        // Error Prompt
        Text submitPrompt = new Text();
        submitPrompt.setFill(Paint.valueOf("FIREBRICK"));
        paymentGrid.add(submitPrompt, 0, 15, 3, 1);

        /* Once the user selects the apartment ID, the program will automatically retrieve the
        lease ID corresponding to this apartment from the LEASE table in the database, and
        display it to the user. */
        aptIDBox.valueProperty().addListener((ov, t, t1) -> {
            aptIDPrompt.setText("");
            aptIDText.setText(t1);
            leaseIDBox.getItems().add(dao.getLeaseIDForPayment(currentComplexID, t1));
        });

        // define the action when selecting lease ID
        leaseIDBox.valueProperty().addListener((ov, t, t1) -> {
            leaseIDPrompt.setText("");
            leaseIDText.setText(t1);
        });

        // define the action when selecting whether it is a late payment
        judgeBox.valueProperty().addListener((ov, t, t1) -> {
            judgePrompt.setText("");
            judgeText.setText(t1);
        });

        /* Once the user fills in all blanks and click the submit button, a new payment record
        will be stored in the PAYMENT table in the database. */
        submitBtn.setOnAction(e -> {
            if (leaseIDText.getText().equals("") ||
                    judgeText.getText().equals("") ||
                    nameField.getText().trim().equals("") ||
                    amountField.getText().trim().equals("")) {
                submitPrompt.setText("Please fill in all the blanks.");
            } else {
                submitPrompt.setText("Submitting...");
                submitPrompt.setFill(Paint.valueOf("Green"));
                // add a record to the PAYMENT table
                PaymentDetail detail = new PaymentDetail(currentComplexID,
                        aptIDText.getText(),
                        leaseIDText.getText(),
                        nameField.getText().trim(),
                        Double.parseDouble(amountField.getText().trim()),
                        judgeText.getText());
                dao.addPayment(detail);
                confirm(stage, "Successfully registered!", true);
            }

        });

        // define the action when clicking the cancel button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // present the Uncovered Rent Registration page
    private void addUncoveredRent(Stage stage) {
        stage.setTitle("Uncovered Rent Registration");

        // set the grid
        GridPane uncoveredRentGrid = new GridPane();
        uncoveredRentGrid.setAlignment(Pos.CENTER);
        uncoveredRentGrid.setHgap(10);
        uncoveredRentGrid.setVgap(10);
        uncoveredRentGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene uncoveredRentScene = new Scene(uncoveredRentGrid, 800, 500);
        uncoveredRentScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(uncoveredRentScene);

        // set the title
        Text uncoveredRentTitle = new Text("Uncovered Rent Registration");
        uncoveredRentTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        uncoveredRentGrid.add(uncoveredRentTitle, 0, 0, 6, 1);

        // Show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        uncoveredRentGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        uncoveredRentGrid.add(complexIDText, 2, 3, 1, 1);

        // Select apartment ID
        Label aptIDLabel = new Label("Apartment ID:");
        aptIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        uncoveredRentGrid.add(aptIDLabel, 0, 4, 2, 1);
        // the ID list is retrieved from the LEASE table in the database
        List<String> aptIDs = dao.getLeasedAptIDs(currentComplexID);
        ObservableList<String> aptObList = FXCollections.observableArrayList(aptIDs);
        ComboBox<String> aptIDBox = new ComboBox<>(aptObList);
        uncoveredRentGrid.add(aptIDBox, 2, 4);
        Label aptIDPrompt = new Label("Please select apartment ID.");
        aptIDPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        uncoveredRentGrid.add(aptIDPrompt, 4, 4, 5,1);
        Text aptIDText = new Text();

        // Select lease quarter
        Label quarterLabel = new Label("Quarter:");
        quarterLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        uncoveredRentGrid.add(quarterLabel, 0, 5, 2, 1);
        List<String> quarters = Arrays.asList("Spring", "Summer", "Fall", "Winter");
        ObservableList<String> quarterList = FXCollections.observableArrayList(quarters);
        ComboBox<String> quarterBox = new ComboBox<>(quarterList);
        uncoveredRentGrid.add(quarterBox, 2, 5);
        Label quarterPrompt = new Label("Please select quarter.");
        quarterPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        uncoveredRentGrid.add(quarterPrompt, 4, 5, 5,1);
        Text quarterText = new Text();

        // Input expense
        Label expenseLabel = new Label("Amount:");
        expenseLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        uncoveredRentGrid.add(expenseLabel, 0, 6, 2, 1);
        TextField expenseField = new TextField();
        uncoveredRentGrid.add(expenseField, 2, 6, 2, 1);

        // Back to main menu
        Button cancelBtn = new Button("Back");
        uncoveredRentGrid.add(cancelBtn, 0, 11);

        // Submit the request
        Button submitBtn = new Button("Submit");
        uncoveredRentGrid.add(submitBtn, 3, 11);

        // Error Prompt
        Text submitPrompt = new Text();
        submitPrompt.setFill(Paint.valueOf("FIREBRICK"));
        uncoveredRentGrid.add(submitPrompt, 0, 13, 3, 1);

        // define the action after selecting the apartment ID
        aptIDBox.valueProperty().addListener((ov, t, t1) -> {
            aptIDPrompt.setText("");
            aptIDText.setText(t1);
        });

        // define the action after selecting the quarter
        quarterBox.valueProperty().addListener((ov, t, t1) -> {
            quarterPrompt.setText("");
            quarterText.setText(t1);
        });

        /* After the user fills in all blanks and click the submit button, a new uncovered
        rent will be added to the UNCOVERED_RENT table in the database. */
        submitBtn.setOnAction(e -> {
            if (aptIDText.getText().equals("") ||
                    quarterText.getText().equals("") ||
                    expenseField.getText().trim().equals("")) {
                submitPrompt.setText("Please fill in all the blanks.");
            } else {
                submitPrompt.setText("Submitting...");
                submitPrompt.setFill(Paint.valueOf("Green"));
                // add a new record to the UNCOVERED_RENT table in the database
                UncoveredRentDetail detail = new UncoveredRentDetail(currentComplexID,
                        aptIDText.getText(),
                        String.valueOf(date.get(Calendar.YEAR)),
                        quarterText.getText(),
                        Double.parseDouble(expenseField.getText().trim()));
                dao.addUncoveredRent(detail);
                confirm(stage, "Successfully registered!", true);
            }

        });

        // define the action when clicking the cancel button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // present the Maintenance Registration page
    private void addMaintenance(Stage stage) {
        stage.setTitle("Maintenance Registration");

        // set the grid
        GridPane maintenanceGrid = new GridPane();
        maintenanceGrid.setAlignment(Pos.CENTER);
        maintenanceGrid.setHgap(10);
        maintenanceGrid.setVgap(10);
        maintenanceGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene maintenanceScene = new Scene(maintenanceGrid, 800, 500);
        maintenanceScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(maintenanceScene);

        // set the title
        Text maintenanceTitle = new Text("Maintenance Registration");
        maintenanceTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        maintenanceGrid.add(maintenanceTitle, 0, 0, 6, 1);

        // Show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        maintenanceGrid.add(complexIDText, 2, 3, 1, 1);

        // Select apartment ID
        Label aptIDLabel = new Label("Apartment ID:");
        aptIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(aptIDLabel, 0, 4, 2, 1);
        // The id list is retrieved from the LEASE table in the database.
        List<String> aptIDs = dao.getLeasedAptIDs(currentComplexID);
        ObservableList<String> aptObList = FXCollections.observableArrayList(aptIDs);
        ComboBox<String> aptIDBox = new ComboBox<>(aptObList);
        maintenanceGrid.add(aptIDBox, 2, 4);
        Label aptIDPrompt = new Label("Please select apartment ID.");
        aptIDPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        maintenanceGrid.add(aptIDPrompt, 4, 4, 5,1);
        Text aptIDText = new Text();

        // Select problem type
        Label typeLabel = new Label("Type:");
        typeLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(typeLabel, 0, 5, 2, 1);
        List<String> types = Arrays.asList("Electrical", "Floor", "Plumbing", "Walls", "Heating", "Gas");
        ObservableList<String> typeList = FXCollections.observableArrayList(types);
        ComboBox<String> typeBox = new ComboBox<>(typeList);
        maintenanceGrid.add(typeBox, 2, 5);
        Label typePrompt = new Label("Please select problem type.");
        typePrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        maintenanceGrid.add(typePrompt, 4, 5, 5,1);
        Text typeText = new Text();

        // Select date of occurrence
        Label occurDateLabel = new Label("Date Of Occurrence:");
        occurDateLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(occurDateLabel, 0, 6, 2, 1);
        Text occurDateText = new Text();

        // define the action after selecting the date
        StringConverter<LocalDate> occurConverter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    occurDateText.setText(dateFormatter.format(date));
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker = new DatePicker();
        datePicker.setConverter(occurConverter);
        datePicker.setPromptText("                     click here ->");
        maintenanceGrid.add(datePicker, 2, 6, 2, 1);

        // Select date of resolution
        Label resDateLabel = new Label("Date Of Resolution:");
        resDateLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(resDateLabel, 0, 7, 2, 1);
        Text resDateText = new Text();

        // define the action after selecting the date
        StringConverter<LocalDate> resConverter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    resDateText.setText(dateFormatter.format(date));
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        DatePicker resDatePicker = new DatePicker();
        resDatePicker.setConverter(resConverter);
        resDatePicker.setPromptText("                     click here ->");
        maintenanceGrid.add(resDatePicker, 2, 7, 2, 1);

        // Input problem description
        Label problemLabel = new Label("Problem Description:");
        problemLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(problemLabel, 0, 8, 2, 1);
        TextField problemField = new TextField();
        maintenanceGrid.add(problemField, 2, 8, 2, 1);

        // Input resolution
        Label resolutionLabel = new Label("Resolution:");
        resolutionLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(resolutionLabel, 0, 9, 2, 1);
        TextField resolutionField = new TextField();
        maintenanceGrid.add(resolutionField, 2, 9, 2, 1);

        // Input expense
        Label expenseLabel = new Label("Expense:");
        expenseLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        maintenanceGrid.add(expenseLabel, 0, 10, 2, 1);
        TextField expenseField = new TextField();
        maintenanceGrid.add(expenseField, 2, 10, 2, 1);

        // Back to main menu
        Button cancelBtn = new Button("Back");
        maintenanceGrid.add(cancelBtn, 0, 13);

        // Submit the request
        Button submitBtn = new Button("Submit");
        maintenanceGrid.add(submitBtn, 3, 13);

        // Error Prompt
        Text submitPrompt = new Text();
        submitPrompt.setFill(Paint.valueOf("FIREBRICK"));
        maintenanceGrid.add(submitPrompt, 0, 15, 3, 1);

        // define the action after selecting the apartment ID
        aptIDBox.valueProperty().addListener((ov, t, t1) -> {
            aptIDPrompt.setText("");
            aptIDText.setText(t1);
        });

        // define the action after selecting the problem type
        typeBox.valueProperty().addListener((ov, t, t1) -> {
            typePrompt.setText("");
            typeText.setText(t1);
        });

        /* Once the user fills in all blanks and clicks the submit button, a new maintenance
        record will be added to the MAINTENANCE table in the database. */
        submitBtn.setOnAction(e -> {
            if (aptIDText.getText().equals("") ||
                    typeText.getText().equals("") ||
                    occurDateText.getText().equals("") ||
                    resDateText.getText().equals("") ||
                    problemField.getText().trim().equals("") ||
                    resolutionField.getText().trim().equals("") ||
                    expenseField.getText().trim().equals("")) {
                submitPrompt.setText("Please fill in all the blanks.");
            } else {
                submitPrompt.setText("Submitting...");
                submitPrompt.setFill(Paint.valueOf("Green"));
                // add a new maintenance record to the MAINTENANCE table
                MaintenanceDetail detail = new MaintenanceDetail(currentComplexID,
                        aptIDText.getText(),
                        occurDateText.getText(),
                        resDateText.getText(),
                        problemField.getText().trim(),
                        typeText.getText(),
                        resolutionField.getText().trim(),
                        Double.parseDouble(expenseField.getText().trim()));
                dao.addMaintenance(detail);
                confirm(stage, "Successfully registered!", true);
            }

        });

        // define the action when clicking the back button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // present the Expense Registration page
    private void addExpense(Stage stage) {
        stage.setTitle("Expense Registration");

        // set the grid
        GridPane expenseGrid = new GridPane();
        expenseGrid.setAlignment(Pos.CENTER);
        expenseGrid.setHgap(10);
        expenseGrid.setVgap(10);
        expenseGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene expenseScene = new Scene(expenseGrid, 800, 500);
        expenseScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(expenseScene);

        // set the title
        Text uncoveredRentTitle = new Text("Expense Registration");
        uncoveredRentTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        expenseGrid.add(uncoveredRentTitle, 0, 0, 6, 1);

        // Show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        expenseGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        expenseGrid.add(complexIDText, 2, 3, 1, 1);

        // Select expense type
        Label typeLabel = new Label("Type:");
        typeLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        expenseGrid.add(typeLabel, 0, 4, 2, 1);
        List<String> types = Arrays.asList("Utilities", "Repairs", "Insurance", "New tenant cleaning");
        ObservableList<String> typeList = FXCollections.observableArrayList(types);
        ComboBox<String> typeBox = new ComboBox<>(typeList);
        expenseGrid.add(typeBox, 2, 4);
        Label typePrompt = new Label("Please select type.");
        typePrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        expenseGrid.add(typePrompt, 4, 4, 5,1);
        Text typeText = new Text();

        // Select quarter
        Label quarterLabel = new Label("Quarter:");
        quarterLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        expenseGrid.add(quarterLabel, 0, 5, 2, 1);
        List<String> quarters = Arrays.asList("Spring", "Summer", "Fall", "Winter");
        ObservableList<String> quarterList = FXCollections.observableArrayList(quarters);
        ComboBox<String> quarterBox = new ComboBox<>(quarterList);
        expenseGrid.add(quarterBox, 2, 5);
        Label quarterPrompt = new Label("Please select quarter.");
        quarterPrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        expenseGrid.add(quarterPrompt, 4, 5, 5,1);
        Text quarterText = new Text();

        // Input expense
        Label expenseLabel = new Label("Expense:");
        expenseLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        expenseGrid.add(expenseLabel, 0, 6, 2, 1);
        TextField expenseField = new TextField();
        expenseGrid.add(expenseField, 2, 6, 2, 1);

        // Back to main menu
        Button cancelBtn = new Button("Back");
        expenseGrid.add(cancelBtn, 0, 11);

        // Submit the request
        Button submitBtn = new Button("Submit");
        expenseGrid.add(submitBtn, 3, 11);

        // Error Prompt
        Text submitPrompt = new Text();
        submitPrompt.setFill(Paint.valueOf("FIREBRICK"));
        expenseGrid.add(submitPrompt, 0, 13, 3, 1);

        // define the action after selecting the expense type
        typeBox.valueProperty().addListener((ov, t, t1) -> {
            typePrompt.setText("");
            typeText.setText(t1);
        });

        // define the action after selecting the quarter
        quarterBox.valueProperty().addListener((ov, t, t1) -> {
            quarterPrompt.setText("");
            quarterText.setText(t1);
        });

        /* Once the user fills in all blanks and clicks the submit button, the expense in the EXPENSE
        table in the database will be updated. */
        submitBtn.setOnAction(e -> {
            if (typeText.getText().equals("") ||
                    quarterText.getText().equals("") ||
                    expenseField.getText().trim().equals("")) {
                submitPrompt.setText("Please fill in all the blanks.");
            } else {
                submitPrompt.setText("Submitting...");
                submitPrompt.setFill(Paint.valueOf("Green"));
                // update the EXPENSE table
                ExpenseDetail detail = new ExpenseDetail(currentComplexID,
                        typeText.getText(),
                        String.valueOf(date.get(Calendar.YEAR)),
                        quarterText.getText(),
                        Double.parseDouble(expenseField.getText().trim()));
                dao.updateExpense(detail);
                confirm(stage, "Successfully registered!", true);
            }

        });

        // define the action when clicking the back button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // return the current quarter
    private String getQuarter(Calendar date) {
        int month = date.get(Calendar.MONTH) + 1;
        if (month <= 3) { return "Spring"; }
        if (month <= 6) { return "Summer"; }
        if (month <= 9) { return "Fall"; }
        return "Winter";
    }

    // return the start date and the end date of the current quarter
    private String[] getDates(String year) {
        String[] dates = new String[2];
        int month = date.get(Calendar.MONTH) + 1;
        if (month <= 3) {
            dates[0] = year + "-01-01";
            dates[1] = year + "-03-31";
        } else if (month <= 6) {
            dates[0] = year + "-04-01";
            dates[1] = year + "-06-30";
        } else if (month <= 9) {
            dates[0] = year + "-07-01";
            dates[1] = year + "-09-30";
        } else {
            dates[0] = year + "-10-01";
            dates[1] = year + "-12-31";
        }
        return dates;
    }

    // present the report menu page
    private void reportMenu(Stage stage) {
        stage.setTitle("View/Print Report");

        // set the grid
        GridPane reportGrid = new GridPane();
        reportGrid.setAlignment(Pos.CENTER);
        reportGrid.setHgap(10);
        reportGrid.setVgap(10);
        reportGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene reportScene = new Scene(reportGrid, 800, 500);
        reportScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(reportScene);

        // set the title
        Text reportTitle = new Text("View/Print Report");
        reportTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 35));
        reportGrid.add(reportTitle, 0, 0, 5, 1);

        // Show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        reportGrid.add(complexIDLabel, 0, 3, 2, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        reportGrid.add(complexIDText, 2, 3, 1, 1);

        // Show year
        Label yearLabel = new Label("Year:");
        yearLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        reportGrid.add(yearLabel, 0, 4, 2, 1);
        String year = String.valueOf(date.get(Calendar.YEAR));
        Text yearText = new Text(year);
        yearText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        reportGrid.add(yearText, 2, 4, 3, 1);

        // Show quarter
        Label quarterLabel = new Label("Quarter:");
        quarterLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        reportGrid.add(quarterLabel, 0, 5, 2, 1);
        Text quarterText = new Text(getQuarter(date));
        quarterText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        reportGrid.add(quarterText, 2, 5, 3, 1);

        // Select report type
        Label typeLabel = new Label("Type:");
        typeLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        reportGrid.add(typeLabel, 0, 6, 2, 1);
        List<String> types = Arrays.asList("Lease Report", "Payment Report",
                "Maintenance Report", "Quarterly Report");
        ObservableList<String> typeObList = FXCollections.observableArrayList(types);
        ComboBox<String> typeBox = new ComboBox<>(typeObList);
        reportGrid.add(typeBox, 2, 6,6,1);
        Label typePrompt = new Label("Please select report type.");
        typePrompt.setTextFill(Paint.valueOf("FIREBRICK"));
        reportGrid.add(typePrompt, 8, 6, 1,1);
        Text typeText = new Text();

        // Back to main menu
        Button cancelBtn = new Button("Back");
        reportGrid.add(cancelBtn, 0, 11);

        // Show Report
        Button showBtn = new Button("View");
        reportGrid.add(showBtn, 6, 11);

        // Print Report
        Button printBtn = new Button("Print");
        reportGrid.add(printBtn, 7, 11);

        // define the action after selecting the report type
        typeBox.valueProperty().addListener((ov, t, t1) -> {
            typePrompt.setText("");
            typeText.setText(t1);
        });

        // show different reports according the report type chosen by the user
        showBtn.setOnAction(e -> {
            String type = typeText.getText();
            String[] dates = getDates(String.valueOf(date.get(Calendar.YEAR)));
            switch (type) {
                case "Lease Report":
                    stage.close();
                    showLeaseReport(stage, yearText.getText(), quarterText.getText(), dates);
                    break;
                case "Payment Report":
                    stage.close();
                    showPaymentReport(stage, yearText.getText(), quarterText.getText(), dates);
                    break;
                case "Maintenance Report":
                    stage.close();
                    showMaintenanceReport(stage, yearText.getText(), quarterText.getText(), dates);
                    break;
                case "Quarterly Report":
                    stage.close();
                    showQuarterlyReport(stage, yearText.getText(), quarterText.getText(), dates);
                    break;
            }
        });

        // save different reports at 'src/report' according the report type chosen by the user
        printBtn.setOnAction(e -> {
            String type = typeText.getText();
            String[] dates = getDates(String.valueOf(date.get(Calendar.YEAR)));
            switch (type) {
                case "Lease Report":
                    printLeaseReport(dao.getLeaseReportDetail(currentComplexID, dates),
                            year, quarterText.getText());
                    confirm(stage, "Saved in 'src/report/LeaseReport_" + year + "_" +
                            quarterText.getText() + ".txt' !", false);
                    break;
                case "Payment Report":
                    printPaymentReport(dao.getPaymentReportDetail(currentComplexID, dates),
                            year, quarterText.getText());
                    confirm(stage, "Saved in 'src/report/PaymentReport_" + year + "_" +
                            quarterText.getText() + ".txt' !", false);
                    break;
                case "Maintenance Report":
                    printMaintenanceReport(dao.getMaintenanceReportDetail(currentComplexID, dates),
                            year, quarterText.getText());
                    confirm(stage, "Saved in 'src/report/MaintenanceReport_" + year + "_" +
                            quarterText.getText() + ".txt' !", false);
                    break;
                case "Quarterly Report":
                    printQuarterlyReport(new QuarterlyReport(currentComplexID, quarterText.getText(), dates),
                            year, quarterText.getText());
                    confirm(stage, "Saved in 'src/report/QuarterlyReport_" + year + "_" +
                            quarterText.getText() + ".txt' !", false);
                    break;
            }
        });

        // define the action when clicking the back button
        cancelBtn.setOnAction(e -> {
            stage.close();
            mainMenu(stage);
        });

        stage.show();
    }

    // display the lease report
    private void showLeaseReport(Stage stage, String year, String quarter, String[] dates) {
        stage.setTitle("Lease Report From " + dates[0] + " To " + dates[1]);

        // create a table
        TableView<LeaseReportDetail> table = new TableView<>();
        table.setEditable(false);
        // retrieve the lease records from the LEASE table in the database
        List<LeaseReportDetail> detailList = dao.getLeaseReportDetail(currentComplexID, dates);
        ObservableList<LeaseReportDetail> data = FXCollections.observableArrayList(detailList);

        // set the column 'Lease ID'
        TableColumn leaseIDCol = new TableColumn("Lease ID");
        leaseIDCol.setMinWidth(140);
        leaseIDCol.setCellValueFactory(new PropertyValueFactory<>("leaseID"));

        // set the column 'Complex ID'
        TableColumn complexIDCol = new TableColumn("Complex ID");
        complexIDCol.setMinWidth(60);
        complexIDCol.setCellValueFactory(new PropertyValueFactory<>("complexID"));

        // set the column 'Apartment ID'
        TableColumn aptIDCol = new TableColumn("Apartment ID");
        aptIDCol.setMinWidth(70);
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("aptID"));

        // set the column 'Lessee Name'
        TableColumn lesseeNameCol = new TableColumn("Lessee Name");
        lesseeNameCol.setMinWidth(120);
        lesseeNameCol.setCellValueFactory(new PropertyValueFactory<>("lesseeName"));

        // set the column 'Start Date'
        TableColumn startDateCol = new TableColumn("Start Date");
        startDateCol.setMinWidth(60);
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        // set the column 'End Date'
        TableColumn endDateCol = new TableColumn("End Date");
        endDateCol.setMinWidth(60);
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        // set the column 'Rent'
        TableColumn rentCol = new TableColumn("Rent($)");
        rentCol.setMinWidth(40);
        rentCol.setCellValueFactory(new PropertyValueFactory<>("rent"));

        // set the column 'Deposit'
        TableColumn depositCol = new TableColumn("Deposit($)");
        depositCol.setMinWidth(45);
        depositCol.setCellValueFactory(new PropertyValueFactory<>("deposit"));

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(leaseIDCol, complexIDCol, aptIDCol, lesseeNameCol,
                startDateCol, endDateCol, rentCol, depositCol);

        Button cancelBtn = new Button("Back");
        Button printBtn = new Button("Print");

        // back to report menu
        cancelBtn.setOnAction(e -> {
            stage.close();
            reportMenu(stage);
        });

        // save the report in 'src/report'
        printBtn.setOnAction(e -> {
            printLeaseReport(detailList, year, quarter);
            confirm(stage, "Saved in 'src/report/LeaseReport_" + year + "_" + quarter + ".txt' !",
                    false);
        });

        // add the buttons and table to the pane
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.getChildren().addAll(cancelBtn, printBtn);
        BorderPane pane = new BorderPane();
        pane.setCenter(table);
        pane.setBottom(hBox);

        Scene leaseReportScene = new Scene(pane, 800, 600);
        leaseReportScene.getStylesheets().addAll(this.getClass().getResource("reportStyle.css").toExternalForm());
        stage.setScene(leaseReportScene);
        stage.show();
    }

    // save the lease report in 'src/report'
    private void printLeaseReport(List<LeaseReportDetail> detailList, String year, String quarter) {
        File outputFile = new File("src/report/LeaseReport_" + year + "_" + quarter + ".txt");

        // create a new file
        try {
            outputFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // write the content of the report to the txt document
        try (FileWriter fw = new FileWriter (outputFile);
             BufferedWriter output = new BufferedWriter(fw)) {

            output.write(String.format("%-25s", "Lease ID") +
                    String.format("%-12s","Complex ID") +
                    String.format("%-8s", "Apt ID") +
                    String.format("%-20s", "Lessee Name") +
                    String.format("%-15s", "Start Date") +
                    String.format("%-15s", "End Date") +
                    String.format("%-10s", "Rent($)") +
                    String.format("%-10s", "Deposit($)"));
            output.write("\n");
            for (LeaseReportDetail detail : detailList) {
                output.write("\n");
                output.write(detail.toString());
            }

        } catch (FileNotFoundException f) {
            System.out.println ("File not found: " + f);
        } catch (IOException e) {
            System.out.println ("IOException: " + e);
        }

    }

    // display the payment report
    private void showPaymentReport(Stage stage, String year, String quarter, String[] dates)  {
        stage.setTitle("Payment Report From " + dates[0] + " To " + dates[1]);

        // set the table
        TableView<PaymentReportDetail> table = new TableView<>();
        table.setEditable(false);
        List<PaymentReportDetail> detailList = dao.getPaymentReportDetail(currentComplexID, dates);
        ObservableList<PaymentReportDetail> data =
                FXCollections.observableArrayList(detailList);

        // set the column 'Payment ID'
        TableColumn paymentIDCol = new TableColumn("Payment ID");
        paymentIDCol.setMinWidth(140);
        paymentIDCol.setCellValueFactory(new PropertyValueFactory<>("paymentID"));

        // set the column 'Complex ID'
        TableColumn complexIDCol = new TableColumn("Complex ID");
        complexIDCol.setMinWidth(70);
        complexIDCol.setCellValueFactory(new PropertyValueFactory<>("complexID"));

        // set the column 'Apartment ID'
        TableColumn aptIDCol = new TableColumn("Apartment ID");
        aptIDCol.setMinWidth(80);
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("aptID"));

        // set the column 'Lease ID'
        TableColumn leaseIDCol = new TableColumn("Lease ID");
        leaseIDCol.setMinWidth(130);
        leaseIDCol.setCellValueFactory(new PropertyValueFactory<>("leaseID"));

        // set the column 'Lessee Name'
        TableColumn lesseeNameCol = new TableColumn("Lessee Name");
        lesseeNameCol.setMinWidth(105);
        lesseeNameCol.setCellValueFactory(new PropertyValueFactory<>("lesseeName"));

        // set the column 'Date'
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(80);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // set teh column 'Amount'
        TableColumn amountCol = new TableColumn("Amount($)");
        amountCol.setMinWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // set the column 'Late'
        TableColumn statusCol = new TableColumn("Late");
        statusCol.setMinWidth(25);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("lateOrNot"));

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(paymentIDCol, complexIDCol, aptIDCol, leaseIDCol, lesseeNameCol,
                dateCol, amountCol, statusCol);

        Button cancelBtn = new Button("Back");
        Button printBtn = new Button("Print");

        // back to report menu
        cancelBtn.setOnAction(e -> {
            stage.close();
            reportMenu(stage);
        });

        // save the report in 'src/report'
        printBtn.setOnAction(e -> {
            printPaymentReport(detailList, year, quarter);
            confirm(stage, "Saved in 'src/report/PaymentReport_" + year + "_" + quarter + ".txt' !",
                    false);
        });

        // add the buttons and the table to the pane
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.getChildren().addAll(cancelBtn, printBtn);
        BorderPane pane = new BorderPane();
        pane.setCenter(table);
        pane.setBottom(hBox);

        Scene paymentReportScene = new Scene(pane, 800, 600);
        paymentReportScene.getStylesheets().addAll(this.getClass().getResource("reportStyle.css").toExternalForm());
        stage.setScene(paymentReportScene);
        stage.show();
    }

    // save the payment report in 'src/report'
    private void printPaymentReport(List<PaymentReportDetail> detailList, String year, String quarter) {
        File outputFile = new File("src/report/PaymentReport" + year + "_" + quarter + ".txt");
        // create a new file
        try {
            outputFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // write the content of the payment report to the txt document
        try (FileWriter fw = new FileWriter (outputFile);
             BufferedWriter output = new BufferedWriter(fw)) {

            output.write(String.format("%-25s", "Payment ID") +
                    String.format("%-12s","Complex ID") +
                    String.format("%-8s", "Apt ID") +
                    String.format("%-25s", "Lease ID") +
                    String.format("%-20s", "Name") +
                    String.format("%-15s", "Date") +
                    String.format("%-15s", "Amount($)") +
                    String.format("%-10s", "Late"));
            output.write("\n");
            for (PaymentReportDetail detail : detailList) {
                output.write("\n");
                output.write(detail.toString());
            }

        } catch (FileNotFoundException f) {
            System.out.println ("File not found: " + f);
        } catch (IOException e) {
            System.out.println ("IOException: " + e);
        }
    }

    // display the maintenance report
    private void showMaintenanceReport(Stage stage, String year, String quarter, String[] dates) {
        stage.setTitle("Maintenance Report From " + dates[0] + " To " + dates[1]);

        // set the table
        TableView<MaintenanceReportDetail> table = new TableView<>();
        table.setEditable(false);
        List<MaintenanceReportDetail> detailList = dao.getMaintenanceReportDetail(currentComplexID, dates);
        ObservableList<MaintenanceReportDetail> data =
                FXCollections.observableArrayList(detailList);

        // set the column 'Complex ID'
        TableColumn complexIDCol = new TableColumn("Complex ID");
        complexIDCol.setMinWidth(60);
        complexIDCol.setCellValueFactory(new PropertyValueFactory<>("complexID"));

        // set the column 'Apartment ID'
        TableColumn aptIDCol = new TableColumn("Apartment ID");
        aptIDCol.setMinWidth(70);
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("aptID"));

        // set the column 'Date'
        TableColumn occurDateCol = new TableColumn("Date");
        occurDateCol.setMinWidth(60);
        occurDateCol.setCellValueFactory(new PropertyValueFactory<>("occurDate"));

        // set teh column 'Problem Description'
        TableColumn problemCol = new TableColumn("Problem Description");
        problemCol.setMinWidth(170);
        problemCol.setCellValueFactory(new PropertyValueFactory<>("problem"));

        // set the column 'Type'
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(45);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        // set the column 'Resolution'
        TableColumn resCol = new TableColumn("Resolution");
        resCol.setMinWidth(110);
        resCol.setCellValueFactory(new PropertyValueFactory<>("resolution"));

        // set the column 'Res Date'
        TableColumn resDateCol = new TableColumn("Res Date");
        resDateCol.setMinWidth(60);
        resDateCol.setCellValueFactory(new PropertyValueFactory<>("resDate"));

        // set the column 'Expense'
        TableColumn expenseCol = new TableColumn("Expense($)");
        expenseCol.setMinWidth(60);
        expenseCol.setCellValueFactory(new PropertyValueFactory<>("expense"));

        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(complexIDCol, aptIDCol, occurDateCol, problemCol, typeCol,
                resCol, resDateCol, expenseCol);

        Button cancelBtn = new Button("Back");
        Button printBtn = new Button("Print");

        // back to report menu
        cancelBtn.setOnAction(e -> {
            stage.close();
            reportMenu(stage);
        });

        // save the report in 'src/report'
        printBtn.setOnAction(e -> {
            printMaintenanceReport(detailList, year, quarter);
            confirm(stage, "Saved in 'src/report/MaintenanceReport_" + year + "_" + quarter + ".txt' !",
                    false);
        });

        // add the buttons and the table to the pane
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.getChildren().addAll(cancelBtn, printBtn);
        BorderPane pane = new BorderPane();
        pane.setCenter(table);
        pane.setBottom(hBox);

        Scene maintenanceReportScene = new Scene(pane, 800, 600);
        maintenanceReportScene.getStylesheets().addAll(this.getClass().getResource("reportStyle.css").toExternalForm());
        stage.setScene(maintenanceReportScene);
        stage.show();
    }

    // save the maintenance report in 'src/report'
    private void printMaintenanceReport(List<MaintenanceReportDetail> detailList, String year, String quarter) {
        File outputFile = new File("src/report/MaintenanceReport_" + year + "_" + quarter + ".txt");
        // create a new file
        try {
            outputFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // write the content of the maintenance report to the txt document
        try (FileWriter fw = new FileWriter (outputFile);
             BufferedWriter output = new BufferedWriter(fw)) {
            output.write(String.format("%-12s","Complex ID") +
                    String.format("%-8s", "Apt ID") +
                    String.format("%-15s", "Date") +
                    String.format("%-35s", "Problem Description") +
                    String.format("%-12s", "Type") +
                    String.format("%-25s", "Resolution") +
                    String.format("%-15s", "Res Date") +
                    String.format("%-15s", "Expense($)"));
            output.write("\n");
            for (MaintenanceReportDetail detail : detailList) {
                output.write("\n");
                output.write(detail.toString());
            }

        } catch (FileNotFoundException f) {
            System.out.println ("File not found: " + f);
        } catch (IOException e) {
            System.out.println ("IOException: " + e);
        }
    }

    // display the quarterly report
    private void showQuarterlyReport(Stage stage, String year, String quarter, String[] dates) {
        stage.setTitle("Quarterly Report From " + dates[0] + " To " + dates[1]);

        GridPane quarterlyReportGrid = new GridPane();
        quarterlyReportGrid.setAlignment(Pos.CENTER);
        quarterlyReportGrid.setHgap(10);
        quarterlyReportGrid.setVgap(10);
        quarterlyReportGrid.setPadding(new Insets(25, 25, 25, 25));
        Scene quarterlyReportScene = new Scene(quarterlyReportGrid, 800, 500);
        quarterlyReportScene.getStylesheets().addAll(this.getClass().getResource("reportStyle.css").toExternalForm());
        stage.setScene(quarterlyReportScene);

        // retrieve the demanded information from the database to generate the report
        QuarterlyReport report = new QuarterlyReport(currentComplexID, quarter, dates);

        // set the title
        Text reportTitle = new Text("Quarterly Report");
        reportTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 30));
        quarterlyReportGrid.add(reportTitle, 0, 0, 4, 1);

        // subtitle 1 - Complex Info
        Text complexInfoTitle = new Text("Complex Information");
        complexInfoTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 20));
        quarterlyReportGrid.add(complexInfoTitle, 0, 2, 4, 1);

        // Show complex ID
        Label complexIDLabel = new Label("Complex ID:");
        complexIDLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(complexIDLabel, 0, 3, 1, 1);
        Text complexIDText = new Text(currentComplexID);
        complexIDText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(complexIDText, 1, 3, 1, 1);

        // Show address
        Label addressLabel = new Label("Address");
        addressLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(addressLabel, 5, 3, 1, 1);
        Text addressText = new Text(report.getComplexInfo()[0]);
        addressText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(addressText, 6, 3, 7, 1);

        // Show year
        Label yearLabel = new Label("Year:");
        yearLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(yearLabel, 0, 4, 1, 1);
        Text yearText = new Text(year);
        yearText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(yearText, 1, 4, 1, 1);

        // Show quarter
        Label quarterLabel = new Label("Quarter:");
        quarterLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(quarterLabel, 5, 4, 1, 1);
        Text quarterText = new Text(getQuarter(date));
        quarterText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(quarterText, 6, 4, 1, 1);

        // Show the number of total apartments
        Label totalAptLabel = new Label("Total Apartments:");
        totalAptLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(totalAptLabel, 0, 5, 1, 1);
        Text totalAptText = new Text(report.getComplexInfo()[1]);
        totalAptText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(totalAptText, 1, 5, 1, 1);

        // Show the number of occupied apartments
        Label occupiedAptLabel = new Label("Occupied Apartments:");
        occupiedAptLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(occupiedAptLabel, 5, 5, 1, 1);
        Text occupiedAptText = new Text(report.getComplexInfo()[2]);
        occupiedAptText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(occupiedAptText, 6, 5, 3, 1);

        // Show occupancy rate
        Label occupancyLabel = new Label("Occupancy Rate:");
        occupancyLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(occupancyLabel, 9, 5, 1, 1);
        Text occupancyText = new Text(report.getComplexInfo()[3]);
        occupancyText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(occupancyText, 10, 5, 1, 1);

        // subtitle 2 - Revenue
        Text revenueTitle = new Text("Revenues:");
        revenueTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 20));
        quarterlyReportGrid.add(revenueTitle, 0, 7, 4, 1);

        // Show total rent revenue
        Label totalRentLabel = new Label("Total Rent:");
        totalRentLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(totalRentLabel, 0, 8, 1, 1);
        Text totalRentText = new Text("$" + report.getRevenue()[0]);
        totalRentText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(totalRentText, 1, 8, 1, 1);

        // Show uncovered rent
        Label uncoveredLabel = new Label("Uncovered Rent:");
        uncoveredLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(uncoveredLabel, 5, 8, 1, 1);
        Text uncoveredRentText = new Text("$" + report.getRevenue()[1]);
        uncoveredRentText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(uncoveredRentText, 6, 8, 3, 1);

        // subtitle 3 - Expense
        Text expenseTitle = new Text("Expenses:");
        expenseTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 20));
        quarterlyReportGrid.add(expenseTitle, 0, 10, 4, 1);

        // Show utilities expense
        Label utilitiesLabel = new Label("Utilities:");
        utilitiesLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(utilitiesLabel, 0, 11, 1, 1);
        Text utilitiesText = new Text("$" + report.getExpenseInfo()[0]);
        utilitiesText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(utilitiesText, 1, 11, 1, 1);

        // Show maintenance expense
        Label maintenanceLabel = new Label("Maintenance:");
        maintenanceLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(maintenanceLabel, 5, 11, 1, 1);
        Text maintenanceText = new Text("$" + report.getMaintenanceExpense());
        maintenanceText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(maintenanceText, 6, 11, 3, 1);

        // Show repairs expense
        Label repairLabel = new Label("Repairs:");
        repairLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(repairLabel, 9, 11, 1, 1);
        Text repairText = new Text("$" + report.getExpenseInfo()[1]);
        repairText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(repairText, 10, 11, 1, 1);

        // Show insurance expense
        Label insuranceLabel = new Label("Insurance:");
        insuranceLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(insuranceLabel, 0, 12, 1, 1);
        Text insuranceText = new Text("$" + report.getExpenseInfo()[2]);
        insuranceText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(insuranceText, 1, 12, 1, 1);

        // Show maintenance expense
        Label cleaningLabel = new Label("New Tenant Cleaning:");
        cleaningLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(cleaningLabel, 5, 12, 1, 1);
        Text cleaningText = new Text("$" + report.getExpenseInfo()[3]);
        cleaningText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(cleaningText, 6, 12, 3, 1);

        // Show wages of the manager
        Label wageLabel = new Label("Wages:");
        wageLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(wageLabel, 9, 12, 1, 1);
        Text wageText = new Text("$" + report.getWage());
        wageText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(wageText, 10, 12, 1, 1);

        // Show total expense
        Label totalExpenseLabel = new Label("Total Expense:");
        totalExpenseLabel.setFont(Font.font("Garamond", FontWeight.BOLD, 16));
        quarterlyReportGrid.add(totalExpenseLabel, 0, 15, 1, 1);
        Text totalExpenseText = new Text("$" + report.getTotalExpense());
        totalExpenseText.setFont(Font.font("Calibri", FontWeight.LIGHT, 14));
        quarterlyReportGrid.add(totalExpenseText, 1, 15, 1, 1);

        // Back to report menu
        Button cancelBtn = new Button("Back");
        quarterlyReportGrid.add(cancelBtn, 0, 17);

        // Print the report
        Button printBtn = new Button("Print");
        quarterlyReportGrid.add(printBtn, 10, 17);

        // View the expenses chart
        Button chartBtn = new Button("View Expenses Chart");
        int[] data = new int[6];
        System.arraycopy(report.getExpenseInfo(), 0, data, 0, 4);
        data[4] = report.getMaintenanceExpense();
        data[5] = report.getWage();
        quarterlyReportGrid.add(chartBtn, 5, 17);

        // back to report menu
        cancelBtn.setOnAction(e -> {
            stage.close();
            reportMenu(stage);
        });

        // save the report in 'src/report'
        printBtn.setOnAction(e -> {
            printQuarterlyReport(report, year, quarter);
            confirm(stage, "Saved in 'src/report/QuarterlyReport_" + year + "_" + quarter + ".txt' !",
                    false);
        });

        // show the expenses chart
        chartBtn.setOnAction(e -> { showExpenseChart(data, year, quarter, dates); });

        stage.show();
    }

    // display the expenses chart
    private void showExpenseChart(int[] data, String year, String quarter, String[] dates) {
        Stage stage = new Stage();
        stage.setTitle("Expenses From " + dates[0] + " To " + dates[1]);

        // set the axis
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number,String> expenseChart = new BarChart<>(xAxis,yAxis);
        expenseChart.setTitle("Expenses");
        xAxis.setLabel("Amount($)");
        xAxis.setTickLabelRotation(45);

        // add data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(year + " " + quarter);
        series1.getData().add(new XYChart.Data(data[0], "Utilities"));
        series1.getData().add(new XYChart.Data(data[1], "Repairs"));
        series1.getData().add(new XYChart.Data(data[2], "Insurance"));
        series1.getData().add(new XYChart.Data(data[3], "Cleaning"));
        series1.getData().add(new XYChart.Data(data[4], "Maintenance"));
        series1.getData().add(new XYChart.Data(data[5], "Wages"));

        Scene scene  = new Scene(expenseChart,800,500);

        expenseChart.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }

    // save the quarterly report in 'src/report'
    private void printQuarterlyReport(QuarterlyReport report, String year, String quarter) {
        File outputFile = new File("src/report/QuarterlyReport_" + year + "_" + quarter + ".txt");
        // create a new file
        try {
            outputFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // write the content of the quarterly report to the txt document
        try (FileWriter fw = new FileWriter (outputFile);
             BufferedWriter output = new BufferedWriter(fw)) {

            output.write("Complex Information\n");

            output.write(String.format("%-22s","Complex ID:") + String.format("%-15s", currentComplexID));
            output.write(String.format("%-25s","Address:") + report.getComplexInfo()[0] + "\n");

            output.write(String.format("%-22s","Year:") + String.format("%-15s", year));
            output.write(String.format("%-25s","Quarter:") + quarter + "\n");

            output.write(String.format("%-22s","Total Apartments:") +
                    String.format("%-15s", report.getComplexInfo()[1]));
            output.write(String.format("%-25s","Occupied Apartments:") +
                    String.format("%-15s", report.getComplexInfo()[2]));
            output.write(String.format("%-25s","Occupancy Rate:") +
                    String.format("%-15s", report.getComplexInfo()[3]) + "\n\n");

            output.write("Revenues\n");
            output.write(String.format("%-22s","Total Rent:") +
                    String.format("%-15s", "$" + report.getRevenue()[0]));
            output.write(String.format("%-25s","Uncovered Rent:") +
                    "$" + report.getRevenue()[1] + "\n\n");

            output.write("Expenses\n");
            output.write(String.format("%-22s","Utilities:") +
                    String.format("%-15s", "$" + report.getExpenseInfo()[0]));
            output.write(String.format("%-25s","Maintenance:") +
                    String.format("%-15s", "$" + report.getMaintenanceExpense()));
            output.write(String.format("%-25s","Repairs:") +
                    String.format("%-15s", "$" + report.getExpenseInfo()[1]) + "\n");
            output.write(String.format("%-22s","Insurance:") +
                    String.format("%-15s", "$" + report.getExpenseInfo()[2]));
            output.write(String.format("%-25s","New Tenant Cleaning:") +
                    String.format("%-15s", "$" + report.getExpenseInfo()[3]));
            output.write(String.format("%-25s","Wages:") +
                    String.format("%-15s", "$" + report.getWage()) + "\n");
            output.write(String.format("%-22s","Total Expense:") +
                    String.format("%-15s", "$" + report.getTotalExpense()));

        } catch (FileNotFoundException f) {
            System.out.println ("File not found: " + f);
        } catch (IOException e) {
            System.out.println ("IOException: " + e);
        }
    }

    // pop up a confirmation window to inform the user of the operation status
    public void confirm(Stage stage, String message, boolean returnToMainMenu) {
        // set the stage
        Stage confirmStage = new Stage();
        confirmStage.setTitle("Confirmation");
        GridPane confirmGrid = new GridPane();
        confirmGrid.setAlignment(Pos.CENTER);
        confirmGrid.setHgap(1);
        confirmGrid.setVgap(10);
        confirmGrid.setPadding(new Insets(25, 25, 25, 25));
        int width = returnToMainMenu ? 260 : 500;
        Scene confirmScene = new Scene(confirmGrid, width, 150);
        confirmStage.setScene(confirmScene);

        // set title
        Text sceneTitle = new Text(message);
        sceneTitle.setFont(Font.font("Times New Roman", FontWeight.LIGHT, 16));
        confirmGrid.add(sceneTitle, 0, 0, 2, 1);

        // add buttons
        Button confirmBtn = new Button("OK");
        HBox confirmBox = new HBox();
        confirmBox.setAlignment(Pos.CENTER);
        confirmBox.getChildren().add(confirmBtn);
        confirmGrid.add(confirmBox,1,3);

        // return to the previous page
        confirmBtn.setOnAction(e -> {
            confirmStage.close();
            stage.close();
            if (returnToMainMenu) { mainMenu(stage); }
            else { reportMenu(stage); }
        });

        confirmStage.setWidth(width);
        confirmStage.setHeight(150);
        confirmStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        logIn(primaryStage);
    }

}
