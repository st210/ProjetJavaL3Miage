package Controller;

import Main.Test;
import Model.Competence;
import Model.Mission;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MissPageCtrl extends Route implements Initializable {
    @FXML
    public JFXTextField durationTF;
    @FXML
    public JFXButton deleteEmpBtn;
    @FXML
    public JFXTextField searchComp;
    @FXML
    public TableView compTable;
    @FXML
    public JFXDatePicker date;
    @FXML
    public JFXTextField nameTF;
    @FXML
    public Label idLabel;
    @FXML
    public JFXButton saveMissBtn;
    @FXML
    public JFXTextField nbEmpTF;

    private boolean creationMode;
    private Mission mission;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void fillData(Mission mission) {
        this.mission = mission;
        this.nameTF.setText(mission.getName());
        this.date.setValue(LocalDate.from(Instant.ofEpochMilli(mission.getDateDebut().getTime())
                .atZone(ZoneId.systemDefault())));
        this.idLabel.setText("ID : " + mission.getId());
        this.durationTF.setText(String.valueOf(mission.getDuration()));
        this.nbEmpTF.setText(String.valueOf(mission.getNbEmployes()));
    }

    public void fillCompTable(Mission mission) {
        ObservableList<CompTableData> compList = getCompTableData(mission.getNeed().getCompetenceInit());
        FilteredList<CompTableData> filteredList = new FilteredList<>(compList, competence -> true);

        TableColumn<CompTableData, String> id = new TableColumn<>("ID");
        TableColumn<CompTableData, String> libelle = new TableColumn<>("Libellé");
        TableColumn<CompTableData, Integer> nbEmp = new TableColumn<>("Besoin");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelleFR"));
        nbEmp.setCellValueFactory(new PropertyValueFactory<>("nbEmp"));
        nbEmp.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        nbEmp.setOnEditCommit(event -> {
//            event.
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

        SortedList<CompTableData> sortedList = new SortedList<>(filteredList);


        this.compTable.setItems(sortedList);

        this.compTable.getColumns().addAll(id, libelle, nbEmp);
        this.compTable.setEditable(true);
    }

    /**
     * Définit si scène est ouverte pour une création ou une modification
     *
     * @param creationMode True
     */
    public void setCreationMode(boolean creationMode) {
        this.creationMode = creationMode;
    }

    private ObservableList<CompTableData> getCompTableData(HashMap<Competence, Integer> comMap) {
        ArrayList<CompTableData> compTableData = new ArrayList<>();

        comMap.forEach((competence, integer) -> compTableData.add(new CompTableData(competence.getId(), competence.getLibelleFR(), competence.getLibelleEN(), integer)));

        return FXCollections.observableList(compTableData);
    }

    public void saveMiss(ActionEvent actionEvent) throws IOException {
        String id;
        id = this.mission == null ? null : mission.getId();
        if (!nameTF.getText().equals("") && nbEmpTF.getText().equals("") && date.getValue() == null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText());
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() == null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()));
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()));
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && !durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()), Integer.parseInt(durationTF.getText()));
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        }
        // TODO else : snackabar ?
    }

    public class CompTableData {
        private String id;
        private String libelleFR;
        private String libelleEN;
        private int nbEmp;

        private CompTableData(String id, String libelleFR, String libelleEN, int nbEmp) {
            this.id = id;
            this.libelleFR = libelleFR;
            this.libelleEN = libelleEN;
            this.nbEmp = nbEmp;
        }

        public String getId() {
            return id;
        }

        public String getLibelleFR() {
            return libelleFR;
        }

        public String getLibelleEN() {
            return libelleEN;
        }

        public int getNbEmp() {
            return nbEmp;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLibelleFR(String libelleFR) {
            this.libelleFR = libelleFR;
        }

        public void setLibelleEN(String libelleEN) {
            this.libelleEN = libelleEN;
        }

        public void setNbEmp(int nbEmp) {
            this.nbEmp = nbEmp;
        }
    }
}
