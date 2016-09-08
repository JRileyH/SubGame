package ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Init
{
	
	public Init()
	{
		
	}
	
	public void update(int count)
	{

	}
	
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("UserInterface", 100, 10);
	}

    public void tick(int count) {}
}
