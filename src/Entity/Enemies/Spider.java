package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Spider extends Enemy {
	
	private BufferedImage[] sprites;
	private int webx, weby;
	
	
	public Spider(TileMap tm, int webx, int weby) {
		
		super(tm);
		
		this.webx = webx;
		this.weby = weby;
		moveSpeed = 0.3;
		maxSpeed = 2;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Enemies/spider.gif"
				)
			);
			
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		down = true;
		
		
	}
	
	private void getNextPosition() {
		
		// movement
		if(up) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		}
		else if(down) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
	
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		//returns to web 
		if(up && dy == 0 || y<=weby) {
			up = false;
			down = true;
			
		}
		else if(down && dy == 0 || y >= tileMap.getHeight() - height) {
			down = false;
			up = true;
			
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		g.setColor(Color.BLACK);
		g.setColor(Color.BLACK);
		g.drawLine((int)(webx + xmap - width / 2 + width)-(width/2), (int)(weby + ymap - height / 2), (int)(x + xmap - width / 2 + width) - (width/2), (int)(y + ymap - height / 2) + 5);
		
	}
	
}





