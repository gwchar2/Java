package FourInARow;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

/*
This class handles the game process & UI.
 */
public class fiarHandler {

    private fiarController controller;

    private Logger logger = new Logger();

    private final int height = 85;      /* Height of the matrix blocks */

    private final int width = 114;      /* Width of the matrix blocks */

    public fiarHandler(fiarController controller) {
        this.controller = controller;
        initializeMatrix(controller.getGameMatrix());
    }

    /*
    *   Initializes the game grid matrix
    */
    public void initializeMatrix(GameSlot[][] gameMatrix){
        int invertedC = 4;
        for (int row = 0; row < gameMatrix.length; row++) {
            for (int col = 0; col < gameMatrix[row].length; col++) {
                /* Set the correct UI Coordinates */
                double topH = (row+1) * height;
                double topW = (col+1) * width;
                double botH = row * height;
                double botW = col * width;
                double centerX = (topW - botW)/2;
                double centerY = (topH - botH)/2;
                gameMatrix[row][invertedC] = new GameSlot(row, col,new Boundaries(topH,topW,botH,botW,centerX,centerY));
                invertedC--;
            }
            invertedC = 4;
        }
    }

    /*
    *   This class returns the slot we clicked on.
    *   Mostly for debugging purposes.
    */
    protected GameSlot getSlot(MouseEvent event){
        for (GameSlot[] row : controller.getGameMatrix()) {
            for (GameSlot slot : row) {
                if (event.getY() <= slot.getBoundary().getTopH() && event.getY() >= slot.getBoundary().getBotH()
                        && event.getX() <=slot.getBoundary().getTopW() && event.getX() >= slot.getBoundary().getBotW())
                    return slot;
            }
        }
        return null;
    }

    /*
    *   This function gets the next available slot in the column we clicked on.
    *   If there is no free column, left (we entered the last one) it will remove the button.
     */
    protected GameSlot getNextValid(int num) {
        int col;
        for (col = 0; col < controller.getGameMatrix().length; col++) {
            if (!controller.getGameMatrix()[num][col].isColored()) {
                logger.logInfo(getClass().getName(), "Free spot: [Row "+(col+1) + "] [Column "+(num+1)+"]");
                if (col == controller.getGameMatrix()[col].length-1) {
                    logger.logWarning(getClass().getName(), "No free left spot in column "+num);
                    controller.getClickButtons()[num].setVisible(false);    //Change visibility to false.
                }
                return controller.getGameMatrix()[num][col];
            }
        }
        return controller.getGameMatrix()[num][0];
    }

    /*
    *   This function adds a circle to the requested location.
     */
    protected void addSlot(int num){
        GameSlot sl = getNextValid(num);
        sl.setColored(true);
        sl.setCircle(new Circle());
        sl.getCircle().setRadius(sl.getBoundary().getRadius());
        sl.getCircle().setFill(controller.getTurnColor().getFill());
        controller.getGameGrid().add(sl.getCircle(), sl.getX(), sl.getY());
        if (!victory(sl)) {
            int counterDisabled = 0;
            for (int i = 0; i < controller.getClickButtons().length; i++){
                if (!controller.getClickButtons()[i].visibleProperty().get())
                    counterDisabled++;
            }
            if (counterDisabled == controller.getClickButtons().length)
                draw();
            else {
                changeColor();
            }
        }
        else {
            victoryMsg();
        }
    }

    /*
    *   This function creates an empty grid.
     */
    protected void newGame()    {
        for (GameSlot[] row : controller.getGameMatrix()) {
            for (GameSlot slot : row) {
                controller.getGameGrid().getChildren().remove(slot.getCircle());
            }
        }
        initializeMatrix(controller.getGameMatrix());
        for (int i = 0; i < controller.getClickButtons().length; i++){
            controller.getClickButtons()[i].setVisible(true);
        }
        logger.logInfo(getClass().getName(), "New game ready");
    }

    /*
    *   This function changes the current color being played.
     */
    protected void changeColor(){
        if (controller.getTurnColor().getFill() == Color.DODGERBLUE ){
            controller.getTurnColor().setFill(Color.RED);
            logger.logInfo(getClass().getName(), "Red player turn");
            return;
        }
        controller.getTurnColor().setFill(Color.DODGERBLUE);
        logger.logInfo(getClass().getName(), "Blue player turn");
    }

