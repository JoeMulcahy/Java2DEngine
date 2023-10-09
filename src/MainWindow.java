import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainWindow{
    private int fileCounter = 0;
    private JFrame frame;
    private int window_width = Helper.mainWindowWidth;
    private int window_height = Helper.mainWindowHeight;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenuItemNew;
    private JMenuItem fileMenuItemOpen;
    private JMenuItem fileMenuItemSave;
    private JMenu optionsMenu;
    private JMenuItem optionsMenuItemGrid;
    private JMenuItem optionsMenuItemSettings;
    private JMenu viewMenu;
    private JMenuItem viewMenuItemOption1;
    private JMenuItem viewMenuItemOption2;
    private JMenuItem viewMenuItemOption3;
    private EditorWindow editorWindow;

    private boolean newSave = true;



    public MainWindow(){
        initialise();
    }

    private void initialise(){
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();
        editorWindow = new EditorWindow();

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

        frame.add(new EditorPanel());
        frame.add(editorWindow);
        frame.add(new GameObjectAttributesPanel());


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
        if(Helper.createdGameObjects.size() > 0){
            save();
        }else{

        }
    }

    public void open(){
        EditorWindow.clearScreen();

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

        File f = file;
        //File f = new File("textfile.txt");
        String line = "";
        try{
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()){
                line = sc.nextLine();

                if(line.contains("GAME_ENGINE_FILE")){
                    while (sc.hasNextLine()){
                        line = sc.nextLine();
                        if(line.contains("\"settings\"")){
                            Color tempColor = Color.black;
                            while(sc.hasNextLine()){
                                line = sc.nextLine();

                                if(line.contains(":")){
                                    line = line.replaceAll("\"", "");
                                    line = line.replaceAll(" ", "");
                                    line = line.trim();
                                    //System.out.println(line);
                                    String[] values = line.split(":");
                                    if(values[1].contains("java.awt.Color")){

                                        String color = values[1];

                                        String temp = color.substring(color.indexOf('[') + 1, color.lastIndexOf(']'));
                                        temp = temp.replace("r=", "");
                                        temp = temp.replace("g=", "");
                                        temp = temp.replace("b=", "");
                                        String[] rgb = temp.split(",");

                                        tempColor = new Color(Integer.valueOf(rgb[0]),
                                                Integer.valueOf(rgb[1]),
                                                Integer.valueOf(rgb[2]));

                                    }

                                    switch(values[0]){
                                        case "projectName" -> Helper.projectName = values[1];
                                        case "mainWindowWidth" -> Helper.mainWindowWidth = Integer.parseInt(values[1]);
                                        case "mainWindowHeight" -> Helper.mainWindowHeight = Integer.parseInt(values[1]);
                                        case "editorPanelWidth" -> Helper.editorPanelWidth = Integer.parseInt(values[1]);
                                        case "editorPanelHeight" -> Helper.editorPanelHeight = Integer.parseInt(values[1]);
                                        case "editorBackgroundColor" -> Helper.editorBackgroundColor = tempColor;
                                        case "statsPanelWidth" -> Helper.statsPanelWidth = Integer.parseInt(values[1]);
                                        case "statsPanelHeight" -> Helper.statsPanelHeight = Integer.parseInt(values[1]);
                                        case "editorControlPanelWidth" -> Helper.editorControlPanelWidth = Integer.parseInt(values[1]);
                                        case "editorControlPanelHeight" -> Helper.editorControlPanelHeight = Integer.parseInt(values[1]);
                                        case "gameObjectsPanelWidth" -> Helper.gameObjectsPanelWidth = Integer.parseInt(values[1]);
                                        case "gameObjectsPanelHeight" -> Helper.gameObjectsPanelHeight = Integer.parseInt(values[1]);
                                        case "colorPanelWidth" -> Helper.colorPanelWidth = Integer.parseInt(values[1]);
                                        case "colorPanelHeight" -> Helper.colorPanelHeight = Integer.parseInt(values[1]);
                                        case "currentColor" -> Helper.currentColor = tempColor;
                                        case "currentShape" -> Helper.currentShape = Helper.ShapeSelector.RECT;
                                        case "lineThickness" -> Helper.lineThickness = Float.parseFloat(values[1]);
                                        case "fillShape" -> Helper.fillShape = Boolean.parseBoolean(values[1]);
                                        case "showGrid" -> Helper.showGrid = Boolean.parseBoolean(values[1]);
                                        case "gridRowsAndColumns" -> Helper.gridRowsAndColumns = Integer.parseInt(values[1]);
                                        case "snapMode" -> Helper.snapMode = Boolean.parseBoolean(values[1]);
                                        default -> System.out.println("error!!");
                                    }
                                }
                            }

                        }else if(line.contains("\"gameObjects\"")){
                            editorWindow = null;
                            String type = null, name = null;
                            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
                            float lineThickness = 0;
                            double rotationAngle = 0.0;
                            Color c = Color.BLACK;
                            boolean fill = false;

                            Color tempColor = Color.black;
                            while(sc.hasNextLine()) {
                                line = sc.nextLine();

                                if (line.contains(":")) {
                                    line = line.replaceAll("\"", "");
                                    line = line.replaceAll(" ", "");
                                    line = line.trim();
                                    //System.out.println(line);
                                    String[] values = line.split(":");

                                    System.out.println("Objects: " + values[0] + " " + values[1]);

                                    if (values[1].contains("java.awt.Color")) {
                                        String color = values[1];
                                        String temp = color.substring(color.indexOf('['), color.lastIndexOf(']'));
                                        temp = temp.replace("r=", "");
                                        temp = temp.replace("g=", "");
                                        temp = temp.replace("b=", "");
                                        String[] rgb = temp.split(",");

                                        tempColor = new Color(Integer.valueOf(rgb[0]),
                                                Integer.valueOf(rgb[1]),
                                                Integer.valueOf(rgb[2]));
                                    }

                                    switch (values[0]) {
                                        case "type" -> type = values[1];
                                        case "name" -> name = values[1];
                                        case "x1" -> x1 = Integer.parseInt(values[1]);
                                        case "y1" -> y1 = Integer.parseInt(values[1]);
                                        case "x2" -> x2 = Integer.parseInt(values[1]);
                                        case "y2" -> y2 = Integer.parseInt(values[1]);
                                        case "color" -> c = tempColor;
                                        case "rotationAngle" -> rotationAngle = Double.parseDouble(values[1]);
                                        case "lineThickness" -> lineThickness = Float.parseFloat(values[1]);
                                        case "fill" -> fill = Boolean.parseBoolean(values[1]);
                                        default -> System.out.println("error!!");
                                    }
                                }

                                //Need a break
                                // it doesn't when the next objects is!!!!

                                EditorWindow.loadGameObjects(type, x1, y1, x2, y2, rotationAngle, c, lineThickness, fill);
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
        String filename = Helper.projectName + fileCounter + ".txt";
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

            fileContent.append(Helper.getSettings());
            fileContent.append("\n\t\"gameObjects\" : [\n");

            for(int i = 0; i < Helper.createdGameObjects.size(); i++){
                GameObject o = Helper.createdGameObjects.get(i);

                if(i == Helper.createdGameObjects.size() - 1){
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
        toggleGrid.addActionListener(s -> toggleGridAndSetGridSize(-1));
        gridSize4x4.addActionListener(s -> toggleGridAndSetGridSize(4));
        gridSize8x8.addActionListener(s -> toggleGridAndSetGridSize(8));
        gridSize16x16.addActionListener(s -> toggleGridAndSetGridSize(16));
        gridSize32x32.addActionListener(s -> toggleGridAndSetGridSize(32));
        gridSize64x64.addActionListener(s -> toggleGridAndSetGridSize(64));

        optionsMenu.add(menuGrid);
    }

    private void initialiseViewMenu(){
        viewMenu = new JMenu("View");

        viewMenuItemOption1 = new JMenuItem("option 1");
        viewMenuItemOption2 = new JMenuItem("option 2");
        viewMenuItemOption3 = new JMenuItem("option 3");

        viewMenu.add(viewMenuItemOption1);
        viewMenu.add(viewMenuItemOption2);
        viewMenu.add(viewMenuItemOption3);

    }
    private void printTest(String message){
        System.out.println(message + " pressed");
    }

    public void toggleGridAndSetGridSize(int size){
        if(size > 0){
            Helper.snapMode = true;
            Helper.showGrid = true;
        }else{
            Helper.snapMode = false;
            Helper.showGrid = false;
        }
        Helper.gridRowsAndColumns = size;

    }
}
