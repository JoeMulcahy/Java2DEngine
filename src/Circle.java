import java.awt.*;

public class Circle extends GameObject {

    public static int id = 1;

    public Circle(int x1, int y1, int x2, int y2, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, color, lineThickness, fill, "circle_" + id);
        id++;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(this.lineThickness));
        g2.setColor(color);

        if(!fill){
            if(x2 > x1 && y2 > y1) {
                g2.drawOval(x1, y1, x2 - x1, y2 - y1);
            }else if(x2 < x1 &&  y2 > y1){
                g2.drawOval(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y2 < y1) {
                g2.drawOval(x1, y2, x2 - x1, y1 - y2);
            }else if(x2 < x1 && y2 < y1){
                g2.drawOval(x2, y2, x1 - x2, y1 - y2);
            }
        }else{
            if(x2 > x1 && y2 > y1) {
                g2.fillOval(x1, y1, x2 - x1, y2 - y1);
            }else if(x2 < x1 &&  y2 > y1){
                g2.fillOval(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y2 < y1) {
                g2.fillOval(x1, y2, x2 - x1, y1 - y2);
            }else if(x2 < x1 && y2 < y1){
                g2.fillOval(x2, y2, x1 - x2, y1 - y2);
            }
        }
    }

    @Override
    public String toString() {
        return "Circle{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", color=" + color +
                ", lineThickness=" + lineThickness +
                ", fill=" + fill +
                "} " + super.toString();
    }
}
