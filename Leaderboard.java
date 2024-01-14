import greenfoot.*;  

public class Leaderboard extends World
{
    // Codul pentru sectiunea "Leaderboard"
    UserInfo myInfo = UserInfo.getMyInfo();
    public Leaderboard()
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
        addObject(new Labels("Leaderboard", 70), getWidth() / 2, 50);
        // Lista celor mai buni jucatori
        
        for (Object obj : UserInfo.getTop(5)) {
            
            UserInfo user = (UserInfo)obj;
            String username = user.getUserName();
            int score = user.getScore();
            int rank = user.getRank();
            addObject(new Labels(Integer.toString(rank) + ". " + username + ": " + Integer.toString(score), 40), getWidth() / 2, rank * 40 + 70);
        }
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
