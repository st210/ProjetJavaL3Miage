package Controller;

import Main.Test;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Root {
    @FXML
    public JFXButton homeBtn;
    @FXML
    public JFXButton missionBtn;
    @FXML
    public JFXButton persBtn;

    public void goDashboard() {
        try {
            Test.showDashboardView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goMissions() {
        try {
            Main.Test.showMissionsView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goEmployees() {
        try {
            Main.Test.showEmployeesView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;

        if (actionEvent.getSource() == homeBtn) {
            //get reference to the button's stage
            stage = (Stage) homeBtn.getScene().getWindow();

            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("view/home.fxml"));

        } else if (actionEvent.getSource() == missionBtn) {
            stage = (Stage) missionBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("view/missions.fxml"));
        } else {
            stage = (Stage) persBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("view/employee.fxml"));
        }

        //create a new scene with root and set the stage
        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }
}
