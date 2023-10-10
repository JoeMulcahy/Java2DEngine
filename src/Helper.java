import java.awt.*;

public class Helper {

    protected static String projectName = "New game 1";
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

    protected static float lineThickness = 1;
    protected static boolean fillShape = false;

    protected static int numberOfObjectsDrawn = 0;

    protected static boolean showGrid = true;
    protected static int gridRowsAndColumns = 10;
    protected static int gridWidthHeight = Helper.editorPanelWidth / Helper.gridRowsAndColumns;
    protected static boolean snapMode = true;
    protected static boolean drawShapeAtCursor = true;
    protected static java.util.List<GameObject> createdGameObjects;
    protected static GameObject currentlySelectedGameObject;
    protected static int instructionCounter = 0;
    protected static boolean highlighterOn = true;
    protected static boolean toggleGraphicsOn = true;

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

    public static int getMouseY1() {
        return mouseY1;
    }

    public static int getMouseX2() {
        return mouseX2;
    }

    public static int getMouseY2() {
        return mouseY2;
    }


    public static String getSettings() {
        String description = "{\n" +
                "\t\t\"projectName\" : \"" + projectName + "\"" +
                "\n\t\t\"mainWindowWidth\" : \"" + mainWindowWidth + "\"" +
                "\n\t\t\"mainWindowHeight\" : \"" + mainWindowHeight + "\"" +
                "\n\t\t\"editorPanelWidth\" : \"" + editorPanelWidth + "\"" +
                "\n\t\t\"editorPanelHeight\" : \"" + editorPanelHeight + "\"" +
                "\n\t\t\"editorBackgroundColor\" : \"" + editorBackgroundColor + "\"" +
                "\n\t\t\"statsPanelWidth\" : \"" + statsPanelWidth + "\"" +
                "\n\t\t\"statsPanelHeight\" : \"" + statsPanelHeight + "\"" +
                "\n\t\t\"editorControlPanelWidth\" : \"" + editorControlPanelWidth + "\"" +
                "\n\t\t\"editorControlPanelHeight\" : \"" + editorControlPanelHeight + "\"" +
                "\n\t\t\"gameObjectsPanelWidth\" : \"" + gameObjectsPanelWidth + "\"" +
                "\n\t\t\"gameObjectsPanelHeight\" : \"" + gameObjectsPanelHeight + "\"" +
                "\n\t\t\"colorPanelWidth\" : \"" + colorPanelWidth + "\"" +
                "\n\t\t\"colorPanelHeight\" : \"" + colorPanelHeight + "\"" +
                "\n\t\t\"currentColor\" : \"" + currentColor + "\"" +
                "\n\t\t\"currentShape\" : \"" + currentShape + "\"" +
                "\n\t\t\"lineThickness\" : \"" + lineThickness + "\"" +
                "\n\t\t\"fillShape\" : \"" + fillShape + "\"" +
                "\n\t\t\"showGrid\" : \"" + showGrid + "\"" +
                "\n\t\t\"gridRowsAndColumns\" : \"" + gridRowsAndColumns + "\"" +
                "\n\t\t\"snapMode\" : \"" + snapMode + "\"" +
                "\n\t}],\n";

        return description;
    }

}
