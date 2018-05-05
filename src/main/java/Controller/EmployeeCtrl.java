package Controller;

import Model.Company;
import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeCtrl implements Initializable {

    @FXML
    private TableView<Employee> empTable;
    @FXML
    private TableColumn<Employee, String> empId;
    @FXML
    private TableColumn<Employee, String> empFirstName;
    @FXML
    private TableColumn<Employee, String> empLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
