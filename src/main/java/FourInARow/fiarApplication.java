package FourInARow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class fiarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        /* Grab the FXML and controller */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fiar-view.fxml"));
        Parent root = fxmlLoader.load();


        /* Set the handler to the manager, and the handler to the controller */
        fiarController controller = fxmlLoader.getController();
        fiarHandler fiarHandler = new fiarHandler(controller);
        controller.setFiarHandler(fiarHandler);

        /* Load a new scene */
        Scene scene = new Scene(root, 800 , 600);
        stage.resizableProperty().setValue(false);
        stage.setTitle("Four in a row");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}