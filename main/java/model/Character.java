package model;

import java.awt.*;
import java.awt.image.BufferedImage;

import control.Background;
import view.Board;

public class Character {
    public Point pos;
    public BufferedImage up, down, left, right, extra1;
    public String direction = "down";
    int tileSize = Board.getTileSize();

    public void moveUp() {

        if (checkWall(pos.x, pos.y - tileSize)) {
            direction = "up";
            pos.translate(0, -tileSize);
        }
    }

    public void moveDown() {
        if (checkWall(pos.x, pos.y + tileSize)) {
            direction = "down";
            pos.translate(0, tileSize);
        }
    }

    public void moveRight() {

        if (checkWall(pos.x + tileSize, pos.y)) {
            direction = "right";
            pos.translate(tileSize, 0);
        }

    }

    public void moveLeft() {
        if (checkWall(pos.x - tileSize, pos.y)) {
            direction = "left";
            pos.translate(-tileSize, 0);
        }
    }

    public boolean checkWall(int currX, int currY) {
        int newX = currX / tileSize;
        int newY = currY / tileSize;

        if (newX < 0 || newY < 0 || newX >= Board.COLUMNS || newY >= Board.ROWS) {
            return false;
        }

        return (Background.getStatus(newX, newY) < 2 || (Background.getStatus(newX, newY) > 9 &&
                Background.getStatus(newX, newY) < 16) && Background.getStatus(newX, newY) != 14);
    }
}
