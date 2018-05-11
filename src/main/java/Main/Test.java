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

    // TODO Suppression employé
    // TODO Suppression mission
    // TODO Modification employé
    // TODO Modification mission
    // TODO Filtre compétences page employé
    // TODO Sélection compétence page employé
    // TODO Proposition employé pour mission
    // TODO Dashboard fonctionnel

    @Override
    public void start(Stage primaryStage) throws IOException {
        showDashboardView();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Charger la scène Dashboard
     *
     * @throws IOException
     */
    public static void showDashboardView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/home.fxml"));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    /**
     * Charger la scène Missions
     *
     * @throws IOException
     */
    public static void showMissionsView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/missions.fxml"));
        stage.setTitle("Missions");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    /**
     * Charger la scène Personnel
     *
     * @throws IOException
     */
    public static void showEmployeesView() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/employee.fxml"));
        stage.setTitle("Personnel");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }

    /**
     * Charger la scène Page Employé
     *
     * @param employee L'employé à charger dans la scène
     * @throws IOException
     * @throws ParseException
     */
    public static void showEmployeePage(Employee employee) throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader(Test.class.getResource("/view/empPage.fxml"));
        AnchorPane anchorPane = loader.load();
        EmpPageCtrl controller = loader.getController();
        if (employee != null) {
            controller.setCreationMode(false);
            // Get the Controller from the FXMLLoader
            controller.fillData(employee);
        } else {
            controller.setCreationMode(true);
        }
        stage.setTitle("Personnel");
        stage.setScene(new Scene(anchorPane, 1080, 720));
        stage.show();
    }
    //TODO Gestion modification date
}