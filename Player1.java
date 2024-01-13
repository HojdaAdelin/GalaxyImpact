import greenfoot.*;  

public class Player1 extends Actor
{
    String caracter_navy;
    int mod;
    public int delay;
    public int double_time = 100;
    public int speed = 7;
    UserInfo myInfo = UserInfo.getMyInfo();
    public Player1(int mode) {
        
        // Verificare nava in baza de date
        this.mod = mode;
        caracter_navy = myInfo.getString(1);
        GreenfootImage playerImage;
        if ("navy1".equals(caracter_navy)) {
            
            playerImage = new GreenfootImage("navy-1.png");
            speed = 8;
            
        } else if ("navy2".equals(caracter_navy)) {
            
            playerImage = new GreenfootImage("navy-2.png");
            speed = 10;
            delay = 25;
            
        } else if ("navy3".equals(caracter_navy)) {
            
            playerImage = new GreenfootImage("navy-3.png");
            speed = 15;
            delay = 20;
            
        }else {
            
            playerImage = new GreenfootImage("main-player.png");
            speed = 7;
            delay = 30;
            
        }
        if (mod != 1) {
            
            playerImage.rotate(180);
            
        }
        
        setImage(playerImage);
        playerImage.scale(120, 120);
    }
    public void act()
    {
        if (mod == 1) {
            
            if (Greenfoot.isKeyDown("A")) {
                move(-speed); 
            }
            if (Greenfoot.isKeyDown("D")) {
                move(speed);  
            }
            checkKeyPress1();
        } else {
            
            if (Greenfoot.isKeyDown("LEFT")) {
                move(-speed); 
            }
            if (Greenfoot.isKeyDown("RIGHT")) {
                move(speed);  
            }
            checkKeyPress1();
            
        }
        
        
    }
    private void checkKeyPress1()
    {
        // Utilitati & gestionarea glontului
        
        if (delay > 0) {
            delay--;
        }
        if (mod == 1) {
            
            if (Greenfoot.isKeyDown("space") && delay == 0)
            {
                fireBullet();
                if ("navy2".equals(caracter_navy)) {
                    
                    delay = 25;
                    
                } else if ("navy3".equals(caracter_navy)) {
                    
                    delay = 20;
                    
                } else {
                    delay = 30; 
                } 
            }
            
        } else {
            
            if (Greenfoot.isKeyDown("down") && delay == 0)
            {
                fireBullet();
                if ("navy2".equals(caracter_navy)) {
                    
                    delay = 25;
                    
                } else if ("navy3".equals(caracter_navy)) {
                    
                    delay = 20;
                    
                } else {
                    delay = 30; 
                } 
            }
            
        }
        
    }
    private void fireBullet()
    {
        PlayerBullet bullet = new PlayerBullet(1);
        PlayerBullet bullet2 = new PlayerBullet(2);
        if (mod == 1) {
            
            getWorld().addObject(bullet, getX(), getY() - 30);
            
        } else {
            
            getWorld().addObject(bullet2, getX(), getY() + 30);
            
        }
        
    }
}
