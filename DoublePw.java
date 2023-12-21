import greenfoot.*; 

public class DoublePw extends Actor
{
    public GreenfootSound get_power;
    public int ySpeed = 2;
    private boolean removed = false;
    public DoublePw() {
        get_power = new GreenfootSound("get-powerup.mp3");
        GreenfootImage enemyImage = new GreenfootImage("double-bullet-powerup.png");
        setImage(enemyImage);
        enemyImage.scale(25, 25);
        
    }
    
    public void act()
    {
        
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() + ySpeed);
        if (getY() >= getWorld().getHeight() - 1) {
            
            if (!removed) {
                getWorld().removeObject(this);
                removed = true;
            }
        }
        else if (isTouching(MainPlayer.class) && !isTouching(PlayerBullet.class)) {
            
            get_power.play();
            getWorld().removeObject(this);
            removed = true;
        }
        
    }
}
