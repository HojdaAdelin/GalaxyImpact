import greenfoot.*;  

public class EnemyBullet extends MainEnemy
{ 
    // Codul pentru glont inamic
    public GreenfootSound hit;
    private int xSpeed = 5;
    private boolean removed = false;
    UserInfo myInfo = UserInfo.getMyInfo();
    public EnemyBullet() {
        
        if (myInfo.getInt(4) == 0) {
            
            xSpeed = 5;
            
        }
        
        else if (myInfo.getInt(4) == 1) {
            
            xSpeed = 6;
            
        } else {
            
            xSpeed = 8;
            
        }
        hit = new GreenfootSound("coll.mp3");
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
            
            hit.play();
            myworld.minHp();
            getWorld().removeObject(this);
            
        }
    }
    
}
