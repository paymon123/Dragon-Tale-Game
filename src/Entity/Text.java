package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class Text {
private double x,y;
private long start, time, elapsed;
private String s;

	public Text(double x, double y, long time, String s)
	{
		this.s = s;
		this.x = x;
		this.y = y;
		this.time = time;
		start = System.nanoTime();
	}
	public boolean update()
	{
		
		y-= 1;
		elapsed = (System.nanoTime()- start) / 1000000;
		if (elapsed>time)
		{
			
			return true;
			
		}
		
		return false;
		
		
		
		
	}
	public void draw(Graphics2D g)
	{
		g.setFont((new Font ("Comic Sans MS", Font.PLAIN, 12)));
		int length = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();
		g.setColor(new Color(0,0,0));
		g.drawString(s, (int)(x - length/2), (int)y);
		
	}
	
}