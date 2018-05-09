import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    private static final int SPACING = 8;
    private Company company = new Company("Company");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Missions Personnel");

        Button dashboardButton = new Button("Tableau de bord");
        Button missionsButton = new Button("Missions");
        Button staffButton = new Button("Personnel");

        VBox buttonBox = new VBox();
        buttonBox.setPadding(new Insets(SPACING));
        buttonBox.setSpacing(SPACING);
        buttonBox.getChildren().addAll(dashboardButton, missionsButton, staffButton);
        for (Node child : buttonBox.getChildren()) {
            VBox.setVgrow(child, Priority.ALWAYS);
        }

        Pane dashboardPane = new DashboardPane();
        Pane missionsPane = new MissionsPane(company);
        Pane staffPane = new StaffPane(company);

        BorderPane rootPane = new BorderPane();
        rootPane.setLeft(buttonBox);
        rootPane.setCenter(dashboardPane);

        // Actions

        dashboardButton.setOnAction(event -> rootPane.setCenter(dashboardPane));
        missionsButton.setOnAction(event -> rootPane.setCenter(missionsPane));
        staffButton.setOnAction(event -> rootPane.setCenter(staffPane));

        // Scene

        Scene scene = new Scene(rootPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();

        Company company = new Company();

        // Création d'un employé
        Employee newEmp = new Employee("Yann", "Godeau", "06/04/2018");
        newEmp.writeEmployeeCSV();

        System.out.println(newEmp.toString());

        // Ajouter compétence
        newEmp.addCompetence(competenceMgt.getCompetenceByIDFromCSV("A.1."));

        // Création mission
        Mission newMission = new Mission("New Mission", 10, company);
        newMission.writeMissionCSV();
        System.out.println(newMission.toString());

        // Ajouter un besoin en compétence
        newMission.addCompetence(competenceMgt.getCompetenceByIDFromCSV("A.1."), 2);

        // Ajouter un employé à un mission
        newMission.addEmployee(competenceMgt.getCompetenceByIDFromCSV("A.1."), newEmp);

        launch(args);
    }

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
