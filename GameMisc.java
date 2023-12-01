import greenfoot.*; 
import java.lang.*;

public class GameMisc extends Actor
{
    public int hp;
    public int score;
    
    public GameMisc(int hp, int score) {
        
        this.hp = hp;
        this.score = score;
        
    }
    
    public void act()
    {
        getWorld().showText("Score: " + score, 80, 10);
        getWorld().showText("HP: " + hp + "/100", 80, 30);
    }
}
