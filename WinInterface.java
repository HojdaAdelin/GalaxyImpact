import greenfoot.*; 

public class WinInterface extends World
{

    // Codul pentru interfata joc finalizat
    UserInfo myInfo = UserInfo.getMyInfo();
    public WinInterface(int score, int chapter)
    {    
        super(900, 600, 1);
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");
        int status = myInfo.getInt(4) - 1;
        if (status == 1) {
            
            backgroundImage = new GreenfootImage("lvl2.jpg");
            
        } else if (status == 2) {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("main-menu.jpg");
            
        }
        // Elemente si afisare imagine
        addObject(new Labels("Chapter " + chapter + " clear", 60), getWidth() / 2, getHeight() / 2 - 30);
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
