package model;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;

public abstract class Tank extends JPanel implements KeyListener {
    protected Map map;

    public double x;
    public double y;

    public int scale;

    protected int speed;

    protected int frame_rate;

    protected double direction;

    protected BufferedImage tankImage;

    public java.util.List<Bullet> bullets;

    protected boolean[] keys = new boolean[256];
    
    protected boolean fireButtonPressed;

    public Tank(Map map,int order) {
        this.map = map;
        this.scale = map.scale;
        this.speed = 3;
        this.frame_rate=30;
        this.fireButtonPressed = false;

        do {
            this.x = map.random.nextInt(map.mapSize * scale * map.wallWidth);
            this.y = map.random.nextInt(map.mapSize * scale * map.wallWidth);

        } while (!canMoveTo(x, y));

        this.direction = 0;
        try {

            if(order==1){
                tankImage = ImageIO.read(new File("res/tank_1.png"));
            }else{
                tankImage = ImageIO.read(new File("res/tank_2.png"));
            }     
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.bullets = new ArrayList<>();
        setFocusable(true);
        addKeyListener(this);

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(y + this.scale, x + this.scale);
        g2d.rotate(-direction);
        g2d.drawImage(tankImage, -this.scale, -this.scale, this.scale*2, this.scale*2, null);
        g2d.dispose();

        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }
    }
    
    public abstract void update();

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        fireButtonPressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}


    protected boolean canMoveTo(double x, double y) {

        for (int i = 0; i < this.scale*2; i++) {
            for (int j = 0; j < this.scale*2; j++) {

                int mapX = (int) Math.round(x + i - this.scale);
                int mapY = (int) Math.round(y + j - this.scale);


                if (mapX < 0 || mapX >= (map.mapSize-2) * scale * map.wallWidth 
                    || mapY < 0 || mapY >= (map.mapSize-2) * scale * map.wallWidth 
                    || map.map[mapX / (scale * map.wallWidth)+1][mapY / (scale * map.wallWidth)+1] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
}


