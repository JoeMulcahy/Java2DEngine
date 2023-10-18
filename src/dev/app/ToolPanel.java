package dev.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ToolPanel extends JPanel {
    private int width = Settings.toolPanelWidth;
    private int height = Settings.toolPanelHeight;
    private JButton btnRectangle;
    private JButton btnCircle;
    private JButton btnLine;
    private JButton btnPoly;
    private JButton btnClear;
    private JButton btnFillShape;
    private JButton btnUndo;
    private JButton btnRedo;
    private JCheckBox cbObjectBorder;
    private JButton btnSelect;
    private JButton btnText;
    private ColorPalette palette;
    private JComboBox cbLineThickness;
    private static JTextArea txtArea;
    public static ToolPanel Instance;

    public ToolPanel(){
        Instance = this;
        initialise();
    }

    public ToolPanel getToolPanel(){
        return this;
    }

    private void initialise(){
        this.setLayout(new GridLayout(8,2));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.setVisible(true);

        btnRectangle = new JButton("Rect");
        btnCircle = new JButton("Circle");
        btnLine = new JButton("Line");
        btnPoly = new JButton("Poly");
        btnClear = new JButton("Clear");
        btnFillShape = new JButton("Fill");
        btnUndo = new JButton("Undo");
        btnRedo = new JButton("Redo");
        cbObjectBorder = new JCheckBox("Toggle border");
        btnSelect = new JButton("Select");
        btnText = new JButton("Text");
        txtArea = new JTextArea();

        cbLineThickness = populateComboBox();

        palette = new ColorPalette();

        this.add(btnRectangle);
        this.add(btnCircle);
        this.add(btnLine);
        this.add(btnPoly);
        this.add(btnClear);
        this.add(palette);
        this.add(cbLineThickness);
        this.add(btnFillShape);
        this.add(btnUndo);
        this.add(btnRedo);
        this.add(cbObjectBorder);
        this.add(btnSelect);
        this.add(btnText);
        this.add(txtArea);


        btnRectangle.addActionListener(s -> changeTool("rect"));
        btnCircle.addActionListener(s -> changeTool("circle"));
        btnLine.addActionListener(s -> changeTool("line"));
        btnPoly.addActionListener(s -> changeTool("poly"));
        btnClear.addActionListener(s -> clearEditorWindow());
        cbLineThickness.addItemListener(s -> selectLineThickness(cbLineThickness.getSelectedItem().toString()));
        cbObjectBorder.addItemListener(s -> toggleObjectBorder(s));

        btnFillShape.addActionListener(s -> toggleFillShape());
        btnUndo.addActionListener(s -> undoRedoEditorWindow("undo"));
        btnRedo.addActionListener(s -> undoRedoEditorWindow("redo"));

        btnSelect.addActionListener(s -> changeTool("select"));
        btnText.addActionListener(s -> changeTool("text"));
    }

    private void toggleObjectBorder(ItemEvent e){

        int checked = e.getStateChange();

        if(checked == 1){
            GameManager.toggleObjectBorder = true;
            GameManager.fillShape = true;
        }else{
            GameManager.toggleObjectBorder = false;
        }

        System.out.println(GameManager.toggleObjectBorder);
    }

    public void changeColor(){
        GameManager.currentColor = palette.getSelectedColor();
    }

    private void undoRedoEditorWindow(String action){
        switch(action){
            case "undo" -> UndoRedoStack.Instance.undo();
            default -> UndoRedoStack.Instance.redo();
        }
    }

    private void selectLineThickness(String s){

        String thickness = s;
        GameManager.lineThickness = Integer.parseInt(s);
    }
    private JComboBox populateComboBox(){

        String[] values = {"1", "4", "8", "16"};
        JComboBox<String> cb = new JComboBox<>(values);

        return cb;
    }

    private void clearEditorWindow(){
        EditorWindow.Instance.clearScreen();
    }

    private void toggleFillShape(){
        if(GameManager.fillShape){
            GameManager.fillShape = false;
        }else {
            GameManager.fillShape = true;
        }
    }

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

    public ToolPanel getEditorPanel(){
        return this;
    }

    public String getText(){
        if(txtArea.getText().equals("")){
            return "Empty Text Field";
        }else{
            return txtArea.getText();
        }
    }
}
