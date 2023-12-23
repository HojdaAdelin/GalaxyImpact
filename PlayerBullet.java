import greenfoot.*;

public class PlayerBullet extends MainPlayer
{
    // Codul pentru glontul actorului principal
    public GreenfootSound fire;
    private int xSpeed = 15;
    public PlayerBullet() {
        
        fire = new GreenfootSound("fire.mp3");
        GreenfootImage bullet = new GreenfootImage("main_bullet.png");
        setImage(bullet);
        bullet.scale(50, 100);
        fire.play();
        
    }
    
    public void act()
    {
        // Gestionare miscare
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() - xSpeed);
        if (getY() == 0) {
            getWorld().removeObject(this);
        }
        
        
    }
    
}
