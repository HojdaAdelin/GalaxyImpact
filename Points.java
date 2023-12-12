import greenfoot.*;  

public class Points extends Actor
{
    
    public Points(int score)
    {
        GreenfootImage score_label = new GreenfootImage("Points: " + score, 40, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(score_label);
    }
}
