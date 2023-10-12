import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

class ModButtons extends JPanel{

    private JButton btnIncreaseVertical;
    private JButton btnDecreaseVertical;
    private JButton btnIncreaseLeft;
    private JButton btnDecreaseRight;
    private static ModButtons Instance;

    public ModButtons(){
        Instance = this;

        btnIncreaseVertical = new JButton("+");
        btnDecreaseVertical = new JButton("-");
        btnIncreaseLeft = new JButton("+");
        btnDecreaseRight = new JButton("-");

        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new GridLayout(2, 3, 2, 2));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.setVisible(true);

        JPanel[][] panelHolder = new JPanel[2][3];

        for(int r = 0; r < 2; r++){
            for(int c = 0; c < 3; c++){
                panelHolder[r][c] = new JPanel();
                this.add(panelHolder[r][c]);
            }
        }

        panelHolder[0][1].add(btnIncreaseVertical);
        panelHolder[1][1].add(btnDecreaseVertical);
        panelHolder[1][0].add(btnIncreaseLeft);
        panelHolder[1][2].add(btnDecreaseRight);

        btnIncreaseVertical.addActionListener(s -> doButtonStuff("up"));
        btnDecreaseVertical.addActionListener(s -> doButtonStuff("down"));
        btnIncreaseLeft.addActionListener(s -> doButtonStuff("left"));
        btnDecreaseRight.addActionListener(s ->doButtonStuff("right"));
    }

    public void doButtonStuff(String s){
        switch(s){
            case "up" -> GameManager.currentlySelectedGameObject.moveUp();
            case "down" -> GameManager.currentlySelectedGameObject.moveDown();
            case "left" -> GameManager.currentlySelectedGameObject.moveLeft();
            case "right" -> GameManager.currentlySelectedGameObject.moveRight();
        }

        System.out.println(s);
    }
}

public class GameObjectAttributesPanel extends JPanel {

    private int width = Settings.gameObjectsPanelWidth;
    private int height = Settings.gameObjectsPanelHeight;

    private JPanel gameObjectsListPanel;
    private JPanel gameObjectAttributesPanel;
    static JList<String> jListOfGameObjectNames;
    private static DefaultListModel<String> jListModelObjectNames;
    private int selectedObjectIndex = 0;
    private JScrollPane scrollPaneForJList;
    public static GameObjectAttributesPanel Instance;
    private static ColorPalette cpPalette;
    private JLabel lblName;
    private JLabel lblX1Pos;
    private JLabel lblY1Pos;
    private JLabel lblX2Pos;
    private JLabel lblY2Pos;
    private JLabel lblColor;
    private JLabel lblFill;
    private JLabel lblRotation;
    private JLabel lblBorder;
    private JLabel lblZ_Depth;
    private JLabel lblBlank;

    private JLabel lblObjectSizeMod;

    private JButton btnNameAccept;
    private JButton btnX1PosAccept;
    private JButton btnY1PosAccept;
    private JButton btnX2PosAccept;
    private JButton btnY2PosAccept;
    private JButton btnColorAccept;
    private JButton btnFillAccept;
    private JButton btnRotationAccept;
    private JButton btnLineThicknessAccept;
    private JButton btnLineZ_DepthAccept;
    private JButton btnBorder;
    private JButton btnDeleteObject;

    private ModButtons btnMod;


    private JTextField txtName;
    private JTextField txtX1Pos;
    private JTextField txtY1Pos;
    private JTextField txtX2Pos;
    private JTextField txtY2Pos;
    private JTextField txtFill;
    private JTextField txtRotation;
    private JTextField txtLineThickness;
    private JTextField txtZ_Depth;
    private JTextField txtBorder;

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
        gameObjectAttributesPanel.setLayout(new GridLayout(15, 3));
        gameObjectAttributesPanel.setPreferredSize(new Dimension(width / 2, height));
        gameObjectAttributesPanel.setBackground(Color.gray);
        gameObjectAttributesPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        initialiseObjectAttributesPanel();

        gameObjects = GameManager.createdGameObjects;

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
        JLabel lblLineThickness = new JLabel("line thickness: ");
        lblZ_Depth = new JLabel("Depth value: ");
        lblBorder = new JLabel("border");
        lblObjectSizeMod = new JLabel("size +/-");
        lblBlank = new JLabel();

        txtName = new JTextField();
        txtX1Pos = new JTextField();
        txtY1Pos = new JTextField();
        txtX2Pos = new JTextField();
        txtY2Pos = new JTextField();
        cpPalette = new ColorPalette();
        txtFill = new JTextField();
        txtRotation = new JTextField();
        txtLineThickness = new JTextField();
        txtBorder = new JTextField();
        txtZ_Depth = new JTextField();

