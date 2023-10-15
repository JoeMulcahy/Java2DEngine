package dev.app;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public class ObjectHighLighter{
    private int r, g, b;
    private int counter = 0;
    private AffineTransform transform;
    private Rectangle rectHighlighter;
    Shape transformedShape;
    private boolean isVisible;
    private int period;
    private int delay;

    public ObjectHighLighter(boolean isVisible, int period, int delay){
        this.isVisible = isVisible;
        this.delay = delay;
        this.period = period;

        r = 0;
        g = 0;
        b = 0;
        colorChangeTimer();
    }

    public ObjectHighLighter(){
        this(true, 100, 100);
    }

    private void colorChangeTimer(){
    // Need to tweak a few things
    // Color. .stop timer when invisible

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
        timer.scheduleAtFixedRate(task,delay,period);
    }

    public Graphics2D drawHighlighterBox(Graphics2D g2, GameObject o){

        rectHighlighter = new Rectangle();

        int width = Math.abs(o.x2 - o.x1);
        int height = Math.abs(o.y2 - o.y1);

        int offsetx1 = o.x1 - 5;
        int offsety1 = o.y1 - 5;
        int offsetwidth = width + 10;
        int offsetHeight = height + 10;

        transformedShape = rectHighlighter;

        rectHighlighter.setRect((o.x2 > o.x1 ? offsetx1 : offsetx1 - offsetwidth), (o.y2 > o.y1 ? offsety1 : offsetHeight), offsetwidth, offsetHeight);

        if(o.rotationAngle > -360 && o.rotationAngle < 360){
            transform = new AffineTransform();
            transform.rotate(Math.toRadians(o.rotationAngle), rectHighlighter.getX() + rectHighlighter.width/2, rectHighlighter.getY() + rectHighlighter.height/2);
            transformedShape = transform.createTransformedShape(rectHighlighter);
        }

        g2.setColor(new Color(r, g, b, 200));
        g2.setStroke(new BasicStroke(0.5F));

        g2.draw(transformedShape);

        return g2;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
