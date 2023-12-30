import greenfoot.*; 

public class VerifyLogIn extends World
{

    
    public VerifyLogIn()
    {    
        super(900, 600, 1); 
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        addObject(new Labels("Log In to play and restart after!", 40), getWidth() / 2, getHeight() / 2);
    }
}
