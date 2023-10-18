package dev.app;

import java.awt.*;

public class GameCharacter {

    private static int id;
    private enum CharacterType{ PLAYER, ENEMY, NPC}

    public String name;
    public int hitPoints;
    public int w;
    public int h;
    public int x;
    public int y;
    public int dx;
    public int dy;
    public int speed;
    public boolean isJumping;
    public boolean isMoving;
    public GameObject o;

    public GameCharacter(String name, int hitPoints, int x, int y, int dx, int dy, GameObject o) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.w = o.getWidth();
        this.h = o.getHeight();
        this.x = o.x1 + w / 2;
        this.y = o.y1 + h /2;
        this.dx = o.dx;
        this.dy = o.dy;
        this.o = o;
        this.isJumping = false;
        this.isMoving = false;
        id++;
    }

    public void draw(Graphics2D g){
        this.o.draw(g);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void moveHorizontal(){
        this.x += 5;
    }
    public void moveVertical(){
        if(isMoving){
            this.y += 5;
        }
    }

    public void updatePosition(){

        if(isMoving){
            this.x *= this.dx;
            this.y *= this.dy;
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public GameObject getGameObject() {
        return o;
    }

    public void setGameObject(GameObject o) {
        this.o = o;
    }
}
