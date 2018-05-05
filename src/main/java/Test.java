import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test extends Application {

    public static void main(String[] args) {
        /*
        CompanyDAO e = new Company("Super Mario", pathEmployees);
        List<Employee> l = new ArrayList<>();
        e.showAllEmployees(pathEmployees);
        Employee vador = new Employee("Vador", "Darth", "01/01/1990");
        e.insertEmployee(vador);
        System.out.println(e.findEmployeeById(51));
        e.updateEmployeeFirstName(vador, "Luke");
        System.out.println(e.findEmployeeById(51));
        e.deleteEmployee(vador);
        e.showAllEmployees(pathEmployees);
        */

        launch(args);
        Company company = new Company("Company Test");
        CompetenceMgt cm = new CompetenceMgt();
        Employee e = new Employee("Yann", "Godeau", "12/12/2008");
        company.addEmployee(e);
        try {
            e.writeEmployeeCSV();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        company.getEmployee("51").addCompetence(cm.getCompetenceByID("A.2."));

        Mission mission = new Mission("Ma mission", 10);
        try {
            mission.writeMissionCSV();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/home.fxml"));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
}