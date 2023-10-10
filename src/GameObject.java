import java.awt.*;
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
    private String name;
    private int r, g, b;
    private int counter = 0;


    public GameObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill, String name) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.rotationAngle = rotationAngle;
        this.color = color;
        this.lineThickness = lineThickness;
        this.fill = fill;
        this.name = name;

        r = 0;
        g = 0;
        b = 0;
        colorChangeTimer();
    }

    public abstract void draw(Graphics2D g2);

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

    public Graphics2D drawHighlighted(boolean isSelected, Graphics2D g2){

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        int offsetx1 = x1 - 5;
        int offsety1 = y1 - 5;
        int offsetwidth = width + 10;
        int offsetHeight = height + 10;

        g2.setColor(new Color(r, g, b, 200));

        g2.drawRect((x2 > x1 ? offsetx1 : offsetx1 - offsetwidth), (y2 > y1 ? offsety1 : offsetHeight), offsetwidth, offsetHeight);

        return g2;
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
