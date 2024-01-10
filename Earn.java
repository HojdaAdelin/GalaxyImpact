import greenfoot.*;

public class Earn extends Actor
{
    UserInfo myInfo = UserInfo.getMyInfo();
    
    public Earn() {
        
        if (myInfo.getInt(3) < 50) {
            GreenfootImage trof = new GreenfootImage("silver.png");
            setImage(trof);
            trof.scale(50, 50);
        } else {
            GreenfootImage trof = new GreenfootImage("gold.png");
            setImage(trof);
            trof.scale(50, 50);
        }
        
        displayScore();
        
    }
    
    private void displayScore() {
        GreenfootImage image = getImage();
        image.setFont(new Font("Arial", false, false, 20));
        image.setColor(Color.WHITE);
        image.drawString(Integer.toString(myInfo.getInt(3)), image.getWidth() / 2 - 10, image.getHeight() / 2 + 7);
    }
}
