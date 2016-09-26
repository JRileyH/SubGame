package subbattle;

import Physics.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Station
{
    int _id;
    Point _position;
    int _step;
    int _floor;
    Point _dimensions;


    public Station(int id, int floor, int x, int step)
    {
        _id=id;
        _dimensions = new Point(50,25);
        _position = new Point(x,((floor+1)*step)-_dimensions.y());
        _floor=floor;
        _step=step;
    }

    public void render(GameContainer game, Graphics g, Point pos)
    {
        g.setColor(Color.green);
        g.drawRect(pos.x()+_position.x(), pos.y()+_position.y(), _dimensions.x(), _dimensions.y());
        g.drawString(String.valueOf(_id), pos.x()+_position.x()+2, pos.y()+_position.y());
    }

    public int Floor(){return _floor;}
}
