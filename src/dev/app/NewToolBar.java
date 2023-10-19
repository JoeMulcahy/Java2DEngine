/*
 * Created by JFormDesigner on Wed Oct 18 22:03:56 IST 2023
 */

package dev.app;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author josep
 */
public class NewToolBar extends JPanel {

    public static NewToolBar Instance;
    public JColorChooser colorChooser;

    public NewToolBar() {
        Instance = this;
        initComponents();

        ////////////////////////////////////////////////////////
        //  Values for the line thickness combo box
        ////////////////////////////////////////////////////////

        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();

        for(int i = 0; i <= 50; i++){
            model.addElement(i);
        }

        cbLineThickness.setModel(model);

        /////////////////////////////////////////////////////////
        //  Action Listeners
        /////////////////////////////////////////////////////////

        btnRectangle.addActionListener(e -> changeTool("rect"));
        btnCircle.addActionListener(e -> changeTool("circle"));
        btnLine.addActionListener(e -> changeTool("line"));
        btnPoly.addActionListener(e -> changeTool("poly"));
        btnText.addActionListener(e -> changeTool("text"));
        cbLineThickness.addItemListener(e -> selectLineThickness(cbLineThickness.getSelectedItem().toString()));
        btnFillShape.addActionListener(e -> toggleFillShape());
        btnPickColor.addActionListener(e -> pickColor(e));
        btnBorder.addActionListener(e -> toggleObjectBorder());
        btnUndo.addActionListener(e -> undoRedoEditorWindow("undo"));
        btnRedo.addActionListener(e -> undoRedoEditorWindow("redo"));
        btnReset.addActionListener(e -> clearEditorWindow());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        lblDrawTools = new JLabel();
        btnRectangle = new JButton();
        btnCircle = new JButton();
        btnLine = new JButton();
        btnPoly = new JButton();
        btnText = new JButton();
        txtArea = new JTextField();
        hSpacer1 = new JPanel(null);
        lblLineThickness = new JLabel();
        cbLineThickness = new JComboBox();
        lblFill = new JLabel();
        btnFillShape = new JButton();
        lblBorder = new JLabel();
        btnBorder = new JButton();
        lblColor = new JLabel();
        btnPickColor = new JButton();
        btnUndo = new JButton();
        btnRedo = new JButton();
        btnReset = new JButton();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- lblDrawTools ----
        lblDrawTools.setText("Tools");
        add(lblDrawTools, "cell 1 1");

        //---- btnRectangle ----
        btnRectangle.setText("Rect");
        add(btnRectangle, "cell 1 2");

        //---- btnCircle ----
        btnCircle.setText("Circle");
        add(btnCircle, "cell 2 2");

        //---- btnLine ----
        btnLine.setText("Line");
        add(btnLine, "cell 1 3");

        //---- btnPoly ----
        btnPoly.setText("Poly");
        add(btnPoly, "cell 2 3");

        //---- btnText ----
        btnText.setText("Text");
        add(btnText, "cell 1 4");
        add(txtArea, "cell 2 4");

        //---- hSpacer1 ----
        hSpacer1.setBackground(Color.white);
        add(hSpacer1, "cell 1 5 2 1");

        //---- lblLineThickness ----
        lblLineThickness.setText("  Thickness");
        lblLineThickness.setFont(new Font("Inter", Font.PLAIN, 12));
        add(lblLineThickness, "cell 1 6");
        add(cbLineThickness, "cell 2 6");

        //---- lblFill ----
        lblFill.setText("  Fill");
        add(lblFill, "cell 1 7");

        //---- btnFillShape ----
        btnFillShape.setText("Fill");
        add(btnFillShape, "cell 2 7");

        //---- lblBorder ----
        lblBorder.setText("  Border");
        add(lblBorder, "cell 1 8");

        //---- btnBorder ----
        btnBorder.setText("btnBorder");
        add(btnBorder, "cell 2 8");

        //---- lblColor ----
        lblColor.setText("  Color");
        add(lblColor, "cell 1 9");

        //---- btnPickColor ----
        btnPickColor.setText("Pick");
        add(btnPickColor, "cell 2 9");

        //---- btnUndo ----
        btnUndo.setText("Undo");
        add(btnUndo, "cell 1 11");

        //---- btnRedo ----
        btnRedo.setText("Redo");
        add(btnRedo, "cell 2 11");

        //---- btnReset ----
        btnReset.setText("Reset");
        add(btnReset, "cell 1 12 2 1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

/////////////////////////////////////////////////////////////////
//    Selects the current tool from the toolbar
////////////////////////////////////////////////////////////////
    private void changeTool(String tool){
        switch(tool){
            case "circle" -> GameManager.currentSelectedTool = GameManager.Tool.CIRCLE;
            case "line" -> GameManager.currentSelectedTool = GameManager.Tool.LINE;
            case "poly" -> GameManager.currentSelectedTool = GameManager.Tool.POLY;
            case "select" -> GameManager.currentSelectedTool = GameManager.Tool.SELECT;
            case "text" -> GameManager.currentSelectedTool = GameManager.Tool.TEXT;
            default -> GameManager.currentSelectedTool = GameManager.Tool.RECT;
        }
    }

    ////////////////////////////////////////////////////////////
    // Toggles border on object
    ////////////////////////////////////////////////////////////
    private void toggleObjectBorder(){

        System.out.println("trigger");
        if(GameManager.toggleObjectBorder){
            GameManager.toggleObjectBorder = false;
            GameManager.fillShape = false;
        }else{
            GameManager.toggleObjectBorder = true;
            GameManager.fillShape = true;
        }

        System.out.println(GameManager.toggleObjectBorder);
    }

    ///////////////////////////////////////////////////////////
    // Gets the text written in the JTextField
    ///////////////////////////////////////////////////////////
    public String getText(){
        if(txtArea.getText().equals("")){
            return "Empty Text Field";
        }else{
            return txtArea.getText();
        }
    }

    ////////////////////////////////////////////////////////////
    //  Selects line thickness of selected tool
    ////////////////////////////////////////////////////////////
    private void selectLineThickness(String s){
        GameManager.lineThickness = Integer.parseInt(s);
    }

    ////////////////////////////////////////////////////////////
    //  Toggle fill shape
    ////////////////////////////////////////////////////////////
    private void toggleFillShape(){
        if(GameManager.fillShape){
            GameManager.fillShape = false;
        }else {
            GameManager.fillShape = true;
        }
    }

    ////////////////////////////////////////////////////////////
    // Chooses a colour for the currently selected tool
    ////////////////////////////////////////////////////////////
    private void pickColor(ActionEvent e) {
        colorChooser =  new JColorChooser();
        GameManager.currentColor = JColorChooser.showDialog(this, "Pick a color", Color.white);
    }

    ////////////////////////////////////////////////////////////
    // !!!!!!!! Undo / Redo created Game Object !!!!!!!!!!!!!
    // This will be changed in the future to cover all
    //  actions in the program. Currently, undo/redo functionality
    // only work on created GameObject and not changes to
    // GameObject properties or attributes
    // //////////////////////////////////////////////////////////
    private void undoRedoEditorWindow(String action){
        switch(action){
            case "undo" -> UndoRedoStack.Instance.undo();
            default -> UndoRedoStack.Instance.redo();
        }
    }

    ////////////////////////////////////////////////////////////
    //  Resets and removes all created Game Objects
    ////////////////////////////////////////////////////////////
    private void clearEditorWindow(){
        EditorWindow.Instance.clearScreen();
    }

    ////////////////////////////////////////////////////////////
    //  Get an instance of the ToolBar
    ////////////////////////////////////////////////////////////
    public NewToolBar getToolBar(){
        return this;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel lblDrawTools;
    private JButton btnRectangle;
    private JButton btnCircle;
    private JButton btnLine;
    private JButton btnPoly;
    private JButton btnText;
    private JTextField txtArea;
    private JPanel hSpacer1;
    private JLabel lblLineThickness;
    private JComboBox cbLineThickness;
    private JLabel lblFill;
    private JButton btnFillShape;
    private JLabel lblBorder;
    private JButton btnBorder;
    private JLabel lblColor;
    private JButton btnPickColor;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnReset;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
