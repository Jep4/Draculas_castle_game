package control;

import model.Tiles;
import view.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Background {

    public Tiles[] tile;
    public static int totalRewards;
    /* represents the actual map in a 2D array where each digit corresponds to a specific tile image, 0 for grass e.t.c.
       the example below is a 20 * 11 map, we can always change the dimensions (make sure to change board row, columns,
       height and width as well). To see the map check the resource folder.
     */
    public static int[][] Boardstatus;
    public int[][] MapTileNum;

    public Background() {
        MapTileNum = new int[Board.TILE_SIZE * 20][Board.TILE_SIZE * 11];
        tile = new Tiles[20];
        loadMap();
        setTileImage();
    }

    /* Loads the tile images to corresponding digits for the map to work, for now model.Tiles can store up to 10 images
       feel free to add more, make sure to change the directory as well, getClass().getResourceAsStream was not
       working on my end check to see if it works on your pc @Jpark.
     */
    public void setTileImage() {
        try {
            //Free model.Tiles
            tile[0] = new Tiles();
            tile[0].image = ImageIO.read(getClass().getResource("/dungeon/tiles/newTile1.png"));
            tile[1] = new Tiles();
            tile[1].image = ImageIO.read(getClass().getResource("/dungeon/tiles/newTile2.png"));

            //Wall model.Tiles
            tile[2] = new Tiles();
            tile[2].image = ImageIO.read(getClass().getResource("/dungeon/tiles/wall3.png"));
            tile[3] = new Tiles();
            tile[3].image = ImageIO.read(getClass().getResource("/dungeon/tiles/cornerTopLeft.png"));
            tile[4] = new Tiles();
            tile[4].image = ImageIO.read(getClass().getResource("/dungeon/tiles/cornerTopRight.png"));
            tile[5] = new Tiles();
            tile[5].image = ImageIO.read(getClass().getResource("/dungeon/tiles/right2.png"));
            tile[6] = new Tiles();
            tile[6].image = ImageIO.read(getClass().getResource("/dungeon/tiles/left2.png"));
            tile[7] = new Tiles();
            tile[7].image = ImageIO.read(getClass().getResource("/dungeon/tiles/cornerBottomLeft.png"));
            tile[8] = new Tiles();
            tile[8].image = ImageIO.read(getClass().getResource("/dungeon/tiles/cornerBottomRight.png"));
            tile[9] = new Tiles();
            tile[9].image = ImageIO.read(getClass().getResource("/dungeon/tiles/bottomWall.png"));

            //Rewards,Punishments and Bonuses
            tile[10] = new Tiles();
            tile[10].image = ImageIO.read(getClass().getResource("/dungeon/tiles/reward.png"));
            tile[11] = new Tiles();
            tile[11].image = ImageIO.read(getClass().getResource("/dungeon/tiles/newTrap.png"));
            tile[12] = new Tiles();
            tile[12].image = ImageIO.read(getClass().getResource("/dungeon/tiles/trapSet.png"));
            tile[13] = new Tiles();
            tile[13].image = ImageIO.read(getClass().getResource("/dungeon/tiles/bonus.png"));

            //Door
            tile[14] = new Tiles();
            tile[14].image = ImageIO.read(getClass().getResource("/dungeon/tiles/closedDoor.png"));
            tile[15] = new Tiles();
            tile[15].image = ImageIO.read(getClass().getResource("/dungeon/tiles/openDoor2.png"));

            //Obstacles
            tile[16] = new Tiles();
            tile[16].image = ImageIO.read(getClass().getResource("/dungeon/tiles/bones.png"));
            tile[17] = new Tiles();
            tile[17].image = ImageIO.read(getClass().getResource("/dungeon/tiles/skulls.png"));
            tile[18] = new Tiles();
            tile[18].image = ImageIO.read(getClass().getResource("/dungeon/tiles/barrier.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* draws the board with the helps of Graphics2D, simply iterates over the map 2d array
       and prints at corresponding x and y locations
    */
    public void draw(Graphics2D G2D) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < 20 && row < 11) {
            int tileNum = MapTileNum[col][row];
            G2D.drawImage(tile[tileNum].image, x, y, Board.TILE_SIZE, Board.TILE_SIZE, null);
            col++;

            x += Board.TILE_SIZE;

            if (col == 20) {
                col = 0;
                x = 0;
                row++;
                y += Board.TILE_SIZE;
            }
        }
    }

    //Load map status to view.Board status
    public void loadMap() {
        totalRewards = 0;
        try {
            InputStream is = new FileInputStream("src/main/resources/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < 20 && row < 11) {
                String line = br.readLine();

                while (col < 20) {
                    String[] numbers = line.split(" ");
                    MapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    if (MapTileNum[col][row] == 10) {
                        totalRewards++;
                    }
                    col++;
                }
                if (col == 20) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boardstatus = MapTileNum;
    }

    public static int getStatus(int x, int y) {
        return Boardstatus[x][y];
    }

    public static void setStatus(int x, int y, int value) {
        Boardstatus[x][y] = value;
    }
}
