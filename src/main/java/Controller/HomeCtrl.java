package Controller;

import com.jfoenix.controls.JFXButton;
import com.sun.corba.se.impl.orb.ParserTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeCtrl implements Initializable {

    @FXML
    private JFXButton missionBtn;

    @FXML
    private JFXButton empBtn;

    @FXML
    private JFXButton homeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void empBtn(ActionEvent event) {
    }
}
