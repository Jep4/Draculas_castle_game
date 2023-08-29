import control.Background;
import model.Player;
import org.junit.Test;
import view.Board;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class PlayerTest {
    public Player player;
    public Board board;
    public Background bg;

    public void setUp(){
        player = new Player();
        board = new Board();
        bg = new Background();
    }


    @Test
    public void KeyTest()  {
        setUp();

        //UP key
        KeyEvent e = new KeyEvent(board,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_UP);
        board.keyPressed(e);

        assertEquals(1,board.getPlayer().getYposition());
        assertEquals("up",board.getPlayer().direction);

        //Down key
         e = new KeyEvent(board,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_DOWN);
        board.keyPressed(e);

        assertEquals(2,board.getPlayer().getYposition());
        assertEquals("down",board.getPlayer().direction);

        //Left key
         e = new KeyEvent(board,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_LEFT);
        board.keyPressed(e);

        assertEquals(1,board.getPlayer().getXposition());
        assertEquals("left",board.getPlayer().direction);

        //Right key
         e = new KeyEvent(board,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_RIGHT);
        board.keyPressed(e);

        assertEquals(2,board.getPlayer().getXposition());
        assertEquals("right",board.getPlayer().direction);

    }

    @Test
    public void tickTest(){
        setUp();

        //Reset move test
        player.setMoves(8);
        player.tick();
        assertEquals(0, player.getMoves());

        //Open door test
        Background.totalRewards = 1;

        player.setScore(0);
        player.tick();
        assertEquals(14, bg.Boardstatus[1][0]);

        //Bonus test
        //Case 1: current location is trap tile, and score is not 0
        player.setScore(10);
        player.tick();
        assertFalse(Board.caughtUp);

        //Case 2: current location is trap tile, and score is 0
        player.setPosition(0, 0);
        bg.Boardstatus[0][0]=11;
        player.setScore(0);
        player.tick();
        assertTrue(Board.caughtUp);


        //Case 3: Current location is reward tile
        player.setScore(0);
        bg.Boardstatus[0][0]=10;
        player.tick();
        assertEquals(1, player.getScore());

        //Case 4: Current location is bonus tile
        bg.Boardstatus[0][0]=13;
        player.setScore(0);
        player.tick();
        assertEquals(2, player.getScore());
        assertEquals(0, bg.Boardstatus[0][0]);

    }

    @Test
    public void doorTest(){
        setUp();
        //Case 1: model.Player is at the door, and is opened
        player.pos.x = 64;
        player.pos.y = 0;
        bg.Boardstatus[1][0]=15;
        assertTrue(player.isPlayerAtDoor());

        //Case 2: model.Player is not at the door, and is opened
        player.pos.x = 0;
        player.pos.y = 0;
        bg.Boardstatus[1][0]=15;
        assertFalse(player.isPlayerAtDoor());

        //Case 3: model.Player is not at the door, and is not opened
        player.pos.x = 0;
        player.pos.y = 0;
        bg.Boardstatus[1][0]=14;
        assertFalse(player.isPlayerAtDoor());

        //Case 4: model.Player is at the door, and is not opened
        player.pos.x = 64;
        player.pos.y = 0;
        bg.Boardstatus[1][0]=14;
        assertFalse(player.isPlayerAtDoor());
    }


}
