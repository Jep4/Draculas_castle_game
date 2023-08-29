import control.Background;
import org.junit.Test;
import view.Board;

import static org.junit.Assert.*;


public class BackgroundTest {

    @Test
    public void loadMapTest() {

        int[][] MapArray = {
                {3, 14, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {5, 0, 0, 18, 1, 0, 1, 0, 1, 18, 1, 0, 1, 1, 1, 0, 1, 0, 16, 6},
                {5, 0, 0, 0, 1, 0, 11, 10, 11, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 6},
                {5, 0, 1, 0, 1, 0, 1, 11, 1, 18, 1, 0, 1, 0, 1, 11, 17, 0, 1, 6},
                {5, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 6},
                {5, 0, 16, 0, 1, 0, 17, 0, 1, 0, 16, 0, 1, 0, 1, 0, 17, 0, 1, 6},
                {5, 0, 1, 0, 1, 0, 1, 0, 11, 0, 1, 0, 1, 17, 1, 0, 1, 10, 1, 6},
                {5, 18, 1, 0, 17, 0, 1, 0, 1, 18, 1, 0, 1, 0, 1, 0, 1, 18, 1, 6},
                {5, 0, 1, 0, 1, 0, 10, 11, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 6},
                {5, 0, 1, 0, 1, 0, 1, 0, 1, 18, 1, 0, 1, 0, 1, 0, 1, 1, 1, 6},
                {7, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 8}};


        Board board = new Board();
        Background bg = new Background();

        for(int i=0; i<11; i++){
            for (int j=0; j<20; j++){
                //Excludes bonus cell that constantly changes its position

                if(bg.MapTileNum[j][i]!=13){
                    assertEquals(MapArray[i][j], bg.MapTileNum[j][i]);
                }
            }
        }
    }
}