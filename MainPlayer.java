import greenfoot.*;

public class MainPlayer extends Actor {
    
    public MainPlayer() {
        GreenfootImage playerImage = new GreenfootImage("main-player.png");
        setImage(playerImage);
        playerImage.scale(100, 80);
    }
    
    public void act() {
        
        if (Greenfoot.isKeyDown("A")) {
            move(-5); 
        }
        if (Greenfoot.isKeyDown("D")) {
            move(5);  
        }
        if (Greenfoot.isKeyDown("W")) {
            setLocation(getX(), getY() - 5);  
        }
        if (Greenfoot.isKeyDown("S")) {
            setLocation(getX(), getY() + 5); 
        }
    }
}
