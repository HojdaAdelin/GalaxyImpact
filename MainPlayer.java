import greenfoot.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class MainPlayer extends Actor {
    
    String caracter_navy;
    public int delay = 25;
    
    public MainPlayer() {
        getNavy();
        
        if ("default".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("main-player.png");
            setImage(playerImage);
            playerImage.scale(100, 100);
            
        } else if ("navy1".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("navy-1.png");
            setImage(playerImage);
            playerImage.scale(120, 100);
            
        } else if ("navy2".equals(caracter_navy)) {
            
            GreenfootImage playerImage = new GreenfootImage("navy-2.png");
            setImage(playerImage);
            playerImage.scale(140, 100);
            
        }
        
    }
    
    public void act() {
        
        if (Greenfoot.isKeyDown("A")) {
            move(-7); 
        }
        if (Greenfoot.isKeyDown("D")) {
            move(7);  
        }
        if (Greenfoot.isKeyDown("W")) {
            setLocation(getX(), getY() - 7);  
        }
        if (Greenfoot.isKeyDown("S")) {
            setLocation(getX(), getY() + 7); 
        }
        checkKeyPress();
    }
    private void checkKeyPress()
    {
        if (delay > 0) {
            delay--;
        }
    
        if (Greenfoot.isKeyDown("space") && delay == 0)
        {
            fireBullet();
            delay = 25;  // Resetăm delay după ce am tras un glonț
        }
    }


    private void fireBullet()
    {
        PlayerBullet bullet = new PlayerBullet();
        getWorld().addObject(bullet, getX(), getY() - 30); // Adaugă bullet-ul deasupra player-ului
    }
    
    public void getNavy() {
        
        try {
            
            String caleFisier = "navy.txt";

            
            File fisier = new File(caleFisier);

            
            BufferedReader reader = new BufferedReader(new FileReader(fisier));

            String linie;
            
            while ((linie = reader.readLine()) != null) {
                
                caracter_navy = linie;
                
            }

            
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
