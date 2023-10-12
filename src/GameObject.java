import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameObject {
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected double rotationAngle;
    protected Color color;
    protected float lineThickness;
    protected boolean fill;
    protected boolean drawBorder;
    private String name;
    private int r, g, b;
    private int counter = 0;
    private AffineTransform transform;
    private Rectangle rectHighlighter;
    Shape transformedShape;
    private static int objectNumber = 0;
    private int z_value;


    public GameObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill, String name, boolean drawBorder) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.rotationAngle = rotationAngle;
        this.color = color;
        this.lineThickness = lineThickness;
        this.fill = fill;
        this.name = name;
        this.drawBorder = drawBorder;

        objectNumber++;

        this.z_value = objectNumber;

        r = 0;
        g = 0;
        b = 0;
        colorChangeTimer();
    }

    public abstract void draw(Graphics2D g2);

    public int getDepth_Z(){
        return z_value;
    }

    public void setDepth_Z(int z){
        z_value = z;
    }

    public boolean isDrawBorder() {
        return drawBorder;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    private void colorChangeTimer(){

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                r = counter % 255;
                g = 0;
                b = 0;

                counter += 50;
            };
        };
        timer.scheduleAtFixedRate(task,200,100);
    }

    public Graphics2D drawHighlighterBox(boolean isSelected, Graphics2D g2){

        rectHighlighter = new Rectangle();

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        int offsetx1 = x1 - 5;
        int offsety1 = y1 - 5;
        int offsetwidth = width + 10;
        int offsetHeight = height + 10;

        transformedShape = rectHighlighter;

        rectHighlighter.setRect((x2 > x1 ? offsetx1 : offsetx1 - offsetwidth), (y2 > y1 ? offsety1 : offsetHeight), offsetwidth, offsetHeight);

        if(rotationAngle > -360 && rotationAngle < 360){
            transform = new AffineTransform();
            transform.rotate(Math.toRadians(rotationAngle), rectHighlighter.getX() + rectHighlighter.width/2, rectHighlighter.getY() + rectHighlighter.height/2);
            transformedShape = transform.createTransformedShape(rectHighlighter);
        }

        g2.setColor(new Color(r, g, b, 200));
        g2.setStroke(new BasicStroke(0.5F));

        g2.draw(transformedShape);

        return g2;
    }

    public void snapToNearestGrid(){
        snapToNearestGridX();
        snapToNearestGridY();
    }

    public void snapToNearestGridX(){
        int width = x2 - x1;

        int x1c = x1 + width / 2;

        int gridNumberX = x1c / Helper.gridWidthHeight;
        int XPosInGrid = x1c % Helper.gridWidthHeight;

        if(XPosInGrid <= Helper.gridWidthHeight / 2){
            x1 = gridNumberX * Helper.gridWidthHeight;
        }else{
            x1 = ((gridNumberX + 1) * Helper.gridWidthHeight) - width;
        }
    }

    public void snapToNearestGridY(){
        int height = y2 - y1;

        int y1c = y1 + height / 2;

        int gridNumberY = y1c / Helper.gridWidthHeight;
        int YPosInGrid = y1c % Helper.gridWidthHeight;

        if(YPosInGrid <= Helper.gridWidthHeight / 2){
            y1 = gridNumberY * Helper.gridWidthHeight;
        }else{
            y1 = ((gridNumberY + 1) * Helper.gridWidthHeight) - height;
        }
    }

    public void moveLeft(){
        // if object center point x is positioned right of the nearest grid x
        // then snap to nearest grid x to left of object
        // else snap to the x grid point is the next nearest grid x

        if(x1 > 0){
            int width = x2 - x1;
            int x1c = x1 + width / 2;
            int gridNumberX = x1c / Helper.gridWidthHeight;

            if(x1 % Helper.gridWidthHeight == 0){
                x1 = x1 - Helper.gridWidthHeight;
            }else{

                x1 = (gridNumberX - 1) * Helper.gridWidthHeight;
            }
        }
    }

    public void moveRight(){
        if(x1 < Helper.editorPanelWidth){
            int width = x2 - x1;
            int x1c = x1 + width / 2;
            int gridNumberX = x1c / Helper.gridWidthHeight;

            if(x1 % Helper.gridWidthHeight + width == 0){
                x1 = (gridNumberX  + 1) * Helper.gridWidthHeight - width;
            }else{
                x1 = (gridNumberX * Helper.gridWidthHeight) - width ;
            }
        }
    }

    public void moveUp(){
        if(y1 > 0){
            int height = y2 - y1;
            int y1c = y1 + height / 2;
            int gridNumberY = y1c / Helper.gridWidthHeight;

            if(y1 % Helper.gridWidthHeight == 0){
                y1 = (gridNumberY - 1) * Helper.gridWidthHeight;
            }else{
                y1 = gridNumberY * Helper.gridWidthHeight;
            }
        }
    }

    public void moveDown(){
        if(y1 < Helper.editorPanelWidth){
            int height = y2 - y1;
            int y1c = y1 + height / 2;
            int gridNumberY = y1c / Helper.gridWidthHeight;

            if(y1 % Helper.gridWidthHeight + height == 0){
                y1 = (gridNumberY  + 1) * Helper.gridWidthHeight - height;
            }else{
                y1 = (gridNumberY * Helper.gridWidthHeight) - height ;
            }
        }
    }

    public void increaseSize(){

    }


    public void decreaseSize(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", rotationAngle=" + rotationAngle +
                ", color=" + color +
                ", lineThickness=" + lineThickness +
                ", fill=" + fill +
                ", name='" + name + '\'' +
                '}';
    }
}
