import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;

import model.Bullet;
import model.Map;
import model.Tank;
import model.Tank1;
import model.Tank2;


public class MainClass {
    public static void main(String[] args) {

        Map mazePanel = new Map(9);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((mazePanel.mapSize + mazePanel.unitWidth) * mazePanel.scale * mazePanel.wallWidth,
                      (mazePanel.mapSize +mazePanel.unitWidth) * mazePanel.scale * mazePanel.wallWidth);


        Tank tankPanel1 = new Tank2(mazePanel);
        Tank tankPanel2 =new Tank1(mazePanel);

        mazePanel.setTank_2(tankPanel2);
        mazePanel.setTank_1(tankPanel1);

        

        frame.add(mazePanel);
        frame.setVisible(true);


        Timer timer = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            mazePanel.tank_2.update();
            mazePanel.tank_1.update();

            mazePanel.judgeBullet(mazePanel);

            mazePanel.repaint();
        }
        });
        timer.start();
       

      
    }
}
