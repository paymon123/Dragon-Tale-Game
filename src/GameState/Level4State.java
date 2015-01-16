package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Object;
import Entity.Enemies.*;
import Entity.Objects.BounceObject;
import Entity.Objects.ExtraHeart;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Level4State extends GameState {
	
	private AudioPlayer bgMusic;
	private AudioPlayer item;
	private TileMap tileMap;
	private Background bg;
	Random rand = new Random();
	
	private static Player player;
	private boolean deathScreen, gameOver, levelStart, messagePlayed, readSign;
	private long deathScreenTimer, levelStartTimerDiff, levelStartTimer = 0;
	private long deathScreenDelay = 2000;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Object> objects;
	private ArrayList<Text> texts;
	
	private HUD hud;
	
	public Level4State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		
	}
	
	public void init() {
		
		levelStart = true;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset2.gif");
		tileMap.loadMap("/Maps/level4-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(10, 150);
		player.setLevel(4);
		
		
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		objects = new ArrayList<Object>();
		texts = new ArrayList<Text>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		item = new AudioPlayer("/SFX/item.mp3");
		if(!player.getMute())
		{
		bgMusic.loop();
		}
		
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
	
		Spider spider;
		Point[] spiderpoints = new Point[] {
			
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75)),
			new Point(rand.nextInt(2500)+160, rand.nextInt(75))
			
		};
		for(int i = 0; i < spiderpoints.length; i++) {
			spider = new Spider(tileMap, spiderpoints[i].x, 00);
			spider.setPosition(spiderpoints[i].x, spiderpoints[i].y);
			enemies.add(spider);
		}

		
	
		
	}
	public void setDeathScreen(boolean b)
	{
		deathScreen = b;
	}
	public void update() {
		
		if(levelStartTimer == 0&& levelStart)
		{

			levelStartTimer = System.nanoTime();

		}
		else
		{

			levelStartTimerDiff = (System.nanoTime() - levelStartTimer)/1000000;
			if(levelStartTimerDiff > 5000)
			{

				
				levelStartTimerDiff = 0;
				levelStart = false;
				
				
				
				

				
			}
		}
		
		if(player.getx()<= 0)
		
		{
			player.setPosition(10, 150);
		}
		//change to lvl 5
		if(player.getx()>tileMap.getWidth()-player.getWidth() && enemies.size() ==0)
		{
			gsm.setState(GameStateManager.LEVEL5STATE);
			bgMusic.stop();
		}
		if(player.getx()>tileMap.getWidth()-player.getWidth() && enemies.size() != 0 && !messagePlayed)
		{
			JOptionPane.showMessageDialog(null, "You must kill all of the enemies before advancing to the next level!");
			messagePlayed = true;
		}
		
		
		//if player falls off map
		if(player.gety()>= tileMap.getHeight()-player.getHeight()){
			player.kill();
		}
		//top of map
		if(player.gety() < 0)
		{
			player.setPosition(player.getx(), 0);
		}
		if(player.isDead())
		{
			
			player.setPosition(0, 80);
			deathScreen = true;
			deathScreenTimer = System.nanoTime();
			player.loseLife();
			player.setHealth(player.getMaxHealth());
			player.setFire(player.getMaxFire());
			
		}
		if (player.getLives() == 0)
		{
			gameOver = true;
		}
		
		
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		player.checkObjects(objects);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
				
				createBounceObject(e.getx(),e.gety());
				
			}
		}
		//update all objects
		for(int i = 0; i<objects.size(); i++){
			Object o = objects.get(i);
			o.update();
			if(o.isDead())
			{
				
				objects.remove(i);
				i--;
				if(player.getHealth()<player.getMaxHealth())
				{
				
				player.increaseHealth(1);
				texts.add(new Text(GamePanel.WIDTH/2,player.gety(),3000, "+1 Health!"));
				item.play();
				}
				else
				{
					texts.add(new Text(GamePanel.WIDTH/2,player.gety(),3000, "Already Max Health!"));
				}
				
			}
			
		}
		//update texts
				for(int i = 0; i<texts.size(); i++)
				{
					Text t = texts.get(i);
					if(texts.get(i).update())
					{
						texts.remove(i);
						i--;
					}
				}
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	public static Player getPlayer()
	{
		return player;
	}
	
	private void createExtraHeart(int x, int y)
	{
		
		ExtraHeart e = new ExtraHeart(tileMap);
		e.setPosition(x,y);
		objects.add(e);
		
	}
	private void createBounceObject(int x, int y)
	{
		
		BounceObject e = new BounceObject(tileMap);
		e.setPosition(x,y);
		objects.add(e);
		
	}

	
	public void draw(Graphics2D g) {
		
	
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//draw objects
		for(int i = 0; i<objects.size(); i++)
		{
			objects.get(i).draw(g);
		}
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		//draw level text
		g.setFont((new Font ("Comic Sans MS", Font.PLAIN, 18)));
		String s = "Level 4: Bouncing Stars";
		int length = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();
		int alpha = (int) (255 * Math.sin(3.14 * levelStartTimerDiff / 5000));
		if(alpha > 255) alpha = 255;
		g.setColor(new Color(255, 255, 255, alpha));
		int ypos = (int)levelStartTimerDiff/5;
		if (ypos <=GamePanel.HEIGHT/2) g.drawString(s, GamePanel.WIDTH/2 - length/2, ypos);
		else
		{
			g.drawString(s, GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2);
		}
		//draw sign1 text
		if (player.getx() > 120 && player.getx() < 150 && readSign)
		{
			g.setFont((new Font ("Comic Sans MS", Font.PLAIN, 10)));
			
			g.setColor(new Color(0, 0, 0));
			g.drawString("The sign reads:", 0, GamePanel.HEIGHT/2 - 20);
			g.drawString("Welcome to the edge of the world!  ", 0, GamePanel.HEIGHT/2 - 10);
			g.drawString("There is very little land ahead. ", 0, GamePanel.HEIGHT/2);
			g.drawString("No man or dragon has traveled beyond these spiders...  ", 0, GamePanel.HEIGHT/2 + 10);
		
		}
		//draw sign2 text
		
		if (player.getx() > 3120 && player.getx() < 3150 && readSign)
		{
			g.setFont((new Font ("Comic Sans MS", Font.PLAIN, 10)));
			
			g.setColor(new Color(0, 0, 0));
			g.drawString("The sign reads:", 0, GamePanel.HEIGHT/2 - 20);
			g.drawString("WARNING!  DANGER AHEAD", 0, GamePanel.HEIGHT/2 - 10);
			g.drawString("DO NOT TOUCH THE SHINY RED ORB", 0, GamePanel.HEIGHT/2 - 10);
			
		}
		//draw texts
				for (int i = 0; i<texts.size();i++)
				{
				texts.get(i).draw(g);
				}
		//draw death screen
		if (deathScreen == true)
		{
				
				long currentTime = System.nanoTime();
				long elapsed = (currentTime-deathScreenTimer)/1000000;
					if (elapsed >= deathScreenDelay)
					{
						levelStart = true;
						levelStartTimer = 0;
					}
					if(elapsed < deathScreenDelay)
					{
						g.setColor(Color.BLACK);
						g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
						Font font = new Font("Arial", Font.PLAIN, 14);
						g.setFont(font);
						g.setColor(Color.RED);
							if(!gameOver)
							{
								g.drawString("You Died!", GamePanel.WIDTH/2 - 30, GamePanel.HEIGHT/2);
							}
							else if(gameOver)
							{
								g.drawString("GAME OVER", GamePanel.WIDTH/2 - 40, GamePanel.HEIGHT/2);
								bgMusic.stop();
							}

						}
						
						else{
							deathScreen = false;
							if(gameOver)
							{
								
								gsm.setState(GameStateManager.MENUSTATE);	
								gameOver = false;
							}
						}
	
		}
		
		
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setJumping(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
		if(k == KeyEvent.VK_S) readSign = true;
		if(k == KeyEvent.VK_M && !player.getMute()) {player.setMute(true);bgMusic.stop();}
		else if(k == KeyEvent.VK_M && player.getMute()) {player.setMute(false);bgMusic.loop();}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setJumping(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
		if(k == KeyEvent.VK_S) readSign = false;
	}
	
}












