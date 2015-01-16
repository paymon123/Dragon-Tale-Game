package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Audio.AudioPlayer;
import TileMap.TileMap;

public class FireBall extends MapObject{

	private boolean hit, remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private HashMap<String, AudioPlayer> sfx;
	public FireBall(TileMap tm, boolean right) {
		super(tm);
		sfx = new HashMap<String,AudioPlayer>();
		sfx.put("explosion",new AudioPlayer("/SFX/explosion.mp3"));
		facingRight = right;
		moveSpeed = 3.8;
		if(right)
			dx = moveSpeed;
		
		else
			dx = -moveSpeed;
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
			sprites = new BufferedImage[4];
			for(int i = 0; i<sprites.length; i++)
			{
				sprites[i]=spritesheet.getSubimage(i*width, 0, width, height);
			}
			hitSprites = new BufferedImage[3];
			for(int i = 0; i<hitSprites.length; i++)
			{
				hitSprites[i]=spritesheet.getSubimage(i*width, height, width, height);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void setHit()
	{
		sfx.get("explosion").play();
		if(hit)return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	public boolean shouldRemove(){return remove;}
	public void update()
	{
		checkTileMapCollision();
		if(x<=0 || x >= tileMap.getWidth()-width)
			setHit();
		setPosition(xtemp, ytemp);
		if(dx == 0 && !hit)
		{
			setHit();
		}
		animation.update();
		if(hit&&animation.hasPlayedOnce())
		{
			remove=true;
		}
		
	}
	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
	}
}
