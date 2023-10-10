import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class LineObject extends GameObject {

    public static int id = 1;
    private Line2D line;
    private double width;
    private double height;
    private AffineTransform transform;
    Shape transformedShape;

    public LineObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, false, "line_" + id);
        id++;
        line = new Line2D.Double();

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(lineThickness));

        line.setLine(x1, y1, x2, y2);
        transformedShape = line;
        if(rotationAngle > -360 && rotationAngle < 360){
            transform = new AffineTransform();

            width = Math.abs(line.getX2() - line.getX1());
            height = Math.abs(line.getY2() - line.getY1());

            transform.rotate(Math.toRadians(rotationAngle), line.getX1() + width/2, line.getY1() + height/2);

            transformedShape = transform.createTransformedShape(line);
        }

        g2.draw(transformedShape);
    }

    @Override
    public String toString() {
        String description =  "\t{\n" +
                "\t\t\"type\" : \"" + "line" + "\"" +
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
