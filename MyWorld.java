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
    
    // Verificari importante
    private boolean gameStarted = false;
    public boolean check_update = false;
    boolean check_dpw = false;
    
    // Index pentru navele din magazin
    public int caracter_navy_index1;
    public int caracter_navy_index2;
    public int caracter_navy_index3;
    String caracter_navy;
    
    // Verificari joc, stare & declarari de muzici & actori
    public boolean check_mute = false;
    public boolean win = false;
    public GreenfootSound game_start_sound;
     public GreenfootSound win_game;
    public GreenfootSound game_over_sound;
    public GreenfootSound game_sound;
    public GreenfootSound click;
    public MainPlayer player;
    
    // Functionalitati 
    private int enemySpawnTimer = 0;
    private int enemySpawnDelay = 60;
    private int power_up_timer = 0;
    private int power_up_delay = 600;
    private int clear_power_up_timer = 0;
    private int clear_power_up_delay = 800;
    public int max_hp;
    public int get_hp;
    public int get_score = 0;
    public int score_from_text;
    public int x_misc = 80;
    public int y_misc = getHeight() - 50;
    public int status = 1;
    public int ctn_enemy = 0;
    public int max_enemy = 46;
    
    // Baza de date a jucatorului 
    UserInfo myInfo = UserInfo.getMyInfo();
    
    public MyWorld() {
        super(900, 600, 1);
        // Verificare date user
        
        if (!myInfo.isStorageAvailable()) {
            
            Greenfoot.setWorld(new VerifyLogIn());
            
        }
        player = new MainPlayer();
        // Scor & status
        score_from_text = myInfo.getScore();
        if (myInfo.getInt(4) == 0) {
            
            status = 1;
            
        } else if (myInfo.getInt(4) == 1) {
            
            status = 2;
            
        } else if (myInfo.getInt(4) == 2) {
            
            status = 3;
            
        } else if (myInfo.getInt(4) == 3) {
            
            status = 4;
            
        } else {
            
            status = 5;
            
        }
        
        // Hp mai mare pentru nava 3
        if ("navy3".equals(myInfo.getString(1))) {
            max_hp = 150;
            get_hp = 150;
        } else {
            max_hp = 100;
            get_hp = 100;
        }
        
        // Verificare nave
        caracter_navy_index1 = myInfo.getInt(1);
        caracter_navy_index2 = myInfo.getInt(2);
        
        // Butoane & Titlu
        addObject(new Labels("Galaxy Impact", 65), getWidth() / 2, 50);
        Labels labels = new Labels(myInfo.getUserName(), 40);
        addObject(labels, getWidth() / 2, 100);
        addObject(new Button("Play", 45), getWidth() / 2, getHeight() - 100);
        addObject(new Button("Reset", 45), getWidth() - 200, getHeight() - 100);
        //addObject(new Button("Hard mode", 45), 200, getHeight() - 100);
        addObject(new Button("Leaderboard", 45), getWidth() - 150, getHeight() - 40);
        addObject(new Button("Shop", 45), 150, getHeight() - 40);
        addObject(new Button("How to play", 45), getWidth() / 2, getHeight() - 40);
        
        // Setari muzica
        if ("mute".equals(myInfo.getString(3))) {
            
            addObject(new Button("Unmute music", 40), getWidth() - 125, 55);
            
        } else {
            
            addObject(new Button("Mute music", 40), getWidth() - 125, 55);
            
        }
        
        // Punctele & declarare muzici 
        Points points = new Points(myInfo.getScore());
        addObject(points, 125, 30);
        int x = points.getX();
        int y = points.getY();
        addObject(new Labels("Best score:" + Integer.toString(myInfo.getInt(9)), 35), x, y + 30);
        addObject(new Labels("Status: " + status + "/5", 35), x, y + 60);
        
        game_start_sound = new GreenfootSound("game-start.mp3");
        game_over_sound = new GreenfootSound("game-over.mp3");
        game_sound = new GreenfootSound("game.mp3");
        win_game = new GreenfootSound("win.mp3");
        click = new GreenfootSound("click.mp3");
        
        // Trofee
        int x_pos = labels.getX();
        addObject(new Earn(), x_pos, labels.getY() + 50);
    }
    
    // Status joc
    public boolean gameOver = false;
    public void act() {
        
        if (!gameOver) {
            
            // Verificare status muzica
            if (!"mute".equals(myInfo.getString(3))) {
                game_sound.play();
            }
            if (Greenfoot.mouseClicked(null) && !gameOver) {
                if (!gameStarted) {
                    // Verificare actiuni
                    checkButtonClicks();
                    
                }
            } 
            if (gameStarted && !gameOver) {
                // Finalizare joc cu status de castigator
                
                if (ctn_enemy >= max_enemy && myInfo.getInt(4) < 5) {
                    
                    // Salvare status 
                    int ante_status = myInfo.getInt(4);
                    ante_status++;
                    myInfo.setInt(4, ante_status);
                    myInfo.store();
                    get_score += 3 * ante_status * 5000;
                    gameOver = true;
                    win = true;
                    
                    
                } else if (ctn_enemy >= max_enemy) {
                    
                    int ante_status = myInfo.getInt(4) - 1;
                    get_score += 3 * ante_status * 5000;
                    gameOver = true;
                    win = true;
                    
                }
                // Oprire muzica daca este pornita
                game_sound.stop();
                // Adaugare inamici si utilitati
                addNewEnemyWithTimer();
                
                // Actualizare pentru fiecare inamic omorat
                if (check_update) {
                    
                    List<Labels> label = getObjects(Labels.class);
                    for (Labels b : label) {
                        removeObject(b);
                    }
                    addObject(new Labels("Enemy: " + ctn_enemy + "/" + max_enemy, 30), 100, 50);
                    addObject(new Labels("Score: " + get_score + "\n" + "HP: " + get_hp + "/" + max_hp, 30), x_misc, y_misc);
                    check_update = false;
                    
                }
            }
            // Actualizare date pentru joc
            if (get_hp <= 0 && !gameOver) {
                
                gameStarted = false;
                gameOver = true;
                
            }
            
        } else {
            
            // Verificare status final
            if (win) {
                
                win_game.play();
                score_from_text = myInfo.getScore();
                if (get_score > myInfo.getInt(9)) {
                    
                    myInfo.setInt(9, get_score);
                    myInfo.store();
                }
                myInfo.setScore(score_from_text + get_score);
                myInfo.store();
                int get_trophie = myInfo.getInt(3);
                if (get_trophie < 50) {
                    
                    get_trophie += 2;
                    myInfo.setInt(3, get_trophie);
                    myInfo.store();
                    
                } else {
                    
                    get_trophie++;
                    myInfo.setInt(3, get_trophie);
                    myInfo.store(); 
                    
                }
                Greenfoot.setWorld(new WinInterface(get_score, status));
                
            } else {
                game_over_sound.play();
                score_from_text = myInfo.getScore();
                if (get_score > myInfo.getInt(9)) {
                    
                    myInfo.setInt(9, get_score);
                    myInfo.store();
                }
                myInfo.setScore(score_from_text + get_score);
                myInfo.store();
                int get_trophie = myInfo.getInt(3);
                if (get_trophie < 2) {
                    
                    myInfo.setInt(3, 0);
                    myInfo.store();
                    
                }
                else if (get_trophie < 50) {
                    
                    get_trophie -= 2;
                    myInfo.setInt(3, get_trophie);
                    myInfo.store();
                    
                } else {
                    
                    get_trophie -= 3;
                    myInfo.setInt(3, get_trophie);
                    myInfo.store(); 
                    
                }
                Greenfoot.setWorld(new GameOver(get_score));
                
            }
        }
    
    }

    // Actiune asupra butoanelor
    public void checkButtonClicks() {
        List<Button> buttons = getObjects(Button.class);

        for (Button button : buttons) {
            if (Greenfoot.mouseClicked(button)) {
                handleButtonClick(button);
            }
        }
    }
    
    // Functionalitati butoane
    private void handleButtonClick(Button button) {
        if (button.getLabel().equals("Play")) {
            click.play();
            if (myInfo.getInt(6) != 0 || myInfo.getInt(4) != 0) {
                gameStarted = true;
                // Stergere elemente
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
                List<Earn> trophie = getObjects(Earn.class);
                    for (Earn p : trophie) {
                        removeObject(p);
                }
                // Incepere meci
                game_start_sound.play();
                addObject(new MainPlayer(), getWidth() / 2, getHeight() - 100);
                addObject(new Labels("Enemy: " + ctn_enemy + "/" + max_enemy, 30), 100, 50);
                addObject(new Labels("Score: " + get_score + "\n" + "HP: " + get_hp + "/" + max_hp, 30), x_misc, y_misc);
                        
            } else if (myInfo.getInt(6) == 0 && myInfo.getInt(4) == 0) {
                
                myInfo.setInt(6, 1);
                myInfo.store();
                Greenfoot.setWorld(new Story1());
                if (!"mute".equals(myInfo.getString(3))) {
                    
                    game_sound.stop();
                    
                }
                
            }
        } else if (button.getLabel().equals("Shop")) {
            
            // Codul pentru magazin
            removeObject(button);
            click.play();
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
            List<Earn> trophie = getObjects(Earn.class);
                for (Earn p : trophie) {
                    removeObject(p);
            }
            Shop();
            
        } else if (button.getLabel().equals("Mute music")) {
            
            // Oprire muzica
            click.play();
            removeObject(button);
            addObject(new Button("Unmute music", 40), getWidth() - 125, 55);
            myInfo.setString(3, "mute");
            myInfo.store();
            game_sound.setVolume(0);
            
        } else if (button.getLabel().equals("How to play")) {
            
            click.play();
            game_sound.stop();
            Greenfoot.setWorld(new Keys());
            
        }else if (button.getLabel().equals("Unmute music")) {
            
            // Pornire muzica
            click.play();
            removeObject(button);
            addObject(new Button("Mute music", 40), getWidth() - 125, 55);
            myInfo.setString(3, " ");
            myInfo.store();
            game_sound.setVolume(100);
            
        } else if (button.getLabel().equals("Buy Navy1")) {
            
            // Codul pentru a cumpara nava 1 din magazin
            click.play();
            caracter_navy_index1 = myInfo.getInt(1);
            List<Points> points = getObjects(Points.class);
            
            if (score_from_text >=  10000 && caracter_navy_index1 == 0) {
                
                get_score = -10000;
                myInfo.setScore(score_from_text + get_score);
                myInfo.store();
                get_score = 0;
                
                score_from_text = myInfo.getScore();
                for (Points b : points) {
                    removeObject(b);
                }
                
                addObject(new Points(score_from_text), 150, 55);
                caracter_navy = "navy1";
                caracter_navy_index1 = 1;
                myInfo.setInt(1, caracter_navy_index1);
                myInfo.store();
                removeObject(button);
                
                addObject(new Button("Select Navy1", 35), 150, 400);
            }
            
        } else if (button.getLabel().equals("Buy Navy2")) {
            
            // Codul pentru a cumpara nava 2 din magazin
            click.play();
            caracter_navy_index2 = myInfo.getInt(2);
            List<Points> points = getObjects(Points.class);
            
            if (score_from_text >=  25000 && caracter_navy_index2 == 0) {
                
                get_score = -25000;
                myInfo.setScore(score_from_text + get_score);
                myInfo.store();
                get_score = 0;
                
                score_from_text = myInfo.getScore();
                for (Points b : points) {
                    removeObject(b);
                }
                
                addObject(new Points(score_from_text), 150, 55);
                caracter_navy = "navy2";
                caracter_navy_index2 = 1;
                myInfo.setInt(2, caracter_navy_index2);
                myInfo.store();
                removeObject(button);
                
                addObject(new Button("Select Navy2", 35), 450, 400);
            } 
                    
        }  else if (button.getLabel().equals("Select Navy1")) {
            
            // Codul pentru a selecta nava 1 & salvare in baza de date 
            click.play();
            caracter_navy = "navy1";
            removeObject(button);
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                if (b.getLabel() == "Selected") {
                    
                    if (myInfo.getInt(2) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy2", 35), 450, 400);
                        
                    }
                    if (myInfo.getInt(5) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy3", 35), 750, 400);
                        
                    }
                }
                if (b.getLabel() == "Select Navy1") {
                    
                    removeObject(b);
                    
                }
            }
            max_hp = 100;
            get_hp = 100;
            addObject(new Button("Selected", 35), 150, 400);
            // Salvarea in baza de date
            myInfo.setString(1, "navy1");
            myInfo.store();
                
        } else if (button.getLabel().equals("Select Navy2")) {
                
            // Selectare nava 2 & salvare in baza de date
            click.play();
            caracter_navy = "navy2";
            removeObject(button);
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                if (b.getLabel() == "Selected") {
                    
                    if (myInfo.getInt(1) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy1", 35), 150, 400);
                        
                    }
                    if (myInfo.getInt(5) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy3", 35), 750, 400);
                        
                    }
                }
                if (b.getLabel() == "Select Navy2") {
                    
                    removeObject(b);
                    
                }
            }
            max_hp = 100;
            get_hp = 100;
            addObject(new Button("Selected", 35), 450, 400);
            // Salvare in baza de date
            myInfo.setString(1, "navy2");
            myInfo.store();
                
        } else if (button.getLabel().equals("Main Menu")) {
            click.play();
            // Instanta pentru meniu
            goBackToMainMenu();
        } else if (button.getLabel().equals("Buy Navy3")) {
            
            // Codul pentru a cumpara nava 3 din magazin
            click.play();
            caracter_navy_index2 = myInfo.getInt(2);
            List<Points> points = getObjects(Points.class);
            
            if (score_from_text >=  50000 && caracter_navy_index3 == 0) {
                
                get_score = -50000;
                myInfo.setScore(score_from_text + get_score);
                myInfo.store();
                get_score = 0;
                
                score_from_text = myInfo.getScore();
                for (Points b : points) {
                    removeObject(b);
                }
                
                addObject(new Points(score_from_text), 150, 55);
                caracter_navy = "navy3";
                caracter_navy_index3 = 1;
                myInfo.setInt(5, caracter_navy_index3);
                myInfo.store();
                removeObject(button);
                
                addObject(new Button("Select Navy3", 35), 750, 400);
            }
        } else if (button.getLabel().equals("Select Navy3")) {
                
            // Selectare nava 3 & salvare in baza de date
            click.play();
            caracter_navy = "navy3";
            removeObject(button);
            
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                if (b.getLabel() == "Selected") {
                    
                    if (myInfo.getInt(1) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy1", 35), 150, 400);
                        
                    }
                    if (myInfo.getInt(2) == 1) {
                        
                        removeObject(b);
                        addObject(new Button("Select Navy2", 35), 450, 400);
                        
                    }
                }
                if (b.getLabel() == "Select Navy3") {
                    
                    removeObject(b);
                    
                }
            }
            max_hp = 150;
            get_hp = 150;
            addObject(new Button("Selected", 35), 750, 400);
            // Salvare in baza de date
            myInfo.setString(1, "navy3");
            myInfo.store();
                
        } else if (button.getLabel().equals("Leaderboard")) {
            
            game_sound.stop();
            click.play();
            Greenfoot.setWorld(new Leaderboard());
            
        } else if (button.getLabel().equals("Reset")) {
            
            click.play();
            List<Button> buttons = getObjects(Button.class);
            for (Button b : buttons) {
                
                if (b.getLabel() == "Reset") {
                    
                    removeObject(b);
                    
                }
                
            }
            addObject(new Button("Ok", 45), getWidth() - 200, getHeight() - 100);
        } else if (button.getLabel().equals("Ok")) {
            
            click.play();
            myInfo.setInt(4, 0);
            myInfo.store();
            Greenfoot.setWorld(new Fundal());
        }
    }

    private void addNewEnemyWithTimer() {
        // Incrementare inamici & utilitati
        enemySpawnTimer++;
        power_up_timer++;
        clear_power_up_timer++;
        // Dificultate
        if (ctn_enemy >= 33) {
            
            enemySpawnDelay = 30;
            
        } else if (ctn_enemy >= 23) {
            
            enemySpawnDelay = 40;
            
        }
        // Inamici
        if (enemySpawnTimer >= enemySpawnDelay) {
            Random rand = new Random();
            int randomX = rand.nextInt(700);
            addObject(new MainEnemy(), randomX, 0);

            
            enemySpawnTimer = 0;
        }
        // Utilitati
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
    
    // Contact cu inamicul
    public void decreaseHp() {
        get_hp -= 10;
        get_score += 50;
        check_update = true;
        
    }
    // Contact cu glontul
    public void minHp() {
        get_hp -= 10;
        check_update = true;
        
    }
    // Inamic neutralizat
    public void increaseScore() {
        get_score += 50;
        ctn_enemy++;
        check_update = true;
        
    }
    
    // Magazin
    public void Shop() {
        
        // Date
        caracter_navy_index1 = myInfo.getInt(1);
        caracter_navy_index2 = myInfo.getInt(2);
        caracter_navy_index3 = myInfo.getInt(5);
        caracter_navy = myInfo.getString(1);
        
        // Elemente
        
        addObject(new Labels("Shop", 50), getWidth() / 2, 50);
        addObject(new Points(myInfo.getScore()), 150, 55);
        addObject(new Labels("Navy 1: 10000", 50), 150, 200);
        addObject(new Images("navy-1.png", 150, 115), 150, 300);
        addObject(new Labels("+1 speed", 28), 270, 270);
        addObject(new Labels("Navy 2: 25000", 50), 450, 200);
        addObject(new Images("navy-2.png", 150, 115), 450, 300);
        addObject(new Labels("+3 speed", 28), 550, 270);
        addObject(new Labels("-5 bullet delay", 28), 573, 300);
        addObject(new Labels("Navy 3: 50000", 50), 750, 200);
        addObject(new Images("navy-3.png", 140, 140), 750, 300);
        addObject(new Labels("+50 hp", 28), 820, 240);
        addObject(new Labels("+8 speed", 28), 833, 270);
        addObject(new Labels("-10 b. delay", 28), 840, 300);
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
        if (caracter_navy_index3 == 0) {
            addObject(new Button("Buy Navy3", 35), 750, 400);
        } else if ("navy3".equals(caracter_navy)) {
            addObject(new Button("Selected", 35), 750, 400);
        } else {
            addObject(new Button("Select Navy3", 35), 750, 400);
        }
        addObject(new Button("Main Menu", 40), getWidth() / 2, getHeight() - 50);
        if (Greenfoot.mouseClicked(null)) {
                
            checkButtonClicks();
            
        } 
        
    }
    
    // Revenire la meniu
    private void goBackToMainMenu() {
        
        game_sound.stop();
        Greenfoot.setWorld(new Fundal());
        
    } 
   
    // Functi pentru utilitatea "double bullet"
    public void dpw_true() {
        
        check_dpw = true;

    }
    public void dpw_false() {
        
        check_dpw = false;
        
    }
    public boolean returnDoublepw() {
        
        return check_dpw;
        
    }
    
    // Utilitatea stergere inamici prezenti pe ecran
    public void clearPowerUp() {
        
        List<MainEnemy> enemy = getObjects(MainEnemy.class);
        for (MainEnemy e : enemy) {
            removeObject(e);
            ctn_enemy++;
            get_score += 50;
            check_update = true;
        }
        
    }
    
}
