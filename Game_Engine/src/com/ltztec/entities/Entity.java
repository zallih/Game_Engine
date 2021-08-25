package com.ltztec.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.world.Camera;

public class Entity {
	
	

	public static final BufferedImage AMMO_EN = Game.spritesheet.getSprite(64, 384, 32, 32);
	
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0, 128, 32, 32);
	
	public static BufferedImage ENEMY_DAMAGE = Game.spritesheet.getSprite(64, 256, 32, 32);
	
	public static BufferedImage HOUSE_EN = Game.spritesheet.getSprite(0, 416, 127, 127);
	
	public static BufferedImage LIFE_EN = Game.spritesheet.getSprite(0, 384, 32, 32);
	
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(32, 384, 32, 32);
	
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(96, 384, 32, 32);
	
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 384, 32, 32);

	public static BufferedImage GUN_LEFT_DAMAGE = Game.spritesheet.getSprite(160, 384, 32, 32);
	
	public static BufferedImage GUN_RIGHT_DAMAGE = Game.spritesheet.getSprite(192, 384, 32, 32);

	

	protected double x;
	protected double y;
	protected int width;
	protected int height;


	private int maskx, masky, mwidth, mheight;
	
	private BufferedImage sprite;
	
	
	public Entity(int x,int y,int width,int height, BufferedImage sprite) {
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY; 
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mwidth);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mwidth);
		
		return e1Mask.intersects(e2Mask);
	}
	

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
