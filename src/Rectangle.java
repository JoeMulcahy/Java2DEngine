import java.awt.*;

public class Rectangle extends Shape{

    public static int id = 1;

    public Rectangle(int x1, int y1, int x2, int y2, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, color, lineThickness, fill, "rect_" + id);
        id++;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(color);

        if(!fill){
            if(x2 > x1 && y2 > y1) {
                g2.drawRect(x1, y1, x2 - x1, y2 - y1); //0
            }else if(x2 < x1 &&  y2 > y1){
                g2.drawRect(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y2 < y1) {
                g2.drawRect(x1, y2, x2 - x1, y1 - y2);
            }else if(x2 < x1 && y2 < y1){
                g2.drawRect(x2, y2, x1 - x2, y1 - y2);
            }
        }else{
            if(x2 > x1 && y2 > y1) {
                g2.fillRect(x1, y1, x2 - x1, y2 - y1); //0
            }else if(x2 < x1 &&  y2 > y1){
                g2.fillRect(x2, y1, x1 - x2, y2 - y1);
            } else if (x2 > x1 && y2 < y1) {
                g2.fillRect(x1, y2, x2 - x1, y1 - y2);
            }else if(x2 < x1 && y2 < y1){
                g2.fillRect(x2, y2, x1 - x2, y1 - y2);
            }
        }

    }
}
