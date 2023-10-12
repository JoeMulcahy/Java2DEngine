import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class EditorPanel extends JPanel {
    private int width = Settings.editorControlPanelWidth;
    private int height = Settings.editorControlPanelHeight;
    private JButton btnRectangle;
    private JButton btnCircle;
    private JButton btnLine;
    private JButton btnPoly;
    private JButton btnClear;
    private JButton btnFillShape;
    private JButton btnUndo;
    private JButton btnRedo;
    private JCheckBox cbObjectBorder;
    private ColorPalette palette;
    private JComboBox cbLineThickness;
    public static EditorPanel Instance;

    public EditorPanel(){
        Instance = this;
        initialise();
    }

    private void initialise(){
        this.setLayout(new GridLayout(6,2));
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

        btnRectangle.addActionListener(s -> changeShape("rect"));
        btnCircle.addActionListener(s -> changeShape("circle"));
        btnLine.addActionListener(s -> changeShape("line"));
        btnPoly.addActionListener(s -> changeShape("poly"));
        btnClear.addActionListener(s -> clearEditorWindow());
        cbLineThickness.addItemListener(s -> selectLineThickness(cbLineThickness.getSelectedItem().toString()));
        cbObjectBorder.addItemListener(s -> toggleObjectBorder(s));

        btnFillShape.addActionListener(s -> toggleFillShape());
        btnUndo.addActionListener(s -> undoRedoEditorWindow("undo"));
        btnRedo.addActionListener(s -> undoRedoEditorWindow("redo"));
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
            case "undo" -> UndoRedoStack.undo();
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

    private void changeShape(String shape){
        switch(shape){
            case "rect" -> GameManager.currentShape = GameManager.ShapeSelector.RECT;
            case "circle" -> GameManager.currentShape = GameManager.ShapeSelector.CIRCLE;
            case "line" -> GameManager.currentShape = GameManager.ShapeSelector.LINE;
            case "poly" -> GameManager.currentShape = GameManager.ShapeSelector.POLY;
            default -> GameManager.currentShape = GameManager.ShapeSelector.LINE;
        }
    }

    public EditorPanel getEditorPanel(){
        return this;
    }
}
