import greenfoot.*; 
import java.lang.*;

public class GameMisc extends Actor
{
    // Codul pentru scor si viata in joc
    public int hp;
    public int score;
    public int x;
    public int y;
    
    public GameMisc(int hp, int score, int x, int y) {
        
        this.hp = hp;
        this.score = score;
        this.x = x;
        this.y = y;
        
    }
    
    public void act()
    {
        getWorld().showText("Score: " + score, x, y);
        getWorld().showText("HP: " + hp + "/100", x, y + 20);
    }
}
