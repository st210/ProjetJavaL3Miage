package Controller;

import Main.Test;
import Model.Company;
import Model.Mission;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class MissionCtrl extends Route implements Initializable {

    @FXML
    public TableView missionTable;
    @FXML
    public JFXTextField missSearch;
    @FXML
    public JFXComboBox<Label> comBoxStatus;
    @FXML
    public Label nbMissLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNbMissLabel();
        initCombobox();
        fillMissTable();
    }

    private void initNbMissLabel() {
        this.nbMissLabel.setText(String.valueOf(Test.company.getMissionNotCompleted().size()) + " missions");
    }

    private void initCombobox() {
        comBoxStatus.setPromptText("État");

        comBoxStatus.getItems().add(new Label(""));
        comBoxStatus.getItems().add(new Label("IN PREPARATION"));
        comBoxStatus.getItems().add(new Label("SCHEDULED"));
        comBoxStatus.getItems().add(new Label("IN PROGRESS"));
        comBoxStatus.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object == null ? "" : object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label(string);
            }
        });

    }

    /**
     * Initialise le tableau de mission et les filtres associés
     */
    private void fillMissTable() {
        ObservableList<Mission> missionList = FXCollections.observableArrayList(Test.company.getMissions());
        FilteredList<Mission> filteredList = new FilteredList<>(missionList, mission -> true);


        TableColumn id = new TableColumn("ID");
        TableColumn name = new TableColumn("Nom");
        TableColumn status = new TableColumn("État");

        id.setCellValueFactory(new PropertyValueFactory<Mission, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Mission, String>("name"));
        status.setCellValueFactory(new PropertyValueFactory<Mission, String>("status"));


        missSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((mission) -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter matches first name.
                return (mission.getName().toLowerCase().contains(lowerCaseFilter)
                        || mission.getId().toLowerCase().contains(lowerCaseFilter));
            });
        });

        comBoxStatus.valueProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate((mission -> {
            String lowerCaseFilter = comBoxStatus.getValue().getText().toLowerCase();
            return mission.getStatus().toString().toLowerCase().contains(lowerCaseFilter);
        })));

        SortedList<Mission> sortedList = new SortedList<>(filteredList);

        this.missionTable.setItems(sortedList);

        this.missionTable.getColumns().addAll(id, name, status);
    }
}