    /*
    *   This function checks to see if a win occurred after every turn.
     */
    protected boolean victory(GameSlot sl) {
        GameSlot[][] gameMatrix = controller.getGameMatrix();
        int rows = gameMatrix.length;
        int cols = gameMatrix[0].length;
        int inARow = 0;

        logger.logInfo(getClass().getName(), "Checking for victory");
        /* Create a loop that runs through the game matrix */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                /* Check horizontally to the right */
                if (j + 3 < cols) {                         // Make sure we are still in bounds
                    boolean foundHorizontalRight = true;
                    for (int k = 0; k < 4; k++) {
                        /* Check for same circle color / existing circle at all */
                        if (!gameMatrix[i][j + k].getCircle().hasProperties() || gameMatrix[i][j + k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundHorizontalRight = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i][j + k].getCircle().getFill() == controller.getTurnColor().getFill()){
                            inARow++;
                        }
                    }
                    if (foundHorizontalRight && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check horizontally to the left */
                if (j - 3 >= 0) {
                    boolean foundHorizontalLeft = true;
                    for (int k = 0; k < 4; k++) {
                        /* Check for same circle color / existing circle at all */
                        if (!gameMatrix[i][j - k].getCircle().hasProperties() || gameMatrix[i][j - k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundHorizontalLeft = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i][j - k].getCircle().getFill() == controller.getTurnColor().getFill()){
                            inARow++;
                        }
                    }
                    if (foundHorizontalLeft && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check vertically going up */
                if (i + 3 < rows) {
                    boolean foundVertical = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i + k][j].getCircle().hasProperties() || gameMatrix[i + k][j].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundVertical = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i + k][j].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundVertical && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check vertically going down */
                if (i - 3 > 0) {
                    boolean foundVerticalDown = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i - k][j].getCircle().hasProperties() || gameMatrix[i - k][j].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundVerticalDown = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i - k][j].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundVerticalDown && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check diagonally (up-right) */
                if (i + 3 < rows && j + 3 < cols) {
                    boolean foundDiagonalUpRight = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i + k][j + k].getCircle().hasProperties() || gameMatrix[i + k][j + k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundDiagonalUpRight = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i + k][j + k].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundDiagonalUpRight && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check diagonally (up-left) */
                if (i + 3 < rows && j - 3 >= 0) {
                    boolean foundDiagonalUpLeft = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i + k][j - k].getCircle().hasProperties() || gameMatrix[i + k][j - k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundDiagonalUpLeft = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i + k][j - k].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundDiagonalUpLeft && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check diagonally (down-right) */
                if (i - 3 >= 0 && j + 3 < cols) {
                    boolean foundDiagonalDownRight = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i - k][j + k].getCircle().hasProperties() || gameMatrix[i - k][j + k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundDiagonalDownRight = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i - k][j + k].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundDiagonalDownRight && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }

                /* Check diagonally (down-left) */
                if (i - 3 >= 0 && j - 3 >= 0) {
                    boolean foundDiagonalDownLeft = true;
                    for (int k = 0; k < 4; k++) {
                        if (!gameMatrix[i - k][j - k].getCircle().hasProperties() || gameMatrix[i - k][j - k].getCircle().getFill() != sl.getCircle().getFill()) {
                            foundDiagonalDownLeft = false;
                            break;
                        }
                        /* If they have the same color, add to the counter and continue */
                        else if (gameMatrix[i - k][j - k].getCircle().getFill() == controller.getTurnColor().getFill()) {
                            inARow++;
                        }
                    }
                    if (foundDiagonalDownLeft && inARow == 4) {
                        return true;
                    }
                    else
                        inARow = 0;
                }
            }
        }
        logger.logInfo(getClass().getName(), "No winner yet");
        return false;
    }

    /*
    *   This function handles the victory message that appears on victory
     */
    protected void victoryMsg(){
        logger.logInfo(getClass().getName(), "We have a winner!");

        /* Create a nice alert button and set its message */
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Victory Message");
        if (controller.getTurnColor().getFill() == Color.RED)
            alert.setHeaderText("\t\t\t\tRed player won!\n\t\tDo you wish to start a new game?");
        else
            alert.setHeaderText("\t\t\t\tBlue player won!\n\t\tDo you wish to start a new game?");
        alertButtons(alert);

    }

    /*
     *   This function handles the message that appears if there is a draw
     */
    protected void draw(){
        logger.logInfo(getClass().getName(), "DRAW!");

        /* Create a nice alert button and set its message */
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Draw Message");
        alert.setHeaderText("\t\t\tDraw! Nobody wins.\n\t\tDo you wish to start a new game?");

        alertButtons(alert);
    }

    /* Handles the buttons of the alert */
    private void alertButtons(Alert alert){

        /* Add buttons for Quit and New Game */
        ButtonType quitButton = new ButtonType("Quit");
        ButtonType newGameButton = new ButtonType("New Game");
        alert.getButtonTypes().setAll(quitButton, newGameButton);

        /* Show the alert and wait for a button response */
        Optional<ButtonType> result = alert.showAndWait();

        /* If user chooses to quit */
        if (result.isPresent() && result.get() == quitButton) {
            Platform.exit();
        }

        /* If User chooses to start a new game */
        else if (result.isPresent() && result.get() == newGameButton) {
            newGame();
        }
    }

}

