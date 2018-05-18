package Controller;

import Main.Test;
import Model.Employee;
import Model.Mission;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.text.ParseException;

public abstract class Route {

    @FXML
    public JFXButton homeBtn;
    @FXML
    public JFXButton missionBtn;
    @FXML
    public JFXButton persBtn;

    public static Employee empToLoad = null; //  ¯\_(ツ)_/¯
    public static Mission missToLoad = null; //  ¯\_(ツ)_/¯

    public void goDashboard() throws IOException {
        Test.showDashboardView();
    }

    public void goMissions() throws IOException {
        Main.Test.showMissionsView();
    }

    public void goEmployees() throws IOException {
        Main.Test.showEmployeesView();
    }

    public void goEmpPage() throws IOException, ParseException {
        Main.Test.showEmployeePage(empToLoad);
    }

    public void goEmpPageEmpty(ActionEvent actionEvent) throws IOException, ParseException {
        Route.empToLoad = null;
        Main.Test.showEmployeePage(null);
    }

    public void goMissPage() throws IOException {
        Main.Test.showMissionPage(missToLoad);
    }

    public void goMissPageEmpty() throws IOException {
        Route.missToLoad = null;
        Main.Test.showMissionPage(null);
    }

}
