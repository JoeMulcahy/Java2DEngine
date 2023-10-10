import java.awt.*;
import java.awt.geom.AffineTransform;

public class PolygonObject extends GameObject {

    //TO DO
    // This is not implemented properly
    // Need to research some basic geometry cos()
    public static int id = 1;
    private java.awt.Polygon polygon;
    private AffineTransform transform;
    Shape transformedShape;

    private java.util.List<Integer> xCoords;
    private java.util.List<Integer> yCoords;
    private int nPoints;
    private int[][] points;


    public PolygonObject(int x1, int y1, int x2, int y2, int[][] points, double rotationAngle, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "poly");
        polygon = new Polygon();
        this.nPoints = 3;

        this.points = new int[][]{
                {x1, y1},
                {x1, y2},
                {x2, y2}
        };

        for(int i = 0; i < nPoints; i++){
            polygon.addPoint(this.points[i][0], this.points[i][1]);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(this.lineThickness));
        g2.setColor(color);

        transformedShape = polygon;

        if(rotationAngle > -359 && rotationAngle < 360){
            transform = new AffineTransform();
            transform.rotate(Math.toRadians(rotationAngle), x1, y2);
            transformedShape = transform.createTransformedShape(polygon);
        }

        if (!fill) {
            g2.draw(transformedShape);
        } else {
            g2.fill(transformedShape);
        }
    }

    public void setPoints(int[][] points){
        this.points = points;
    }

    public int[][] getPoints(){
        return this.points;
    }
}
