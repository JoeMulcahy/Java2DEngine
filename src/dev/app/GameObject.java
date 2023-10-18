package dev.app;

import java.awt.*;
import java.awt.geom.AffineTransform;

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
    private AffineTransform transform;
    private Rectangle rectHighlighter;
    Shape transformedShape;
    private static int objectNumber = 0;
    private int z_value;

    protected int dx;
    protected int dy;

    public GameObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness, boolean fill, String name, boolean drawBorder) {

        if(!(this instanceof LineObject)){
            int h = Math.abs(y2 - y1);
            int w = Math.abs(x2 - x1);

            if(x1 < x2 && y1 > y2){
                y1 = y1 - h;
                y2 = y2 + h;
            }else if(x1 > x2 && y1 < y2){
                x1 = x1 - w;
                x2 = x2 + w;
            }else if(x1 > x2 && y1 > y2){
                x1 = x1 - w;
                y1 = y1 - h;
                x2 = x2 + w;
                y2 = y2 + h;
            }
        }

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

        this.dx = (int)(Math.random() * 6 - 3) + 1;
        this.dy = (int)(Math.random() * 6 - 3) + 1;

        this.z_value = objectNumber;
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

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth(){
        return Math.abs(x2 - x1);
    }

    public int getHeight(){
        return Math.abs(y2 - y1);
    }

    @Override
    public String toString() {
        return "dev.joe.GameObject{" +
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
