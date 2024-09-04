package Dictionary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        /* Grab the FXML and controller */
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("dictionary.fxml"));
        Parent root = fxmlLoader.load();

        /* Load a new scene */
        Scene scene = new Scene(root, 640 , 480);
        stage.resizableProperty().setValue(false);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}