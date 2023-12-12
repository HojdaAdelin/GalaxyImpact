import greenfoot.*;

public class Shop extends Actor
{
    public int x, y;
    
    public Shop(int x, int y) {
        
        this.x = x;
        this.y = y;
        MyWorld myworld = (MyWorld) getWorld();
        // Creează o imagine de bază
        GreenfootImage baseImage = new GreenfootImage(x, y);

        // Adaugă primul label
        GreenfootImage label1 = new GreenfootImage("Label 1", 20, Color.WHITE, new Color(0, 0, 0, 0));
        baseImage.drawImage(label1, 100, 10);

        // Adaugă al doilea label
        GreenfootImage label2 = new GreenfootImage("Label 2", 20, Color.WHITE, new Color(0, 0, 0, 0));
        baseImage.drawImage(label2, 50, 0);

        // Setează această imagine ca imagine pentru actor
        setImage(baseImage);
    }
    
    public void act()
    {
        
    }
}
