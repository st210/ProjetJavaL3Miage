package Controller;

import Main.Test;
import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeCtrl extends Route implements Initializable {

    @FXML
    public TableView empTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillEmpTable();
    }

    private void fillEmpTable() {
        ObservableList<Employee> empList = FXCollections.observableArrayList(Test.company.getEmployees());

        TableColumn lastName = new TableColumn("Nom");
        TableColumn firstName = new TableColumn("Prénom");
        TableColumn entryDate = new TableColumn("Date d'entrée");

        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstname"));
        entryDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("entryIntoCompany"));


        this.empTable.setItems(empList);

        this.empTable.getColumns().addAll(lastName, firstName, entryDate);


    }
}
