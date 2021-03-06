package model;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import java.awt.*;


public class CreateShapeCommand implements ICommand, IUndoable{

    private ShapeType shapeType;
    ApplicationState appState;
    private Point startPoint;
    private Point endPoint;
    private ShapeList shapeList;
    Color pColor;
    Color sColor;
    private ShapeShadingType shapeShadingType;
    public IShape iShape;
    boolean pastedShape;

    public CreateShapeCommand(ApplicationState appState, Point startPoint, Point endPoint, ShapeList shapeList, Color pColor, Color sColor, ShapeShadingType shapeShadingType, ShapeType shapeType){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shapeList = shapeList;
        this.appState = appState;
        this.pColor = pColor;
        this.sColor = sColor;
        this.shapeShadingType = shapeShadingType;
        this.shapeType = shapeType;
    }

    public void run() {
        Shape shape = new Shape(startPoint,endPoint,appState,pColor,sColor,shapeShadingType,shapeType);
        ShapeFactory shapeFactory = new ShapeFactory();
        iShape = shapeFactory.makeShape(shape);
        shapeList.addShape(iShape);
        CommandHistory.add(this);
    }


    @Override
    public void undo() { shapeList.removeShape(); }

    @Override
    public void redo() {
        shapeList.redoShape();
    }
}