import greenfoot.*;  

public class Labels extends Actor
{
    // Codul pentru fiecare text din joc
    
    public Labels(String text, int size) {
        
        GreenfootImage image = new GreenfootImage(text, size, Color.WHITE, new Color(150, 150, 150, 0));
        setImage(image);
        
    }
    
    public void act()
    {
         
    }
}
