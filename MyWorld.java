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
    boolean check_dpw = false;
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
    private int power_up_timer = 0;
    private int power_up_delay = 600;
    private int clear_power_up_timer = 0;
    private int clear_power_up_delay = 800;
    public int get_hp = 100;
    public int get_score = 0;
    public int score_from_text;
    public int x_misc = 80;
    public int y_misc = getHeight() - 50;

    public int status = 1;
    
    public MyWorld() {
        super(900, 600, 1);
        getStatusA();
        player = new MainPlayer();
        getScore();
        String caleFisier = "navy.txt";
        String indexFisier = "navy_index.txt";
        File misc = new File(caleFisier);
        File index = new File(indexFisier);
    
        if (!misc.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(misc))) {
                misc.createNewFile();
                writer.write("default");
                
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
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
        addObject(new Labels("Galaxy Impact", 65), getWidth() / 2, 50);
        addObject(new Button("Play", 50), getWidth() / 2, getHeight() - 100);
        addObject(new Button("Status: " + status + "/3", 50), getWidth() - 150, getHeight() - 100);
        addObject(new Button("Shop", 50), getWidth() - 750, getHeight() - 100);
        if (!check_mute) {
            
            addObject(new Button("Mute music", 40), 775, 55);
            
        } else {
            
            addObject(new Button("Unmute music", 40), 775, 55);
            
        }
        
        addObject(new Points(score_from_text), 125, 55);
        game_start_sound = new GreenfootSound("game-start.mp3");
        game_over_sound = new GreenfootSound("game-over.mp3");
        game_sound = new GreenfootSound("game.mp3");
        
        // Status & keys
        
        addObject(new Labels("W - move forward", 28), getWidth() - 125, getHeight() / 2 - 60);
        addObject(new Labels("A - move left", 28), getWidth() - 150, getHeight() / 2 - 30);
        addObject(new Labels("S - move backward", 28), getWidth() - 115, getHeight() / 2);
        addObject(new Labels("D - move right", 28), getWidth() - 140, getHeight() / 2 + 30);
        addObject(new Labels("SPACE - shoot", 28), getWidth() - 138, getHeight() / 2 + 60);
        
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

    public void checkButtonClicks() {
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
            List<Labels> label = getObjects(Labels.class);
            for (Labels b : label) {
                removeObject(b);
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
            List<Labels> label = getObjects(Labels.class);
            for (Labels b : label) {
                removeObject(b);
            }
            List<Points> points = getObjects(Points.class);
                for (Points p : points) {
                    removeObject(p);
            }
            
            Shop();
            
        } else if (button.getLabel().equals("Mute music")) {
            
            removeObject(button);
            addObject(new Button("Unmute music", 40), 775, 55);
            game_sound.setVolume(0);
            
        } else if (button.getLabel().equals("Unmute music")) {
            
            removeObject(button);
            addObject(new Button("Mute music", 40), 775, 55);
            game_sound.setVolume(100);
            
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
                removeObject(button);
                addObject(new Button("Select Navy1", 35), 150, 400);
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
                removeObject(button);
                addObject(new Button("Select Navy2", 35), 450, 400);
            } 
                    
        } else if (button.getLabel().equals("Select Navy1")) {
                
            caracter_navy = "navy1";
            removeObject(button);
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                if (b.getLabel() == "Selected") {
                    
                    removeObject(b);
                    addObject(new Button("Select Navy2", 35), 450, 400);
                    
                }
            }
            addObject(new Button("Selected", 35), 150, 400);
            saveNavy();
                
        } else if (button.getLabel().equals("Select Navy2")) {
                
            caracter_navy = "navy2";
            removeObject(button);
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                if (b.getLabel() == "Selected") {
                    
                    removeObject(b);
                    addObject(new Button("Select Navy1", 35), 150, 400);
                    
                }
            }
            
            addObject(new Button("Selected", 35), 450, 400);
            saveNavy();
                
        } else if (button.getLabel().equals("Main Menu")) {
            goBackToMainMenu();
        }
    }

    private void addNewEnemyWithTimer() {
        enemySpawnTimer++;
        power_up_timer++;
        clear_power_up_timer++;

        if (enemySpawnTimer >= enemySpawnDelay) {
            Random rand = new Random();
            int randomX = rand.nextInt(700);
            addObject(new MainEnemy(), randomX, 0);

            
            enemySpawnTimer = 0;
        }
        if (power_up_timer >= power_up_delay) {
            
            Random rand = new Random();
            int randomX = rand.nextInt(700);
            addObject(new DoublePw(), randomX, 0);
            
            power_up_timer = 0;
            
        }
        if (clear_power_up_timer >= clear_power_up_delay) {
            
            Random rand = new Random();
            int randomX = rand.nextInt(700);
            addObject(new ClearPw(), randomX, 0);
            
            clear_power_up_timer = 0;
            
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(fisier));
            if (!fisier.exists()) {
                fisier.createNewFile();
                writer.write(Integer.toString(get_score));
            }
    
            // Deschide BufferedWriter pentru a scrie în fișier
    
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
        addObject(new Labels("Soon!", 60), 750, getHeight() / 2);
        addObject(new Labels("Shop", 50), getWidth() / 2, 50);
        addObject(new Points(score_from_text), 150, 55);
        addObject(new Labels("Navy 1: 10000", 50), 150, 200);
        addObject(new Images("navy-1.png", 150, 115), 150, 300);
        addObject(new Labels("+1 speed", 28), 270, 270);
        addObject(new Labels("Navy 2: 50000", 50), 450, 200);
        addObject(new Images("navy-2.png", 150, 115), 450, 300);
        addObject(new Labels("+3 speed", 28), 550, 270);
        addObject(new Labels("-5 bullet delay", 28), 573, 300);
        if (caracter_navy_index1 == 0) {
            addObject(new Button("Buy Navy1", 35), 150, 400);
        } else if ("navy1".equals(caracter_navy)) {
            addObject(new Button("Selected", 35), 150, 400);
        } else {
            addObject(new Button("Select Navy1", 35), 150, 400);
        }
        if (caracter_navy_index2 == 0) {
            addObject(new Button("Buy Navy2", 35), 450, 400);
        } else if ("navy2".equals(caracter_navy)) {
            addObject(new Button("Selected", 35), 450, 400);
        } else {
            addObject(new Button("Select Navy2", 35), 450, 400);
        }
        addObject(new Button("Main Menu", 40), getWidth() / 2, getHeight() - 50);
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
    private void goBackToMainMenu() {
        
        game_sound.stop();
        Greenfoot.setWorld(new Fundal());
        
    } 
   
    public void dpw_true() {
        
        check_dpw = true;

    }
    public void dpw_false() {
        
        check_dpw = false;
        
    }

    public boolean returnDoublepw() {
        
        return check_dpw;
        
    }
    public void clearPowerUp() {
        
        List<MainEnemy> enemy = getObjects(MainEnemy.class);
        for (MainEnemy e : enemy) {
            removeObject(e);
            get_score += 50;
        }
        
    }
    public void getStatusA() {
        
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
