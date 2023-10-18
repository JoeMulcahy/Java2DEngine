package dev.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainWindow{
    public static MainWindow Instance;
    private int fileCounter = 0;
    protected JFrame frame;
    private int window_width = Settings.mainWindowWidth;
    private int window_height = Settings.mainWindowHeight;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenuItemNew;
    private JMenuItem fileMenuItemOpen;
    private JMenuItem fileMenuItemSave;
    private JMenu optionsMenu;
    private JMenuItem optionsMenuItemGrid;
    private JMenuItem optionsMenuItemSettings;
    private JMenu viewMenu;
    private JMenuItem viewMenuItemToggleGraphics;
    private JMenuItem viewMenuItemToggleHighlighter;
    private JMenuItem viewMenuItemToggleCo_orAtCursor;
    private JMenuItem viewMenuItemToggleObjectNames;
    private JMenuItem viewMenuItemShowCollision;
    private EditorWindow editorWindow;
    private Grid gridFromMainWindow;

    private boolean newSave;

    public MainWindow(){
        initialise();
        Instance = this;
        gridFromMainWindow = new Grid();
    }

    public void closeWindow(){
        frame.setVisible(false);
        frame.dispose();
    }

    private void initialise(){
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();
        editorWindow = new EditorWindow();
        newSave = false;

        //TO DO reorganise the menu items
        //Have top level menu items initialed here
        //

        menuBar = new JMenuBar();
        optionsMenu = new JMenu("Options");

        initialiseFileMenu();
        initialiseViewMenu();
        initialiseOptionsMenu();

        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(viewMenu);


        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setTitle("Engine v0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(window_width, window_height));
        //frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        frame.add(new ToolPanel());
        frame.add(editorWindow);
        frame.add(new InspectorPanel());
        frame.add(new SidePanel());

        frame.pack();
    }

    private void initialiseFileMenu(){
        fileMenu = new JMenu("File");
        fileMenuItemNew = new JMenuItem("New");
        fileMenuItemOpen = new JMenuItem("Open");
        fileMenuItemSave = new JMenuItem("Save");
        fileMenu.add(fileMenuItemNew);
        fileMenu.add(fileMenuItemOpen);
        fileMenu.add(fileMenuItemSave);

        fileMenuItemNew.addActionListener(s -> newProject());
        fileMenuItemOpen.addActionListener(s -> open());
        fileMenuItemSave.addActionListener(s -> save());
    }

    public void newProject(){
        // ask to save current project
        new NewProjectDialogBox();
    }

    public void open(){
        EditorWindow.Instance.clearScreen();

        final JFileChooser fc = new JFileChooser();
        File file = new File(".");

        System.setProperty("user.dir", "");
        fc.setCurrentDirectory(file);

        int returnValue = fc.showOpenDialog(null);
        System.out.println(returnValue);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            file = new File(fc.getSelectedFile().getAbsolutePath());
            System.out.println(file);
            loadProject(file);
        }

    }

    public void loadProject(File file){

        boolean settingsHaveLoaded = false;
        //variables used to create the objects on load
        String type = null, name = null;
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        float lineThickness = 0;
        double rotationAngle = 0.0;
        Color c = Color.BLACK;
        boolean borderThickness = false;
        boolean fill = false;
        boolean hasCollisionDetection = false;
        // tempColor is the color extracted from a string in the save file
        // eg. "color" : "java.awt.Color[r=112,g=146,b=190]"
        Color tempColor = Color.black;

        int objectsCounter = 0;

        File f = file;
        String line = "";
        try{
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()){
                line = sc.nextLine();

                //All save files must have this header
                if(line.contains("GAME_ENGINE_FILE")){

                    while (sc.hasNextLine()){
                        line = sc.nextLine();

                        //first load the project settings
                        if(line.contains("\"settings\"")){
                            editorWindow = null;

                            while(sc.hasNextLine()){
                                line = sc.nextLine();

                                if(line.contains(":")){

                                    line = line.replaceAll("\"", "");
                                    line = line.replaceAll(" ", "");
                                    line = line.trim();
                                    String[] values = line.split(":");

                                    if(values[1].contains("java.awt.Color")){
                                        //extracts the rgb values from the string
                                        String color = values[1];
                                        //"color" : "java.awt.Color[r=112,g=146,b=190]"
                                        String temp = color.substring(color.indexOf('[') + 1, color.lastIndexOf(']'));
                                        // --> r=112,g=146,b=190
                                        temp = temp.replace("r=", "");
                                        temp = temp.replace("g=", "");
                                        temp = temp.replace("b=", "");
                                        // --> 112,146,190
                                        String[] rgb = temp.split(",");

                                        tempColor = new Color(Integer.valueOf(rgb[0]),
                                                Integer.valueOf(rgb[1]),
                                                Integer.valueOf(rgb[2]));

                                    }

                                    if(!settingsHaveLoaded){
                                        switch(values[0]){
                                            case "projectName" -> Settings.projectName = values[1];
                                            case "mainWindowWidth" -> Settings.mainWindowWidth = Integer.parseInt(values[1]);
                                            case "mainWindowHeight" -> Settings.mainWindowHeight = Integer.parseInt(values[1]);
                                            case "editorPanelWidth" -> Settings.editorPanelWidth = Integer.parseInt(values[1]);
                                            case "editorPanelHeight" -> Settings.editorPanelHeight = Integer.parseInt(values[1]);
                                            case "editorBackgroundColor" -> Settings.editorBackgroundColor = tempColor;
                                            case "statsPanelWidth" -> Settings.statsPanelWidth = Integer.parseInt(values[1]);
                                            case "statsPanelHeight" -> Settings.statsPanelHeight = Integer.parseInt(values[1]);
                                            case "editorControlPanelWidth" -> Settings.toolPanelWidth = Integer.parseInt(values[1]);
                                            case "editorControlPanelHeight" -> Settings.toolPanelHeight = Integer.parseInt(values[1]);
                                            case "gameObjectsPanelWidth" -> Settings.gameObjectsPanelWidth = Integer.parseInt(values[1]);
                                            case "gameObjectsPanelHeight" -> Settings.gameObjectsPanelHeight = Integer.parseInt(values[1]);
                                            case "colorPanelWidth" -> Settings.colorPanelWidth = Integer.parseInt(values[1]);
                                            case "colorPanelHeight" -> Settings.colorPanelHeight = Integer.parseInt(values[1]);
                                            case "currentColor" -> GameManager.currentColor = tempColor;
                                            case "currentShape" -> GameManager.currentSelectedTool = GameManager.Tool.RECT;
                                            case "lineThickness" -> GameManager.lineThickness = Float.parseFloat(values[1]);
                                            case "fillShape" -> GameManager.fillShape = Boolean.parseBoolean(values[1]);
                                            case "showGrid" -> GameManager.showGrid = Boolean.parseBoolean(values[1]);
                                            case "gridRowsAndColumns" -> GameManager.gridNumberOfRowsAndCols[0] = Integer.parseInt(values[1]);
                                            case "snapMode" -> {
                                                GameManager.snapMode = Boolean.parseBoolean(values[1]);
                                                settingsHaveLoaded = true;
                                            }
                                        }
                                    }else if(settingsHaveLoaded){
                                        switch(values[0]){
                                            case "type" -> type = values[1];
                                            case "name" -> name = values[1];
                                            case "x1" -> x1 = Integer.parseInt(values[1]);
                                            case "y1" -> y1 = Integer.parseInt(values[1]);
                                            case "x2" -> x2 = Integer.parseInt(values[1]);
                                            case "y2" -> y2 = Integer.parseInt(values[1]);
                                            case "color" -> c = tempColor;
                                            case "rotationAngle" -> rotationAngle = Double.parseDouble(values[1]);
                                            case "lineThickness" -> lineThickness = Float.parseFloat(values[1]);
                                            case "borderThickness" -> borderThickness = false;
                                            case "hasCollisionDetection" -> hasCollisionDetection = Boolean.parseBoolean(values[1]);
                                            case "fill" -> {
                                                fill = Boolean.parseBoolean(values[1]);
                                                EditorWindow.Instance.loadGameObjects(type, x1, y1, x2, y2, rotationAngle, c, lineThickness, fill, borderThickness);
                                            }


                                            default -> System.out.println("error!!");
                                        }

                                    }
                                        //System.out.println(values[0] + " " + values[1]);
                                }
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid File Format");
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean save(){
        String filename = Settings.projectName + fileCounter + ".txt";

        try{
            File newFile = new File(filename);
            if(!newFile.exists()){
                newFile.createNewFile();
                writeNewGameSave(filename);
            }else{
                fileCounter++;
                save();
            }
        }catch(IOException e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    private void writeNewGameSave(String fileName){
        StringBuilder fileContent = new StringBuilder("GAME_ENGINE_FILE");

        fileContent.append("\n{\n\t\"settings\" : [\n\t");
        try{
            FileWriter myWriter = new FileWriter(fileName);

            fileContent.append(Settings.getSettings());
            fileContent.append("\n\t\"gameObjects\" : [\n");

            for(int i = 0; i < EditorWindow.Instance.getGameObjects().size(); i++){
                GameObject o = EditorWindow.Instance.getGameObjects().get(i);

                if(i == EditorWindow.Instance.getGameObjects().size() - 1){
                    fileContent.append(o.toString() + "]");
                }else{
                    fileContent.append(o.toString() + ",\n");
                }
            }

            fileContent.append("\n}");

            myWriter.write(fileContent.toString());
            myWriter.close();

            System.out.println("file written");

        }catch(IOException e){
            System.out.println(e);
        }

    }

    private void initialiseOptionsMenu(){
        JMenu menuGrid = new JMenu("Grid");
        JMenuItem menuToggleShapeAtCursor = new JMenuItem("Shape Cursor");
        JMenuItem toggleGrid = new JMenuItem("On/Off");
        JMenuItem gridSize4x4 = new JMenuItem("4 x 4");
        JMenuItem gridSize8x8 = new JMenuItem("8 x 8");
        JMenuItem gridSize16x16 = new JMenuItem("16 x 16");
        JMenuItem gridSize32x32 = new JMenuItem("32 x 32");
        JMenuItem gridSize64x64 = new JMenuItem("64 x 64");
        menuGrid.add(toggleGrid);
        menuGrid.add(gridSize4x4);
        menuGrid.add(gridSize8x8);
        menuGrid.add(gridSize16x16);
        menuGrid.add(gridSize32x32);
        menuGrid.add(gridSize64x64);
        menuGrid.add(menuToggleShapeAtCursor);
        JMenuItem goNuts = new JMenuItem("Go nuts!!");

        toggleGrid.addActionListener(s -> toggleGridAndSetGridSize(-1));
        gridSize4x4.addActionListener(s -> toggleGridAndSetGridSize(4));
        gridSize8x8.addActionListener(s -> toggleGridAndSetGridSize(8));
        gridSize16x16.addActionListener(s -> toggleGridAndSetGridSize(16));
        gridSize32x32.addActionListener(s -> toggleGridAndSetGridSize(32));
        gridSize64x64.addActionListener(s -> toggleGridAndSetGridSize(64));

        goNuts.addActionListener(s -> toggleGoNuts());

        menuToggleShapeAtCursor.addActionListener(s -> toggleCursorShape());

        optionsMenu.add(menuGrid);
        optionsMenu.add(goNuts);

    }

    private void toggleGoNuts(){
        if(!GameManager.gotNuts){
            GameManager.gotNuts = true;
        }
        else{
            GameManager.gotNuts = false;
        }
    }

    private void initialiseViewMenu(){
        viewMenu = new JMenu("View");

        viewMenuItemToggleGraphics = new JMenuItem("draw graphics");
        viewMenuItemToggleHighlighter = new JMenuItem("Toggle highlighter");
        viewMenuItemToggleCo_orAtCursor = new JMenuItem("Co-or at Cursor");
        viewMenuItemToggleObjectNames = new JMenuItem("Object Names");
        viewMenuItemShowCollision = new JMenuItem("Toggle collision boxes");

        viewMenuItemToggleGraphics.addActionListener(s -> toggleGraphics());
        viewMenuItemToggleHighlighter.addActionListener(s -> toggleHighLighter());
        viewMenuItemToggleCo_orAtCursor.addActionListener(s -> toggleViewCoordinates());
        viewMenuItemToggleObjectNames.addActionListener(s -> toggleObjectNames());
        viewMenuItemShowCollision.addActionListener(s -> toggleCollisionBoxes());

        viewMenu.add(viewMenuItemToggleGraphics);
        viewMenu.add(viewMenuItemToggleHighlighter);
        viewMenu.add(viewMenuItemToggleCo_orAtCursor);
        viewMenu.add(viewMenuItemToggleObjectNames);
        viewMenu.add(viewMenuItemShowCollision);
    }

    private void toggleCollisionBoxes(){
        if(Settings.toggleCollisionBoxes){
            Settings.toggleCollisionBoxes = false;
        }else{
            Settings.toggleCollisionBoxes = true;
        }
    }

    private void toggleObjectNames(){
        if(Settings.toggleObjectNamesOnEditor){
            Settings.toggleObjectNamesOnEditor = false;
        }else{
            Settings.toggleObjectNamesOnEditor = true;
        }
    }
    private void toggleViewCoordinates(){
        if(Settings.toggleCoordinatesAtCursor){
            Settings.toggleCoordinatesAtCursor = false;
        }else{
            Settings.toggleCoordinatesAtCursor = true;
        }
    }

    private void toggleGraphics(){
        if(Settings.toggleGraphicsOn){
            Settings.toggleGraphicsOn = false;
        }else{
            Settings.toggleGraphicsOn = true;
        }
    }

    private void toggleHighLighter(){
        if(!GameManager.highlighterOn){
            GameManager.highlighterOn = true;
        }else{
            GameManager.highlighterOn = false;
        }
        System.out.println(GameManager.highlighterOn);
    }

    public void toggleGridAndSetGridSize(int size){
        if(size > 0){
            GameManager.snapMode = true;
            EditorWindow.getEditorGrid().setVisible(true);
        }else{
            GameManager.snapMode = false;
            EditorWindow.getEditorGrid().setVisible(false);
        }

        EditorWindow.getEditorGrid().setNumberOfColumns(size);
        EditorWindow.getEditorGrid().setNumberOfRows(size);

        System.out.println("get cell width: " +EditorWindow.getEditorGrid().getCellWidth());

    }

    public void toggleCursorShape(){
        if(GameManager.drawShapeAtCursor){
            GameManager.drawShapeAtCursor = false;
        }else{
            GameManager.drawShapeAtCursor = true;
        }
    }

    public MainWindow getMainWindow(){
        return this;
    }
}
