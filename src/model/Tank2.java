package model;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tank2 extends Tank{

    public Tank2(Map map) {
        
        super(map,2);

    }

    @Override
    public void update() {
        if (keys[KeyEvent.VK_W]) {
            double newX = x - this.speed * Math.sin(direction);
            double newY = y + this.speed * Math.cos(direction);
            if (canMoveTo(newX, newY)) {
                x = newX;
                y = newY;
            }
        }
        if (keys[KeyEvent.VK_S]) {
            double newX = x + this.speed * Math.sin(direction);
            double newY = y - this.speed * Math.cos(direction);
            if (canMoveTo(newX, newY)) {
                x = newX;
                y = newY;
            }
        }
        if (keys[KeyEvent.VK_A]) {
            direction += Math.PI / 30;
        }
        if (keys[KeyEvent.VK_D]) {
            direction -= Math.PI / 30;
        }
        if(keys[KeyEvent.VK_J]){
            if (!fireButtonPressed && bullets.size()<10) {
                bullets.add(new Bullet(map, x + scale - 20 * Math.sin(direction), y + scale + 20 * Math.cos(direction), 4, direction));
                fireButtonPressed = true;
            } 
        }

        
        repaint();
    }
    
}
