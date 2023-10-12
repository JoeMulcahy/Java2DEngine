import java.awt.*;
import java.awt.geom.AffineTransform;

public class PolygonObject extends GameObject {

    //TO DO
    // This is not implemented properly
    // Need to research some basic geometry cos()
    public static int id = 1;
    private java.awt.Polygon polygon;
    private java.awt.Polygon polyBorder;
    private AffineTransform transform;
    Shape transformedShape;

    private java.util.List<Integer> xCoords;
    private java.util.List<Integer> yCoords;
    private int nPoints;
    private int[][] points;


    public PolygonObject(int x1, int y1, int x2, int y2, int[][] points, double rotationAngle, Color color, float lineThickness, boolean fill, boolean drawBorder) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "poly", drawBorder);
        polygon = new Polygon();
        polyBorder = new Polygon();
        this.nPoints = 3;

        this.points = new int[][]{
                {x1, y1},
                {x1, y2},
                {x2, y2}
        };

        for(int i = 0; i < nPoints; i++){
            polygon.addPoint(this.points[i][0], this.points[i][1]);
        }

        //Bit of a hack but draws border on instantiation if drawBorder
        // I tried draw it on the fly but too resource hungry

        if(drawBorder){
            polyBorder = drawPolyBorder();
        }
    }

    public java.awt.Polygon drawPolyBorder(){

        int[][] borderPoints = new int[][]{
                {(int)(x1 + lineThickness / 2), (int)(y1 + lineThickness)},
                {(int)(x1 + lineThickness / 2), (int)(y2 - lineThickness / 2)},
                {(int)(x2 - lineThickness), (int)(y2 - lineThickness / 2)}
        };

        for(int i = 0; i < nPoints; i++){
            polyBorder.addPoint((int)(borderPoints[i][0]), (int)(borderPoints[i][1]));
        }

        return polyBorder;
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

                transformedShape = transform.createTransformedShape(polyBorder);
                g2.draw(transformedShape);
            }
        }
    }

    public void setPoints(int[][] points){
        this.points = points;
    }

    public int[][] getPoints(){
        return this.points;
    }
}
