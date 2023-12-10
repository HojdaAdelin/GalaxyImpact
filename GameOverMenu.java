import greenfoot.*;

public class GameOverMenu extends Actor {
    
    
    public GameOverMenu(int score) {
        GreenfootImage image = new GreenfootImage("Game Over!\nScore: " + score, 70, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(image);
    }
    
    public void act() {
        // Nu este necesar să faci nimic în metoda act pentru acest actor static
    }
}