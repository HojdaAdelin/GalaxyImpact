import greenfoot.*;

public class Button2 extends Actor {
    private String label;

    public Button2(String label) {
        this.label = label;
        updateImage();
    }

    public String getLabel() {
        return label;
    }

    public void act() {
        
    }

    private void updateImage() {
        GreenfootImage image = new GreenfootImage(label, 40, Color.WHITE, new Color(150, 150, 150, 0));
        setImage(image);
    }

    
}
