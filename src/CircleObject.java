import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class CircleObject extends GameObject {

    public static int id = 1;
    private Ellipse2D.Double circle;
    private AffineTransform transform;
    Shape transformedShape;


    public CircleObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "circle_" + id);
        circle = new Ellipse2D.Double();
        id++;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(this.lineThickness));
        g2.setColor(color);

        if(x2 > x1 && y2 > y1) {
            circle.setFrame(x1, y1, x2 - x1, y2 - y1);
        }else if(x2 < x1 &&  y2 > y1){
            circle.setFrame(x2, y1, x1 - x2, y2 - y1);
        } else if (x2 > x1 && y2 < y1) {
            circle.setFrame(x1, y2, x2 - x1, y1 - y2);
        }else if(x2 < x1 && y2 < y1){
            circle.setFrame(x2, y2, x1 - x2, y1 - y2);
        }

        transformedShape = circle;

        if(rotationAngle > -359 && rotationAngle < 360){
            transform = new AffineTransform();
            transform.rotate(Math.toRadians(rotationAngle), circle.getX() + circle.width/2, circle.getY() + circle.height/2);
            transformedShape = transform.createTransformedShape(circle);
        }

        if(!fill){
            g2.draw(transformedShape);
        }else{
            g2.fill(transformedShape);
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