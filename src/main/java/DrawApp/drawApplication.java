package DrawApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class drawApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /* Grab the FXML and controller */
        FXMLLoader fxmlLoader = new FXMLLoader(drawApplication.class.getResource("draw-view.fxml"));
        Parent root = fxmlLoader.load();

        /* Set the handler to the manager, and the handler to the controller */
        drawController controller = fxmlLoader.getController();
        drawHandler drawHandler = new drawHandler(controller);
        controller.setGameManager(drawHandler);

        /* Load a new scene */
        Scene scene = new Scene(root, 640 , 450);
        stage.resizableProperty().setValue(false);
        stage.setTitle("Painter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}