package dev.app;

import java.awt.*;

public class Settings {

    protected static String projectName = "New game 1";
    protected static int mainWindowWidth = 1500;
    protected static int mainWindowHeight= 800;

    protected static int editorPanelWidth = 512;
    protected static int editorPanelHeight = 512;

    protected static int inspectorWidth = 512;
    protected static int inspectorHeight= 512;
    protected static Color editorBackgroundColor = Color.black;
    protected static int statsPanelWidth = 256;
    protected static int statsPanelHeight = 512;
    protected static int editorControlPanelWidth = 256;
    protected static int editorControlPanelHeight = 512;
    protected static int gameObjectsPanelWidth = 512;
    protected static int gameObjectsPanelHeight = 512;
    protected static int colorPanelWidth = 124;
    protected static int colorPanelHeight = 48;

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
                "\n\t}],\n";

        return description;
    }

}
