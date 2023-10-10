import java.awt.*;
import java.awt.geom.AffineTransform;

public class RectangleObject extends GameObject {

    public static int id = 1;
    private Rectangle rect;
    private AffineTransform transform;
    Shape transformedShape;
    public RectangleObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "rect_" + id);
        rect = new Rectangle();
        id++;
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(color);

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        rect.setRect((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);

        transformedShape = rect;

        if(rotationAngle > -360 && rotationAngle < 360){
            transform = new AffineTransform();
            transform.rotate(Math.toRadians(rotationAngle), rect.getX() + rect.width/2, rect.getY() + rect.height/2);
            transformedShape = transform.createTransformedShape(rect);
        }

        if(!fill){
            g2.draw(transformedShape);
        }else{
            g2.fill(transformedShape);
        }
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        RectangleObject.id = id;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public String toString() {
        String description = "\t{\n" +
                "\t\t\"type\" : \"" + "rect" + "\"" +
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
