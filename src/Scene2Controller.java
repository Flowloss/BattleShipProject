import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.control.Button;


public class Scene2Controller {
    @FXML
    public void handleButtonPress(ActionEvent event) {
        // Get the pressed button
        Button pressedButton = (Button) event.getSource();

        // Check if the button corresponds to a location with a ship
        if (!isShipAtButtonLocation(pressedButton)) {
            // Disable the button if there's no ship
            pressedButton.setDisable(true);
        } else {
            // Handle the case where there is a ship (e.g., change button color)
            pressedButton.setStyle("-fx-background-color: red;");
        }
    }

    private boolean isShipAtButtonLocation(Button btn) {
        // Implement your logic here to check if the ship is at the given button's location
        // This is just a stub and always returns false
        return false;
    }

}
