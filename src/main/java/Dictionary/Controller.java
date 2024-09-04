package Dictionary;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.security.InvalidKeyException;
import java.util.Iterator;
import java.util.Optional;

public class Controller {

    @FXML private Button remove;
    @FXML private Button cancel;
    @FXML private TextField searchText;
    @FXML private ListView<HBox> listView;
    @FXML private TextArea meaningBox;
    private TextArea wordField;
    private TextArea definitionField;
    private final Label wordLabel = new Label("Word:");
    private final Label definitionLabel = new Label("Definition:");
    private Optional<ButtonType> result;
    private Dictionary dictionary;
    private boolean bRemoveMode = false;
    private enum Update {
        NEW_ENTRY,
        UPDATE_WORD,
        UPDATE_DEFINITION
    }


    public void initialize() {
        dictionary = new Dictionary();
        updateListView();
        meaningBox.setMouseTransparent(true);
        if (!dictionary.getAllEntries().isEmpty())
            meaningBox.setText(dictionary.getAllEntries().firstEntry().getValue());
        listView.setOnMouseClicked(this::selectWord);

        /* Initialize the wordField and definitionField for later */
        wordField = new TextArea();
        definitionField = new TextArea();
        definitionField.setWrapText(true);
        wordField.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.TAB) {
                definitionField.requestFocus();
                e.consume();
            }
        });
        wordField.setPrefSize(400, 30);
        definitionField.setPrefSize(400, 70);
    }

    @FXML
    void helpAbout(){
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Maman 14 Dictionary");
        aboutAlert.setContentText("Version 1.1\nAuthor: Tommer Toledo");
        aboutAlert.showAndWait();
    }

    @FXML
    void close() {
        Platform.exit();
    }

    @FXML
    void cancel() {
        setRemoveSettings(false);
    }

    @FXML
    void remove() {
        Iterator<HBox> iterator = listView.getItems().iterator();
        while (iterator.hasNext()) {
            HBox box = iterator.next();
            ObservableList<Node> children = box.getChildren();
            boolean checkBoxSelected = false;

            for (Node child : children) {
                if (child instanceof CheckBox checkBox && checkBox.isSelected()) {
                    checkBoxSelected = true;
                    break;
                }
            }

            if (checkBoxSelected) {
                iterator.remove();
                try {
                    dictionary.removeEntry(((Label) box.getChildren().get(1)).getText());
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("CheckBox selected in HBox");
            }
        }
        if (dictionary.getAllEntries().isEmpty())
            meaningBox.clear();
        setRemoveSettings(false);
    }

    @FXML
    void removeMode() {
        if (bRemoveMode && remove.isVisible()){
            setRemoveSettings(false);
        }
        else if (!dictionary.getAllEntries().isEmpty()) {
            setRemoveSettings(true);
        }

    }

    @FXML
    void newEntry() {
        changeManager(Update.NEW_ENTRY);
    }

    @FXML
    void updateMeaning() {
        changeManager(Update.UPDATE_DEFINITION);
    }

    @FXML
    void updateWord() {
        wordLabel.setText("Word to change:");
        definitionLabel.setText("New Word:");
        changeManager(Update.UPDATE_WORD);
    }

    @FXML
    void selectWord(MouseEvent event) {
        HBox selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            return;
        for (Node node : selectedItem.getChildren()) {
            if (node instanceof Label label) {
                String definition = dictionary.search(label.getText());
                meaningBox.setText(definition);
            }
        }
    }

    @FXML
    void searchText() {
        searchText.setText("");
        searchText.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                scrollSelect(searchText.getText());
                e.consume();
            }
        });
    }

    @FXML
    void search() {
        if (dictionary.search(searchText.getText()) == null && searchText.getText().compareTo("Search For...") != 0) {
            CustomAlert alert = new CustomAlert(Alert.AlertType.WARNING, null);
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                newEntry();
        }
        else
            scrollSelect(searchText.getText());
        searchText.setText("Search For...");
    }

    @FXML
    private void updateListView() {
        listView.getItems().clear();
        dictionary.getAllEntries().forEach((word, meaning) -> {
            HBox hbox = new HBox(14);
            hbox.setPrefHeight(30);
            hbox.setPadding(new Insets(1.0));
            if (bRemoveMode) {
                CheckBox checkBox = new CheckBox();
                checkBox.setPrefHeight(30);
                hbox.getChildren().add(checkBox);
            }
            Label label = new Label(word);
            label.setPrefSize(140,30);
            hbox.getChildren().add(label);
            listView.getItems().add(hbox);
        });
    }

    private void scrollSelect(String word){
        for (HBox box : listView.getItems()){
            for (Node node : box.getChildren()){
                if (node instanceof Label && ((Label)node).getText().compareTo(word) == 0){
                    listView.scrollTo(box);
                    listView.getSelectionModel().select(box);
                    String definition = dictionary.search(((Label)node).getText());
                    meaningBox.setText(definition);
                }
            }
        }
    }

    private void setRemoveSettings(Boolean bool){
        bRemoveMode = bool;
        remove.setVisible(bool);
        cancel.setVisible(bool);
        updateListView();
    }

    private void fixSetup(String word, String definition){
        wordField.clear();
        definitionField.clear();
        wordLabel.setText("Word:");
        definitionLabel.setText("Definition:");
        System.out.println("Word: " + word);
        System.out.println("Definition: " + definition);
        updateListView();
        scrollSelect(word);
    }

    private void changeManager(Update update){
        /* Creating a GridPane for the label & text areas */
        Object[] args = {wordField,definitionField,wordLabel,definitionLabel};
        GridPane gridPane = new CustomGridPane(args);

        /* Creating the Alert */
        Alert alert = new CustomAlert(Alert.AlertType.CONFIRMATION,gridPane);

        while (true) {
            result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("User canceled the input.");
                fixSetup(null,null);
                break;
            } else if (result.isPresent() && result.get() == ButtonType.OK) {
                String word = wordField.getText().trim();
                String definition = definitionField.getText().trim();

                /* User must enter both fields */
                if (word.isEmpty() || definition.isEmpty()) {
                    new CustomAlert(Alert.AlertType.ERROR, null).showAndWait();
                } else {
                    try {
                        switch (update) {
                            case NEW_ENTRY:
                                dictionary.addEntry(word, definition);
                                fixSetup(word, definition);
                                break;
                            case UPDATE_WORD:
                                String defHolder = dictionary.search(word);
                                dictionary.removeEntry(word);
                                dictionary.addEntry(definition, defHolder);
                                break;
                            case UPDATE_DEFINITION:
                                dictionary.updateEntry(word, definition);
                                break;
                        }
                        break;
                    } catch (InvalidKeyException e) {
                        new CustomAlert(Alert.AlertType.WARNING, null).showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            dictionary.addEntry(word, definition);
                        }
                        fixSetup(word, definition);
                        break;
                    }
                }
            }
        }
    }
}
