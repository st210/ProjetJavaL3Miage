package Controller;

import Model.Competence;
import Model.CompetenceMgt;
import Model.Employee;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZoneId;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillCompTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remplie le tableau de compétence avec toutes les compétences du fichier CSV
     *
     * @throws IOException
     */
    private void fillCompTable() throws IOException {
        CompetenceMgt cptMgt = new CompetenceMgt();
        ObservableList<Competence> compList = FXCollections.observableArrayList(cptMgt.importCompetencesFromCSV("liste_competences.csv"));

        TableColumn id = new TableColumn("ID");
        TableColumn libelle = new TableColumn("Libellé");
        TableColumn bool = new TableColumn("Pos");

        id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<Employee, String>("libelleFR"));
        bool.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("competencesEmployee"));
        bool.setCellFactory(CheckBoxTableCell.forTableColumn(bool));

        this.compTable.setItems(compList);

        this.compTable.getColumns().addAll(id, libelle);
    }

    public void fillData(Employee employee) throws ParseException {
        this.nameTF.setText(employee.getName());
        this.firstNameTF.setText(employee.getFirstname());
        this.date.setValue(employee.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.idLabel.setText(employee.getId());
    }
}
