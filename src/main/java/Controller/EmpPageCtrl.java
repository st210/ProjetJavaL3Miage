package Controller;

import Main.Test;
import Model.Competence;
import Model.CompetenceMgt;
import Model.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EmpPageCtrl extends Route implements Initializable {

    @FXML
    public TableView compTable;
    @FXML
    public JFXTextField nameTF;
    @FXML
    public JFXTextField firstNameTF;
    @FXML
    public JFXDatePicker date;
    @FXML
    public Label idLabel;
    @FXML
    public JFXButton saveEmpBtn;
    @FXML
    public JFXButton deleteEmpBtn;
    @FXML
    public JFXTextField searchComp;

    private boolean creationMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillCompTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Définit si scène est ouverte pour une création ou une modification
     *
     * @param creationMode True
     */
    public void setCreationMode(boolean creationMode) {
        this.creationMode = creationMode;
    }

    /**
     * Remplie le tableau de compétences avec toutes les compétences du fichier CSV
     * Lecture CSV
     *
     * @throws IOException
     */
    private void fillCompTable() throws IOException {
        CompetenceMgt cptMgt = new CompetenceMgt();
        ObservableList<Competence> compList = FXCollections.observableArrayList(cptMgt.importCompetencesFromCSV());
        FilteredList<Competence> filteredList = new FilteredList<>(compList, competence -> true);

        TableColumn<Employee, String> id = new TableColumn<>("ID");
        TableColumn<Employee, String> libelle = new TableColumn<>("Libellé");
        TableColumn<Employee, Boolean> bool = new TableColumn<>("Possède");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelleFR"));
        bool.setCellValueFactory(new PropertyValueFactory<>("competencesEmployee"));
        bool.setCellFactory(param -> new CheckBoxTableCell<>());
//        bool.setOnEditCommit(event -> {
//            event.getTableView().
//        });
        searchComp.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(competence -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            // Filter matches first name.
            return competence.getId().toLowerCase().contains(lowerCaseFilter) || competence.getLibelleFR().toLowerCase().contains(lowerCaseFilter);

        }));

        SortedList<Competence> sortedList = new SortedList<>(filteredList);


        this.compTable.setItems(sortedList);

        this.compTable.getColumns().addAll(id, libelle, bool);
        this.compTable.setEditable(true);
    }

    /**
     * Initialise les données affichées
     *
     * @param employee L'employé duquel sont extraites les données
     * @throws ParseException
     */
    public void fillData(Employee employee) throws ParseException {
        this.nameTF.setText(employee.getName());
        this.firstNameTF.setText(employee.getFirstname());
        this.date.setValue(employee.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.idLabel.setText(employee.getId());
    }

    /**
     * Sauvegarder (créer/modifier) l'employé
     * <p>
     * Écriture CSV
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveEmp(ActionEvent actionEvent) throws IOException {
        if (!nameTF.getText().equals("") && !firstNameTF.getText().equals("") && date.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Employee employee = new Employee(firstNameTF.getText(), nameTF.getText(), date.getValue().format(formatter));
            Test.company.addEmployee(employee);
            goEmployees();
        }
        // TODO else : snackabar ?
    }

    public void deleteEmp(ActionEvent actionEvent) {

    }
}
