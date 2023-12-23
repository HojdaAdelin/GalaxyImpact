import greenfoot.*;  

public class EnemyBullet extends MainEnemy
{ 
    // Codul pentru glont inamic
    private int xSpeed = 5;
    private boolean removed = false;
    
    public EnemyBullet() {
        
        GreenfootImage bullet = new GreenfootImage("enemy_bullet.png");
        setImage(bullet);
        bullet.scale(50, 100);
        
    }
    
    public void act()
    {   
        // Verificare intalnire cu actor principal & y = 0
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() + xSpeed);
        if (getY() >= getWorld().getHeight() - 1) {
            if (!removed) {
                getWorld().removeObject(this);
                removed = true;
                
            }
        } else if (isTouching(MainPlayer.class) && !isTouching(PlayerBullet.class)) {
            
            myworld.minHp();
            getWorld().removeObject(this);
            
        }
    }
    
}
