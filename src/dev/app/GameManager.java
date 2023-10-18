package dev.app;

import java.awt.*;

public class GameManager {
    protected static int countFrames = 0;
    protected static int secondsRun = 0;
    protected static int numberOfObjectsDrawn = 0;
    protected static int instructionCounter = 0;
    protected static Color currentColor = Color.white;
    //protected enum ShapeSelector{ RECT, CIRCLE, LINE, POLY};

   // protected static GameManager.ShapeSelector currentShape;

    protected static float lineThickness = 1;
    protected static boolean fillShape = false;
    protected static boolean showGrid = true;
    protected static int[] gridNumberOfRowsAndCols = {16,16};
    protected static boolean snapMode = true;
    protected static boolean drawShapeAtCursor = true;
    protected static java.util.List<GameObject> createdGameObjects;
    protected static GameObject currentlySelectedGameObject;
    protected static boolean highlighterOn = false;
    protected static boolean toggleObjectBorder = false;

    protected static boolean selectOn = false;

    protected enum Tool{ RECT, CIRCLE, LINE, POLY, SELECT, TEXT};
    protected static Tool currentSelectedTool = Tool.RECT;

    //Resize dev.joe.GameObject setting. Resize from center or from origin(x1,y1)
    protected static boolean toggleResizeFromCenter = true;
    protected static int moveAmount = 5;
    protected static int mouseX1;
    protected static int mouseY1;
    protected static int mouseX2;
    protected static int mouseY2;
    protected static boolean gotNuts = false;

    public static int getMouseX1() {
        return mouseX1;
    }

    public static int getMouseY1() {
        return mouseY1;
    }

    public static int getMouseX2() {
        return mouseX2;
    }

    public static int getMouseY2() {
        return mouseY2;
    }

    public static int getSecondsRun(){

        long time = System.currentTimeMillis();
        if(time % 1000 == 0){
            secondsRun++;
        }

        return secondsRun;
    }

    public static int getEditorCounter(){
        return countFrames;
    }
}
