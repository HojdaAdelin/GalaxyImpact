import greenfoot.*;  

public class Images extends Actor
{
    public Images(String path, int x, int y) {
        
        GreenfootImage playerImage = new GreenfootImage(path);
        setImage(playerImage);
        playerImage.scale(x, y);
        
    }
    
    public void act()
    {
       
    }
}
