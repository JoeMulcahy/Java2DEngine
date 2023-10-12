import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel {

    private int width = Settings.editorPanelWidth;
    private int height = Settings.editorPanelHeight;
    private Grid2 testGrid;

    public TestPanel(){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);

        testGrid = new Grid2(400, 400, 10, 5, 5, 50, true);
        //testGrid.drawGrid();
    }

    public void paint(Graphics g) {
        super.paint(g);
        testGrid.drawGrid(g);
        repaint();
    }

}
