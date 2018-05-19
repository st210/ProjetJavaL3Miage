package Controller;

import Main.Test;
import Model.Mission;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class HomeCtrl extends Route implements Initializable {

    @FXML
    public Label nbEmpLabel;
    @FXML
    public Label nbMissCompl;
    @FXML
    public Label nbMissEnCours;
    @FXML
    public Label nbMissPlan;
    @FXML
    public Label dayEndMiss;
    @FXML
    public Label monthEndMiss;
    @FXML
    public Label yearEndMiss;
    @FXML
    public Label scheduledDay1;
    @FXML
    public Label scheduledDay2;
    @FXML
    public Label scheduledDay3;
    @FXML
    public Label scheduled1;
    @FXML
    public Label scheduled2;
    @FXML
    public Label scheduled3;
    @FXML
    public Label toCompl1;
    @FXML
    public Label toCompl2;
    @FXML
    public Label toCompl3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNbEmpLabel();
        setNbMissCompl();
        setNbMissEnCours();
        setMissScheduled();
        setDateEndMiss();
    }

    private void setNbEmpLabel() {
        this.nbEmpLabel.setText(String.valueOf(Test.company.getEmployees().size()) + " employés");
    }

    private void setNbMissCompl() {
        ArrayList<Mission> missions = Test.company.getMissionPreparation();
        this.nbMissCompl.setText(String.valueOf(missions.size()));

        if (missions.size() >= 1) {
            toCompl1.setText(missions.get(missions.size() - 1).getName());
        }
        if (missions.size() >= 2) {
            toCompl2.setText(missions.get(missions.size() - 2).getName());
        }
        if (missions.size() >= 3) {
            toCompl3.setText(missions.get(missions.size() - 3).getName());
        }

    }

    private void setNbMissEnCours() {
        this.nbMissEnCours.setText(String.valueOf(Test.company.getMissionInProgress().size()));
    }

    private void setMissScheduled() {
        ArrayList<Mission> missions = Test.company.getMissionScheduled();

        this.nbMissPlan.setText(String.valueOf(missions.size()));

        if (missions.size() >= 1) {
            scheduled1.setText(missions.get(missions.size() - 1).getName());
        }
        if (missions.size() >= 2) {
            scheduled2.setText(missions.get(missions.size() - 2).getName());
        }
        if (missions.size() >= 3) {
            scheduled3.setText(missions.get(missions.size() - 3).getName());
        }
    }

    private void setDateEndMiss() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM");
        Calendar c = Calendar.getInstance();
        c.setTime(Test.company.getDateNextFinMiss());
        this.dayEndMiss.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        this.monthEndMiss.setText(formatter.format(c.get(Calendar.MONTH)));
        this.yearEndMiss.setText(String.valueOf(c.get(Calendar.YEAR)));
    }
}
