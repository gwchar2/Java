package Dictionary;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CustomGridPane extends GridPane {
    public CustomGridPane(Object [] args) {
        super();
        setGridPaneSettings(args);
    }

    private void setGridPaneSettings(Object [] args) {
        int labelC = 0,defC = 0;
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        for (Object obj : args) {
            if (obj instanceof Label) {
                Label label = (Label) obj;
                add(label, 0, labelC);
                labelC++;
            } else if (obj instanceof TextArea) {
                TextArea textArea = (TextArea) obj;
                add(textArea, 1, defC);
                defC++;
            }
        }

    }
}