package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNums;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        int tileNum = 6;

        mapTileNums = new int[gp.maxWorldCol][gp.maxWorldRow];

        tile = new Tile[tileNum];

        getTileImage(tileNum);
        loadMap("/maps/world01.txt");
    }
    
    private void getTileImage(int tn) {

        try {
            
            for (int x = 0; x < tn; x++) {
                tile[x] = new Tile();
            }
            
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

        } catch (IOException e) {
        }

    }

    private void loadMap(String mapPath) {

        try {
            // Sets up for reading the map values in the txt file
            InputStream is = getClass().getResourceAsStream(mapPath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    String line = br.readLine();
                    String[] nums = line.split(" ");
                    
                    for (int col = 0; col < gp.maxWorldCol; col++) {
                        int num = Integer.parseInt(nums[col]);
                        mapTileNums[col][row] = num;
                    }
                }
                
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exception
        }
        

    }

    public void draw(Graphics2D g2) {

        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                
                int tileNum = mapTileNums[worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                
            }
        }
        


    }

}
