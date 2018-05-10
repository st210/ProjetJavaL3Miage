package Main;

import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    private static final int SPACING = 8;
    public static Company company = new Company("Company");
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {
        showDashboardView();
//        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
//        stage.setTitle("Home");
//        stage.setScene(new Scene(root, 1080, 720));
//        stage.show();
    }


    public static void main(String[] args) throws IOException {
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

    public static void showEmployeePage() throws IOException {
        Parent root = FXMLLoader.load(Test.class.getResource("/view/empPage.fxml"));
        stage.setTitle("Personnel");
        stage.setScene(new Scene(root, 1080, 720));
        stage.show();
    }


    //////////////////////////////////////////////////////////////////////////////


    private static class DashboardPane extends BorderPane {
        private DashboardPane() {
            setCenter(new Label("Tableau de bord"));
        }
    }

    private static class MissionsPane extends BorderPane {

        private Company company;
        private TableView tableView;
        private ObservableList<Mission> missionList;

        private MissionsPane(Company company) {
            this.company = company;
            this.tableView = new TableView();
            this.missionList = FXCollections.observableArrayList(this.company.getMissions());

            setCenter(tableView);

            TableColumn name = new TableColumn("Nom");
            TableColumn status = new TableColumn("État");

            name.setCellValueFactory(new PropertyValueFactory<Mission, String>("name"));
            status.setCellValueFactory(new PropertyValueFactory<Mission, String>("status"));

            this.tableView.setItems(missionList);

            this.tableView.getColumns().addAll(name, status);
        }
    }

    private static class StaffPane extends BorderPane {

        private Company company;
        private TableView tableView;
        private ObservableList<Employee> empList;

        private StaffPane(Company company) {
            this.company = company;
            this.tableView = new TableView();
            this.empList = FXCollections.observableArrayList(this.company.getEmployees());

            setCenter(tableView);

            TableColumn lastName = new TableColumn("Nom");
            TableColumn firstName = new TableColumn("Prénom");
            TableColumn entryDate = new TableColumn("Date d'entrée");

            lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
            firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstname"));
            entryDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("entryIntoCompany"));


            this.tableView.setItems(empList);

            this.tableView.getColumns().addAll(lastName, firstName, entryDate);

        }
    }

    //TODO Gestion modification date
}
