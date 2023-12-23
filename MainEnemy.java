import greenfoot.*; 
import java.util.Random;
import java.util.List;

public class MainEnemy extends Actor {
    // Codul pentru inamic
    
    public GreenfootSound fitt_in;
    private int ySpeed = 1;
    private boolean removed = false;
    private boolean remove_bullet = false;
    private int bulletSpawnTimer = 0;
    private int bulletSpawnDelay = 100;
    
    public MainEnemy() {
        // Afisare
        fitt_in = new GreenfootSound("coll.mp3");
        GreenfootImage enemyImage = new GreenfootImage("main-enemy.png");
        setImage(enemyImage);
        enemyImage.scale(80, 60);
    }

    public void act() {   
        // Mișcare în jos & instanta World
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() + ySpeed);
        addBullet();
        // Verificare dacă a ajuns în partea de jos a ecranului
        // Verificare intalnire cu actorul principal & glont principal
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
            if (!remove_bullet) {
                
                int misc = 0;
                List<PlayerBullet> bullet = myworld.getObjects(PlayerBullet.class);
                for (PlayerBullet b : bullet) {
                    
                    myworld.removeObject(b);
                    misc++;
                    if (misc == 1) {
                        
                        misc = 0;
                        break;
                        
                    }
                    
                }
                remove_bullet = true;
                
            }
            removed = true;
          
        }
        else if (isTouching(MainPlayer.class)) {
            
            myworld.decreaseHp();
            fitt_in.play();
            getWorld().removeObject(this);
            removed = true;
        }
    }
    // Glont al inamicului
    public void addBullet() {
        
        bulletSpawnTimer++;
        if (bulletSpawnTimer >= bulletSpawnDelay) {
            
            EnemyBullet bullet = new EnemyBullet();
            getWorld().addObject(bullet, getX(), getY() + 30);
            bulletSpawnTimer = 0;
        }
        
    }
    
}