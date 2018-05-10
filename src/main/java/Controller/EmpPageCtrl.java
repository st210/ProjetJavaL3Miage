package Controller;

import Main.Test;
import Model.Competence;
import Model.CompetenceMgt;
import Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmpPageCtrl extends Route implements Initializable {

    @FXML
    public TableView compTable;

    private Employee employee;

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
}
