package utilz;

import static utilz.Constants.Enemy.SWORD_KNIGHT;
import static utilz.Constants.ObjectConstant.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.SwordKnight;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Projectile;
import objects.Spike;

public class HelpMethods {

	// check co the di chuyen hay khong
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData)) {
			if (!IsSolid(x + width, y + height, lvlData)) {
				if (!IsSolid(x + width, y, lvlData)) {
					if (!IsSolid(x, y + height, lvlData)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// check co phai chuong ngai vat hay khong
	public static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILE_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT) {
			return true;
		}

		float xIndex = x / Game.TILE_SIZE;
		float yIndex = y / Game.TILE_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}
	
	public static boolean IsProjectileHittingLevel(Projectile p , int[][] lvlData) {
		return IsSolid( p.getHitbox().x + p.getHitbox().width/2, p.getHitbox().y + p.getHitbox().height/2, lvlData);
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11) {
			return true;
		}
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
		if (xSpeed > 0) {
			int tileXPos = currentTile * Game.TILE_SIZE;
			int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
			return tileXPos + xOffset - 1; // minus 1 because of overlapping
		} else {
			return (currentTile) * Game.TILE_SIZE;
		}
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILE_SIZE);
		if (airSpeed > 0) {
			if ((float) currentTile + 0.8 > (hitbox.y / Game.TILE_SIZE)) {
				int tileYPos = currentTile * Game.TILE_SIZE;
				int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
				return tileYPos + yOffset - 1;
			} else {
				int tileYPos = (currentTile + 1) * Game.TILE_SIZE;
				int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
				return tileYPos + yOffset - 1;
			}
		} else
			return (currentTile) * Game.TILE_SIZE;
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0) {
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		}
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean CanCannonSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitbox,
			Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILE_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILE_SIZE);

		if (firstXTile < secondXTile) {
			return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
		} else
			return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
	}

	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + 1, y, lvlData)) {
				return false;
			}
		}
		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
			int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILE_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILE_SIZE);

		if (firstXTile < secondXTile) {
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
		} else
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
	}

	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		if (IsAllTilesClear(xStart, xEnd, y, lvlData))
			for (int i = 0; i < xEnd - xStart; i++) {
				if (!IsTileSolid(xStart + 1, y + 1, lvlData)) {
					return false;
				}
			}
		return true;
	}

	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvData = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if (value >= 48) {
					value = 11;
				}
				lvData[i][j] = value;
			}
		}
		return lvData;
	}

	public static ArrayList<SwordKnight> GetKnight(BufferedImage img) {
		ArrayList<SwordKnight> list = new ArrayList<>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getGreen();
				if (value == SWORD_KNIGHT) {
					list.add(new SwordKnight(j * Game.TILE_SIZE, i * Game.TILE_SIZE));
				}
			}
		}
		return list;
	}

	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getGreen();
				if (value == 100) {
					return new Point(j * Game.TILE_SIZE, i * Game.TILE_SIZE);
				}
			}
		}
		return new Point(1 * Game.TILE_SIZE, 1 * Game.TILE_SIZE);
	}

	public static ArrayList<Potion> GetPotions(BufferedImage img) {
		ArrayList<Potion> list = new ArrayList<>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if (value == RED_POTION || value == BLUE_POTION) {
					list.add(new Potion(j * Game.TILE_SIZE, i * Game.TILE_SIZE, value));
				}
			}
		}
		return list;
	}

	public static ArrayList<GameContainer> GetContainers(BufferedImage img) {
		ArrayList<GameContainer> list = new ArrayList<>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if (value == BOX || value == BARREL) {
					list.add(new GameContainer(j * Game.TILE_SIZE, i * Game.TILE_SIZE, value));
				}
			}
		}
		return list;
	}

	public static ArrayList<Spike> GetSpikes(BufferedImage img) {
		ArrayList<Spike> list = new ArrayList<>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if (value == SPIKE)
					list.add(new Spike(j * Game.TILE_SIZE, i * Game.TILE_SIZE, value));

			}
		}
		return list;
	}

	public static ArrayList<Cannon> GetCannons(BufferedImage img) {
		ArrayList<Cannon> list = new ArrayList<>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getBlue();
				if (value == CANNON_RIGHT || value == CANNON_LEFT)
					list.add(new Cannon(j * Game.TILE_SIZE, i * Game.TILE_SIZE, value));

			}
		}
		return list;
	}

}
