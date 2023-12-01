import greenfoot.*;

public class Button extends Actor {
    private String label;

    public Button(String label) {
        this.label = label;
        updateImage();
    }

    public String getLabel() {
        return label;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            onButtonClick();
        }
    }

    private void updateImage() {
        GreenfootImage image = new GreenfootImage(label, 50, Color.WHITE, new Color(150, 150, 150, 0));
        setImage(image);
    }

    private void onButtonClick() {
        System.out.println("Buton apăsat: " + label);
    }
}
