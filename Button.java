import greenfoot.*;
import java.util.List;

public class Button extends Actor {
    // Variabile basics
    private String label;
    private GreenfootSound hover;
    private int size;
    private boolean isHovered = false;
    private boolean verify = true;
    
    
    public Button(String label, int size) {
        hover = new GreenfootSound("hover.mp3");
        hover.setVolume(40);
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

        if (world != null && mouse != null) {
            List<Button> objects = world.getObjectsAt(mouse.getX(), mouse.getY(), Button.class);

            for (Button object : objects) {
                if (object == this) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updateImage() {
        // Constructia dreptunghiului
        int padding = 10;
        int arcSize = 40; // Raza colțurilor rotunjite
        int textWidth = (label.length() * size) / 2+5; // Estimare lățime text
        int textHeight = size-14; // Înălțimea textului

        int rectWidth = textWidth + padding * 2;
        int rectHeight = textHeight + padding * 2;

        GreenfootImage image = new GreenfootImage(rectWidth, rectHeight);
        
        // Desenăm dreptunghiul cu colțuri rotunjite si verificam hover-ul
        if (checkHover()) {
            if (verify) {
                hover.play();
                verify = false;
            }
            image.setColor(new Color(100, 149, 237)); // Cornflower Blue
        } else {
            image.setColor(new Color(65, 105, 225)); // Royal Blue
            verify = true;
        }


        // Colțurile rotunjite
        image.fillOval(0, 0, arcSize, arcSize); // stânga sus
        image.fillOval(rectWidth - arcSize, 0, arcSize, arcSize); // dreapta sus
        image.fillOval(0, rectHeight - arcSize, arcSize, arcSize); // stânga jos
        image.fillOval(rectWidth - arcSize, rectHeight - arcSize, arcSize, arcSize); // dreapta jos

        // Dreptunghiuri pentru a conecta colțurile
        image.fillRect(arcSize / 2, 0, rectWidth - arcSize, rectHeight); // centru
        image.fillRect(0, arcSize / 2, rectWidth, rectHeight - arcSize); // centru

        // Desenăm textul
        GreenfootImage textImage = new GreenfootImage(label, size, Color.WHITE, new Color(0, 0, 0, 0));
        int textX = (rectWidth - textImage.getWidth()) / 2;
        int textY = (rectHeight - textImage.getHeight()) / 2;
        image.drawImage(textImage, textX, textY);

        setImage(image);
    }

    // Actiunea butonului
    public void act() {
        boolean hovered = checkHover();
        isHovered = hovered;
        updateImage();
    }
}
