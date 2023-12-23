import greenfoot.*;  

public class Images extends Actor
{
    // Codul pentru eventuale imagini adaugate in joc
    
    public Images(String path, int x, int y) {
        
        GreenfootImage playerImage = new GreenfootImage(path);
        setImage(playerImage);
        playerImage.scale(x, y);
        
    }
    
}
