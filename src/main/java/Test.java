import Model.Company;
import Model.Employee;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Test extends Application {

    private static final int SPACING = 8;
    private Company company = new Company("Company");

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Pane missionsPane = new MissionsPane();
        Pane staffPane = new StaffPane(company);

        BorderPane rootPane = new BorderPane();
        rootPane.setLeft(buttonBox);
        rootPane.setCenter(dashboardPane);

        // Actions

        dashboardButton.setOnAction(event -> {
            rootPane.setCenter(dashboardPane);
        });
        missionsButton.setOnAction(event -> {
            rootPane.setCenter(missionsPane);
        });
        staffButton.setOnAction(event -> {
            rootPane.setCenter(staffPane);
        });

        // Scene

        Scene scene = new Scene(rootPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class DashboardPane extends BorderPane {
        private DashboardPane() {
            setCenter(new Label("Tableau de bord"));
        }
    }

    private static class MissionsPane extends BorderPane {
        private MissionsPane() {
            TableView tableView = new TableView();
            setCenter(tableView);
            tableView.getColumns().addAll(
                    new TableColumn("Nom"),
                    new TableColumn("État"));
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
}
