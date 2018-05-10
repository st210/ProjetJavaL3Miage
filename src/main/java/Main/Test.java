package Main;

import Controller.EmpPageCtrl;
import Model.Company;
import Model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Test extends Application {

    public static Company company = new Company();
    private static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {
        showDashboardView();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void showDashboardView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/home.fxml"));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    public static void showMissionsView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/missions.fxml"));
        stage.setTitle("Missions");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    public static void showEmployeesView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/employee.fxml"));
        stage.setTitle("Personnel");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    public static void showEmployeePage(Employee employee) throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader(Test.class.getResource("/view/empPage.fxml"));
        AnchorPane anchorPane = loader.load();
        // Get the Controller from the FXMLLoader
        EmpPageCtrl controller = loader.getController();
        controller.fillData(employee);
        stage.setTitle("Personnel");
        stage.setScene(new Scene(anchorPane, 1080, 720));
        stage.show();
    }
    //TODO Gestion modification date
}