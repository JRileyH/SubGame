package subbattle;

import Physics.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Stack;

public class Crew
{
    private int _floor;
    private Point _position;
    private Point _dimensions;
    private int _step;

    private Stack<Point> _instructions = new Stack<>();
    private Point _next;
    private boolean _moving = false;

    public Crew(int floor, float x, int step)
    {
        _floor = floor;
        _dimensions = new Point(10,20);
        _step=step;
        _position = new Point(x, ((floor+1)*step)-_dimensions.y());

        _next=new Point(_position);
    }

    public void update()
    {
        if(_moving)
        {
            if(_position.x()==_next.x()&&_position.y()==_next.y()-_dimensions.y())
            {
                _moving=false;
            }
            else
            {
                if(_position.x()<_next.x()){_position.shift(1,0);}else if(_position.x()>_next.x()){_position.shift(-1,0);}
                if(_position.y()<_next.y()-_dimensions.y()){_position.shift(0,1);}else if(_position.y()>_next.y()-_dimensions.y()){_position.shift(0,-1);}
            }

        }
        else if(_instructions.size()>0)
        {
            if( ((_floor+1)*_step)>_position.y()+_dimensions.y() ){_floor--;}else
            if( ((_floor+1)*_step)<_position.y()+_dimensions.y() ){_floor++;}

            _next=_instructions.pop();
            _moving=true;
        }
    }

    public void render(GameContainer game, Graphics g, Point pos)
    {
        g.setColor(Color.blue);
        g.drawRect(pos.x()+_position.x(), pos.y()+_position.y(), _dimensions.x(), _dimensions.y());
    }

    public void addInstruction(Point p)
    {
        _instructions.push(p);
    }
    public Stack<Point> Instructions(){return _instructions;}

    public int Floor(){return _floor;}

    public Point Position(){return _position;}
}
