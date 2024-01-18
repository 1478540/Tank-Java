package model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.*;

public class Map extends JPanel implements KeyListener{

    public Tank tank_1;
    public Tank tank_2;

    public Tank getTank_1() {
        return tank_1;
    }

    public void setTank_1(Tank tank_1) {
        this.tank_1 = tank_1;
    }

    public Tank getTank_2() {
        return tank_2;
    }

    public void setTank_2(Tank tank_2) {
        this.tank_2 = tank_2;
    }

    public int level;

    public int wallWidth;
    public int roadWidth;
    public int unitWidth;
    public int mapSize;

    public int[][] map;

    public int scale;
   
    public Random random = new Random();

    public Map(int level) {
        this.level = level;

        this.wallWidth = 1;
        this.roadWidth = 5 * wallWidth;
        this.unitWidth=wallWidth+roadWidth;
        this.mapSize=level*this.unitWidth;

        this.scale = 15;

        generateMaze();

        setFocusable(true);
        addKeyListener(this);
    }

    private void generateMaze() {

        map = new int[this.mapSize][this.mapSize];

        for (int i = 0; i < this.mapSize; i++) {
            map[i][0] = 1;
            map[i][this.mapSize - 1] = 1;
            map[0][i] = 1;
            map[this.mapSize - 1][i] = 1;
        }

        carve(roadWidth, roadWidth);
    }

    private void carve(int current_x, int current_y) {
        int[] add_x = {0, 0, roadWidth + wallWidth, -(roadWidth + wallWidth)};
        int[] add_y = {roadWidth + wallWidth, -(roadWidth + wallWidth), 0, 0};

        int dir = random.nextInt(4);
        int count = 0;
        
        while (count < 4) {
            int new_x = current_x + add_x[dir];
            int new_y = current_y + add_y[dir];

            if (new_x > 0 && new_x < this.mapSize && new_y > 0 && new_y <this.mapSize && map[new_x][new_y] == 0) {

                //将当前坐标到新坐标全部变成墙壁
                for (int i = Math.min(current_x, new_x); i <= Math.max(current_x, new_x); i++) {
                    for (int j = Math.min(current_y, new_y); j <= Math.max(current_y, new_y); j++) {
                        map[i][j] = 1;
                    }
                }

                carve(new_x, new_y);

            } else {
                dir = (dir + 1) % 4;
                count += 1;
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
      

        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                if (map[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * scale * wallWidth, i * scale * wallWidth, scale * wallWidth, scale * wallWidth);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * scale * wallWidth, i * scale * wallWidth, scale * wallWidth, scale * wallWidth);
                }
            }
        }

        
        tank_2.paintComponent(g);
        tank_1.paintComponent(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

       tank_1.keyTyped(e);
       tank_2.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        tank_1.keyPressed(e);
        tank_2.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        tank_1.keyReleased(e);
        tank_2.keyReleased(e);
    }

    
    public static void judgeBullet(Map mazePanel){
        for (Bullet bullet : mazePanel.tank_2.bullets) {
            bullet.update();
            if (bullet.hitTest(mazePanel.tank_1)||bullet.hitTest(mazePanel.tank_2)) {
                System.out.println("Game Over");
                System.exit(0);
            }
        }
        mazePanel.tank_1.bullets.removeIf(bullet -> !bullet.isAlive());

        for (Bullet bullet : mazePanel.tank_1.bullets) {
            bullet.update();
            if (bullet.hitTest(mazePanel.tank_1)||bullet.hitTest(mazePanel.tank_2)) {
                System.out.println("Game Over");
                System.exit(0);
            }
        }
        mazePanel.tank_2.bullets.removeIf(bullet -> !bullet.isAlive());
    }
}
