package dev.app;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Collision {
    /// Have a static method that checks all collisions?????

    private Shape collisionBoxShape;
    private Shape transformedShape;
    private AffineTransform transform;
    private AffineTransform transform2;
    private Rectangle rect;
    private Ellipse2D.Double eclipse;
    private GameObject thisObject;
    private enum CollisionBoxType{ RECT, OVAL}
    private CollisionBoxType currentCollisionBoxShape;
    private boolean collisionOn;
    private double[] boundaryPoints;
    private boolean isCollision;
    private Color bColor;
    private double scale = 0.5;

    public Collision(GameObject o){
        thisObject = o;
        boundaryPoints = new double[]{o.x1 * scale, o.y1 * scale, o.getWidth() * scale, o.getHeight() * scale};
        setCollisionBoxShape(CollisionBoxType.RECT);
        isCollision = false;
        bColor = new Color(20, 200, 30, 200);

    }

    public void setCollisionBoxShape(CollisionBoxType c){
        switch(c){
            case RECT -> {
                rect = new Rectangle();
                rect.setRect(boundaryPoints[0], boundaryPoints[1], boundaryPoints[2], boundaryPoints[3]);
                collisionBoxShape = rect;
            }
            case OVAL -> {
                eclipse = new Ellipse2D.Double();
                eclipse.setFrame(boundaryPoints[0], boundaryPoints[1], boundaryPoints[2], boundaryPoints[3]);
                collisionBoxShape = eclipse;
            }
        }

        currentCollisionBoxShape = c;
    }

    public boolean checkCollision(Collision o){
        Shape s = o.getCollisionBoxShape();

        if(this.getCollisionBoxShape().getBounds2D().intersects(s.getBounds2D())){
           //isCollision = true;
           return true;
        }
        //isCollision = false;
        return false;
    }

    public void updateCollisionBoxLocation(){

        double scaling = .5;

        switch(currentCollisionBoxShape){
            case RECT ->  ((Rectangle) collisionBoxShape).setRect(thisObject.x1, thisObject.y1, thisObject.getWidth(), thisObject.getHeight());
            case OVAL ->  ((Ellipse2D) collisionBoxShape).setFrame(thisObject.x1, thisObject.y1, thisObject.getWidth(), thisObject.getHeight());
        }

        transform = new AffineTransform();

        transform.rotate(Math.toRadians(thisObject.rotationAngle), collisionBoxShape.getBounds().getX() + collisionBoxShape.getBounds().getWidth() /2 ,
                collisionBoxShape.getBounds().getY() + collisionBoxShape.getBounds().getHeight()/2);

//        double tx = collisionBoxShape.getBounds2D().getX() + (collisionBoxShape.getBounds2D().getX() * (1 - scaling) / 2);
//        double ty = collisionBoxShape.getBounds2D().getY();
//        double tw = collisionBoxShape.getBounds2D().getWidth() * scaling;
//        double th = collisionBoxShape.getBounds2D().getHeight() * scaling;
//
//        ((Rectangle) collisionBoxShape).setRect(tx, ty, tw, th);
        transformedShape = transform.createTransformedShape(collisionBoxShape);

    }

    public double[] getBoundaryPoints(){
        return new double[]{
                transformedShape.getBounds().getX(),
                transformedShape.getBounds().getY(),
                transformedShape.getBounds().getWidth(),
                transformedShape.getBounds().getHeight()
        };
    }

    public void drawCollisionBox(Graphics2D g){

        g.setStroke(new BasicStroke(1));

        if(transformedShape != null){
            g.setColor(bColor);
            g.draw(transformedShape);
        }
    }

    public GameObject getAssociatedGameObject(){
        return thisObject;
    }

    public Shape getCollisionBoxShape(){
        return transformedShape;
    }

    public static java.util.List<Collision[]> collisionChecker(java.util.List<Collision> collisions) {
        //this method will take a list of collision objects
        // and measures which ones have collided. It will filter and remove duplicate collision. i.e obj1 collides with obj 2 and vice versa
        // returns a list of collisions on which to perform some actions on
        Collision[] collision = new Collision[2];
        java.util.List<Collision[]> uniqueCollisions = new ArrayList<>();

        Collision c1;
        Collision c2;

        for (int i = 0; i < collisions.size(); i++) {
            c1 = collisions.get(i);
            for (int j = i; j < collisions.size(); j++) {
                c2 = collisions.get(j);
                if (!c1.equals(c2) && c2.getCollisionBoxShape().getBounds2D().intersects(c1.collisionBoxShape.getBounds2D())) {
                    collision[0] = c1;
                    collision[1] = c2;

                    if (uniqueCollisions.isEmpty()) {
                        uniqueCollisions.add(collision);
                    } else {
                        for (int k = 0; k < uniqueCollisions.size(); k++) {
                            if (!uniqueCollisions.get(k)[0].equals(collision[1]) && !uniqueCollisions.get(k)[1].equals(collision[0])) {
                                uniqueCollisions.add(collision);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return uniqueCollisions;

    }
//        if(!uniqueCollisions.isEmpty()){
//            for(int i = 0; i < uniqueCollisions.size(); i++){
//                System.out.printf("Collision between %s and %s%n", uniqueCollisions.get(i)[0].getAssociatedGameObject().getName(),
//                        uniqueCollisions.get(i)[1].getAssociatedGameObject().getName());
//
//            }
//            System.out.println();




}
