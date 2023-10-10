import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GameObjectAttributesPanel extends JPanel {

    private int width = Helper.gameObjectsPanelWidth;
    private int height = Helper.gameObjectsPanelHeight;

    private JPanel gameObjectsListPanel;
    private static JPanel gameObjectAttributesPanel;
    static JList<String> jListOfGameObjectNames;
    private static DefaultListModel<String> jListModelObjectNames;
    private int selectedObjectIndex = 0;
    private JScrollPane scrollPaneForJList;
    public static GameObjectAttributesPanel Instance;
    private static ColorPalette cpPalette;

    JLabel lblName;
    JLabel lblX1Pos;
    JLabel lblY1Pos;
    JLabel lblX2Pos;
    JLabel lblY2Pos;
    JLabel lblColor;
    JLabel lblFill;
    JLabel lblRotation;

    JButton btnNameAccept;
    JButton btnX1PosAccept;
    JButton btnY1PosAccept;
    JButton btnX2PosAccept;
    JButton btnY2PosAccept;
    JButton btnColorAccept;
    JButton btnFillAccept;
    JButton btnRotationAccept;

    private JTextField txtName = new JTextField();
    private JTextField txtX1Pos = new JTextField();
    private JTextField txtY1Pos = new JTextField();
    private JTextField txtX2Pos = new JTextField();
    private JTextField txtY2Pos = new JTextField();
    private JTextField txtColor = new JTextField();
    private JTextField txtFill = new JTextField();
    private JTextField txtRotation = new JTextField();
    java.util.List<GameObject> gameObjects;
    static GameObject selectedGameObject;
    static int currentSelectedObjectIndex = 0;

    public GameObjectAttributesPanel(){
        //Constructor
        initialise();
    }

    public void initialise(){
        Instance = this;

        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridLayout(1,2));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        this.setVisible(true);

        gameObjectsListPanel = new JPanel();
        gameObjectsListPanel.setPreferredSize(new Dimension(width / 2, height));
        gameObjectsListPanel.setBackground(Color.gray);
        gameObjectsListPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        gameObjectAttributesPanel = new JPanel();
        gameObjectAttributesPanel.setLayout(new GridLayout(8, 3));
        gameObjectAttributesPanel.setPreferredSize(new Dimension(width / 2, height));
        gameObjectAttributesPanel.setBackground(Color.gray);
        gameObjectAttributesPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        initialiseObjectAttributesPanel();

        gameObjects = Helper.createdGameObjects;

        jListModelObjectNames = new DefaultListModel<>();
        jListOfGameObjectNames = new JList<>(jListModelObjectNames);
        //jListOfGameObjectNames.setSize(new Dimension(width / 3 , height / 2));

        scrollPaneForJList = new JScrollPane(jListOfGameObjectNames);
        scrollPaneForJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneForJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        updateGameObjectJList();

        gameObjectsListPanel.add(scrollPaneForJList);

        this.add(gameObjectsListPanel);
        this.add(gameObjectAttributesPanel);

    }

    private void initialiseObjectAttributesPanel(){
        lblName = new JLabel("Name: ");
        lblX1Pos = new JLabel("x1 pos: ");
        lblY1Pos= new JLabel("y1 pos: ");
        lblX2Pos = new JLabel("x2 pos: ");
        lblY2Pos= new JLabel("y2 pos: ");
        lblColor = new JLabel("color: ");
        lblFill = new JLabel("fill: ");
        lblRotation = new JLabel("rotation: ");

        txtName = new JTextField();
        txtX1Pos = new JTextField();
        txtY1Pos = new JTextField();
        txtX2Pos = new JTextField();
        txtY2Pos = new JTextField();
        cpPalette = new ColorPalette();
        //txtColor = new JTextField();
        txtFill = new JTextField();
        txtRotation = new JTextField();

        btnNameAccept = new JButton("\u2714");
        btnX1PosAccept = new JButton("\u2714");
        btnY1PosAccept= new JButton("\u2714");
        btnX2PosAccept = new JButton("\u2714");
        btnY2PosAccept = new JButton("\u2714");
        btnColorAccept = new JButton("\u2714");
        btnFillAccept = new JButton("\u2714");
        btnRotationAccept = new JButton("\u2714");

        gameObjectAttributesPanel.add(lblName);
        gameObjectAttributesPanel.add(txtName);
        gameObjectAttributesPanel.add(btnNameAccept);

        gameObjectAttributesPanel.add(lblX1Pos);
        gameObjectAttributesPanel.add(txtX1Pos);
        gameObjectAttributesPanel.add(btnX1PosAccept);

        gameObjectAttributesPanel.add(lblY1Pos);
        gameObjectAttributesPanel.add(txtY1Pos);
        gameObjectAttributesPanel.add(btnY1PosAccept);

        gameObjectAttributesPanel.add(lblX2Pos);
        gameObjectAttributesPanel.add(txtX2Pos);
        gameObjectAttributesPanel.add(btnX2PosAccept);

        gameObjectAttributesPanel.add(lblY2Pos);
        gameObjectAttributesPanel.add(txtY2Pos);
        gameObjectAttributesPanel.add(btnY2PosAccept);

        gameObjectAttributesPanel.add(lblColor);
        gameObjectAttributesPanel.add(cpPalette);
        gameObjectAttributesPanel.add(btnColorAccept);

        gameObjectAttributesPanel.add(lblFill);
        gameObjectAttributesPanel.add(txtFill);
        gameObjectAttributesPanel.add(btnFillAccept);

        gameObjectAttributesPanel.add(lblRotation);
        gameObjectAttributesPanel.add(txtRotation);
        gameObjectAttributesPanel.add(btnRotationAccept);

        btnNameAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtName.getText()));
        btnX1PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtX1Pos.getText()));
        btnY1PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtY1Pos.getText()));
        btnX2PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtX2Pos.getText()));
        btnY2PosAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtY2Pos.getText()));
        btnColorAccept.addActionListener(s -> changeObjectColor(cpPalette.Instance.getSelectedColor()));
        btnFillAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtFill.getText()));
        btnRotationAccept.addActionListener(s -> updateObjectFromJTextfield(s.getSource(), txtRotation.getText()));

        // sending a null value will make all the objects
        // in the container unselectable;
        updateAttributeValues(null);
    }

    private void changeObjectColor(Color c){
        selectedGameObject.setColor(c);
    }

    public void updateObjectFromJTextfield(Object o, String value){

        if(o.equals(btnNameAccept)){
            selectedGameObject.setName(value);
        } else if (o.equals(btnX1PosAccept)) {
            selectedGameObject.setX1(Integer.valueOf(value));
        }
        else if (o.equals(btnY1PosAccept)) {
            selectedGameObject.setY1(Integer.valueOf(value));
        }
        else if (o.equals(btnX2PosAccept)) {
            selectedGameObject.setX2(Integer.valueOf(value));
        }
        else if (o.equals(btnY2PosAccept)) {
            selectedGameObject.setY2(Integer.valueOf(value));
        }
        else if (o.equals(btnColorAccept)) {
            selectedGameObject.setColor(cpPalette.getSelectedColor());
            btnColorAccept.setBackground(cpPalette.getSelectedColor());
        }else if(o.equals(btnFillAccept)){
            selectedGameObject.setFill(Objects.equals(value, "true") ? true : false);
        }else if (o.equals(btnRotationAccept)) {
            selectedGameObject.setRotationAngle(Double.valueOf(value));
        }

        updateGameObjectJList();
    }

    public void updateObjectsAttributesPanel(int index){

        //index is received from the jList panel
        // from a mouse click that selects the index of the JList
        // this corresponds to the position of the shape object
        // in the objects arraylist (by reference)
        if(Helper.createdGameObjects != null && jListOfGameObjectNames.getModel().getSize() > 0){
            selectedGameObject = Helper.createdGameObjects.get(index);
            Helper.currentlySelectedGameObject = selectedGameObject;
            updateAttributeValues(selectedGameObject);
        }
    }

    public void updateAttributeValues(GameObject gameObject){
        if(gameObject != null){
            gameObjectAttributesPanel.setVisible(true);
            txtName.setText(gameObject.getName());
            txtX1Pos.setText(String.valueOf(gameObject.getX1()));
            txtY1Pos.setText(String.valueOf(gameObject.getY1()));
            txtX2Pos.setText(String.valueOf(gameObject.getX2()));
            txtY2Pos.setText(String.valueOf(gameObject.getY2()));
            cpPalette.Instance.setSelectedColor(gameObject.getColor());
           // txtColor.setText(String.valueOf(gameObject.getColor()));
            txtFill.setText(String.valueOf(gameObject.isFill()));
            txtRotation.setText(String.valueOf(gameObject.getRotationAngle()));

        }else {
            gameObjectAttributesPanel.setVisible(false);
            txtName.setText("");
            txtX1Pos.setText("");
            txtY1Pos.setText("");
            txtX2Pos.setText("");
            txtY2Pos.setText("");
            //cpPalette.setVisible(false);
            //txtColor.setText("");
            txtFill.setText("");
            txtRotation.setText("");
        }
    }


    // clears and updates GameObjects in the JList panel
    public void updateGameObjectJList(){
        if(Helper.createdGameObjects != null){
            jListModelObjectNames.clear();

            if(Helper.createdGameObjects.size() > 0){
                for(int i = 0; i < UndoRedoStack.Instance.getStackCounter(); i++){
                    String element = Helper.createdGameObjects.get(i).getName();
                    jListModelObjectNames.addElement(element);
                }
            }

            jListOfGameObjectNames.getModel();

            jListOfGameObjectNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    currentSelectedObjectIndex = jListOfGameObjectNames.getSelectedIndex();
                    Instance.updateObjectsAttributesPanel(currentSelectedObjectIndex);
                }
            });
        }
    }

}
