import Model.Company;
import Model.Competence;
import Model.CompetenceMgt;
import Model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {

//        launch(args);
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

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/home.fxml"));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
}