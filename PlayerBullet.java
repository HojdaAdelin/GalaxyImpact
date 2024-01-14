import greenfoot.*;

public class PlayerBullet extends MainPlayer
{
    // Codul pentru glontul actorului principal
    public GreenfootSound fire;
    private int xSpeed = 15;
    int mod;
    public PlayerBullet(int mode) {
        
        this.mod = mode;
        fire = new GreenfootSound("fire.mp3");
        GreenfootImage bullet = new GreenfootImage("main_bullet.png");
        setImage(bullet);
        bullet.scale(50, 100);
        fire.play();
        
    }
        
    public void act() {
        
        if (mod == 1) {
            setLocation(getX(), getY() - xSpeed);
            if (getY() == 0) {
                World world = getWorld();
                if (world != null) {
                    world.removeObject(this);
                }
            }
            
        } else {
            setLocation(getX(), getY() + xSpeed);
            World world = getWorld();
            if (getY() >= world.getHeight() - getImage().getHeight() / 2) {
                if (world != null) {
                    world.removeObject(this);
                }
            }
            
        }
    }

    
}
