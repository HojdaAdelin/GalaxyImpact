import greenfoot.*; 
import java.util.Random;
import java.util.List;

public class MainEnemy extends Actor {
    // Codul pentru inamic
    
    public GreenfootSound fitt_in;
    public int enemy_type;
    private int ySpeed = 1;
    private boolean removed = false;
    private boolean remove_bullet = false;
    private int bulletSpawnTimer = 0;
    private int bulletSpawnDelay = 60;
    UserInfo myInfo = UserInfo.getMyInfo();
    
    public MainEnemy() {
        // Afisare in functie de tipul inamicului
        GreenfootImage enemyImage = new GreenfootImage("main-enemy.png");;
        if (myInfo.getInt(4) == 1) {
            
            enemyImage = new GreenfootImage("medium-enemy.png");
            bulletSpawnDelay = 55;
            
        } else if (myInfo.getInt(4) == 2) {
            
            enemyImage = new GreenfootImage("hard-enemy.png");
            bulletSpawnDelay = 50;
            ySpeed = 1;
            
        }
        fitt_in = new GreenfootSound("coll.mp3");
        setImage(enemyImage);
        enemyImage.scale(80, 60);
    }

    public void act() {   
        // Mișcare în jos & instanta World
        MyWorld myworld = (MyWorld) getWorld();
        setLocation(getX(), getY() + ySpeed);
        if (myInfo.getInt(4) == 2) {
            
            addBulletHard();
            
        } else {
            addBullet();
        }
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
    public void addBulletHard() {
        
        EnemyBullet bullet1 = new EnemyBullet();
        EnemyBullet bullet2 = new EnemyBullet();
        
        bulletSpawnTimer++;
        if (bulletSpawnTimer >= bulletSpawnDelay) {
            
            getWorld().addObject(bullet1, getX() + 5, getY() + 30);
            getWorld().addObject(bullet2, getX() - 5, getY() + 30);
            bulletSpawnTimer = 0;
        }
        
    }
    
}