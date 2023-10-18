package dev.app;

import java.awt.*;

public class Settings {

    protected static String projectName = "New game 1";
    protected static int mainWindowWidth = 1500;
    protected static int mainWindowHeight= 800;

    protected static int editorPanelWidth = 512;
    protected static int editorPanelHeight = 512;
    protected static int sidePanelWidth = 512;
    protected static int sidePanelHeight = 512;
    protected static int previewWidth = 128;
    protected static int previewHeight = 128;

    protected static int inspectorWidth = 512;
    protected static int inspectorHeight= 512;
    protected static Color editorBackgroundColor = Color.black;
    protected static int statsPanelWidth = 128;
    protected static int statsPanelHeight = 512;
    protected static int toolPanelWidth = 128;
    protected static int toolPanelHeight = 512;
    protected static int gameObjectsPanelWidth = 384;
    protected static int gameObjectsPanelHeight = 512;
    protected static int colorPanelWidth = 124;
    protected static int colorPanelHeight = 48;
    protected static boolean toggleGraphicsOn = true;
    protected static boolean toggleCoordinatesAtCursor = false;
    protected static boolean toggleObjectNamesOnEditor = false;
    protected static boolean toggleCollisionBoxes = false;

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
                "\n\t\t\"editorControlPanelWidth\" : \"" + toolPanelWidth + "\"" +
                "\n\t\t\"editorControlPanelHeight\" : \"" + toolPanelHeight + "\"" +
                "\n\t\t\"gameObjectsPanelWidth\" : \"" + gameObjectsPanelWidth + "\"" +
                "\n\t\t\"gameObjectsPanelHeight\" : \"" + gameObjectsPanelHeight + "\"" +
                "\n\t\t\"colorPanelWidth\" : \"" + colorPanelWidth + "\"" +
                "\n\t\t\"colorPanelHeight\" : \"" + colorPanelHeight + "\"" +
                "\n\t}],\n";

        return description;
    }

}
