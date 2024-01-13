import greenfoot.*;  

public class vss extends World
{
    UserInfo myInfo = UserInfo.getMyInfo();
    public vss()
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
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        addObject(new Player1(1), getWidth() / 2, getHeight() - 75);
        addObject(new Player1(2), getWidth() / 2, 75);
    }
}
