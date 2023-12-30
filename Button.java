import greenfoot.*;
import java.util.List;

public class Button extends Actor {
    // Customizări
    private String label;
    private int size;
    private boolean isHovered = false;

    public Button(String label, int size) {
        this.label = label;
        this.size = size;
        updateImage();
    }

    // Nume buton
    public String getLabel() {
        return label;
    }

    // Verificare hover
    private boolean checkHover() {
        World world = getWorld();
        MouseInfo mouse = Greenfoot.getMouseInfo();
    
        if (world != null && world instanceof MyWorld && mouse != null) {
            MyWorld myWorld = (MyWorld) world;
            GreenfootImage normalImage = new GreenfootImage(label, size, Color.WHITE, new Color(150, 150, 150, 0));
            List objects = myWorld.getObjectsAt(mouse.getX(), mouse.getY(), Button.class);
    
            for (Object object : objects) {
                if (object == this) {
                    return true;
                }
            }
        }
        
        return false;
    }



    
    private void updateImage() {
        
        if (checkHover()) {
            
            GreenfootImage hoverImage = new GreenfootImage(label, size, Color.LIGHT_GRAY, new Color(150, 150, 150, 0));
            setImage(hoverImage);
            
        } else {
            
            GreenfootImage normalImage = new GreenfootImage(label, size, Color.WHITE, new Color(150, 150, 150, 0));
            setImage(normalImage);
            
        }
           
    }

    // Actiunea butonului
    public void act() {
        checkHover();
        updateImage();
    }
}
