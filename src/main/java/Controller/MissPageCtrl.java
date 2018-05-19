package Controller;

import Main.Test;
import Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.glass.ui.TouchInputSupport;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private HashMap<Competence, ArrayList<Employee>> affectations = Route.missToLoad == null ? new HashMap<>() : Route.missToLoad.getNeed().getCompetenceCurrent();

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
        actionBtn.setCellFactory(p -> new BtnAddEmp(this));

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
    private HashMap<Competence, Integer> getNewCompInit() {
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
            mission.getNeed().setCompetenceCurrent(affectations);
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() == null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            mission.getNeed().setCompetenceCurrent(affectations);
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            mission.getNeed().setCompetenceCurrent(affectations);
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        } else if (!nameTF.getText().equals("") && !nbEmpTF.getText().equals("") && date.getValue() != null && !durationTF.getText().equals("")) {
            Mission mission = new Mission(id, nameTF.getText(), Integer.parseInt(nbEmpTF.getText()), Date.valueOf(date.getValue()), Integer.parseInt(durationTF.getText()));
            mission.getNeed().setCompetenceInit(getNewCompInit());
            mission.getNeed().setCompetenceCurrent(affectations);
            Test.company.removeMission(this.mission);
            Test.company.addMission(mission);
            goMissions();
        }
        // TODO else : snackabar ?
    }

    public void deleteMission(ActionEvent actionEvent) throws IOException {
        if (Route.missToLoad != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette mission ?");
            alert.setContentText(Route.missToLoad.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                Test.company.removeMission(Route.missToLoad);
                goMissions();
            } else {
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }
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
            return new Competence(getId(), getLibelleEN(), getLibelleFR());
        }
    }

    private class BtnAddEmp extends TableCell<CompTableData, CompTableData> {
        private Button cellButton;

        BtnAddEmp(MissPageCtrl missPageCtrl) {
            EmployeeMgt employeeMgt = new EmployeeMgt();
            DialogPane dialogPane = new DialogPane();
            cellButton = new Button();

            cellButton.setOnAction(t -> {

                CompTableData compTableData = getItem();

                // OUVERTURE DIALOG
                openDialog(compTableData.toCompetence(), missPageCtrl);
            });
        }

        private void openDialog(Competence competence, MissPageCtrl missPageCtrl) {
            Dialog<ArrayList<SelectEmployee>> dialog = new ChoiceDialog<>();
            dialog.setResultConverter((ButtonType type) -> {
                ButtonBar.ButtonData data = type == null ? null : type.getButtonData();
                if (data == ButtonBar.ButtonData.OK_DONE) {
                    TableView tableView;
                    ArrayList<SelectEmployee> result = new ArrayList<>();
                    tableView = (TableView) dialog.getDialogPane().getContent();

                    tableView.getItems().forEach(o -> {
                        SelectEmployee selectEmployee = (SelectEmployee) o;
                        if (selectEmployee.isSelected()) {
                            result.add((SelectEmployee) o);
                        }
                    });
                    return result;
                } else {
                    return null;
                }
            });
            dialog.setTitle("Affectation");
            dialog.getDialogPane().setContent(createTable(competence));
            Optional<ArrayList<SelectEmployee>> result = dialog.showAndWait();
            if (result.isPresent()) {
                ArrayList<Employee> addEmployees = new ArrayList<>();
                result.get().forEach(selectEmployee -> addEmployees.add(selectEmployee.toEmployee()));
                missPageCtrl.affectations.put(competence, addEmployees);

            }
        }


        private TableView createTable(Competence competence) {
            EmployeeMgt employeeMgt = new EmployeeMgt();
            TableView empTable = new TableView();
            setCenterShape(true);

            ArrayList<Employee> list = employeeMgt.findEmpForComp(competence);
            ArrayList<SelectEmployee> list1 = new ArrayList<>();
            for (Employee e : list) {
                list1.add(new SelectEmployee(e, Route.missToLoad));
            }
            ObservableList<SelectEmployee> empList = FXCollections.observableArrayList(list1);


            TableColumn<SelectEmployee, String> id = new TableColumn<>("ID");
            TableColumn<SelectEmployee, String> lastName = new TableColumn<>("Nom");
            TableColumn<SelectEmployee, String> firstName = new TableColumn<>("Prénom");
            TableColumn<SelectEmployee, String> entryDate = new TableColumn<>("Date d'entrée");
            TableColumn<SelectEmployee, Boolean> select = new TableColumn<>("Choix");

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("name"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            entryDate.setCellValueFactory(new PropertyValueFactory<>("entryIntoCompany"));
            select.setCellValueFactory(new PropertyValueFactory<>("selected"));
            select.setCellFactory(param -> new CheckBoxTableCell<>());

            empTable.setItems(empList);
            empTable.getColumns().addAll(id, lastName, firstName, entryDate, select);
            empTable.setEditable(true);

            return empTable;
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(CompTableData compTableData, boolean empty) {
            super.updateItem(compTableData, empty);
            if (!empty) {
                cellButton.setText("Voir");
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    public class SelectEmployee {
        private StringProperty id = new SimpleStringProperty();
        private StringProperty name = new SimpleStringProperty();
        private StringProperty firstname = new SimpleStringProperty();
        private StringProperty entryIntoCompany = new SimpleStringProperty();
        private BooleanProperty selected = new SimpleBooleanProperty();

        private SelectEmployee(Employee employee, Mission mission) {
            this.id.setValue(employee.getId());
            this.name.setValue(employee.getName());
            this.firstname.setValue(employee.getFirstname());
            this.entryIntoCompany.setValue(employee.getEntryIntoCompany());
            this.selected.setValue(Route.missToLoad != null && employee.workingForMission(mission));
        }

        public String getId() {
            return id.get();
        }

        public StringProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getFirstname() {
            return firstname.get();
        }

        public StringProperty firstnameProperty() {
            return firstname;
        }

        public String getEntryIntoCompany() {
            return entryIntoCompany.get();
        }

        public StringProperty entryIntoCompanyProperty() {
            return entryIntoCompany;
        }

        public boolean isSelected() {
            return selected.get();
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public void setFirstname(String firstname) {
            this.firstname.set(firstname);
        }

        public void setEntryIntoCompany(String entryIntoCompany) {
            this.entryIntoCompany.set(entryIntoCompany);
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }

        public Employee toEmployee() {
            return new Employee(getId(), getName(), getFirstname(), getEntryIntoCompany());
        }
    }
}
