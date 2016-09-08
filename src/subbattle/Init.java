package subbattle;

import engine.XMLHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Init
{
	public Init(XMLHandler xmlh)
	{
		xmlh.processSubBattle();
	}
	
	public void update(int count)
	{

	}
	
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("SubBattle", 100, 10);
	}

	public void tick(int count) {

	}
}
