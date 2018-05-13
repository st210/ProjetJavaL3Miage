package Controller;

import Main.Test;
import Model.Mission;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNbEmpLabel();
        setNbMissCompl();
        setNbMissEnCours();
        setNbMissPlan();
        setDateEndMiss();
    }

    private void setNbEmpLabel() {
        this.nbEmpLabel.setText(String.valueOf(Test.company.getEmployees().size()) + " employés");
    }

    private void setNbMissCompl() {
        this.nbMissCompl.setText(String.valueOf(Test.company.getMissionPreparation().size()));
    }

    private void setNbMissEnCours() {
        this.nbMissEnCours.setText(String.valueOf(Test.company.getMissionInProgress().size()));
    }

    private void setNbMissPlan() {
        this.nbMissPlan.setText(String.valueOf(Test.company.getMissionScheduled().size()));
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
