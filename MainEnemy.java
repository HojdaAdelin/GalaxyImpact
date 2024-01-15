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
        GreenfootImage enemyImage = new GreenfootImage("main-enemy.png");
        if (myInfo.getInt(4) == 0) {
            
            enemyImage = new GreenfootImage("main-enemy.png");;
            
        }
        
        else if (myInfo.getInt(4) == 1) {
            
            enemyImage = new GreenfootImage("medium-enemy.png");
            bulletSpawnDelay = 55;
            
        } else if (myInfo.getInt(4) == 2) {
            
            enemyImage = new GreenfootImage("hard-enemy.png");
            bulletSpawnDelay = 50;
            ySpeed = 1;
            
        } else if (myInfo.getInt(4) == 3) {
            
            enemyImage = new GreenfootImage("lvl4-enemy.png");
            bulletSpawnDelay = 45;
            ySpeed = 1;
            
        } else {
            
            enemyImage = new GreenfootImage("lvl5-enemy.png");
            bulletSpawnDelay = 40;
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
            
            if (myInfo.getInt(4) == 3) {
                // Adaugă al doilea glonț la o distanță suplimentară
                EnemyBullet secondBullet = new EnemyBullet();
                getWorld().addObject(secondBullet, getX(), getY() + 60);
            } else if (myInfo.getInt(4) >= 4) {
                
                EnemyBullet secondBullet = new EnemyBullet();
                EnemyBullet secondBullet1 = new EnemyBullet();
                getWorld().addObject(secondBullet, getX() - 15, getY() + 30);
                getWorld().addObject(secondBullet1, getX() + 15, getY() + 30);
            }
            
            bulletSpawnTimer = 0;
        }
    }

    
}