import greenfoot.*;  

public class GameOver extends World
{
    // Codul pentru interfata joc finalizat
    UserInfo myInfo = UserInfo.getMyInfo();
    public GameOver(int score)
    {    
        super(900, 600, 1);
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");
        if (myInfo.getInt(4) == 0) {
            
            backgroundImage = new GreenfootImage("main-menu.jpg");
            
        }
        else if (myInfo.getInt(4) == 1) {
            
            backgroundImage = new GreenfootImage("lvl2.jpg");
            
        } else if (myInfo.getInt(4) == 2) {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        } else if (myInfo.getInt(4) == 3) {
            
            backgroundImage = new GreenfootImage("lvl4.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("lvl5.png");
            
        }
        // Elemente si afisare imagine
        addObject(new Labels("Game Over!", 60), getWidth() / 2, getHeight() / 2 - 30);
        addObject(new Labels("Score: " + score, 60), getWidth() / 2, getHeight() / 2 + 30);
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        
        // Intoarcere la meniul principal
        addObject(new Button("Click on background to return", 40), getWidth() / 2, getHeight() - 50);
        
    }
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Fundal());
        }
    }

}
