import java.awt.*;

public class TestRect implements Runnable{

    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public TestRect(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void run() {

    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.RED);
        if(x2 > x1 && y2 > y1) {
            g2.drawRect(x1, y1, x2 - x1, y2 - y1); //0
        }else if(x2 < x1 &&  y2 > y1){
            g2.drawRect(x2, y1, x1 - x2, y2 - y1);
        } else if (x2 > x1 && y2 < y1) {
            g2.drawRect(x1, y2, x2 - x1, y1 - y2);
        }else if(x2 < x1 && y2 < y1){
            g2.drawRect(x2, y2, x1 - x2, y1 - y2);
        }
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
}
