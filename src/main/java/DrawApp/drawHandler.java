package DrawApp;

import javafx.scene.paint.Color;
import java.awt.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class drawHandler extends Logger{

    private final drawController drawController;                                  /* Controller Class */

    public drawHandler (drawController drawController){
        this.drawController = drawController;
    }

    /* Handles the line drawing feature */
    protected void drawLine(){
        logInfo(getClass().getName(),"Drawing a Line");
        try {
            if (!(isWithinDrawingArea(drawController.getEndX(), drawController.getEndY()) &&
                    isWithinDrawingArea(drawController.getStartX(), drawController.getStartY())))
                throw new IllegalStateException("User is out of bounds!");

            /* Set the new line according to the final coordinates */
            Line line = new Line(drawController.getStartX(), drawController.getStartY(), drawController.getEndX(), drawController.getEndY());
            setStrokeFill(line);

            /* Add to Pane List */
            drawController.getDrawingAreaPane().getChildren().add(line);

        }catch (IllegalStateException illegalStateException){
            logError(getClass().getName(),illegalStateException.getMessage());
        }
    }

    /* Handles the rectangle drawing feature */
    protected void drawRectangle(){
        logInfo(getClass().getName(),"Drawing a Rectangle");
        /* Draw the rectangle according to the direction of the clicks and make sure it is not bigger than the boundaries */
        try {
            if (!(isWithinDrawingArea(drawController.getEndX(), drawController.getEndY()) && isWithinDrawingArea(drawController.getStartX(), drawController.getStartY())))
                throw new IllegalStateException("User is out of bounds!");
            Rectangle rectangle = new Rectangle(Math.min(drawController.getStartX(), drawController.getEndX()), Math.min(drawController.getStartY(), drawController.getEndY()), Math.abs(drawController.getEndX() - drawController.getStartX()), Math.abs(drawController.getEndY() - drawController.getStartY()));

            /* Set the stroke size & fill color */
            setStrokeFill(rectangle);

            /* Add the rectangle to the Pane list */
            drawController.getDrawingAreaPane().getChildren().add(rectangle);

        }catch (IllegalStateException illegalStateException){
                logError(getClass().getName(),illegalStateException.getMessage());
        }
    }

    /* Handles the triangle drawing feature */
    protected void drawTriangle(){
        logInfo(getClass().getName(),"Drawing a Triangle");
        double sideLength = Math.sqrt(Math.pow(drawController.getEndX() - drawController.getStartX(), 2) + Math.pow(drawController.getEndY() - drawController.getStartY(), 2));
        double height = sideLength * Math.sqrt(3) / 2; // Height of an equilateral triangle
        drawController.setCenterX((drawController.getStartX() + drawController.getEndX()) / 2);
        drawController.setCenterY((drawController.getStartY() + drawController.getEndY()) / 2);

        /* Draw the Triangle according to the direction of the clicks and make sure it is not bigger than the boundaries */
        try {
            if (!(isWithinDrawingArea(drawController.getEndX(), drawController.getEndY()) && isWithinDrawingArea(drawController.getStartX(), drawController.getStartY()) &&
                    isWithinDrawingArea(drawController.getCenterX() - sideLength / 2, drawController.getCenterY() + height / 2) &&
                    isWithinDrawingArea(drawController.getCenterX() + sideLength / 2, drawController.getCenterY() + height / 2) &&
                    isWithinDrawingArea(drawController.getCenterX(), drawController.getCenterY() - height / 2) ))
                throw new IllegalStateException("User is out of bounds!");

            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    drawController.getCenterX(), drawController.getCenterY() - height / 2,
                    drawController.getCenterX() - sideLength / 2, drawController.getCenterY() + height / 2,
                    drawController.getCenterX() + sideLength / 2, drawController.getCenterY() + height / 2
            );

            /* Set the stroke size & fill color */
            setStrokeFill(triangle);

            /* Add the rectangle to the Pane list */
            drawController.getDrawingAreaPane().getChildren().add(triangle);

        }catch (IllegalStateException illegalStateException){
            logError(getClass().getName(),illegalStateException.getMessage());
        }
    }

    /* Handles the circle drawing feature */
    protected void drawCircle(){
        logInfo(getClass().getName(),"Drawing a Circle");

        /* Calculate the center & radius of the circle */
        drawController.setCenterX((drawController.getStartX() + drawController.getEndX()) / 2.0);
        drawController.setCenterY((drawController.getStartY() + drawController.getEndY()) / 2.0);
        double radius = Math.sqrt(Math.pow(drawController.getEndX() - drawHandler.this.drawController.getCenterX(), 2) + Math.pow(drawController.getEndY() - drawController.getCenterY(), 2));

        /* Draw the circle according to the direction of the clicks and make sure it is not bigger than the boundaries */
        try {
            if (!(drawHandler.this.drawController.getCenterX()+radius < drawController.getDrawingAreaPane().getMaxWidth() &&
                    drawController.getCenterY()+radius < drawController.getDrawingAreaPane().getMaxHeight() && drawController.getCenterY()-radius > 0 &&
                    drawHandler.this.drawController.getCenterX()-radius > 0))
                    throw new IllegalStateException("User is out of bounds!");

            Circle circle = new Circle(drawHandler.this.drawController.getCenterX(), drawController.getCenterY(), radius);

            /* Set the stroke size & fill color */
            setStrokeFill(circle);

            /* Add the rectangle to the Pane list */
            drawController.getDrawingAreaPane().getChildren().add(circle);

        }catch (IllegalStateException illegalStateException){
            logError(getClass().getName(),illegalStateException.getMessage());
        }
    }

    /* Handles filling and setting the stroke with the correct color */
    protected void setStrokeFill(Object obj){
        try {
            if (!(obj instanceof javafx.scene.shape.Shape))
                throw new IllegalComponentStateException("Invalid shape type");
            ((javafx.scene.shape.Shape) obj).setStroke(drawController.getdrawingColor().getValue());
            ((javafx.scene.shape.Shape) obj).setStrokeWidth(drawController.getSizeOfPen().getRadius());
            if (drawController.getFillBox().isSelected()) {
                ((javafx.scene.shape.Shape) obj).setFill(drawController.getdrawingColor().getValue());
            }else {
                ((javafx.scene.shape.Shape) obj).setFill(Color.TRANSPARENT);
            }
        }catch (IllegalComponentStateException e) {
            logError(getClass().getName(), e.getMessage());
        }
    }

    /* Checks for user boundaries */
    protected boolean isWithinDrawingArea(double x, double y) {
        return x >= 0 && x <= drawController.getDrawingAreaPane().getWidth()-4 && y >= 0 && y <= drawController.getDrawingAreaPane().getHeight()-4;
    }

}
