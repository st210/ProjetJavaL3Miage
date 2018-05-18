package Controller;

import Main.Test;
import Model.Competence;
import Model.CompetenceMgt;
import Model.EmployeeMgt;
import Model.Mission;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.glass.ui.TouchInputSupport;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        try {
            fillCompTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void fillCompTable() throws IOException {
        ObservableList<CompTableData> compList = fillCompMissData();
        FilteredList<CompTableData> filteredList = new FilteredList<>(compList, competence -> true);

        TableColumn<CompTableData, String> id = new TableColumn<>("ID");
        TableColumn<CompTableData, String> libelle = new TableColumn<>("Libellé");
        TableColumn<CompTableData, String> nbCurr = new TableColumn<>("Auj.");
        TableColumn<CompTableData, Integer> nbEmp = new TableColumn<>("Besoin");
        TableColumn<CompTableData, CompTableData> actionBtn = new TableColumn<>("Action");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelleFR"));
        nbCurr.setCellValueFactory(new PropertyValueFactory<>("nbCurrent"));
        nbEmp.setCellValueFactory(new PropertyValueFactory<>("nbEmp"));
        nbEmp.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        actionBtn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionBtn.setCellFactory(p -> new BtnAddEmp());

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

        this.compTable.getColumns().addAll(id, libelle, nbCurr, nbEmp, actionBtn);

        this.compTable.setEditable(true);
    }

    /**
     * Chargement de la liste de compétences selon le formalisme utilisé dans le tableau
     *
     * @return ObservableList<CompTableData> La liste de compétences
     * @throws IOException
     */
    private ObservableList<CompTableData> fillCompMissData() throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();
        ArrayList<CompTableData> compEmpData = new ArrayList<>();
        ArrayList<Competence> competences = competenceMgt.importCompetencesFromCSV();
        int nbEmp = 0;
        int nbCurrent = 0;
        for (Competence c : competences) {
            if (Route.missToLoad != null) {
                if (Route.missToLoad.getNeed().getCompetenceInit().get(c) != null)
                    nbEmp = Route.missToLoad.getNeed().getCompetenceInit().get(c);
                else
                    nbEmp = 0;
                if (Route.missToLoad.getNeed().getCompetenceCurrent().get(c) != null)
                    nbCurrent = Route.missToLoad.getNeed().getCompetenceCurrent().get(c).size();
                else
                    nbCurrent = 0;
            }
            compEmpData.add(new CompTableData(c.getId(), c.getLibelleFR(), c.getLibelleEN(), nbEmp, nbCurrent));
        }
        return FXCollections.observableArrayList(compEmpData);
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
     * Retourne la hashmap mise à jours des besoins de la mission
     *
     * @return Hashmap de compétences/besoin mise à jour
     * @throws IOException
     */
    private HashMap<Competence, Integer> getNewCompInit() throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();
        HashMap<Competence, Integer> missComp = new HashMap<>();
        List<CompTableData> compTableData = compTable.getItems();

        for (CompTableData c : compTableData) {
            if (c.getNbEmp() > 0) {
                missComp.put(competenceMgt.getCompetenceByIDFromCSV(c.getId()), c.getNbEmp());
            }
        }
        return missComp;
    }


    public void saveMiss(ActionEvent actionEvent) throws IOException {
        String id;
        id = creationMode ? String.valueOf(Test.company.getMissions().size() + 1) : mission.getId();
        if (!nameTF.getText().equals("") && nbEmpTF.getText().equals("") && date.getValue() == null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText());
            mission.getNeed().setCompetenceInit(getNewCompInit());
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() == null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && !durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()), Integer.parseInt(durationTF.getText()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        }
        // TODO else : snackabar ?
    }

    public void deleteMission(ActionEvent actionEvent) throws IOException {
        Test.company.removeMission(Route.missToLoad);
        goMissions();
    }

    /////////////////////////////////////

    public class CompTableData {
        private StringProperty id = new SimpleStringProperty();
        private StringProperty libelleFR = new SimpleStringProperty();
        private StringProperty libelleEN = new SimpleStringProperty();
        private IntegerProperty nbEmp = new SimpleIntegerProperty();
        private IntegerProperty nbCurrent = new SimpleIntegerProperty();

        private CompTableData(String id, String libelleFR, String libelleEN, int nbEmp, int nbCurrent) {
            this.id.setValue(id);
            this.libelleFR.setValue(libelleFR);
            this.libelleEN.setValue(libelleEN);
            this.nbEmp.setValue(nbEmp);
            this.nbCurrent.setValue(nbCurrent);
        }

        public String getId() {
            return id.get();
        }

        public StringProperty idProperty() {
            return id;
        }

        public String getLibelleFR() {
            return libelleFR.get();
        }

        public StringProperty libelleFRProperty() {
            return libelleFR;
        }

        public String getLibelleEN() {
            return libelleEN.get();
        }

        public StringProperty libelleENProperty() {
            return libelleEN;
        }

        public int getNbEmp() {
            return nbEmp.get();
        }

        public IntegerProperty nbEmpProperty() {
            return nbEmp;
        }

        public int getNbCurrent() {
            return nbCurrent.get();
        }

        public IntegerProperty nbCurrentProperty() {
            return nbCurrent;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public void setLibelleFR(String libelleFR) {
            this.libelleFR.set(libelleFR);
        }

        public void setLibelleEN(String libelleEN) {
            this.libelleEN.set(libelleEN);
        }

        public void setNbEmp(int nbEmp) {
            this.nbEmp.set(nbEmp);
        }

        public void setNbCurrent(int nbCurrent) {
            this.nbCurrent.set(nbCurrent);
        }

        public Competence toCompetence() {
            return new Competence(getId(),getLibelleEN(),getLibelleFR());
        }
    }

    public class BtnAddEmp extends TableCell<CompTableData, CompTableData> {
        private Button cellButton;

        BtnAddEmp() {
            EmployeeMgt employeeMgt = new EmployeeMgt();
            cellButton = new Button();
            cellButton.setOnAction(t -> {
                // do something when button clicked
                CompTableData compTableData = getItem();
                // do something with record....
                System.out.println(compTableData.toCompetence().toString());
                System.out.println(employeeMgt.findEmpForComp(compTableData.toCompetence()));
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(CompTableData compTableData, boolean empty) {
            super.updateItem(compTableData, empty);
            if (!empty) {
                cellButton.setText("ADD");
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
