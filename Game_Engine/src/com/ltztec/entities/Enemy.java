package com.ltztec.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.main.Sound;
import com.ltztec.world.Camera;
import com.ltztec.world.World;

public class Enemy extends Entity {

	private double speed = 0.5;
	private int maskx = 14, masky = 14, maskw = 16, maskh = 16;
	private int frames = 0, maxFrames = 30, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;

	public boolean isDamage = false;
	private int damageFrames = 0;

	private int life = 10;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(0, 256, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 256, 32, 32);
	}

	public void tick() {
		if (this.isCollidingWithPlayer() == false) {
			if (Game.rand.nextInt(100) < 50) {
				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
						&& !isColliding((int) (x + speed), this.getY())) {
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
						&& !isColliding((int) (x - speed), this.getY())) {
					x -= speed;
				} else if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
						&& !isColliding(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
						&& !isColliding(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			}
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}

			}
		} else {
			// estamos colidindo
			if (Game.rand.nextInt(100) < 10) {
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamage = true;
			}

		}

		this.collindingAmmo();

		if (life <= 0) {
			this.destroySelf();
			return;
		}
		if (isDamage) {
			Sound.hurtEffect.play();
			this.damageFrames++;
			if (this.damageFrames == 5) {
				this.damageFrames = 0;
				this.isDamage = false;
			}
		}
	}

	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}

	public void collindingAmmo() {
		for (int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if (e instanceof Shoot) {
				if (Entity.isColliding(this, e)) {
					isDamage = true;
					life--;
					// System.out.println("colidindo bala");
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}

	public boolean isCollidingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY(), maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32, 32);
		return enemyCurrent.intersects(player);
	}

	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}

		}
		return false;
	}

	public void render(Graphics g) {
		if (!isDamage)
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(Entity.ENEMY_DAMAGE, this.getX() - Camera.x, this.getY() - Camera.y, null);

		// g.setColor(Color.black);
		// g.fillRect(this.getX()+maskx - Camera.x,this.getY()+masky - Camera.y ,
		// this.maskw, this.maskh);

	}

}