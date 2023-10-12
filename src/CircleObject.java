import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class CircleObject extends GameObject {

    public static int id = 1;
    private Ellipse2D.Double circle;
    private AffineTransform transform;
    Shape transformedShape;

    public CircleObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill, boolean drawBorder) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "circle_" + id, drawBorder);
        circle = new Ellipse2D.Double();
        id++;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(this.lineThickness));
        g2.setColor(color);

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        circle.setFrame((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);

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

            if(drawBorder){
                Color bColor = this.color;
                // > 1 for border color brighter than object
                // < 1 for darker
                double ratio = 0.8;

                double r = (bColor.getRed() * ratio) % 255;
                double g = (bColor.getGreen() * ratio) % 255;;
                double b = (bColor.getBlue() * ratio) % 255;;

                bColor = new Color((int)r,(int)g,(int)b);

                g2.setStroke(new BasicStroke(lineThickness));
                g2.setColor(bColor);

                int tx1 = (int)(x1 + lineThickness / 2);
                int ty1 = (int)(y1 + lineThickness / 2);;

                circle.setFrame((x2 > x1 ? tx1 : tx1 - width), (y2 > y1 ? ty1 : ty1 - height), (width - lineThickness), height - lineThickness);
                transformedShape = transform.createTransformedShape(circle);
                g2.draw(transformedShape);
            }

        }
    }

    public Ellipse2D.Double getCircle() {
        return circle;
    }

    public void setCircle(Ellipse2D.Double circle) {
        this.circle = circle;
    }

    public boolean isDrawBorder() {
        return drawBorder;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    @Override
    public String toString() {
        String description = "\t{\n" +
                "\t\t\"type\" : \"" + "circle" + "\"" +
                "\n\t\t\"name\" : \"" + this.getName() + "\"" +
                "\n\t\t\"x1\" : \"" + x1 + "\"" +
                "\n\t\t\"y1\" : \"" + y1 + "\"" +
                "\n\t\t\"x2\" : \"" + x2 + "\"" +
                "\n\t\t\"y2\" : \"" + y2 + "\"" +
                "\n\t\t\"rotationAngle\" : \"" + rotationAngle + "\"" +
                "\n\t\t\"color\" : \"" + color + "\"" +
                "\n\t\t\"lineThickness\" : \"" + lineThickness + "\"" +
                "\n\t\t\"fill\" : \"" + fill + "\"" +
                "\n\t}";

        return description;
    }
}
