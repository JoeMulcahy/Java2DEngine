import java.awt.*;

public class Line extends GameObject {

    public static int id = 1;

    public Line(int x1, int y1, int x2, int y2, Color color, float lineThickness) {
        super(x1, y1, x2, y2, color, lineThickness, false, "line_" + id);
        id++;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(lineThickness));
        g2.drawLine(x1, y1, x2, y2);
    }
}
