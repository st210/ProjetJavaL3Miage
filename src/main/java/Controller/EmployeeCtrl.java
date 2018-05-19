package Controller;

import Main.Test;
import Model.Company;
import Model.Employee;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class EmployeeCtrl extends Route implements Initializable {


    @FXML
    public TableView empTable;
    @FXML
    public JFXTextField empSearch;
    @FXML
    public Label nbEmpLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNbEmpLabel();
        fillEmpTable();
    }


    private void setNbEmpLabel() {
        this.nbEmpLabel.setText(String.valueOf(Test.company.getEmployees().size()) + " employés");
    }

    /**
     * Initialisation du tableau d'employés
     */
    private void fillEmpTable() {
        ObservableList<Employee> empList = FXCollections.observableArrayList(Test.company.getEmployees());
        FilteredList<Employee> filteredList = new FilteredList<>(empList, employee -> true);

        TableColumn id = new TableColumn("ID");
        TableColumn<Employee, String> lastName = new TableColumn<>("Nom");
        TableColumn<Employee, String> firstName = new TableColumn<>("Prénom");
        TableColumn<Employee, String> entryDate = new TableColumn<>("Date d'entrée");

        id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        entryDate.setCellValueFactory(new PropertyValueFactory<>("entryIntoCompany"));


        empSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(employee -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            // Filter matches first name.
            return employee.getFirstname().toLowerCase().contains(lowerCaseFilter) || employee.getName().toLowerCase().contains(lowerCaseFilter) || employee.getId().toLowerCase().contains(lowerCaseFilter);
        }));

        empTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Employee rowData = row.getItem();
                    try {
                        Route.empToLoad = rowData;
                        goEmpPage();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        SortedList<Employee> sortedList = new SortedList<>(filteredList);

        this.empTable.setItems(sortedList);

        this.empTable.getColumns().addAll(id, lastName, firstName, entryDate);
    }

}
