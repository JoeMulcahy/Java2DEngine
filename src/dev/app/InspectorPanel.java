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
    //  1. Displays a list of created GameObjects
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
    JLabel lblTextObjectTextArea = new JLabel("text");
    JLabel lblTextObjectFontSize = new JLabel("font size");
    JLabel lblTextObjectFontType = new JLabel("font type");
    JLabel lblTextObjectFontStyle = new JLabel("font style");

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
    private JButton btnTextAreaAccept;
    private JButton btnDeleteObject;
    private JTextField txtGameObjectName;

    private JTextArea textAreaTextObject;
    private JComboBox<Integer> cmbTextObjectSize;
    private JComboBox<String> cmbTextObjectFontName;
    private JComboBox<String> cmbTextObjectFontStyle;
    private JComboBox<Boolean> cmbHasFill;
    private JComboBox<Boolean> cmbHasBorder;
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
        initialiseObjectPropertiesPanel();

        btnNameAccept.addActionListener(s -> {
            selectedGameObject.setName(txtGameObjectName.getText());
            updateGameObjectJList();
        });

        btnColorAccept.addActionListener(s -> changeObjectColor(cpPalette.getSelectedColor()));

        btnTextAreaAccept.addActionListener(s -> {
                if(selectedGameObject instanceof TextObject && !textAreaTextObject.getText().isEmpty()){
                    ((TextObject) selectedGameObject).setText(textAreaTextObject.getText());
                }
        });

        cmbHasBorder.addActionListener(s -> selectedGameObject.setDrawBorder(cmbHasBorder.getItemAt(cmbHasBorder.getSelectedIndex())));

        cmbHasFill.addActionListener(s -> selectedGameObject.setFill(cmbHasFill.getItemAt(cmbHasFill.getSelectedIndex())));

        cmbLineThickness.addActionListener(s -> selectedGameObject.setLineThickness(cmbLineThickness.getItemAt(cmbLineThickness.getSelectedIndex())));

        cmbTextObjectSize.addActionListener(s -> {
            TextObject t = (TextObject)selectedGameObject;
            t.setSize(cmbTextObjectSize.getItemAt(cmbTextObjectSize.getSelectedIndex()));
        });

        cmbTextObjectFontStyle.addActionListener(s -> {
            TextObject t = (TextObject)selectedGameObject;
            t.setFontStyle((cmbTextObjectFontStyle.getSelectedIndex()));
        });

        cmbTextObjectFontName.addActionListener(s -> {
            TextObject t = (TextObject)selectedGameObject;
            t.setFontName(cmbTextObjectFontName.getItemAt(cmbTextObjectFontName.getSelectedIndex()));
        });

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
            updatePropertiesValues(GameManager.currentlySelectedGameObject);
        });

        spinnerPositionUD.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldPosUpDown, (Integer)spinnerPositionUD.getModel().getValue());
            updatePropertiesValues(GameManager.currentlySelectedGameObject);
        });

        spinnerEdgePositionLR.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldEdgePosLeftRight, (Integer)spinnerEdgePositionLR.getModel().getValue());
            updatePropertiesValues(GameManager.currentlySelectedGameObject);
        });

        spinnerEdgePositionUD.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldEdgePosUpDown, (Integer)spinnerEdgePositionUD.getModel().getValue());
            updatePropertiesValues(GameManager.currentlySelectedGameObject);
        });

        spinnerSize.addChangeListener(s -> {
            updateObjectFromSpinnerAndGrid(s.getSource(), oldSize, (Integer)spinnerSize.getModel().getValue());
            updatePropertiesValues(GameManager.currentlySelectedGameObject);
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
                updateObjectsPropertiesPanel(currentSelectedObjectIndex);
            }
        });

        updateGameObjectJList();
    }

    private void initialiseObjectPropertiesPanel(){

        gameObjectAttributesPanel = new JPanel();
        gameObjectAttributesPanel.setLayout(new GridLayout(25, 3));
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

        cmbHasFill = new JComboBox<>(new Boolean[]{false, true});
        cmbHasBorder = new JComboBox<>(new Boolean[]{false, true});
        cmbLineThickness = new JComboBox<>(new Float[]{1f, 2f, 4f, 8f, 16f, 32f, 64f, 128f});

        textAreaTextObject = new JTextArea();
        cmbTextObjectFontName = new JComboBox<>(new String[]{"Helvetica", "Monospaced", "Serif"});
        cmbTextObjectFontStyle = new JComboBox<>(new String[]{"Plain", "Bold", "Italic", "Bold & Italic"});

        Integer[] fontSizes = new Integer[50];
        for(int i = 2; i < fontSizes.length; i++){
            fontSizes[i] = i * 2;        }

        cmbTextObjectSize = new JComboBox<>(fontSizes);
        txtGameObjectName = new JTextField();
        cpPalette = new ColorPalette();

        btnNameAccept = new JButton("\u2714");
        btnColorAccept = new JButton("\u2714");
        btnTextAreaAccept = new JButton("\u2714");

        btnDeleteObject = new JButton("DELETE");
        btnDeleteObject.setBackground(Color.red);
        btnDeleteObject.setForeground(Color.white);

        gameObjectAttributesPanel.add(lblName);
        gameObjectAttributesPanel.add(txtGameObjectName);
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
        gameObjectAttributesPanel.add(cmbHasFill);
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
        gameObjectAttributesPanel.add(cmbHasBorder);
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

        gameObjectAttributesPanel.add(lblTextObjectTextArea);
        gameObjectAttributesPanel.add(textAreaTextObject);
        gameObjectAttributesPanel.add(btnTextAreaAccept);

        gameObjectAttributesPanel.add(lblTextObjectFontSize);
        gameObjectAttributesPanel.add(cmbTextObjectSize);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblTextObjectFontType);
        gameObjectAttributesPanel.add(cmbTextObjectFontName);
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(lblTextObjectFontStyle);
        gameObjectAttributesPanel.add(cmbTextObjectFontStyle);
        gameObjectAttributesPanel.add(new JLabel());

     /////////////////////////////////////////////////////

        gameObjectAttributesPanel.add(new JLabel());
        gameObjectAttributesPanel.add(new JLabel());
        gameObjectAttributesPanel.add(new JLabel());

        gameObjectAttributesPanel.add(btnDeleteObject);

        // sending a null value will make all the objects
        // in the container unselectable;
        updatePropertiesValues(null);
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

    public void updateObjectsPropertiesPanel(int index){
        if(EditorWindow.Instance.getGameObjects() != null && jListOfGameObjectNames.getModel().getSize() > 0){
            selectedGameObject = GameManager.currentlySelectedGameObject;
            updatePropertiesValues(selectedGameObject);
        }
    }

    public void updatePropertiesValues(GameObject o){
        if(o != null){
            boolean isTextObject = o instanceof TextObject;

            //lblTextObjectTextArea.setVisible(isTextObject);
            textAreaTextObject.setVisible(isTextObject);
            btnTextAreaAccept.setVisible(isTextObject);
           // lblTextObjectFontSize.setVisible(isTextObject);
            cmbTextObjectSize.setVisible(isTextObject);
            //lblTextObjectFontType.setVisible(isTextObject);
            cmbTextObjectFontName.setVisible(isTextObject);
            //lblTextObjectFontStyle.setVisible(isTextObject);
            cmbTextObjectFontStyle.setVisible(isTextObject);

            //lblX2Pos.setVisible(!isTextObject);
            spinnerX2.setVisible(!isTextObject);
            //lblY2Pos.setVisible(!isTextObject);
            spinnerY2.setVisible(!isTextObject);
            //lblBorder.setVisible(!isTextObject);
            cmbHasBorder.setVisible(!isTextObject);
            //lblLineThickness.setVisible(!isTextObject);
            cmbLineThickness.setVisible(!isTextObject);
            //lblEdgePositionVertical.setVisible(!isTextObject);
            spinnerEdgePositionUD.setVisible(!isTextObject);
           // lblEdgePositionHorizontal.setVisible(!isTextObject);
            spinnerEdgePositionLR.setVisible(!isTextObject);
            //lblSize.setVisible(!isTextObject);
            spinnerSize.setVisible(!isTextObject);

            if(isTextObject){
                textAreaTextObject.setText(((TextObject) o).getText());
                cmbTextObjectSize.setSelectedItem((Integer)((TextObject) o).getSize());
                cmbTextObjectFontName.setSelectedItem((String)((TextObject) o).getFontName());
                cmbTextObjectFontStyle.setSelectedItem((Integer)((TextObject) o).getFontStyle());
            }

            smX1.setValue(o.getX1());
            smX2.setValue(o.getX2());
            smY1.setValue(o.getY1());
            smY2.setValue(o.getY2());
            smRotation.setValue(o.getRotationAngle());
            smDepth.setValue(o.getDepth_Z());

            gameObjectAttributesPanel.setVisible(true);
            txtGameObjectName.setText(o.getName());
            cpPalette.setSelectedColor(o.getColor());

            cmbHasFill.setSelectedItem((Boolean)o.isFill());
            cmbHasBorder.setSelectedItem((Boolean)o.isDrawBorder());
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
