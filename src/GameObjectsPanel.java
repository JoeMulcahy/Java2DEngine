import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GameObjectsPanel extends JPanel {

    private int width = Helper.gameObjectsPanelWidth;
    private int height = Helper.gameObjectsPanelHeight;

    private JPanel objectsPanel;
    private static JPanel objectAttributesPanel;

    static JList<String> jListOfGameObjectNames;
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

    JButton btnNameAccept;
    JButton btnX1PosAccept;
    JButton btnY1PosAccept;
    JButton btnX2PosAccept;
    JButton btnY2PosAccept;
    JButton btnColorAccept;
    JButton btnFillAccept;

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
        this.setLayout(new GridLayout(1,3));
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

        btnNameAccept = new JButton("\u2714");
        btnX1PosAccept = new JButton("\u2714");
        btnY1PosAccept= new JButton("\u2714");
        btnX2PosAccept = new JButton("\u2714");
        btnY2PosAccept = new JButton("\u2714");
        btnColorAccept = new JButton("\u2714");
        btnFillAccept = new JButton("\u2714");

        //txtX1Pos.addActionListener(s -> changeX1Pos());

        objectAttributesPanel.add(lblName);
        objectAttributesPanel.add(txtName);
        objectAttributesPanel.add(btnNameAccept);

        objectAttributesPanel.add(lblX1Pos);
        objectAttributesPanel.add(txtX1Pos);
        objectAttributesPanel.add(btnX1PosAccept);

        objectAttributesPanel.add(lblY1Pos);
        objectAttributesPanel.add(txtY1Pos);
        objectAttributesPanel.add(btnY1PosAccept);

        objectAttributesPanel.add(lblX2Pos);
        objectAttributesPanel.add(txtX2Pos);
        objectAttributesPanel.add(btnX2PosAccept);

        objectAttributesPanel.add(lblY2Pos);
        objectAttributesPanel.add(txtY2Pos);
        objectAttributesPanel.add(btnY2PosAccept);

        objectAttributesPanel.add(lblColor);
        objectAttributesPanel.add(txtColor);
        objectAttributesPanel.add(btnColorAccept);

        objectAttributesPanel.add(lblFill);
        objectAttributesPanel.add(txtFill);
        objectAttributesPanel.add(btnFillAccept);

        btnNameAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtName.getText()));
        btnX1PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtX1Pos.getText()));
        btnY1PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtY1Pos.getText()));
        btnX2PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtX2Pos.getText()));
        btnY2PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtY2Pos.getText()));
        btnColorAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtColor.getText()));
        btnFillAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtFill.getText()));

        // sending a null value will make all the objects
        // in the container unselectable;
        updateAttributeValues(null);
    }

    public void updateObjectFromJTextfield(Object o, String value){

        if(o.equals(btnNameAccept)){
            selectedShape.setName(value);
        } else if (o.equals(btnX1PosAccept)) {
            selectedShape.setX1(Integer.valueOf(value));
        }
        else if (o.equals(btnY1PosAccept)) {
            selectedShape.setY1(Integer.valueOf(value));
        }
        else if (o.equals(btnX2PosAccept)) {
            selectedShape.setX2(Integer.valueOf(value));
        }
        else if (o.equals(btnY2PosAccept)) {
            selectedShape.setY2(Integer.valueOf(value));
        }
        else if (o.equals(btnColorAccept)) {
            selectedShape.setColor(Color.GREEN);
        }else if(o.equals(btnFillAccept)){
            selectedShape.setFill(Objects.equals(value, "true") ? true : false);
        }

        updateGameObjectJList();

       // updateAttributeValues(selectedShape);

    }

    public void changeX1Pos(String value){
        //selectedShape.setX1(value);
        selectedShape.setX1(Integer.valueOf(value));
        System.out.println("txtbox event triggered");
    }

    public static void updateObjectsAttributesPanel(int index){

        //index is received from from the jList panel
        // from a mouse click that selects the index of the JList
        // this corresponds to the position of the shape object
        // in the objects arraylist (by reference)
        if(Helper.createdGameObjects != null && jListOfGameObjectNames.getModel().getSize() > 0){
            selectedShape = Helper.createdGameObjects.get(index);
            updateAttributeValues(selectedShape);

        }
    }

    static void updateAttributeValues(Shape shape){
        if(shape != null){
            objectAttributesPanel.setVisible(true);
            txtName.setText(shape.getName());
            txtX1Pos.setText(String.valueOf(shape.getX1()));
            txtY1Pos.setText(String.valueOf(shape.getY1()));
            txtX2Pos.setText(String.valueOf(shape.getX2()));
            txtY2Pos.setText(String.valueOf(shape.getY2()));
            txtColor.setText(String.valueOf(shape.getColor()));
            txtFill.setText(String.valueOf(shape.isFill()));

        }else {
            objectAttributesPanel.setVisible(false);
            txtName.setText("");
            txtX1Pos.setText("");
            txtY1Pos.setText("");
            txtX2Pos.setText("");
            txtY2Pos.setText("");
            txtColor.setText("");
            txtFill.setText("");
        }
    }

    public static void updateGameObjectJList(){
        if(Helper.createdGameObjects != null){
            jListModelObjectNames.clear();
            Helper.createdGameObjects.forEach(s -> jListModelObjectNames.addElement(s.getName()));
            jListOfGameObjectNames.getModel();

            jListOfGameObjectNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    currentSelectedObjectIndex = jListOfGameObjectNames.getSelectedIndex();
                    updateObjectsAttributesPanel(currentSelectedObjectIndex);
                }
            });
        }
    }

}
