import java.awt.*;

public class Grid {

    private static Stroke gridWidth = new BasicStroke(1);

    static Graphics draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        int width = Helper.editorPanelWidth;
        int height = Helper.editorPanelHeight;

        if (Helper.showGrid == true) {
            g2.setColor(new Color(183, 186, 190, 50));

            g2.setStroke(gridWidth);
            for (int i = 0; i < Helper.gridRowsAndColumns; i++) {
                g2.drawLine(i * ((width / Helper.gridRowsAndColumns)), 0, i * ((width / Helper.gridRowsAndColumns)), height);
                g2.drawLine(0, i * ((height / Helper.gridRowsAndColumns)), width, i * ((height / Helper.gridRowsAndColumns)));
            }

        }

        return g2;
    }

}