import lang.stride.*;
import java.util.*;
import greenfoot.*;

public class MyWorld extends World {
    
    private boolean gameStarted = false;
    public boolean check_update = false;
    public GreenfootSound game_start_sound;
    public GreenfootSound game_over_sound;
    public MainPlayer player;
    public GameMisc game_misc;
    
    private int enemySpawnTimer = 0;
    private int enemySpawnDelay = 60;
    public int get_hp = 100;
    public int get_score = 0;
    public int x_misc = 80;
    public int y_misc = getHeight() - 50;

    public MyWorld() {
        super(900, 600, 1);
        player = new MainPlayer();
        //game_misc = new GameMisc(get_hp, get_score, x_misc, y_misc);
        addObject(new Button("Play"), getWidth() / 2, getHeight() - 100);
        addObject(new Button("Exit"), getWidth() - 150, getHeight() - 100);
        addObject(new Button("Shop"), getWidth() - 750, getHeight() - 100);
        game_start_sound = new GreenfootSound("game-start.mp3");
        game_over_sound = new GreenfootSound("game-over.mp3");
    }

    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            if (!gameStarted) {
                checkButtonClicks();
            }
        }

        if (gameStarted) {
            addNewEnemyWithTimer();
            
            if (check_update) {
                
                addObject(new GameMisc(get_hp, get_score, x_misc, y_misc), x_misc, y_misc);
                check_update = false;
                
            }
        }
        if (get_hp <= 0) {
            
            gameStarted = false;
            game_over_sound.play();
            Greenfoot.stop();
            addObject(new GameOverMenu(), getWidth() / 2, getHeight() / 2 - 100);
            addObject(new Button("Retry"), getWidth() / 2, getHeight() / 2);
            
        }
    }

    // Menu
    
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
            addObject(new GameMisc(get_hp, get_score, x_misc, y_misc), x_misc, y_misc);
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
    public void decreaseHp() {
        
        get_hp -= 10;
        get_score += 50;
        check_update = true;
        
    }
    public void minHp() {
        
        get_hp -= 10;
        check_update = true;
        
    }
    public void increaseScore() {
        
        get_score += 50;
        check_update = true;
    }
}
