package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = (gp.screenWidth/2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight/2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 20, 32, 24);

        setDefaultValues();
        getPlayerImage();
    }

    private void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }

    private void getPlayerImage() {
        try {

            forward = ImageIO.read(getClass().getResourceAsStream("/player/forward.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            backward = ImageIO.read(getClass().getResourceAsStream("/player/backward.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/left.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/right.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            
        } catch (IOException e) {

        }
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
    
            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cCheck.checkTile(this);
    
            if (!collisionOn) {
    
                switch (direction) {
    
                    case "up":
                        worldY -= speed;
                        break;
                    
                    case "down":
                        worldY += speed;
                        break;
    
                    case "left":
                        worldX -= speed;
                        break;
                    
                    case "right":
                        worldX += speed;
                        break;
                    
                    default: 
                        break;
    
                }
    
            }
    
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        
        if (direction.equals("up") && keyH.upPressed == false) {
            image = backward;
        } else if (direction.equals("up")) {
            if (spriteNum == 1) {
                image = up1;
            } else if (spriteNum == 2) {
                image = up2;
            }
        } else if (direction.equals("down") && keyH.downPressed == false) {
            image = forward;
        } else if (direction.equals("down")) {
            if (spriteNum == 1) {
                image = down1;
            } else if (spriteNum == 2) {
                image = down2;
            }
        } else if (direction.equals("left") && keyH.leftPressed == false) {
            image = left;
        } else if (direction.equals("left")) {
            if (spriteNum == 1) {
                image = left1;
            } else if (spriteNum == 2) {
                image = left2;
            }
        } else if (direction.equals("right") && keyH.rightPressed == false) {
            image = right;
        } else if (direction.equals("right")) {
            if (spriteNum == 1) {
                image = right1;
            } else if (spriteNum == 2) {
                image = right2;
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }

}
