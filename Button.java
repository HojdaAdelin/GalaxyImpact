import greenfoot.*;

public class Button extends Actor {
    private String label;
    public int size;

    public Button(String label, int size) {
        this.label = label;
        this.size = size;
        updateImage();
    }

    public String getLabel() {
        return label;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            
        }
    }

    private void updateImage() {
        GreenfootImage image = new GreenfootImage(label, size, Color.WHITE, new Color(150, 150, 150, 0));
        setImage(image);
    }

}
