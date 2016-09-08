package subbattle;

import engine.XMLHandler;
import org.newdawn.slick.*;
import java.util.HashMap;
import java.util.Map;

public class Init
{
    Map<String,Star> stars = new HashMap<>();

	public Init(XMLHandler xmlh)
	{
		xmlh.processSubBattle();
        stars.put("Star1",new Star("Star1",200,200,stars,true));
        stars.put("Star2",new Star("Star2",600,300,stars,false));
	}

	public void update(int count)
	{
        for(Star star : stars.values())
        {
            star.update(count);
        }
	}

    public void tick(int count) {

    }

	public void render(GameContainer game, Graphics g)
	{
        for(Star star : stars.values())
        {
            star.render(game, g);
        }
	}
}
