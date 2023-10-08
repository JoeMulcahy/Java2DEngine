import javax.swing.*;
import java.awt.*;

public class MainWindow{
    private JFrame frame;
    private int window_width = Helper.mainWindowWidth;
    private int window_height = Helper.mainWindowHeight;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenuItemOpen;
    private JMenuItem fileMenuItemSave;
    private JMenu optionsMenu;
    private JMenuItem optionsMenuItemGrid;
    private JMenuItem optionsMenuItemSettings;
    private JMenu viewMenu;
    private JMenuItem viewMenuItemOption1;
    private JMenuItem viewMenuItemOption2;
    private JMenuItem viewMenuItemOption3;



    public MainWindow(){
        initialise();
    }

    private void initialise(){
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();

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
        frame.add(new EditorWindow());
        frame.add(new GameObjectsPanel());


        frame.pack();
    }

    private void initialiseFileMenu(){
        fileMenu = new JMenu("File");
        fileMenuItemOpen = new JMenuItem("Open");
        fileMenuItemSave = new JMenuItem("Save");
        fileMenu.add(fileMenuItemOpen);
        fileMenu.add(fileMenuItemSave);

        fileMenuItemOpen.addActionListener(s -> printTest("open"));
        fileMenuItemSave.addActionListener(s -> printTest("save"));
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
}

