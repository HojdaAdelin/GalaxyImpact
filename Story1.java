import greenfoot.*; 

public class Story1 extends World
{
    // Codul pentru povestea 1
    UserInfo myInfo = UserInfo.getMyInfo();
    public Story1()
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
        addObject(new Labels("Melvin is a lonely spaceship in a huge galaxy.\nHe's lonely but he wants supremacy in this galaxy.\nHelp him develop his spaceship and destroy the enemies.", 30), getWidth() / 2, 150);
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
