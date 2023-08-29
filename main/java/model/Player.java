package model;

import control.Background;
import view.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Random;

public class Player extends Character {

    private int score;
    private int points;
    private int key;
    boolean check = false;
    private int moves = 8;
    Point bonus = new Point(1, 1);
    Point trapPos = new Point();
    Sound effect = new Sound();

    public Player() {
        loadImage();
        pos = new Point(tileSize, tileSize);
        score = 0;
    }

    private void loadImage() {
        try {
            up = ImageIO.read(getClass().getResource("/player/idleup.png"));
            down = ImageIO.read(getClass().getResource("/player/idledown.png"));
            left = ImageIO.read(getClass().getResource("/player/idleleft.png"));
            right = ImageIO.read(getClass().getResource("/player/idleright.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        BufferedImage sprite = null;

        if (direction.equals("up")) {
            sprite = up;
        } else if (direction.equals("down")) {
            sprite = down;
        } else if (direction.equals("left")) {
            sprite = left;
        } else if (direction.equals("right")) {
            sprite = right;
        }
        g.drawImage(sprite, pos.x, pos.y, tileSize - 15, tileSize - 15, observer);
    }

    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            direction = "up";
            moveUp();
            moves++;
        }
        if (key == KeyEvent.VK_RIGHT) {
            direction = "right";
            moveRight();
            moves++;
        }
        if (key == KeyEvent.VK_DOWN) {
            direction = "down";
            moveDown();
            moves++;
        }
        if (key == KeyEvent.VK_LEFT) {
            direction = "left";
            moveLeft();
            moves++;
        }
    }

    public void tick() {
        int newX = getXposition();
        int newY = getYposition();

        if (moves == 8) {
            moves = 0;
            Background.setStatus(bonus.x, bonus.y, 0);
            bonus = randomPoint();
            Background.setStatus(bonus.x, bonus.y, 13);
        }

        // Reset spikes
        if (check && Background.getStatus(newX, newY) != 12) {
            Background.setStatus(trapPos.x, trapPos.y, 11);
            check = false;
        }
        //Open Door
        if (points >= Background.totalRewards && Background.getStatus(1, 0) != 15) {
            Background.setStatus(1,0,15);
            effect.setFile(5);
            effect.play();
        }

        //Bonus points
        if (Background.getStatus(newX, newY) == 10) {
            score++;
            points++;
            Background.setStatus(newX, newY, 0);
            effect.setFile(3);
            effect.play();
        } else if (Background.getStatus(newX, newY) == 11) {
            //punish the player
            effect.setFile(4);
            effect.play();

            if (score == 0) {
                Board.caughtUp = true;
                Background.setStatus(newX,newY,12);
            } else {
                score--;
                Background.setStatus(newX,newY,12);
                check = true;
                trapPos.x = newX;
                trapPos.y = newY;
            }
        } else if (Background.getStatus(newX, newY) == 13) {
            score += 2;
            Background.setStatus(newX, newY, 0);
            effect.setFile(3);
            effect.play();
        }
    }

    public boolean isPlayerAtDoor() {
        //if player is at the door position, and if the door is open
        if (getXposition() == 1 && getYposition() == 0 && Background.getStatus(1, 0) == 15) {
            {
                return true;
            }
        } else {
            return false;
        }
    }

    private Point randomPoint() {
        Random rand = new Random();
        Point bonus = new Point();
        int check;
        do {
            bonus.x = rand.nextInt(20);
            bonus.y = rand.nextInt(11);
            check = Background.getStatus(bonus.x, bonus.y);
        } while (check >= 2);

        return bonus;
    }

    public int getScore() {
        return score;
    }

    public int getXposition() {
        return pos.x / tileSize;
    }

    public int getYposition() {
        return pos.y / tileSize;
    }

    public int getKey() {
        return key;
    }

    //for testing
    public void setScore(int score) {
        this.score = score;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void setPosition(int x, int y) {
        Point pt = new Point(x * 64, y * 64);
        this.pos = pt;
    }

}
