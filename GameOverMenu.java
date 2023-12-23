import greenfoot.*;

public class GameOverMenu extends Actor {
    
    // Codul pentru interfata joc pierdut
    
    public GameOverMenu(int score) {
        GreenfootImage image = new GreenfootImage("Game Over!\nScore: " + score, 70, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(image);
    }
    
}
