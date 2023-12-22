import greenfoot.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fundal extends MyWorld
{
    public Button button;
    public int status;
    public Fundal()
    {    
        getStatus();
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
    public void getStatus() {
        
        try {
            
            String caleFisier = "status.txt";

            
            File fisier = new File(caleFisier);

            
            BufferedReader reader = new BufferedReader(new FileReader(fisier));

            String linie;
            
            while ((linie = reader.readLine()) != null) {
                
                status = Integer.parseInt(linie);
                
            }

            
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
