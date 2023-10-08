import java.awt.*;
import java.util.Stack;

public class Helper {
    protected static int countFrames = 0;
    protected static int secondsRun = 0;

    protected static int mainWindowWidth = 1500;
    protected static int mainWindowHeight= 800;
    protected static int editorPanelWidth = 512;
    protected static int editorPanelHeight = 512;

    protected static Color editorBackgroundColor = Color.black;
    protected static int statsPanelWidth = 256;
    protected static int statsPanelHeight = 512;
    protected static int editorControlPanelWidth = 256;
    protected static int editorControlPanelHeight = 512;

    protected static int gameObjectsPanelWidth = 512;
    protected static int gameObjectsPanelHeight = 512;
    protected static int colorPanelWidth = 124;
    protected static int colorPanelHeight = 48;
    protected static Color currentColor = Color.white;

    protected static int mouseX1;
    protected static int mouseY1;
    protected static int mouseX2;
    protected static int mouseY2;

    protected enum ShapeSelector{ RECT, CIRCLE, LINE, POLY};
    protected static ShapeSelector currentShape;
    protected static int currentShapeRotation = 0;

    protected static float lineThickness = 1;
    protected static boolean fillShape = false;

    protected static int numberOfObjectsDrawn = 0;

    protected static boolean showGrid = false;
    protected static int gridRowsAndColumns = 10;
    protected static int gridDimension = 50;
    protected static boolean snapMode = false;
    protected static java.util.List<GameObject> createdGameObjects;
    protected static int instructionCounter = 0;

    protected static Stack undoRedoStack;


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

    public static int getMouseX1() {
        return mouseX1;
    }

    public static void setMouseX1(int mouseX1) {
        Helper.mouseX1 = mouseX1;
    }

    public static int getMouseY1() {
        return mouseY1;
    }

    public static void setMouseY1(int mouseY1) {
        Helper.mouseY1 = mouseY1;
    }

    public static int getMouseX2() {
        return mouseX2;
    }

    public static void setMouseX2(int mouseX2) {
        Helper.mouseX2 = mouseX2;
    }

    public static int getMouseY2() {
        return mouseY2;
    }

    public static void setMouseY2(int mouseY2) {
        Helper.mouseY2 = mouseY2;
    }

    @Override
    public String toString() {
        return "Helper{}";
    }
}
