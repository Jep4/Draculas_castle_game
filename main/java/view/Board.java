package view;

import control.Background;
import model.Enemy;
import model.Player;
import model.Sound;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class Board extends JPanel implements ActionListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 704;
    public static final int TILE_SIZE = 64;
    public static boolean caughtUp = false;

    public static final int ROWS = 10;
    public static final int COLUMNS = 19;

    private boolean ifEnd = false;
    private boolean isDead = false;
    // controls the delay between each tick in ms
    private final int DELAY = 25;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    private Player player;
    private Background background;
    public ArrayList<Enemy> enemies;
    private int x = 0;
    long startTime;

    public long elaTime = 0;
    long time = 0;

    private boolean bonusVisible = false;
    private int bonusPos = 0;
    private long bonusSpawnTimer = 0;
    private final Sound soundTrack = new Sound();
    private final Sound backgroundMusic = new Sound();

    public Board() {
        Game.background.stop();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        //sets up the boards and players value so that it can be printed once repaint() is called under the gameloop.
        background = new Background();
        player = new Player();
        enemies = new ArrayList<>();
        enemies.add(new Enemy(new Point(TILE_SIZE * 5, TILE_SIZE * 7)));

        backgroundMusic.setFile(0);
        backgroundMusic.loop();

        // this timer will call the actionPerformed() method every DELAY ms,
        timer = new Timer(DELAY, this);
        timer.start();
        startTime = System.nanoTime();

    }

    //prints the background and the player to the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D G2D = (Graphics2D) g;
        background.draw(G2D);

        if (enemies.size() == 0) {
            player.draw(g, this);
        } else {
            for (Enemy enemy : enemies) {
                if (!caughtUp) {
                    player.draw(g, this);
                }
                enemy.draw(g, this);
            }
        }

        Toolkit.getDefaultToolkit().sync();

        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 64));
        g.drawString("Score:" + (player.getScore()), 400, 50);
        g.drawString("Time:" + getTime(), 700, 50);

        time = getTime();
        if (time % 5 == 0 && time - bonusSpawnTimer >= 5) {
            if (bonusVisible) {
                bonusVisible = false;
                bonusSpawnTimer = time;
                removeRandomPunishments(bonusPos);
            } else {
                bonusVisible = true;
                bonusSpawnTimer = time;
                bonusPos = randomPunishments();
            }
        }

        //displays endScreen for when the player gets killed by monster
        if (isDead) {
            try {
                BufferedImage youDiedLabel = ImageIO.read(getClass().getResource("/game_over.png"));
                Image youDiedImage = youDiedLabel.getScaledInstance(450, 450, Image.SCALE_DEFAULT);

                BufferedImage restartLabel = ImageIO.read(getClass().getResource("/space.png"));
                Image restartImage = restartLabel.getScaledInstance(370, 570, Image.SCALE_DEFAULT);

                g.drawImage(youDiedImage, 415, 87, this);
                g.drawImage(restartImage, 462, 135, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (player.isPlayerAtDoor()) {
            ifEnd = true;
            String playerTime = String.valueOf(elaTime / 1000000000);
            backgroundMusic.stop();
            Game.endScreen(String.valueOf(player.getScore()), playerTime);
        }
    }

    public void actionPerformed(ActionEvent e) {
        elaTime = System.nanoTime() - startTime;

        // prevent the player from disappearing off the board
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        if (!isDead && !ifEnd) {
            player.tick();

            for (Enemy enemy : enemies) {
                enemy.tick(player.getXposition(), player.getYposition());
            }
            repaint();
        }

        // to let the program know the game has ended, so it doesn't repaint.
        // the x is added to make sure that it is ran only once.
        for (Enemy enemy : enemies) {
            if (caughtUp && x == 0) {
                isDead = true;
                soundTrack.setFile(2);
                soundTrack.play();
                x = 1;
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    public void keyPressed(KeyEvent e) {

        if (isDead) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                backgroundMusic.stop();
                resetBoard();
            }
        }
        // react to key down events
        player.keyPressed(e);

        //the enemy should move after the player moves
        for (Enemy enemy : enemies) {
            enemy.moveToPlayer(player);
        }
    }

    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    public int randomPunishments() {
        int x = 0;
        int y = 0;
        Random random = new Random();
        while (Background.getStatus(x, y) > 1) {
            x = random.nextInt(Board.COLUMNS);
            y = random.nextInt(Board.ROWS);
        }
        Background.setStatus(x, y, 11);
        return (x * 100) + y;
    }

    public void removeRandomPunishments(int pos) {
        int y = pos % 100;
        int x = pos / 100;
        Background.setStatus(x, y, 0);
    }

    public void resetBoard() {
        Game.background.stop();
        background.loadMap();
        player = new Player();
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(new Point(TILE_SIZE * 5, TILE_SIZE * 7)));
        if (caughtUp == true) {
            soundTrack.stop();
        }
        backgroundMusic.setFile(0);
        backgroundMusic.loop();
        caughtUp = false;
        ifEnd = false;
        isDead = false;
        x = 0;
        timer = new Timer(DELAY, this);
        timer.start();
        startTime = System.nanoTime();
    }


    public long getTime(){
        time = elaTime / 1000000000;
        return time;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    //USED FOR TESTING
    public boolean getIsDead() {
        return isDead;
    }

    public boolean getIfEnd() {
        return ifEnd;
    }

    public Player getPlayer() {
        return player;
    }

    public Background getBoardBackground() {
        return background;
    }

    public boolean getCaughtUp() {
        return caughtUp;
    }

    public ArrayList getEnemies(){
        return enemies;
    }

    public void setElaTime(long i){
        elaTime = i;
    }

    public void setTime(long i){
        this.time = i;
    }

    public long getTime2(){
        return time;
    }

    public boolean getBonusVisible(){
        return bonusVisible;
    }
}