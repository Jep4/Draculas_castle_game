import control.Background;
import model.Enemy;
import org.junit.Test;
import view.Board;


import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board;

    public void setUp() {
        this.board = new Board();
    }

    @Test
    public void timerTest() {
        setUp();

        board.setElaTime(1975737200);
        System.out.println(board.elaTime);
        System.out.println(board.getTime());
        assertEquals(0, board.getTime());
    }

    //test to see if the endScreen code is ran when player has completed the game
    @Test
    public void endScreenTest() {
        setUp();
        //set the door open
        Background.Boardstatus[1][0] = 15;
        //move the player up
        board.getPlayer().moveUp();

        //test for whether the player is now at the door should assertTrue now because player is in position and
        //the door is open
        assertTrue(board.getPlayer().isPlayerAtDoor());

    }

    @Test
    public void enemyArrayTest(){
        setUp();

        assertEquals(1,board.getEnemies().size());
    }


    @Test
    public void playerLocationOnBoardTest(){
        setUp();

        assertEquals(1,board.getPlayer().getXposition());
        assertEquals(1,board.getPlayer().getYposition());
    }


    @Test
    public void enemyLocationOnBoardTest(){
        setUp();

        for (Enemy enemy : board.enemies){
            assertEquals(1,enemy.getXPosition());
            assertEquals(1,enemy.getYPosition());
        }
    }

    @Test
    public void bonusTimerTest(){
        setUp();

        board.setTime(5);
        assertFalse(board.getBonusVisible());
    }



    //INTEGRATED TESTS FOR PLAYER.JAVA
    //tests for keyPressed events
    @Test
    public void KeyUpTest() {
        setUp();

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP);
        board.keyPressed(e);

        assertEquals(1, board.getPlayer().getYposition());
    }

    @Test
    public void KeyDownTest() {
        setUp();

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e);

        assertEquals(2, board.getPlayer().getYposition());
    }

    @Test
    public void KeyRightTest() {
        setUp();

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e);

        assertEquals(2, board.getPlayer().getXposition());
    }

    @Test
    public void KeyLeftTest() {
        setUp();

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);
        board.keyPressed(e);

        assertEquals(1, board.getPlayer().getXposition());
    }


    @Test
    public void scoreTest() {
        setUp();

        Background.Boardstatus[2][1] = 10;
        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e);

        board.getPlayer().tick();


        assertEquals(1, board.getPlayer().getScore());
    }

    @Test
    public void falseKeyTest() {
        setUp();

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE);
        board.keyPressed(e);

        assertEquals(1, board.getPlayer().getYposition());
        assertEquals(1, board.getPlayer().getXposition());
    }

    @Test
    public void punishmentTest() {
        setUp();

        board.getPlayer().setScore(2);

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);

        board.getPlayer().tick();
        assertEquals(1, board.getPlayer().getScore());
    }

    @Test
    public void punishmentTest2() {
        setUp();

        board.getPlayer().setScore(0);

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e2);

        board.getPlayer().tick();

        assertTrue(board.getCaughtUp());
    }


    //INTEGRATED TESTS FOR ENEMY.JAVA
    @Test
    public void isDeadTest() {
        setUp();

        Enemy newEnemy = new Enemy(new Point(64, 64));
        //putting an enemy in the same tile as player should kill player
        board.enemies.add(newEnemy);

        newEnemy.tick(board.getPlayer().getXposition(), board.getPlayer().getYposition());
        assertTrue(board.getIsDead());
    }

    @Test
    public void enemyMovementTest() {
        setUp();

        Enemy enemyRight = new Enemy(new Point(64 * 2, 64));

        board.enemies.add(enemyRight);

        KeyEvent e = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);
        board.keyPressed(e);
        assertEquals(board.getPlayer().getXposition(), enemyRight.getXPosition());
        assertEquals(board.getPlayer().getYposition(), enemyRight.getYPosition());
    }


    //test for enemy navigating around walls
    //here, the enemy should recognize that there is a wall above him, and navigate to either left or right depending
    //on which side the player is on

    //player is to the left and wall is above
    @Test
    public void enemyMovementWithWallAboveTest1() {
        setUp();
        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e2);


        Enemy newEnemy = new Enemy(new Point(64 * 3, 64 * 2));
        board.enemies.add(newEnemy);

        KeyEvent e3 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP);
        board.keyPressed(e3);

        assertEquals(2, newEnemy.getXPosition());
        assertEquals(2, newEnemy.getYPosition());
    }


    //player is to the right and wall is above
    @Test
    public void enemyMovementWithWallAboveTest2() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e2);
        board.keyPressed(e1);
        board.keyPressed(e1);

        Enemy newEnemy = new Enemy(new Point(64 * 3, 64 * 2));
        board.enemies.add(newEnemy);

        KeyEvent e3 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP);
        board.keyPressed(e3);

        assertEquals(4, newEnemy.getXPosition());
        assertEquals(2, newEnemy.getYPosition());
    }


    //player is above and wall is to the left
    @Test
    public void enemyMovementWithWallLeftTest1() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);
        board.keyPressed(e2);

        Enemy newEnemy = new Enemy(new Point(64 * 3, 64 * 5));
        board.enemies.add(newEnemy);

        KeyEvent e3 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);
        board.keyPressed(e3);

        assertEquals(3, newEnemy.getXPosition());
        assertEquals(4, newEnemy.getYPosition());
    }

    //player is below and wall is to the left
    @Test
    public void enemyMovementWithWallLeftTest2() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);
        board.keyPressed(e2);
        board.keyPressed(e1);
        board.keyPressed(e1);

        Enemy newEnemy = new Enemy(new Point(64 * 3, 64 * 5));
        board.enemies.add(newEnemy);

        KeyEvent e3 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT);
        board.keyPressed(e3);

        assertEquals(3, newEnemy.getXPosition());
        assertEquals(6, newEnemy.getYPosition());
    }


    //player is above and wall is to the right
    @Test
    public void enemyMovementWithWallRightTest1() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);

        Enemy newEnemy = new Enemy(new Point(64, 64 * 5));
        board.enemies.add(newEnemy);

        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);

        assertEquals(1, newEnemy.getXPosition());
        assertEquals(4, newEnemy.getYPosition());
    }

    //player is below and wall is to the right
    @Test
    public void enemyMovementWithWallRightTest2() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);

        Enemy newEnemy = new Enemy(new Point(64, 64 * 5));
        board.enemies.add(newEnemy);

        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);

        assertEquals(1, newEnemy.getXPosition());
        assertEquals(6, newEnemy.getYPosition());
    }


    //player is to the left and wall is below
    @Test
    public void enemyMovementWithWallBelowTest1() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);

        Enemy newEnemy = new Enemy(new Point(64 * 2, 64 * 4));
        board.enemies.add(newEnemy);

        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e2);

        assertEquals(1, newEnemy.getXPosition());
        assertEquals(4, newEnemy.getYPosition());
    }

    //player is to the right and wall is below
    @Test
    public void enemyMovementWithWallBelowTest2() {
        setUp();

        KeyEvent e1 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        board.keyPressed(e1);
        KeyEvent e2 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT);
        board.keyPressed(e2);
        board.keyPressed(e2);

        Enemy newEnemy = new Enemy(new Point(64 * 2, 64 * 4));
        board.enemies.add(newEnemy);

        KeyEvent e3 = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN);
        board.keyPressed(e3);

        assertEquals(3, newEnemy.getXPosition());
        assertEquals(4, newEnemy.getYPosition());
    }


}