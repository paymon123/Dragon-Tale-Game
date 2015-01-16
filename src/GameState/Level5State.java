package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Object;
import Entity.Enemies.*;
import Entity.Objects.BounceObject;
import Entity.Objects.ExtraHeart;
import Entity.Objects.FireObject;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Level5State extends GameState {
	
	private AudioPlayer bgMusic;
	private AudioPlayer item;
	private AudioPlayer secret;
	private TileMap tileMap;
	private Background bg;
	Random rand = new Random();
	
	private static Player player;
	private boolean deathScreen, gameOver, levelStart, messagePlayed, readSign, playedOnce;
	private long deathScreenTimer, levelStartTimerDiff, levelStartTimer = 0;
	private long deathScreenDelay = 2000;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Object> objects;
	private ArrayList<Text> texts;
	
	private HUD hud;
	
	public Level5State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		
	}
	
	public void init() {
		
		levelStart = true;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset2.gif");
		tileMap.loadMap("/Maps/level5-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 170);
		player.setLevel(5);
		
		
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		objects = new ArrayList<Object>();
		texts = new ArrayList<Text>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/04-labyrinth.mp3");
		item = new AudioPlayer("/SFX/item.mp3");
		secret = new AudioPlayer("/SFX/secret.mp3");
		if(!player.getMute())
		{
		bgMusic.loop();
		}
		
		
	}
	
	private void populateEnemies() {
		
		
		SnailQueen snailQueen = new SnailQueen(tileMap);
		snailQueen.setPosition(220,50);
		enemies.add(snailQueen);
		
	
	}
	private void populateEnemies2(int x, int y){
		
		
		for(int i = 1; i<4; i++)
		{
		Boss1 boss1 = new Boss1(tileMap);
		boss1.setPosition(rand.nextInt(340)+40,40);
		enemies.add(boss1);
		}
	}
	private void populateEnemies3(int x, int y){
		
		
		for(int i = 1; i<5; i++)
		{
		Slugger s = new Slugger(tileMap);
		s.setPosition(rand.nextInt(340)+40,40);
		enemies.add(s);
		}
	}
	public void setDeathScreen(boolean b)
	{
		deathScreen = b;
	}
	public void update() {
		
		if(enemies.size() == 0&& !playedOnce)
		{
		tileMap.loadMap("/Maps/level5-2.map");
		secret.play();
		createFireObject(687, 95);
		playedOnce = true;
		              
		}
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
			player.setPosition(220, 170);
		}
		//change to lvl 6
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
			
			player.setPosition(100, 170);
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
				explosions.add(new Explosion(e.getx(), e.gety()));
	
				if(e.getRank() == 3){
				populateEnemies2(e.getx(), e.gety());
				}
				else if (e.getRank() == 2){
				populateEnemies3(e.getx(), e.gety());
				}
				if(rand.nextInt(10)<2)
				{
					createExtraHeart(e.getx(),e.gety());
				}
	
			}
		}
		//update all objects
		for(int i = 0; i<objects.size(); i++){
			Object o = objects.get(i);
			o.update();
			if(o.isDead()&& o.isHeart())
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
			if(o.isDead()&& o.isFire())
			{
				objects.remove(i);
				i--;
				texts.add(new Text(GamePanel.WIDTH/2,player.gety(),3000, "Upgraded Fireballs!"));
				item.play();
				player.setMaxFire(5000);
				player.setFireRegen(5);
				
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
	private void createFireObject(int x, int y)
	{
		FireObject e = new FireObject(tileMap);
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
		String s = "Level 5: Snail Queen";
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
								player.setLives(3);
								player.setHealth(5);
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












