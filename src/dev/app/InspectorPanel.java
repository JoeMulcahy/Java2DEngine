package dev.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class InspectorPanel extends JPanel {

    int oldPosLeftRight = 0;
    int oldPosUpDown = 0;
    int oldEdgePosLeftRight = 0;
    int oldEdgePosUpDown = 0;
    int oldSize = 0;

    private int width = Settings.gameObjectsPanelWidth;
    private int height = Settings.gameObjectsPanelHeight;

    ///////////////////////////////////////////////////////////////
    //  These are the two main JPanels
    //  1. Displays a list of created dev.joe.GameObject
    //  2. Displays the attributes for the currently selected object
    private JPanel gameObjectsListPanel;
    private JPanel gameObjectAttributesPanel;
    /////////////////////////////////////////////////////////////////

    static JList<String> jListOfGameObjectNames;
    private static DefaultListModel<String> jListModelObjectNames;
    private int selectedObjectIndex = 0;
    private JScrollPane scrollPaneForJList;
    public static InspectorPanel Instance;
    private ColorPalette cpPalette;

    private SpinnerModel smX1;
    private SpinnerModel smY1;
    private SpinnerModel smX2;
    private SpinnerModel smY2;
    private SpinnerModel smRotation;
    private SpinnerModel smDepth;
    private SpinnerModel smPositionLR;
    private SpinnerModel smPositionUD;
    private SpinnerModel smEdgePositionLR;
    private SpinnerModel smEdgePositionUD;
    private SpinnerModel smSize;

    private JSpinner spinnerX1;
    private JSpinner spinnerY1;
    private JSpinner spinnerX2;
    private JSpinner spinnerY2;
    private JSpinner spinnerRotation;
    private JSpinner spinnerDepth;
    private JSpinner spinnerPositionLR;
    private JSpinner spinnerPositionUD;
    private JSpinner spinnerEdgePositionLR;
    private JSpinner spinnerEdgePositionUD;
    private JSpinner spinnerSize;

    private JButton btnNameAccept;
    private JButton btnColorAccept;
    private JButton btnDeleteObject;
    private JTextField txtName;

    private JComboBox<Boolean> cmbFill;
    private JComboBox<Boolean> cmbBorder;
    private JComboBox<Float> cmbLineThickness;

    java.util.List<GameObject> gameObjects;
    static GameObject selectedGameObject;
    static int currentSelectedObjectIndex = 0;

    public InspectorPanel(){
        //////////////////////////////////
        //  Constructor
        //////////////////////////////////
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridLayout(1,2));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        this.setVisible(true);

        Instance = this;

        initialiseGameObjectsListPanel();
        initialiseObjectAttributesPanel();

        btnNameAccept.addActionListener(s -> {
            selectedGameObject.setName(txtName.getText());
            updateGameObjectJList();
        });

        btnColorAccept.addActionListener(s -> changeObjectColor(cpPalette.getSelectedColor()));

        cmbBorder.addItemListener(s -> selectedGameObject.setDrawBorder(cmbBorder.getItemAt(cmbBorder.getSelectedIndex())));
        cmbFill.addItemListener(s -> selectedGameObject.setFill(cmbFill.getItemAt(cmbFill.getSelectedIndex())));
        cmbLineThickness.addItemListener(s -> selectedGameObject.setLineThickness(cmbLineThickness.getItemAt(cmbLineThickness.getSelectedIndex())));

        spinnerX1.addChangeListener(s -> selectedGameObject.setX1((Integer)spinnerX1.getModel().getValue()));
        spinnerY1.addChangeListener(s -> selectedGameObject.setY1((Integer)spinnerY1.getModel().getValue()));
        spinnerX2.addChangeListener(s -> selectedGameObject.setX2((Integer)spinnerX2.getModel().getValue()));
        spinnerY2.addChangeListener(s -> selectedGameObject.setY2((Integer)spinnerY2.getModel().getValue()));

        spinnerDepth.addChangeListener(s -> {
            EditorWindow.Instance.reorderObjects(selectedGameObject, currentSelectedObjectIndex, (Integer)spinnerDepth.getModel().getValue());
        });

        spinnerRotation.addChangeListener(s -> selectedGameObject.setRotationAngle((Double)spinnerRotation.getModel().getValue()));

        spinnerPositionLR.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldPosLeftRight, (Integer)spinnerPositionLR.getModel().getValue());
            updateAttributeValues(GameManager.currentlySelectedGameObject);
        });

        spinnerPositionUD.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldPosUpDown, (Integer)spinnerPositionUD.getModel().getValue());
            updateAttributeValues(GameManager.currentlySelectedGameObject);
        });
        spinnerEdgePositionLR.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldEdgePosLeftRight, (Integer)spinnerEdgePositionLR.getModel().getValue());
            updateAttributeValues(GameManager.currentlySelectedGameObject);
        });
        spinnerEdgePositionUD.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldEdgePosUpDown, (Integer)spinnerEdgePositionUD.getModel().getValue());
            updateAttributeValues(GameManager.currentlySelectedGameObject);
        });
        spinnerSize.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldSize, (Integer)spinnerSize.getModel().getValue());
            updateAttributeValues(GameManager.currentlySelectedGameObject);
        });

        this.add(gameObjectsListPanel);
        this.add(gameObjectAttributesPanel);
    }

    private void initialiseGameObjectsListPanel(){
        gameObjectsListPanel = new JPanel();
        gameObjectsListPanel.setPreferredSize(new Dimension(width / 2, height));
        gameObjectsListPanel.setBackground(Color.gray);
        gameObjectsListPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        jListModelObjectNames = new DefaultListModel<>();
        jListOfGameObjectNames = new JList<>(jListModelObjectNames);

        scrollPaneForJList = new JScrollPane(jListOfGameObjectNames);
        scrollPaneForJList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneForJList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        gameObjectsListPanel.add(scrollPaneForJList);

        // Selects the GameObject from the JList
        jListOfGameObjectNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                GameManager.currentlySelectedGameObject = EditorWindow.Instance.getGameObjects().get(jListOfGameObjectNames.getSelectedIndex());
                currentSelectedObjectIndex = EditorWindow.Instance.getGameObjects().indexOf(GameManager.currentlySelectedGameObject);
                updateObjectsAttributesPanel(currentSelectedObjectIndex);
            }
        });

        updateGameObjectJList();
    }

    private void initialiseObjectAttributesPanel(){

        gameObjectAttributesPanel = new JPanel();
        gameObjectAttributesPanel.setLayout(new GridLayout(20, 3));
        gameObjectAttributesPanel.setPreferredSize(new Dimension(width / 2, height));
        gameObjectAttributesPanel.setBackground(Color.gray);
        gameObjectAttributesPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        smX1 = new SpinnerNumberModel(0, 0, EditorWindow.Instance.getWidth(), 1);
        smY1 = new SpinnerNumberModel(0, 0, EditorWindow.Instance.getHeight(), 1);
        smX2 = new SpinnerNumberModel(0, 0, EditorWindow.Instance.getWidth(), 1);
        smY2 = new SpinnerNumberModel(0, 0, EditorWindow.Instance.getHeight(), 1);
        smDepth = new SpinnerNumberModel(0, 0, 1000, 1);

        smRotation = new SpinnerNumberModel(0, -359, 369, 1.0);

        smPositionLR = new SpinnerNumberModel(0, -1920, 1920, 1);
        smPositionUD = new SpinnerNumberModel(0, -1920, 1920, 1);
        smEdgePositionLR = new SpinnerNumberModel(0, -1920, 1920, 1);
        smEdgePositionUD = new SpinnerNumberModel(0, -1920, 1920, 1);
        smSize = new SpinnerNumberModel(0, -100, 100, 1);

        spinnerX1 = new JSpinner(smX1);
        spinnerY1 = new JSpinner(smY1);
        spinnerX2 = new JSpinner(smX2);
        spinnerY2 = new JSpinner(smY2);
        spinnerRotation = new JSpinner(smRotation);
        spinnerDepth = new JSpinner(smDepth);
        spinnerPositionLR = new JSpinner(smPositionLR);
        spinnerPositionUD = new JSpinner(smPositionUD);
        spinnerEdgePositionLR = new JSpinner(smEdgePositionLR);
        spinnerEdgePositionUD = new JSpinner(smEdgePositionUD);
        spinnerSize = new JSpinner(smSize);

        cmbFill = new JComboBox<>(new Boolean[]{false, true});
        cmbBorder = new JComboBox<>(new Boolean[]{false, true});
        cmbLineThickness = new JComboBox<>(new Float[]{1f, 2f, 4f, 8f, 16f, 32f, 64f, 128f});

        JLabel lblName = new JLabel("Name: ");
        JLabel lblX1Pos = new JLabel("x1 pos: ");
        JLabel lblY1Pos = new JLabel("y1 pos: ");
        JLabel lblX2Pos = new JLabel("x2 pos: ");
        JLabel lblY2Pos = new JLabel("y2 pos: ");
        JLabel lblColor = new JLabel("color: ");
        JLabel lblFill = new JLabel("fill: ");
        JLabel lblRotation = new JLabel("rotation: ");
        JLabel lblLineThickness = new JLabel("line thickness: ");
        JLabel lblZ_Depth = new JLabel("Depth value: ");
        JLabel lblBorder = new JLabel("border");
        JLabel lblPositionHorizontal = new JLabel("Position(hzt)");
        JLabel lblPositionVertical = new JLabel("Position(vrt)");
        JLabel lblEdgePositionVertical = new JLabel("Edge(hzt)");
        JLabel lblEdgePositionHorizontal = new JLabel("Edge(vrt)");
        JLabel lblSize = new JLabel("Size");

        txtName = new JTextField();
        cpPalette = new ColorPalette();

        btnNameAccept = new JButton("\u2714");
        btnColorAccept = new JButton("\u2714");

        btnDeleteObject = new JButton("DELETE");
        btnDeleteObject.setBackground(Color.red);
        btnDeleteObject.setForeground(Color.white);

        gameObjectAttributesPanel.add(lblName);
        gameObjectAttributesPanel.add(txtName);
        gameObjectAttributesPanel.add(btnNameAccept);

        gameObjectAttributesPanel.add(lblX1Pos);
        gameObjectAttributesPanel.add(spinnerX1);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblY1Pos);
        gameObjectAttributesPanel.add(spinnerY1);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblX2Pos);
        gameObjectAttributesPanel.add(spinnerX2);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblY2Pos);
        gameObjectAttributesPanel.add(spinnerY2);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblColor);
        gameObjectAttributesPanel.add(cpPalette);
        gameObjectAttributesPanel.add(btnColorAccept);

        gameObjectAttributesPanel.add(lblFill);
        gameObjectAttributesPanel.add(cmbFill);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblRotation);
        gameObjectAttributesPanel.add(spinnerRotation);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblLineThickness);
        gameObjectAttributesPanel.add(cmbLineThickness);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblZ_Depth);
        gameObjectAttributesPanel.add(spinnerDepth);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblBorder);
        gameObjectAttributesPanel.add(cmbBorder);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblPositionHorizontal);
        gameObjectAttributesPanel.add(spinnerPositionLR);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblPositionVertical);
        gameObjectAttributesPanel.add(spinnerPositionUD);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblEdgePositionHorizontal);
        gameObjectAttributesPanel.add(spinnerEdgePositionLR);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblEdgePositionVertical);
        gameObjectAttributesPanel.add(spinnerEdgePositionUD);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblSize);
        gameObjectAttributesPanel.add(spinnerSize);
        gameObjectAttributesPanel.add(new JLabel());

     /////////////////////////////////////////////////////

        gameObjectAttributesPanel.add(new JLabel());
        gameObjectAttributesPanel.add(new JLabel());
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(btnDeleteObject);

        // sending a null value will make all the objects
        // in the container unselectable;
        updateAttributeValues(null);
    }

    private void updateObjectFromSpinnerAndGrid(Object o, int oldValue, int newValue){

        System.out.println("old: " + oldValue);
        System.out.println("new: " + newValue);
        if(o.equals(spinnerPositionLR)){
            if ((newValue > oldValue)) {
                EditorWindow.getEditorGrid().moveObject(selectedGameObject, "right");
            } else {
                EditorWindow.getEditorGrid().moveObject(selectedGameObject, "left");
            }
            oldPosLeftRight = newValue;
        }else if(o.equals(spinnerPositionUD)){
            if ((newValue > oldValue)) {
                EditorWindow.getEditorGrid().moveObject(selectedGameObject, "down");
            } else {
                EditorWindow.getEditorGrid().moveObject(selectedGameObject, "up");
            }
            oldPosUpDown = newValue;
        }else if(o.equals(spinnerEdgePositionLR)){
            if ((newValue > oldValue)) {
                EditorWindow.getEditorGrid().adjustObjectEdge(selectedGameObject, "right");
            } else {
                EditorWindow.getEditorGrid().adjustObjectEdge(selectedGameObject, "left");
            }
            oldEdgePosLeftRight = newValue;
        }else if(o.equals(spinnerEdgePositionUD)){
            if ((newValue > oldValue)) {
                EditorWindow.getEditorGrid().adjustObjectEdge(selectedGameObject, "down");
            } else {
                EditorWindow.getEditorGrid().adjustObjectEdge(selectedGameObject, "up");
            }
            oldEdgePosUpDown = newValue;
        }else if(o.equals(spinnerSize)){
            if ((newValue > oldValue)) {
                EditorWindow.getEditorGrid().adjustObjectSize(selectedGameObject, "increase");

            } else {
                EditorWindow.getEditorGrid().adjustObjectSize(selectedGameObject, "decrease");
            }
            oldSize = newValue;
        }
    }

    public void updateObjectsAttributesPanel(int index){
        if(EditorWindow.Instance.getGameObjects() != null && jListOfGameObjectNames.getModel().getSize() > 0){
            selectedGameObject = GameManager.currentlySelectedGameObject;
            updateAttributeValues(selectedGameObject);
        }
    }

    public void updateAttributeValues(GameObject o){
        if(o != null){
            smX1.setValue(o.getX1());
            smX2.setValue(o.getX2());
            smY1.setValue(o.getY1());
            smY2.setValue(o.getY2());
            smRotation.setValue(o.getRotationAngle());
            smDepth.setValue(o.getDepth_Z());

            gameObjectAttributesPanel.setVisible(true);
            txtName.setText(o.getName());
            cpPalette.setSelectedColor(o.getColor());

            cmbFill.setSelectedItem((Boolean)o.isFill());
            cmbBorder.setSelectedItem((Boolean)o.isDrawBorder());
            cmbLineThickness.setSelectedItem((Float)o.getLineThickness());

            oldPosLeftRight = (Integer)smPositionLR.getValue();
            oldPosUpDown = (Integer)smPositionUD.getValue();
            oldEdgePosLeftRight = (Integer)smEdgePositionLR.getValue();
            oldEdgePosUpDown = (Integer)smEdgePositionUD.getValue();
            oldSize = (Integer)smSize.getValue();

        }else {
            gameObjectAttributesPanel.setVisible(false);
        }
    }

    // clears and updates GameObjects in the JList panel
    public void updateGameObjectJList(){

        //Updates the JList from the UndoRedoStack (0 to stack counter)

        if(EditorWindow.Instance.getGameObjects() != null){
            jListModelObjectNames.clear();

            if(!EditorWindow.Instance.getGameObjects().isEmpty()){
                for(int i = 0; i < UndoRedoStack.Instance.getStackCounter(); i++){
                    String element = EditorWindow.Instance.getGameObjects().get(i).getName();
                    jListModelObjectNames.addElement(element);
                }
            }

        }
    }

    private void changeObjectColor(Color c){
        selectedGameObject.setColor(c);
    }
}
