import lang.stride.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import greenfoot.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;

public class MyWorld extends World {
    
    private boolean gameStarted = false;
    public boolean check_update = false;
    public int caracter_navy_index1 = 0;
    public int caracter_navy_index2 = 0;
    public boolean check_mute = false;
    public GreenfootSound game_start_sound;
    public GreenfootSound game_over_sound;
    public GreenfootSound game_sound;
    public MainPlayer player;
    public GameMisc game_misc;
    String caracter_navy = "default";
    
    private int enemySpawnTimer = 0;
    private int enemySpawnDelay = 60;
    public int get_hp = 100;
    public int get_score = 0;
    public int score_from_text;
    public int x_misc = 80;
    public int y_misc = getHeight() - 50;

    public MyWorld() {
        super(900, 600, 1);
        
        player = new MainPlayer();
        getScore();
        
        String caleFisier = "navy.txt";
        String indexFisier = "navy_index.txt";
        File misc = new File(caleFisier);
        File index = new File(indexFisier);
    
        if (!misc.exists()) {
            saveNavy();
        }
        if (!index.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(index))) {
                index.createNewFile();
                writer.write(Integer.toString(0) + "\n");
                writer.write(Integer.toString(0));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }   
        
        //game_misc = new GameMisc(get_hp, get_score, x_misc, y_misc);
        addObject(new Button("Play", 50), getWidth() / 2, getHeight() - 100);
        addObject(new Button("Exit", 50), getWidth() - 150, getHeight() - 100);
        addObject(new Button("Shop", 50), getWidth() - 750, getHeight() - 100);
        if (!check_mute) {
            
            addObject(new Button("Mute music", 40), 750, 55);
            
        } else {
            
            addObject(new Button("Unmute music", 40), 750, 55);
            
        }
        
