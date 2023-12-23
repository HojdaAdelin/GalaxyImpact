import greenfoot.*;

public class Button extends Actor {
    // Customizari
    private String label;
    public int size;

    public Button(String label, int size) {
        this.label = label;
        this.size = size;
        updateImage();
    }

    // Nume buton
    public String getLabel() {
        return label;
    }

    // Actualizare buton
    private void updateImage() {
        GreenfootImage image = new GreenfootImage(label, size, Color.WHITE, new Color(150, 150, 150, 0));
        setImage(image);
    }

}
