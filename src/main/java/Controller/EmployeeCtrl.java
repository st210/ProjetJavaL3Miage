package Controller;

import Main.Test;
import Model.Employee;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    @FXML
    public JFXTextField empSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillEmpTable();
    }

    private void fillEmpTable() {
        ObservableList<Employee> empList = FXCollections.observableArrayList(Test.company.getEmployees());
        FilteredList<Employee> filteredList = new FilteredList<>(empList, employee -> true);

        TableColumn id = new TableColumn("ID");
        TableColumn lastName = new TableColumn("Nom");
        TableColumn firstName = new TableColumn("Prénom");
        TableColumn entryDate = new TableColumn("Date d'entrée");

        id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstname"));
        entryDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("entryIntoCompany"));


        empSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(employee -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter matches first name.
                return employee.getFirstname().toLowerCase().contains(lowerCaseFilter) || employee.getName().toLowerCase().contains(lowerCaseFilter) || employee.getId().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Employee> sortedList = new SortedList<>(filteredList);

        this.empTable.setItems(sortedList);

        this.empTable.getColumns().addAll(id, lastName, firstName, entryDate);
    }
}
