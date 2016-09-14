package myestate;

import engine.XMLHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Init
{
	public Init(XMLHandler xmlh)
	{
		xmlh.processMyEstate();
	}

    @SuppressWarnings("unused")
	public void update(int count)
	{

	}
    @SuppressWarnings("unused")
    public void tick(int count) {

    }
    @SuppressWarnings("unused")
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("MyEstate", 100, 10);
	}
}
