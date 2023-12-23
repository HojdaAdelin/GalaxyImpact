import greenfoot.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fundal extends MyWorld
{
    public Button button;
    public int status;
    UserInfo myInfo = UserInfo.getMyInfo();
    public Fundal()
    {    
        if (myInfo.getInt(4) == 0) {
            
            status = 1;
            
        } else if (myInfo.getInt(4) == 2) {
            
            status = 2;
            
        } else {
            
            status = 3;
            
        }
       
        GreenfootImage backgroundImage = new GreenfootImage("main-menu.jpg");;
        if (status == 2) {
            
            backgroundImage = new GreenfootImage("lvl2.jpg");
            
        } else if (status == 3) {
            
            backgroundImage = new GreenfootImage("background.jpg");
            
        } else {
            
            backgroundImage = new GreenfootImage("main-menu.jpg");
            
        }
        
        backgroundImage.scale(getWidth(), getHeight());
        setBackground(backgroundImage);
        
    }
    
}
