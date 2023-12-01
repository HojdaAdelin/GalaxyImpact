import greenfoot.*;

public class Fundal extends MyWorld
{
    public Button button;
    public Fundal()
    {    
        GreenfootImage backgroundImage = new GreenfootImage("background.jpg");
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        
    }
}
