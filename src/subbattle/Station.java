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
    Point _dimensions;
    Rectangle _bounds;

    public Station(int id, Point pos, Point dim)
    {
        _id=id;
        _position = pos;
        _dimensions = dim;
    }

    public void render(GameContainer game, Graphics g, Point pos)
    {
        g.setColor(Color.green);
        g.drawRect(pos.x()+_position.x(), pos.y()+_position.y(), _dimensions.x(), _dimensions.y());
        g.drawString(String.valueOf(_id), pos.x()+_position.x()+2, pos.y()+_position.y());
    }
}
