package Dictionary;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

public class CustomAlert extends Alert {
    public CustomAlert(Alert.AlertType alertType, GridPane gridPane) {
        super(alertType);
        getDialogPane().setGraphic(null);
        setHeaderText(null);
        switch (alertType) {
            case CONFIRMATION:
                getDialogPane().setContent(gridPane);
                setTitle("Enter Word and Definition");
                break;
            case ERROR:
                setTitle("Input Error");
                setContentText("Both fields must be filled out.");
                break;
            case WARNING:
                getButtonTypes().addAll(ButtonType.CANCEL);
                setTitle("Definition Update Error");
                setContentText("The word does not currently exist in the dictionary.\nDo you wish to add it?");
                break;
            default:
                break;
        }
    }
}