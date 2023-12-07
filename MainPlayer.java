import greenfoot.*;

public class MainPlayer extends Actor {
    
    public int delay = 20;
    
    public MainPlayer() {
        GreenfootImage playerImage = new GreenfootImage("main-player.png");
        setImage(playerImage);
        playerImage.scale(100, 80);
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
            delay = 20;  // Resetăm delay după ce am tras un glonț
        }
    }


    private void fireBullet()
    {
        PlayerBullet bullet = new PlayerBullet();
        getWorld().addObject(bullet, getX(), getY() - 30); // Adaugă bullet-ul deasupra player-ului
    }
}
