package com.project.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.project.breadapp.config.StageManager;
import com.project.breadapp.models.Customer;
import com.project.breadapp.services.impl.CustomerService;
import com.project.breadapp.views.FxmlView;

//import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class CustomerController implements Initializable {

    @FXML
    private Label customerId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    
    @FXML
    private TextField contactNumber;

    @FXML
    private TextField address;
    
    @FXML
    private DatePicker createdAt;
    
    @FXML
    private DatePicker updatedAt;


    @FXML
    private Button reset;

    @FXML
    private Button saveCustomer;
    
    @FXML
    private Button openCustomer;
    
     @FXML
    private Button deleteCustomer;
     
     @FXML
    private Button openUser;
     

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Long> colCustomerId;

    @FXML
    private TableColumn<Customer, String> colFirstName;

    @FXML
    private TableColumn<Customer, String> colLastName;

    @FXML
    private TableColumn<Customer, String> colContactNumber;

    @FXML
    private TableColumn<Customer, String> colAddress;
    
     @FXML
    private TableColumn<Customer, LocalDate> colCreatedAt;
      
    @FXML
    private TableColumn<Customer, LocalDate> colUpdatedAt;

    @FXML
    private TableColumn<Customer, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CustomerService customerService;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    
     @FXML
    private void openUser(ActionEvent event) {
       stageManager.switchScene(FxmlView.USER);
    
}

    @FXML
    private void saveCustomer(ActionEvent event) {

        if (validate("First Name", getFirstName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Last Name", getLastName(), "([a-zA-Z]{3,30}\\s*)+")
                 && validate("Contact Number", getContactNumber(), "([a-zA-Z]{3,30}\\s*)+")
                 && validate("Address", getAddress(), "([a-zA-Z]{3,30}\\s*)+"));{

            if (customerId.getText() == null || "".equals(customerId.getText())) {
                if (true) {

                    Customer customer = new Customer();
                    customer.setFirstName(getFirstName());
                    customer.setLastName(getLastName());
                    customer.setContactNumber(getContactNumber());
                    customer.setAddress(getAddress());
                    customer.setCreatedAt(getCreatedAt());
                    customer.setUpdatedAt(getUpdatedAt());
                    Customer newCustomer = customerService.save(customer);

                    saveAlert(newCustomer);
                }

            } else {
                Customer customer = customerService.find(Long.parseLong(customerId.getText()));
                customer.setFirstName(getFirstName());
                customer.setLastName(getLastName());
                customer.setContactNumber(getContactNumber());
                customer.setAddress(getAddress());
                customer.setCreatedAt(getCreatedAt());
                customer.setUpdatedAt(getUpdatedAt());
                Customer updatedCustomer = customerService.update(customer);
                updateAlert(updatedCustomer);
            }

            clearFields();
            loadCustomerDetails();
        }

    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        List<Customer> customers = customerTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            customerService.deleteInBatch(customers);
        }

        loadCustomerDetails();
    }

    private void clearFields() {
        customerId.setText(null);
        firstName.clear();
        lastName.clear();
        contactNumber.clear();
        address.clear();
        createdAt.getEditor().clear();
        updatedAt.getEditor().clear();
    }

    private void saveAlert(Customer customer) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer " + customer.getFirstName() + " " + customer.getLastName() + " " + customer.getContactNumber() + " " + customer.getAddress() + " has been created and \n" + " id is " + customer.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Customer customer) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer " + customer.getFirstName() + " " + customer.getLastName() +  " " + customer.getContactNumber() +  " " + customer.getAddress() + " has been updated.");
        alert.showAndWait();
    }

//    private String getGenderTitle(String gender) {
//        return (gender.equals("Male")) ? "his" : "her";
//    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public String getContactNumber() {
        return contactNumber.getText();
    }
    
    public String getAddress() {
        return address.getText();
   
    }
    public LocalDate getCreatedAt() {
        return createdAt.getValue();
    }
    

    public LocalDate getUpdatedAt() {
        return updatedAt.getValue();
    }


    /*
	 *  Set All userTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
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
		 }));*/

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> cellFactory
            = new Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>>() {
        @Override
        public TableCell<Customer, Boolean> call(final TableColumn<Customer, Boolean> param) {
            final TableCell<Customer, Boolean> cell = new TableCell<Customer, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Customer customer = getTableView().getItems().get(getIndex());
                            updateCustomer(customer);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateCustomer(Customer customer) {
                    customerId.setText(Long.toString(customer.getId()));
                    firstName.setText(customer.getFirstName());
                    lastName.setText(customer.getLastName());
                    contactNumber.setText(customer.getContactNumber());
                    address.setText(customer.getAddress());
                    createdAt.setValue(customer.getCreatedAt());
                    updatedAt.setValue(customer.getUpdatedAt());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadCustomerDetails() {
        customerList.clear();
        customerList.addAll(customerService.findAll());

        customerTable.setItems(customerList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all customer into table
        loadCustomerDetails();
    }
}
