import Controller.FabController;
import Model.Company;
import Model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Test extends Application {

    public static void main(String[] args) {

        launch(args);

//        Company e = new Company("Super Mario");
//        List<Employee> l = new ArrayList<>();
//        e.showAllEmployees();
//        Employee vador = new Employee("Vador", "Darth", "01/01/1990");
//        e.insertEmployee(vador);
//        System.out.println(e.findEmployeeById(51));
//        e.updateEmployeeFirstName(vador, "Luke");
//        System.out.println(e.findEmployeeById(51));
//        e.deleteEmployee(vador);
//        System.out.println(e.findEmployeeById(51));

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/home.fxml"));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
}