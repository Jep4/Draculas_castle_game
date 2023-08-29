import model.Enemy;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class EnemyTest {

    @Test
    public void enemyTickTest(){
        Enemy newEnemy = new Enemy(new Point(64,64));

        newEnemy.tick(1,1);

        assertEquals("caught", newEnemy.direction);
    }

    @Test
    public void enemyTickTest2(){
        Enemy newEnemy = new Enemy(new Point(64,64));

        newEnemy.tick(1,2);

        assertFalse(newEnemy.direction.equals("caught"));
    }
}