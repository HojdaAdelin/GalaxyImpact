import greenfoot.*;  

public class Keys extends World
{
    // Codul pentru "How to play"
    UserInfo myInfo = UserInfo.getMyInfo();
    public Keys()
    {    
        super(900, 600, 1);
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");
        if (myInfo.getInt(4) == 0) {
            
            backgroundImage = new GreenfootImage("main-menu.jpg");
            
        }
        else if (myInfo.getInt(4) == 1) {
            
            backgroundImage = new GreenfootImage("lvl2.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        }
        // Elemente si afisare imagine
        addObject(new Labels("KeyBinds", 70), getWidth() / 2, 50);
        addObject(new Labels("W - move forward\nA - move left\nS - move backward\nD - move right\nSPACE - shoot", 30), getWidth() / 2, 180);
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
