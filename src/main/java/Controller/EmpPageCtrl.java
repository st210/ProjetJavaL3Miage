package Controller;

import Main.Test;
import Model.Competence;
import Model.CompetenceMgt;
import Model.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.paint.Color;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
     * Retourne la liste mise à jours des compétences de l'employé telle qu'elle apparait dans le tableau
     *
     * @return ArrayList de compétences mise à jour
     * @throws IOException
     */
    private ArrayList<Competence> getNewComps() throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();
        ArrayList<Competence> empComp = new ArrayList<>();
        List<CompEmpData> compEmpData = compTable.getItems();

        for (CompEmpData c : compEmpData) {
            if (c.isKnown()) {
                empComp.add(competenceMgt.getCompetenceByIDFromCSV(c.getId()));
            }
        }
        return empComp;
    }

    /**
     * Remplie le tableau de compétences avec toutes les compétences du fichier CSV
     * Lecture CSV
     *
     * @throws IOException
     */
    private void fillCompTable() throws IOException {
        ObservableList<CompEmpData> compList = fillCompEmpData();
        FilteredList<CompEmpData> filteredList = new FilteredList<>(compList, competence -> true);

        TableColumn<CompEmpData, String> id = new TableColumn<>("ID");
        TableColumn<CompEmpData, String> libelle = new TableColumn<>("Libellé");
        TableColumn<CompEmpData, Boolean> bool = new TableColumn<>("Possède");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelleFR"));
        bool.setCellValueFactory(new PropertyValueFactory<>("known"));
        bool.setCellFactory(param -> new CheckBoxTableCell<>());

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

        SortedList<CompEmpData> sortedList = new SortedList<>(filteredList);


        this.compTable.setItems(sortedList);

        this.compTable.getColumns().addAll(id, libelle, bool);

        compTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                System.out.println(sortedList.get(newValue.intValue()));
            }
        });

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
            Employee employee;
            if (Route.empToLoad == null) {
                // Création
                employee = new Employee(firstNameTF.getText(), nameTF.getText(), date.getValue().format(formatter));
                employee.setCompetencesEmployee(getNewComps());
                Test.company.addEmployee(employee);
            } else {
                // Modification
                Test.company.getEmployee(Route.empToLoad.getId()).setCompetencesEmployee(getNewComps());

            }
            goEmployees();
        }
        // TODO else : snackabar ?
    }

    /**
     * Btn suppression d'un employé
     *
     * @param actionEvent
     */
    public void deleteEmp(ActionEvent actionEvent) throws IOException {
        Test.company.removeEmployee(Route.empToLoad);
        goEmployees();
    }

    /**
     * Chargement de la liste de compétences selon le formalisme utilisé dans le tableau
     *
     * @return ObservableList<CompEmpData> La liste de compétences
     * @throws IOException
     */
    private ObservableList<CompEmpData> fillCompEmpData() throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();
        ArrayList<CompEmpData> compEmpData = new ArrayList<>();
        ArrayList<Competence> competences = competenceMgt.importCompetencesFromCSV();
        Boolean bool = false;
        for (Competence c : competences) {
            if (Route.empToLoad != null) {
                bool = Route.empToLoad.getCompetencesEmployee().contains(c);
            }
            compEmpData.add(new CompEmpData(c.getId(), c.getLibelleFR(), c.getLibelleEN(), bool));
        }
        return FXCollections.observableArrayList(compEmpData);
    }

    //////////////////////////////////////////////

    public class CompEmpData {
        private StringProperty id = new SimpleStringProperty();
        private StringProperty libelleFR = new SimpleStringProperty();
        private StringProperty libelleEN = new SimpleStringProperty();
        private BooleanProperty known = new SimpleBooleanProperty();

        private CompEmpData(String id, String libelleFR, String libelleEN, boolean known) {
            this.id.setValue(id);
            this.libelleFR.setValue(libelleFR);
            this.libelleEN.setValue(libelleEN);
            this.known.setValue(known);
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

        public boolean isKnown() {
            return known.get();
        }

        public BooleanProperty knownProperty() {
            return known;
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

        public void setKnown(boolean known) {
            this.known.set(known);
        }
    }
}
