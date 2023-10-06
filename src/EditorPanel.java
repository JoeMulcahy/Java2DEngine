import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {
    private int width = Helper.editorControlPanelWidth;
    private int height = Helper.editorControlPanelHeight;
    private JButton btnRectangle;
    private JButton btnCircle;
    private JButton btnLine;
    private JButton btnPoly;
    private JButton btnClear;
    private JButton btnFillShape;
    private ColorPalette palette;
    private JComboBox cbLineThickness;

    public EditorPanel(){
        initialise();
    }

    private void initialise(){
        this.setLayout(new GridLayout(4,2));
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

        btnRectangle.addActionListener(s -> changeShape("rect"));
        btnCircle.addActionListener(s -> changeShape("circle"));
        btnLine.addActionListener(s -> changeShape("line"));
        btnPoly.addActionListener(s -> changeShape("poly"));
        btnClear.addActionListener(s -> clearEditorWindow());
        cbLineThickness.addItemListener(s -> selectLineThickness(cbLineThickness.getSelectedItem().toString()));
        btnFillShape.addActionListener(s -> toggleFillShape());

    }

    private void selectLineThickness(String s){

        String thickness = s;
        Helper.lineThickness = Integer.parseInt(s);
    }
    private JComboBox populateComboBox(){

        String[] values = {"1", "4", "8", "16"};
        JComboBox<String> cb = new JComboBox<>(values);

        return cb;
    }

    private void clearEditorWindow(){
        EditorWindow.clearScreen();
    }

    private void toggleFillShape(){
        if(Helper.fillShape){
            Helper.fillShape = false;
        }else {
            Helper.fillShape = true;
        }
    }

    private void changeShape(String shape){
        switch(shape){
            case "rect" -> Helper.currentShape = Helper.ShapeSelector.RECT;
            case "circle" -> Helper.currentShape = Helper.ShapeSelector.CIRCLE;
            case "line" -> Helper.currentShape = Helper.ShapeSelector.LINE;
            case "poly" -> Helper.currentShape = Helper.ShapeSelector.POLY;
            default -> Helper.currentShape = Helper.ShapeSelector.LINE;
        }
    }

    public EditorPanel getEditorPanel(){
        return this;
    }
}
