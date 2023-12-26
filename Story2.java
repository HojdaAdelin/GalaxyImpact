import greenfoot.*; 

public class Story2 extends World
{
    // Codul pentru povestea 2
    UserInfo myInfo = UserInfo.getMyInfo();
    public Story2()
    {    
        super(900, 600, 1);
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");
        if (myInfo.getInt(4) == 1) {
            
            backgroundImage = new GreenfootImage("lvl2.jpg");
            
        } else if (myInfo.getInt(4) == 2) {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("main-menu.jpg");
            
        }
        // Elemente si afisare imagine
        addObject(new Labels("Story", 70), getWidth() / 2, 50);
        addObject(new Labels("Now Melvin has become invincible.\n He destroyed the enemies and now he is the king of the galaxy.\n Melvin is grateful for your help.", 30), getWidth() / 2, 150);
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        
        // Intoarcere la meniul principal
        addObject(new Button("Click on background to return", 40), getWidth() / 2, getHeight() - 50);
        
    }
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            
            Greenfoot.setWorld(new Fundal());
        }
    }
}