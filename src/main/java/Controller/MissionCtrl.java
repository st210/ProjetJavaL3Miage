package Controller;

import Main.Test;
import Model.Mission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MissionCtrl extends Route implements Initializable {

    @FXML
    public TableView missionTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillMissTable();
    }

    private void fillMissTable() {
        ObservableList<Mission> missionList = FXCollections.observableArrayList(Test.company.getMissions());

        TableColumn name = new TableColumn("Nom");
        TableColumn status = new TableColumn("État");

        name.setCellValueFactory(new PropertyValueFactory<Mission, String>("name"));
        status.setCellValueFactory(new PropertyValueFactory<Mission, String>("status"));

        this.missionTable.setItems(missionList);

        this.missionTable.getColumns().addAll(name, status);
    }
}
