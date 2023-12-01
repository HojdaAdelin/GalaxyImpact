import lang.stride.*;
import java.util.*;
import greenfoot.*;

public class MyWorld extends World {
    
    private boolean gameStarted = false;
    public GreenfootSound game_start_sound;
    public MainPlayer player;
    
    private int enemySpawnTimer = 0;
    private int enemySpawnDelay = 150; // Numărul de acte între apariția inamicilor

    public MyWorld() {
        super(900, 600, 1);
        player = new MainPlayer();
        addObject(new Button("Play"), getWidth() / 2, getHeight() - 100);
        addObject(new Button("Exit"), getWidth() - 150, getHeight() - 100);
        addObject(new Button("Shop"), getWidth() - 750, getHeight() - 100);
        game_start_sound = new GreenfootSound("game-start.mp3");
    }

    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            if (!gameStarted) {
                checkButtonClicks();
            }
        }

        if (gameStarted) {
            addNewEnemyWithTimer();
            
        }
    }

    private void checkButtonClicks() {
        List<Button> buttons = getObjects(Button.class);

        for (Button button : buttons) {
            if (Greenfoot.mouseClicked(button)) {
                handleButtonClick(button);
            }
        }
    }

    private void handleButtonClick(Button button) {
        if (button.getLabel().equals("Play")) {
            gameStarted = true;
            removeObject(button);
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                removeObject(b);
            }
            game_start_sound.play();
            addObject(new MainPlayer(), getWidth() / 2, getHeight() - 100);
        } else if (button.getLabel().equals("Exit")) {
            System.out.println("Jocul a fost închis.");
        } else if (button.getLabel().equals("Shop")) {
            System.out.println("Accesează magazinul!");
        }
    }

    private void addNewEnemyWithTimer() {
        enemySpawnTimer++;

        if (enemySpawnTimer >= enemySpawnDelay) {
            Random rand = new Random();
            int randomX = rand.nextInt(800);
            addObject(new MainEnemy(), randomX, 0);

            // Resetăm timerul
            enemySpawnTimer = 0;
        }
    }
}
