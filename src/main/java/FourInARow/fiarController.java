package FourInARow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;



public class fiarController{

    @FXML private Button clickOne;

    @FXML private Button clickTwo;

    @FXML private Button clickThree;

    @FXML private Button clickFour;

    @FXML private Button clickFive;

    @FXML private Button clickSix;

    @FXML private Button clickSeven;

    @FXML private Circle turnColor;

    @FXML private GridPane gameGrid;

    private Button []clickButtons;                          // The column buttons

    private GameSlot [][]gameMatrix;                        // Matrix representing the slots in the game

    private fiarHandler fiarHandler;                        // Game handler


    @FXML
    void mouseClick(MouseEvent event) {
        GameSlot slot = fiarHandler.getSlot(event);
    }

    @FXML
    void newTable(ActionEvent event) {
        fiarHandler.newGame();
    }

    @FXML
    void clickSeven(ActionEvent event) {
        fiarHandler.addSlot(6);
    }

    @FXML
    void clickSix(ActionEvent event) {
        fiarHandler.addSlot(5);
    }

    @FXML
    void clickFive(ActionEvent event) {
        fiarHandler.addSlot(4);
    }

    @FXML
    void clickFour(ActionEvent event) {
        fiarHandler.addSlot(3);
    }

    @FXML
    void clickThree(ActionEvent event) {
        fiarHandler.addSlot(2);
    }

    @FXML
    void clickTwo(ActionEvent event) {
        fiarHandler.addSlot(1);
    }

    @FXML
    void clickOne(ActionEvent event) {
        fiarHandler.addSlot(0);
    }

    protected Button[] getClickButtons(){
        return clickButtons;
    }
    protected GameSlot[][] getGameMatrix(){
        return this.gameMatrix;
    }

    protected GridPane getGameGrid(){
        return this.gameGrid;
    }

    protected Circle getTurnColor(){
        return this.turnColor;
    }


    protected void setFiarHandler(fiarHandler fiarHandler) {
        this.fiarHandler = fiarHandler;
    }

    public void initialize(){
        gameMatrix = new GameSlot[7][5]; /* The size of the game grid */
        clickButtons = new Button[]{clickOne, clickTwo, clickThree, clickFour, clickFive, clickSix, clickSeven};
    }


}