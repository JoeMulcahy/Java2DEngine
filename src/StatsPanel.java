import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    Thread thread;
    private int FPS;

    private int width = Helper.statsPanelWidth;
    private int height = Helper.statsPanelHeight;

    public StatsPanel(){
        initialise();
    }
    public void initialise(){
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
        g2.drawString("Time running: " + Helper.getSecondsRun(), 20, 50);
        g2.drawString("Mouse x: " + Helper.getMouseX1(), 20, 80);
        g2.drawString("Mouse y: " + Helper.getMouseY1(), 20, 110);
        g2.drawString("Number of Objects: " + Helper.numberOfObjectsDrawn, 20, 130);
        g2.drawString("Current Shape: " + Helper.currentShape, 20, 150);
        g2.drawString("Grid: " + (Helper.showGrid ? "on" : "off"), 20, 170);
        g2.drawString("Grid Size: " + Helper.gridRowsAndColumns, 20, 190);
        g2.setColor(Helper.currentColor);
        g2.drawString("Color: ", 20, 210);
        g2.drawString("Line thickness: " + Helper.lineThickness, 20, 230);
        g2.drawString("Fill: " + (Helper.fillShape ? "on" : "off"), 20, 250);
        g2.drawString("Snap Mode" + (Helper.snapMode ? "On" : "Off"), 20, 270);
        repaint();
    }
}
