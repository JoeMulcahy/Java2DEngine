import java.awt.*;

public class Grid {

    private int width = Settings.editorPanelWidth;

    private static Stroke gridWidth = new BasicStroke(1);

    static void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        int width = Settings.editorPanelWidth;
        int height = Settings.editorPanelHeight;

        if (GameManager.showGrid == true) {
            g2.setColor(new Color(183, 186, 190, 50));

            g2.setStroke(gridWidth);
            for (int i = 0; i <= GameManager.gridNumberOfRowsAndCols[0]; i++) {
                g2.drawLine(i * ((width / GameManager.gridNumberOfRowsAndCols[0])), 0, i * ((width / GameManager.gridNumberOfRowsAndCols[0])), height);
                g2.drawLine(0, i * ((height / GameManager.gridNumberOfRowsAndCols[1])), width, i * ((height / GameManager.gridNumberOfRowsAndCols[1])));
            }
        }
    }

}