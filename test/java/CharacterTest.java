import control.Background;
import model.Character;
import org.junit.Test;
import view.Board;

import java.awt.*;

import static org.junit.Assert.*;

public class CharacterTest {
    public Character chr;
    Board bd = new Board();
    Background bg = new Background();
    Point pt;

    public void setUp(int x, int y){
        chr = new Character();
        pt = new Point(x, y);
        chr.pos = pt;
    }

    @Test
    //checkWall returns true if the player can move
    public void checkWallTest(){
        setUp(64,64);
        //Case 1: There is no Wall
        assertTrue(chr.checkWall(64, 64));

        //Case 2: There is a Wall
        assertFalse(chr.checkWall(64*3, 64));

        //Case 3: Out of range
        assertFalse( chr.checkWall(-3, -3));

    }

    @Test
    public void upTest(){
        //Case 1: Wall does not exist
        setUp(64,64*2);
        chr.moveUp();
        assertEquals(64, chr.pos.y);

        //Case 2: Wall exists
        setUp(64,64);
        chr.moveUp();
        assertEquals(64, chr.pos.y);
    }

    @Test
    public void downTest(){
        //Case 1: Wall does not exist
        setUp(64,64*2);
        chr.moveDown();
        assertEquals(64*3, chr.pos.y);

        //Case 2: Wall exists
        setUp(64,64*19);
        chr.moveDown();
        assertEquals(64*19, chr.pos.y);

    }

    @Test
    public void leftTest(){

        //Case 1: Wall does not exist
        setUp(64*2,64);
        chr.moveLeft();
        assertEquals(64, chr.pos.x);

        //Case 2: Wall exists
        setUp(64,64);
        chr.moveLeft();
        assertEquals(64, chr.pos.x);

    }

    @Test
    public void rightTest() {
        //Case 1: Wall does not exist
        setUp(64, 64);
        chr.moveRight();
        assertEquals(64 * 2, chr.pos.x);

        //Case 2: Wall exists
        setUp(64 * 19, 64);
        chr.moveRight();
        assertEquals(64 * 19, chr.pos.x);
    }



    }