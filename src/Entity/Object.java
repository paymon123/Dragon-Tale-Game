package Entity;

import TileMap.TileMap;

public class Object extends MapObject{
	protected boolean dead, bounce, fire, heart;

	public Object(TileMap tm) {
		super(tm);
		
	}
	public void kill(){dead = true;}
	public boolean isDead(){return dead;}
	public boolean getBounce(){return bounce;}
	public boolean isFire(){return fire;}
	public boolean isHeart(){return heart;}
	
	public void update(){}

}
