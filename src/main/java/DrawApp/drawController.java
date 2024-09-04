package DrawApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class drawController extends Logger {

    @FXML private Pane drawingAreaPane;

    @FXML private ColorPicker drawingColor;

    @FXML private ToggleGroup sizeToggleGroup;

    @FXML private TitledPane penSize;

    @FXML private RadioButton smallRadioButton;

    @FXML private RadioButton mediumRadioButton;

    @FXML private RadioButton largeRadioButton;

    @FXML private TitledPane shape;

    @FXML private ToggleGroup shapeToggleGroup;

    @FXML private RadioButton lineRadioButton;

    @FXML private RadioButton squareRadioButton;

    @FXML private RadioButton triangleRadioButton;

    @FXML private RadioButton circleRadioButton;

    @FXML private RadioButton defaultRadioButton;

    @FXML private Button undo;

    @FXML private Button clear;

    @FXML private CheckBox fillBox;

    private drawHandler drawHandler;

    private double startX,startY;

    private double endX,endY;

    private double centerX,centerY;

    private PenSize sizeOfPen = PenSize.MEDIUM;

    private DrawApp.Shape userShape = DrawApp.Shape.DEFAULT;

    private boolean sentmessage = false;

    @FXML
    void drawingAreaMousePressed(MouseEvent e) {
        if (drawHandler.isWithinDrawingArea(e.getX(), e.getY())) {
            startX = e.getX();
            startY = e.getY();
            drawHandler.logInfo(getClass().getName(),"User started draw sequence at x"+startX+" y"+startY);
        }
    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent e) {
        if (!sentmessage) {
            drawHandler.logInfo(getClass().getName(), "Drawing Freely");
            sentmessage = true;
        }
        if (drawHandler.isWithinDrawingArea(e.getX(), e.getY()) && userShape == DrawApp.Shape.DEFAULT) {
            Circle newCircle = new Circle(e.getX(),e.getY(), sizeOfPen.getRadius(),drawingColor.getValue());
            drawingAreaPane.getChildren().add(newCircle);
        }

    }

    @FXML
    void drawingAreaMouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        switch (userShape){
            case LINE:
                drawHandler.drawLine();
                break;
            case RECTANGLE:
                drawHandler.drawRectangle();
                break;
            case TRIANGLE:
                drawHandler.drawTriangle();
                break;
            case CIRCLE:
                drawHandler.drawCircle();
                break;
            default:
                break;
        }
        sentmessage = false;
        drawHandler.logInfo(getClass().getName(),"User ended draw sequence at x"+endX+" y"+endY);

    }

    @FXML
    void shapeRadioButtonSelected(ActionEvent event) {
        userShape = (DrawApp.Shape) shapeToggleGroup.getSelectedToggle().getUserData();
        drawHandler.logInfo(getClass().getName(), "User chose shape "+userShape.getShape());
    }

    @FXML
    void sizeRadioButtonSelected(ActionEvent event) {
        sizeOfPen = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
        drawHandler.logInfo(getClass().getName(), "User chose pen size "+sizeOfPen.getRadius());
    }

    @FXML
    void clearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear();
        drawHandler.logInfo(getClass().getName(), "Cleared the panel");
    }

    @FXML
    void undoButtonPressed(ActionEvent event) {
        int count = drawingAreaPane.getChildren().size();
        if (count > 0){
            drawingAreaPane.getChildren().remove(count-1 );
            drawHandler.logInfo(getClass().getName(), "Performed Undo");
        }
        else
            drawHandler.logWarning(getClass().getName(), "Nothing to remove");

    }

    @FXML
    void colorListener(ActionEvent event){
        drawHandler.logInfo(getClass().getName(),"User chose color: "+drawingColor.getValue());
    }

    /* Configures the Draw Handler */
    void setGameManager(drawHandler drawHandler){
        this.drawHandler = drawHandler;
        drawHandler.logInfo(getClass().getName(), "Initialized the game");
    }

    /* Initializes the paint app */
    public void initialize(){
        smallRadioButton.setUserData(PenSize.SMALL);
        mediumRadioButton.setUserData(PenSize.MEDIUM);
        largeRadioButton.setUserData(PenSize.LARGE);
        lineRadioButton.setUserData((DrawApp.Shape.LINE));
        squareRadioButton.setUserData((DrawApp.Shape.RECTANGLE));
        triangleRadioButton.setUserData((DrawApp.Shape.TRIANGLE));
        circleRadioButton.setUserData((DrawApp.Shape.CIRCLE));
        defaultRadioButton.setUserData((Shape.DEFAULT));

    }

    protected Pane getDrawingAreaPane() {
        return drawingAreaPane;
    }

    protected ColorPicker getdrawingColor(){
        return this.drawingColor;
    }

    protected PenSize getSizeOfPen(){
        return this.sizeOfPen;
    }

    protected CheckBox getFillBox() {
        return fillBox;
    }

    protected double getStartX() {
        return startX;
    }

    protected double getStartY() {
        return startY;
    }

    protected double getEndX() {
        return endX;
    }

    protected double getEndY() {
        return endY;
    }

    protected double getCenterX() {
        return centerX;
    }

    protected double getCenterY() {
        return centerY;
    }

    protected void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    protected void setCenterY(double centerY) {
        this.centerY = centerY;
    }
}
