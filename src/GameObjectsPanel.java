import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameObjectsPanel extends JPanel {

    private int width = Helper.gameObjectsPanelWidth;
    private int height = Helper.gameObjectsPanelHeight;

    private JPanel objectsPanel;
    private JPanel objectAttributesPanel;

    private static JList<String> jListOfGameObjectNames;
    private static DefaultListModel<String> jListModelObjectNames;

    private static int selectedObjectIndex = 0;
    private JScrollPane scrollPaneForJList;

    JLabel lblName;
    JLabel lblX1Pos;
    JLabel lblY1Pos;
    JLabel lblX2Pos;
    JLabel lblY2Pos;
    JLabel lblColor;
    JLabel lblFill;

    static JTextField txtName = new JTextField();
    static JTextField txtX1Pos = new JTextField();
    static JTextField txtY1Pos = new JTextField();
    static JTextField txtX2Pos = new JTextField();
    static JTextField txtY2Pos = new JTextField();
    static JTextField txtColor = new JTextField();
    static JTextField txtFill = new JTextField();
    java.util.List<Shape> gameObjects;
    static Shape selectedShape;
    static int currentSelectedObjectIndex = 0;

    public GameObjectsPanel(){
        initialise();
    }

    public void initialise(){
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridLayout(1,2));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        this.setVisible(true);

        objectsPanel = new JPanel();
        objectsPanel.setPreferredSize(new Dimension(width / 2, height));
        objectsPanel.setBackground(Color.gray);
        objectsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        objectAttributesPanel = new JPanel();
        objectAttributesPanel.setLayout(new GridLayout(7, 2));
        objectAttributesPanel.setPreferredSize(new Dimension(width / 2, height));
        objectAttributesPanel.setBackground(Color.gray);
        objectAttributesPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        initialiseObjectAttributesPanel();

        gameObjects = Helper.createdGameObjects;

        jListModelObjectNames = new DefaultListModel<>();
        jListOfGameObjectNames = new JList<>(jListModelObjectNames);
        //jListOfGameObjectNames.setSize(new Dimension(width / 3 , height / 2));

        scrollPaneForJList = new JScrollPane(jListOfGameObjectNames);
        scrollPaneForJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneForJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        updateGameObjectJList();

        objectsPanel.add(scrollPaneForJList);

        this.add(objectsPanel);
        this.add(objectAttributesPanel);

    }

    private void initialiseObjectAttributesPanel(){
        lblName = new JLabel("Name: ");
        lblX1Pos = new JLabel("x1 pos: ");
        lblY1Pos= new JLabel("y1 pos: ");
        lblX2Pos = new JLabel("x2 pos: ");
        lblY2Pos= new JLabel("y2 pos: ");
        lblColor = new JLabel("color: ");
        lblFill = new JLabel("fill: ");

        txtName = new JTextField();
        txtX1Pos = new JTextField();
        txtY1Pos = new JTextField();
        txtX2Pos = new JTextField();
        txtY2Pos = new JTextField();
        txtColor = new JTextField();
        txtFill = new JTextField();

        //txtX1Pos.addActionListener(s -> changeX1Pos());

        objectAttributesPanel.add(lblName);
        objectAttributesPanel.add(txtName);
        objectAttributesPanel.add(lblX1Pos);
        objectAttributesPanel.add(txtX1Pos);
        objectAttributesPanel.add(lblY1Pos);
        objectAttributesPanel.add(txtY1Pos);
        objectAttributesPanel.add(lblX2Pos);
        objectAttributesPanel.add(txtX2Pos);
        objectAttributesPanel.add(lblY2Pos);
        objectAttributesPanel.add(txtY2Pos);
        objectAttributesPanel.add(lblColor);
        objectAttributesPanel.add(txtColor);
        objectAttributesPanel.add(lblFill);
        objectAttributesPanel.add(txtFill);
    }

    public void changeX1Pos(int value){
        //selectedShape.setX1(value);
        Helper.createdGameObjects.get(value).setX1(value);
        System.out.println("txtbox event triggered");
    }

    public static void updateObjectsAttributesPanel(int index){
        if(Helper.createdGameObjects != null){
            selectedShape = Helper.createdGameObjects.get(index);

            updateAttributeValues(selectedShape);
        }
    }

    private static void updateAttributeValues(Shape s){
        txtName.setText(s.getName());
        txtX1Pos.setText(String.valueOf(s.getX1()));
        txtY1Pos.setText(String.valueOf(s.getY1()));
        txtX2Pos.setText(String.valueOf(s.getX2()));
        txtY2Pos.setText(String.valueOf(s.getY2()));
        txtColor.setText(String.valueOf(s.getColor()));
        txtFill.setText(String.valueOf(s.isFill()));
    }



    public static void updateGameObjectJList(){
        if(Helper.createdGameObjects != null){
            jListModelObjectNames.clear();
            Helper.createdGameObjects.forEach(s -> jListModelObjectNames.addElement(s.getName()));
            jListOfGameObjectNames.getModel();

            jListOfGameObjectNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //when Jlist item is clicked 2 events are triggered, one for mouseclick on and one for mouseclick off
                    // this will register only one mouse click
                    if(e.getClickCount() >= 1){

                        currentSelectedObjectIndex = jListOfGameObjectNames.getSelectedIndex();

                        System.out.println(currentSelectedObjectIndex);
                        updateObjectsAttributesPanel(currentSelectedObjectIndex);
                        System.out.println();
                    }
                }
            });
        }
    }

}
