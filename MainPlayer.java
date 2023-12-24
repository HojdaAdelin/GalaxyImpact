import greenfoot.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class MainPlayer extends Actor {
    
    // Codul pentru actorul principal
    
    String caracter_navy;
    public int delay;
    public int double_time = 100;
    public int speed = 7;
    UserInfo myInfo = UserInfo.getMyInfo();
    public MainPlayer() {
        
        // Verificare nava in baza de date
        
        caracter_navy = myInfo.getString(1);
        
        if ("navy1".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("navy-1.png");
            speed = 8;
            setImage(playerImage);
            playerImage.scale(120, 100);
            
        } else if ("navy2".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("navy-2.png");
            speed = 10;
            delay = 25;
            setImage(playerImage);
            playerImage.scale(140, 100);
            
        } else if ("navy3".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("navy-3.png");
            speed = 12;
            delay = 22;
            setImage(playerImage);
            playerImage.scale(120, 120);
            
        }else {
            
            GreenfootImage playerImage = new GreenfootImage("main-player.png");
            speed = 7;
            setImage(playerImage);
            playerImage.scale(100, 100);
            
        }
        
    }
    
    public void act() {
        
        // Miscarea actorului
        
        if (Greenfoot.isKeyDown("A")) {
            move(-speed); 
        }
        if (Greenfoot.isKeyDown("D")) {
            move(speed);  
        }
        if (Greenfoot.isKeyDown("W")) {
            setLocation(getX(), getY() - speed);  
        }
        if (Greenfoot.isKeyDown("S")) {
            setLocation(getX(), getY() + speed); 
        }
        checkKeyPress();
    }
    private void checkKeyPress()
    {
        // Utilitati & gestionarea glontului
        MyWorld myworld = (MyWorld) getWorld();
        if (delay > 0) {
            delay--;
        }
        if (double_time > 0 && myworld.returnDoublepw()) {
            
            double_time--;
            
        } else {
            
            myworld.dpw_false();
            double_time = 100;
            
        }
        if (Greenfoot.isKeyDown("space") && delay == 0 && myworld.returnDoublepw())
        {
    
            doublefireBullet();
            if ("navy2".equals(caracter_navy)) {
                
                delay = 25;
                
            } else if ("navy3".equals(caracter_navy)) {
                
                delay = 22;
                
            }else {
                delay = 30; 
            }
        } else if (Greenfoot.isKeyDown("space") && delay == 0)
        {
            fireBullet();
            if ("navy2".equals(caracter_navy)) {
                
                delay = 25;
                
            } else if ("navy3".equals(caracter_navy)) {
                
                delay = 22;
                
            } else {
                delay = 30; 
            } 
        }
    }

    // Afisare glont
    private void fireBullet()
    {
        PlayerBullet bullet = new PlayerBullet();
        getWorld().addObject(bullet, getX(), getY() - 30); // Adaugă bullet-ul deasupra player-ului
    }
    // Utilitatea "double bullet"
    private void doublefireBullet()
    {
        PlayerBullet bullet1 = new PlayerBullet();
        PlayerBullet bullet2 = new PlayerBullet();
    
        getWorld().addObject(bullet1, getX(), getY() - 30);
        getWorld().addObject(bullet2, getX() + 10, getY() - 30);
    }
    
    
}