        addObject(new Points(score_from_text), 150, 55);
        game_start_sound = new GreenfootSound("game-start.mp3");
        game_over_sound = new GreenfootSound("game-over.mp3");
        game_sound = new GreenfootSound("game.mp3");
    }
    
    public boolean gameOver = false;
    public void act() {
        
        if (!gameOver) {
            
            game_sound.play();
            if (Greenfoot.mouseClicked(null) && !gameOver) {
                if (!gameStarted) {
                    checkButtonClicks();
                    
                }
            } 
            if (gameStarted && !gameOver) {
                game_sound.stop();
                addNewEnemyWithTimer();
                
                if (check_update) {
                    
                    addObject(new GameMisc(get_hp, get_score, x_misc, y_misc), x_misc, y_misc);
                    check_update = false;
                    
                }
            }
            if (get_hp <= 0 && !gameOver) {
                
                gameStarted = false;
                gameOver = true;
                
            }
            
        } else {
            
            game_over_sound.play();
            getScore();
            saveScore();
            addObject(new GameOverMenu(get_score), getWidth() / 2, getHeight() / 2);
            Greenfoot.stop();
            
        }
    
    }
    
    // Alte metode existente...

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
            
            List<Points> points = getObjects(Points.class);
                for (Points p : points) {
                    removeObject(p);
            }
            
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
            
            removeObject(button);
            
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                removeObject(b);
            }
            
            List<Points> points = getObjects(Points.class);
                for (Points p : points) {
                    removeObject(p);
            }
            
            Shop();
            
        } else if (button.getLabel().equals("Mute music")) {
            
            removeObject(button);
            addObject(new Button("Unmute music", 40), 750, 55);
            game_sound.setVolume(0);
            
        } else if (button.getLabel().equals("Unmute music")) {
            
            removeObject(button);
            addObject(new Button("Mute music", 40), 750, 55);
            game_sound.setVolume(100);
            
        }else if (button.getLabel().equals("Main Menu")) {
            System.out.println("Ok");
        } else if (button.getLabel().equals("Buy Navy1")) {
            
            getNavyIndex();
            List<Points> points = getObjects(Points.class);
            
            if (score_from_text >=  10000 && caracter_navy_index1 == 0) {
                
                get_score = -10000;
                saveScore();
                get_score = 0;
                getScore();
                for (Points b : points) {
                    removeObject(b);
                }
                addObject(new Points(score_from_text), 150, 55);
                caracter_navy = "navy1";
                saveNavy();
                caracter_navy_index1 = 1;
                saveNavyIndex();
            }
            
        } else if (button.getLabel().equals("Buy Navy2")) {
            getNavyIndex();
            List<Points> points = getObjects(Points.class);
            if (score_from_text >=  50000 && caracter_navy_index2 == 0) {
                
                get_score = -50000;
                saveScore();
                get_score = 0;
                getScore();
                for (Points b : points) {
                    removeObject(b);
                }
                addObject(new Points(score_from_text), 150, 55);
                caracter_navy = "navy2";
                saveNavy();
                caracter_navy_index2 = 1;
                saveNavyIndex();
            }
                    
        }
    }

    private void addNewEnemyWithTimer() {
        enemySpawnTimer++;

        if (enemySpawnTimer >= enemySpawnDelay) {
            Random rand = new Random();
            int randomX = rand.nextInt(700);
            addObject(new MainEnemy(), randomX, 0);

            // Resetăm timerul
            enemySpawnTimer = 0;
        }
    }
    
    private void retryGame() {
        // Implementează logica pentru a reseta jocul
        // de exemplu, reinițializarea variabilelor și eliminarea obiectelor existente
        // sau redeschiderea scenei de joc
    }

    private void goToMainMenu() {
        // Implementează logica pentru a reveni la meniul principal
        // de exemplu, înlocuirea scenei curente cu meniul principal
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
    
    // Save Score
    public void saveScore() {
        try {
            
            String caleFisier = "scor.txt";
            File fisier = new File(caleFisier);
    
            if (!fisier.exists()) {
                fisier.createNewFile();
            }
    
            // Deschide BufferedWriter pentru a scrie în fișier
            BufferedWriter writer = new BufferedWriter(new FileWriter(fisier));
    
            // Converteste scorul la String si scrie-l în fișier
            writer.write(Integer.toString(get_score + score_from_text));
    
            // Închide BufferedWriter
            writer.close();
    
            // System.out.println("Datele au fost scrise în fișier.");
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Get Score
    public void getScore() {
        
        try {
            // Specifică calea către fișier
            String caleFisier = "scor.txt";

            // Crează un obiect File
            File fisier = new File(caleFisier);

            // Deschide BufferedReader pentru a citi din fișier
            BufferedReader reader = new BufferedReader(new FileReader(fisier));

            String linie;
            // Citeste fiecare linie din fișier
            while ((linie = reader.readLine()) != null) {
                
                score_from_text = Integer.parseInt(linie);
                
            }

            // Închide BufferedReader
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    // Shop
    
    public void Shop() {
        
        getScore();
        getNavyIndex();
        getNavy();
        addObject(new Labels("Shop"), getWidth() / 2, 50);
        addObject(new Points(score_from_text), 150, 55);
        addObject(new Labels("Navy 1: 10000"), 180, 125);
        addObject(new Images("navy-1.png", 150, 115), 150, 215);
        addObject(new Labels("Navy 2: 50000"), 180, 375);
        addObject(new Images("navy-2.png", 150, 115), 150, 470);
        if (caracter_navy_index1 == 0) {
            addObject(new Button("Buy Navy1", 35), 170, 310);
        } else if ("navy1".equals(caracter_navy)) {
            addObject(new Button("Selected", 35), 170, 310);
        } else {
            addObject(new Button("Select Navy1", 35), 170, 310);
        }
        if (caracter_navy_index2 == 0) {
            addObject(new Button("Buy Navy2", 35), 170, 555);
        } else if ("navy2".equals(caracter_navy)) {
            addObject(new Button("Selected", 35), 170, 555);
        } else {
            addObject(new Button("Select Navy1", 35), 170, 555);
        }
        
        if (Greenfoot.mouseClicked(null)) {
                
            checkButtonClicks();
            
        } 
        
        
    }
    
    public void saveNavy() {
        try {
            
            String caleFisier = "navy.txt";
            File fisier = new File(caleFisier);
    
            if (!fisier.exists()) {
                fisier.createNewFile();
            }
    
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(fisier));
    
           
            writer.write(caracter_navy);
    
            
            writer.close();
    
            
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveNavyIndex() {
        try {
            
            String caleFisier = "navy_index.txt";
            File fisier = new File(caleFisier);
                       
            BufferedWriter writer = new BufferedWriter(new FileWriter(fisier));
        
            writer.write(Integer.toString(caracter_navy_index1) + "\n");
            writer.write(Integer.toString(caracter_navy_index2));
                
            writer.close();
                
            
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getNavyIndex() {
        
        try {
            // Specifică calea către fișier
            String caleFisier = "navy_index.txt";

            // Crează un obiect File
            File fisier = new File(caleFisier);

            // Deschide BufferedReader pentru a citi din fișier
            BufferedReader reader = new BufferedReader(new FileReader(fisier));

            String linie;

            
            if ((linie = reader.readLine()) != null) {
                caracter_navy_index1 = Integer.parseInt(linie);
            }
            
            
            if ((linie = reader.readLine()) != null) {
                caracter_navy_index2 = Integer.parseInt(linie);
            }

            // Închide BufferedReader
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public void getNavy() {
        
        try {
            
            String caleFisier = "navy.txt";

            
            File fisier = new File(caleFisier);

            
            BufferedReader reader = new BufferedReader(new FileReader(fisier));

            String linie;
            
            while ((linie = reader.readLine()) != null) {
                
                caracter_navy = linie;
                
            }

            
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