        btnNameAccept = new JButton("\u2714");
        btnX1PosAccept = new JButton("\u2714");
        btnY1PosAccept= new JButton("\u2714");
        btnX2PosAccept = new JButton("\u2714");
        btnY2PosAccept = new JButton("\u2714");
        btnColorAccept = new JButton("\u2714");
        btnFillAccept = new JButton("\u2714");
        btnRotationAccept = new JButton("\u2714");
        btnLineThicknessAccept = new JButton("\u2714");
        btnLineZ_DepthAccept= new JButton("\u2714");
        btnBorder= new JButton("\u2714");
        btnMod = new ModButtons();



        btnDeleteObject = new JButton("DELETE");
        btnDeleteObject.setBackground(Color.red);
        btnDeleteObject.setForeground(Color.white);

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

        gameObjectAttributesPanel.add(lblLineThickness);
        gameObjectAttributesPanel.add(txtLineThickness);
        gameObjectAttributesPanel.add(btnLineThicknessAccept);

        gameObjectAttributesPanel.add(lblZ_Depth);
        gameObjectAttributesPanel.add(txtZ_Depth);
        gameObjectAttributesPanel.add(btnLineZ_DepthAccept);

        gameObjectAttributesPanel.add(lblBorder);
        gameObjectAttributesPanel.add(txtBorder);
        gameObjectAttributesPanel.add(btnBorder);

        gameObjectAttributesPanel.add(lblObjectSizeMod);
        gameObjectAttributesPanel.add(btnMod);

//        gameObjectAttributesPanel.add(lblBlank);
//        gameObjectAttributesPanel.add(lblBlank);

        gameObjectAttributesPanel.add(lblBlank);
        gameObjectAttributesPanel.add(lblBlank);

        gameObjectAttributesPanel.add(btnDeleteObject);



        btnNameAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtName.getText()));
        btnX1PosAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtX1Pos.getText()));
        btnY1PosAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtY1Pos.getText()));
        btnX2PosAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtX2Pos.getText()));
        btnY2PosAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtY2Pos.getText()));
        btnColorAccept.addActionListener(s -> changeObjectColor(cpPalette.Instance.getSelectedColor()));
        btnFillAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtFill.getText()));
        btnRotationAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtRotation.getText()));
        btnLineThicknessAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtLineThickness.getText()));
        btnLineZ_DepthAccept.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtZ_Depth.getText()));
        btnBorder.addActionListener(s -> updateObjectFromAttributeFields(s.getSource(), txtBorder.getText()));

        btnDeleteObject.addActionListener(s ->  updateObjectFromAttributeFields(s.getSource(), null));

        // sending a null value will make all the objects
        // in the container unselectable;
        updateAttributeValues(null);
    }

    private void changeObjectColor(Color c){
        selectedGameObject.setColor(c);
    }

    public void updateObjectFromAttributeFields(Object o, String value){

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
        }else if (o.equals(btnLineThicknessAccept)) {
            selectedGameObject.setLineThickness(Integer.valueOf(value));
        }else if (o.equals(btnLineZ_DepthAccept)) {
            EditorWindow.Instance.reorderObjects(selectedGameObject, currentSelectedObjectIndex, Integer.parseInt(txtZ_Depth.getText()));
        }else if (o.equals(btnBorder)) {
            selectedGameObject.setDrawBorder(Objects.equals(value, "true") ? true : false);
        }else if (o.equals(btnDeleteObject)) {
            EditorWindow.Instance.deleteObject(currentSelectedObjectIndex);
        }

        updateGameObjectJList();
    }

    public void updateObjectsAttributesPanel(int index){

        //index is received from the jList panel
        // from a mouse click that selects the index of the JList
        // this corresponds to the position of the shape object
        // in the objects arraylist (by reference)
        if(GameManager.createdGameObjects != null && jListOfGameObjectNames.getModel().getSize() > 0){
            selectedGameObject = GameManager.createdGameObjects.get(index);
            GameManager.currentlySelectedGameObject = selectedGameObject;
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
            txtFill.setText(String.valueOf(gameObject.isFill()));
            txtRotation.setText(String.valueOf(gameObject.getRotationAngle()));
            txtLineThickness.setText(String.valueOf(gameObject.getLineThickness()));
            txtBorder.setText(String.valueOf(gameObject.isDrawBorder()));
            txtZ_Depth.setText(String.valueOf(currentSelectedObjectIndex));

        }else {
            gameObjectAttributesPanel.setVisible(false);
            txtName.setText("");
            txtX1Pos.setText("");
            txtY1Pos.setText("");
            txtX2Pos.setText("");
            txtY2Pos.setText("");
            txtFill.setText("");
            txtRotation.setText("");
            txtLineThickness.setText("");
            txtBorder.setText("");
            txtZ_Depth.setText("");
        }
    }


    // clears and updates GameObjects in the JList panel
    public void updateGameObjectJList(){
        if(GameManager.createdGameObjects != null){
            jListModelObjectNames.clear();

            if(GameManager.createdGameObjects.size() > 0){
                for(int i = 0; i < UndoRedoStack.Instance.getStackCounter(); i++){
                    String element = GameManager.createdGameObjects.get(i).getName();
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
