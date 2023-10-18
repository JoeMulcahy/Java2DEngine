package dev.app;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class TextObject extends GameObject{

    public static int id = 1;
    private Stroke stroke;
    private Font font;
    private int size;
    private String fontName;
    private int fontStyle;      // 0 - PLAIN, 1 - BOLD, 2 - ITALIC, 3 - BOLD & ITALIC
    private String text;
    private TextLayout textTl;

    public TextObject(int x1, int y1, int x2, int y2, double rotationAngle, Color color, float lineThickness,
                      boolean fill, boolean drawBorder, boolean hasCollisionDetection, String text) {
        super(x1, y2, x2, y2, rotationAngle, color, lineThickness, fill, "txt_" + id, drawBorder);
        this.text = text;
        this.size = y2 - y1;
        id++;

        font = new Font("Helvetica", 0, size);
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        updateFont();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        updateFont();
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        updateFont();
    }

    private void updateFont(){
        font = new Font(fontName, fontStyle, size);
    }

    @Override
    public void draw(Graphics2D g) {

        AffineTransform originalG = g.getTransform();

        FontRenderContext frc = g.getFontRenderContext();
        textTl = new TextLayout(text, font, frc);
        Shape textOutline = textTl.getOutline(null);

        if(rotationAngle > -359 && rotationAngle < 360){
            g.rotate(Math.toRadians(rotationAngle), x1 + textOutline.getBounds().getWidth() / 2, y1 + textOutline.getBounds().getHeight() / 2);
        }

        g.setColor(color);

        if(fill){
            g.setFont(font);
            g.drawString(text, x1, y1);
        }else{
            textTl = new TextLayout(text, font, frc);
            AffineTransform transform;
            transform = g.getTransform();
            transform.translate(x1, y1);
            g.transform(transform);
            g.draw(textOutline);
        }

        g.setTransform(originalG);
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
