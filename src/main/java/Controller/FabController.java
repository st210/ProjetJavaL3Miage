package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Parent;

public class FabController extends Parent {

    public FabController() {
        JFXButton addEmpBtn = new JFXButton("+");
        addEmpBtn.setButtonType(JFXButton.ButtonType.RAISED);
    }
}
