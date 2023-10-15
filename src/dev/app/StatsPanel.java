package dev.app;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    Thread thread;
    private int FPS;
    private int width = Settings.statsPanelWidth;
    private int height = Settings.statsPanelHeight;
    public static StatsPanel Instance;

    public StatsPanel(){
        initialise();
    }
    public void initialise(){
        Instance = this;
        FPS = 1;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.setVisible(true);

        //thread = new Thread(this, "Stats");
        //thread.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.RED);
        g2.setFont(new Font("Courier", Font.PLAIN, 15));
        g2.drawString("Time running: " + GameManager.getSecondsRun(), 20, 50);
        g2.drawString("Mouse x: " + GameManager.getMouseX1(), 20, 80);
        g2.drawString("Mouse y: " + GameManager.getMouseY1(), 20, 110);
        g2.drawString("Number of Objects: " + GameManager.numberOfObjectsDrawn, 20, 130);
        g2.drawString("Current Shape: " + GameManager.currentShape, 20, 150);
        //g2.drawString("dev.joe.Grid: " + (dev.joe.GameManager.showGrid ? "on" : "off"), 20, 170);
        g2.drawString("dev.joe.Grid Size: " + GameManager.gridNumberOfRowsAndCols, 20, 190);
        g2.setColor(GameManager.currentColor);
        g2.drawString("Color: ", 20, 210);
        g2.drawString("Line thickness: " + GameManager.lineThickness, 20, 230);
        g2.drawString("Fill: " + (GameManager.fillShape ? "on" : "off"), 20, 250);
        g2.drawString("Snap Mode" + (GameManager.snapMode ? "On" : "Off"), 20, 270);
        g2.drawString("Instruction counter: " + GameManager.instructionCounter, 20, 290);
        g2.drawString("Stack counter: " + UndoRedoStack.Instance.getStackCounter(), 20, 310);
        g2.drawString("Canvas W: " + Settings.editorPanelWidth, 20, 330);
        g2.drawString("Canvas H: " + Settings.editorPanelHeight, 20, 350);
        repaint();
    }
}
