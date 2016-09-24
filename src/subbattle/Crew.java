package subbattle;

import Physics.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Crew
{
    int _floor;
    float _x, _y;
    int _step;

    public Crew(int floor, float x, int step)
    {
        _floor = floor;
        _x=x;
        _y=floor*step;
    }

    public void update()
    {

    }

    public void render(GameContainer game, Graphics g, Point pos)
    {

    }
}
