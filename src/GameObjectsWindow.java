import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Ignore this for the moment
// Eventually will be a separate window

public class GameObjectsWindow {
    private JFrame frameGameObjects;
    private JPanel objectsPanel;
    private JScrollPane scrollableTextArea;

    private JList jListOfObjects;

    private int window_width = Helper.statsPanelWidth;
    private int window_height = Helper.statsPanelHeight;

    public static GameObjectsWindow Instance;

    public GameObjectsWindow(){
        objectsPanel = new JPanel();
        Instance = this;
        initialiseFrame();

        java.util.List<GameObject> objects = Helper.createdGameObjects;
        ArrayList<String> objectNames = new ArrayList<>();

        if(objects != null){
            objects.forEach(s -> {
                objectNames.add(s.getName());
            });
            jListOfObjects = new JList(objectNames.toArray());
            objectsPanel.add(jListOfObjects);
        }

        objectsPanel.add(jListOfObjects);
        frameGameObjects.getContentPane().add(objectsPanel);
        frameGameObjects.pack();

    }

    private void initialiseFrame(){
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();

        frameGameObjects = new JFrame();
        frameGameObjects.setLayout(new FlowLayout());
        frameGameObjects.setTitle("Objects");
        frameGameObjects.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGameObjects.setPreferredSize(new Dimension(window_width, window_height));
        //frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frameGameObjects.setVisible(true);
        frameGameObjects.setLocationRelativeTo(null);
        frameGameObjects.setResizable(true);

    }
}


