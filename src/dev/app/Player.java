package dev.app;

public class Player extends GameCharacter{

    private static int id;

    public Player(String name, int hitPoints, int x, int y, int dx, int dy, GameObject o) {
        super(name, hitPoints, x, y, dx, dy, o);
        id++;
    }

    public Player(GameObject o) {
        this("Player_" + id, 10, 0, 0, 5, 5, o);
        this.x = o.x1;
        this.y = o.y1;
    }



}
