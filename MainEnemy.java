import greenfoot.*; 
import java.util.Random;

public class MainEnemy extends Actor {
    public GreenfootSound fitt_in;
    private int ySpeed = 1;
    private boolean removed = false;
    private int bulletSpawnTimer = 0;
    private int bulletSpawnDelay = 85;
    
    public MainEnemy() {
        fitt_in = new GreenfootSound("coll.mp3");
        GreenfootImage enemyImage = new GreenfootImage("main-enemy.png");
        setImage(enemyImage);
        enemyImage.scale(80, 60);
    }

    public void act() {   
        // Mișcare în jos
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() + ySpeed);
        addBullet();
        // Verificare dacă a ajuns în partea de jos a ecranului și resetare poziție la partea de sus
        if (getY() >= getWorld().getHeight() - 1) {
            
            if (!removed) {
                getWorld().removeObject(this);
                removed = true;
                myworld.minHp();
            }
        }
        else if (isTouching(PlayerBullet.class)) {
            
            
            getWorld().removeObject(this);
            myworld.increaseScore();
            removed = true;
          
        }
        else if (isTouching(MainPlayer.class)) {
            
            myworld.decreaseHp();
            fitt_in.play();
            getWorld().removeObject(this);
            removed = true;
        }
    }
    
    public void addBullet() {
        
        bulletSpawnTimer++;
        if (bulletSpawnTimer >= bulletSpawnDelay) {
            
            EnemyBullet bullet = new EnemyBullet();
            getWorld().addObject(bullet, getX(), getY() + 30);
            // Resetăm timerul
            bulletSpawnTimer = 0;
        }
        
    }
    
}
