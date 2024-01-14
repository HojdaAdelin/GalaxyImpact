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
            
        } else if (myInfo.getInt(4) == 2) {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        } else if (myInfo.getInt(4) == 3) {
            
            backgroundImage = new GreenfootImage("lvl4.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("lvl5.png");
            
        }
        // Elemente si afisare imagine
        addObject(new Labels("KeyBinds", 70), getWidth() / 2, 50);
        addObject(new Labels("W - move forward\nA - move left\nS - move backward\nD - move right\nSPACE - shoot\n\n\n\nIn the front of your name you will see a silver & gold dot.\nThis is the trophie count.\nFor each win you earn 2 trophie & -2 for lose(in silver).\nFor each win you earn 1 trophie & -3 for lose(in gold).", 30), getWidth() / 2, 280);
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
