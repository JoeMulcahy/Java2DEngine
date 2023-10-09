import java.awt.*;

public class PolygonObject extends GameObject {

    //TO DO
    // This is not implemented properly
    // Need to research some basic geometry cos()

    private int[] xCoords;
    private int[] yCoords;
    private int verWidth;
    private int numberOfPoints;
    public static int id = 1;

    public PolygonObject(int x1, int y1, int x2, int y2, double rotationAngle, int verWidth, int numberOfPoints, Color color, float lineThickness, boolean fill) {
        super(x1, y1, x2, y2, rotationAngle, color, lineThickness, fill, "poly_" + id);
        this.verWidth = verWidth;
        this.numberOfPoints = numberOfPoints;
        this.xCoords = new int[numberOfPoints];
        this.yCoords = new int[numberOfPoints];
        setCoorinatesArrays();

        id++;

    }

    private void setCoorinatesArrays(){

        if(y2 <= y1 || y2 >= y1){
            this.xCoords[0] = x1;
            this.xCoords[1] = x2 + (verWidth / 2);
            this.xCoords[2] = x2 - (verWidth / 2);

            this.yCoords[0] = y1;
            this.yCoords[1] = y2;
            this.yCoords[2] = y2;
        }
        else if(x2 <= x1 || x2 >= x2){
            this.xCoords[0] = x1;
            this.xCoords[1] = x2;
            this.xCoords[2] = x2;

            this.yCoords[0] = y1;
            this.yCoords[1] = y2 + (verWidth / 2);
            this.yCoords[2] = y2 - (verWidth / 2);
        }

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(lineThickness));
        if(!fill){
            g2.drawPolygon(this.xCoords, this.yCoords, this.numberOfPoints);
        }else{
            g2.fillPolygon(this.xCoords, this.yCoords, this.numberOfPoints);
        }


    }

    public int getVerWidth() {
        return verWidth;
    }

    public void setVerWidth(int verWidth) {
        this.verWidth = verWidth;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    public String toString() {
        String description = "{\n" +
                "\t\"type\" : \"" + "poly" + "\"" +
                "\n\t\"name\" : \"" + this.getName() + "\"" +
                "\n\t\"x1\" : \"" + x1 + "\"" +
                "\n\t\"y1\" : \"" + y1 + "\"" +
                "\n\t\"x2\" : \"" + x2 + "\"" +
                "\n\t\"y2\" : \"" + y2 + "\"" +
                "\n\t\"rotationAngle\" : \"" + rotationAngle + "\"" +
                "\n\t\"color\" : \"" + color + "\"" +
                "\n\t\"lineThickness\" : \"" + lineThickness + "\"" +
                "\n\t\"fill\" : \"" + fill + "\"" +
                "\n},";

        return description;
    }
}
