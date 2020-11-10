package model;

import java.awt.*;
import java.util.ArrayList;

import model.interfaces.IShape;
import model.persistence.ApplicationState;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;

public class ShapeList {

//    public static Stack<Shape> shapeList = new Stack<>();
//    public static Stack<Shape> deletedShapeList = new Stack<>();
//    public static Stack<Shape> selectedShapeList = new Stack<>();
//    public static Stack<Shape> copiedShapeList = new Stack<>();

    public static ArrayList<IShape> shapeList = new ArrayList<>();
    public static ArrayList<IShape> deletedShapeList = new ArrayList<>();
    public static ArrayList<IShape> selectedShapeList = new ArrayList<>();
    public static ArrayList<IShape> copiedShapeList = new ArrayList<>();

    private static PaintCanvas paintCanvas;
    public ApplicationState appState;


    public ShapeList(PaintCanvasBase paintCanvas, ApplicationState appState) {
        this.paintCanvas = (PaintCanvas) paintCanvas;
        this.appState = appState;
    }

    public void addShape(IShape shape){
        shapeList.add(shape);
        deletedShapeList.clear();
        shapeListDrawer(shapeList,selectedShapeList);
    }


    public void shapeListDrawer(ArrayList<IShape> shapeList, ArrayList<IShape> selectedShapeList){

        Graphics2D g = paintCanvas.getGraphics2D();
        g.setColor(Color.white);
        g.fillRect(0,0,9999,9999);
        for (IShape s: shapeList){
            s.draw(g);
            if(s.getSize()>0){
                System.out.println("OH YAS THIS IS GROUP");
                s.drawChildren(g);
            }
        }
        for (IShape z: selectedShapeList){
            ShapeDecorator shapeDecorator = new ShapeDecorator(paintCanvas);
            if(z.getSize()>0){
                System.out.println("BRO HOW DO I OUTLINE THIS");
                shapeDecorator.outlineGroup(z);
            }
            else {
                shapeDecorator.outlineShape(z);
            }
        }
//        CommandHistory.add(this);
    }

    public void removeShape(){
        if(shapeList.size() == 0) {
            System.out.println("There's nothing in the list to remove!");
            return;
        }
        //get last shape in Shape List
        IShape lastShape = shapeList.get(shapeList.size()-1);
        shapeList.remove(lastShape);
        deletedShapeList.add(lastShape);
//        }
        shapeListDrawer(shapeList,selectedShapeList);
    }

    public void removeSpecificShape(IShape s){
        if(shapeList.size() == 0) {
            System.out.println("There's nothing in the list to remove!");
            return;
        }
        //get last shape in Shape List
        shapeList.remove(s);
        deletedShapeList.add(s);
//        }
        shapeListDrawer(shapeList,selectedShapeList);
    }

    public void redoShape(){
        if(deletedShapeList.size() == 0 && shapeList.size()== 0) {
            System.out.println("There's nothing to redo!");
            return;
        }
        else if(deletedShapeList.size() == 0 && shapeList.size() != 0){
            IShape lastShape = shapeList.get(shapeList.size()-1);
            if(lastShape.getShape().undoPerformered==true){
                lastShape.getShape().redoMove();
                lastShape.getShape().undoPerformered=false;
                shapeListDrawer(shapeList,selectedShapeList);
            }
        }
        else{
            addDeletedShapes();
        }
    }



    public void addDeletedShapes(){
//        System.out.println("AddDeletedShapes called");
        IShape dShape = deletedShapeList.get(deletedShapeList.size()-1);
        System.out.println("Adding shape: " + dShape);
        IShape d = deletedShapeList.remove(deletedShapeList.size()-1);
        shapeList.add(d);
//        if(deletedShapeList.size()!=0){
//            addDeletedShapes();
//        }
        shapeListDrawer(shapeList,selectedShapeList);
    }

//    public void addSpecificDeletedShape(Shape s){
//        shapeList.add(s);
//    }
//
//    public void deleteSpecificShape(Shape s){
//        shapeList.remove(s);
//    }

    public ArrayList<IShape> getShapeList() {
        return shapeList;
    }

    public ArrayList<IShape> getSelectedShapeList(){
        return selectedShapeList;
    }

    public ArrayList<IShape> getDeletedShapeList(){
        return deletedShapeList;
    }

    public ArrayList<IShape> getCopiedShapeList() { return copiedShapeList;}


}

