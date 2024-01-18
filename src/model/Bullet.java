package model;
import java.awt.*;

public class Bullet {
    private Map map;

    private double x;
    private double y;

    private double speed;
    private double direction;

    private int scale;
    
    private int life;

    public Bullet(Map map, double x, double y, double speed, double direction) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.scale = map.scale;
        this.life = 5000;
    }

    public void update() {
        life -= 10;

        double newX = x - speed * Math.sin(direction);
        double newY = y + speed * Math.cos(direction);

        if (newX < 0 || newX >= map.mapSize * scale * map.wallWidth 
           || map.map[(int) Math.round(newX) / (scale * map.wallWidth)][(int) Math.round(y) / (scale * map.wallWidth)] == 1) {
            
            direction = -direction;
            newX = x - speed * Math.sin(direction);
            newY = y + speed * Math.cos(direction);
        }
        if (newY < 0 || newY >= map.mapSize * scale * map.wallWidth 
           || map.map[(int) Math.round(x) / (scale * map.wallWidth)][(int) Math.round(newY) / (scale * map.wallWidth)] == 1) {

            direction = Math.PI - direction;
            newX = x - speed * Math.sin(direction);
            newY = y + speed * Math.cos(direction);
        }
        x = newX;
        y = newY;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int) Math.round(y), (int) Math.round(x), 6, 6);
    }

    public boolean isAlive() {
        return life > 0;
    }

    public boolean hitTest(Tank tank) {
        return x >= tank.x && x < tank.x + 1.5*tank.scale && y >= tank.y && y < tank.y + 1.5*tank.scale;
    }
}
