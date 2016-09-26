package subbattle;

import Physics.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Stack;

public class Crew
{
    int _floor;
    float _x, _y;
    float _w, _h;
    int _step;

    Stack<Point> _instructions = new Stack<>();
    Point _next;
    boolean _moving = false;

    public Crew(int floor, float x, int step)
    {
        _floor = floor;
        _w=10;
        _h=20;
        _step=step;
        _x=x;
        _y=((floor+1)*step)-_h;

        _instructions.push(new Point(120,150));
        _instructions.push(new Point(100,150));
        _instructions.push(new Point(100,100));
        _instructions.push(new Point(80,100));
        _instructions.push(new Point(80,50));
        _next=new Point(_x,_y);
    }

    public void update()
    {
        if(_moving)
        {
            if(_x==_next.x()&&_y==_next.y()-_h)
            {
                _moving=false;
            }
            else
            {
                if(_x<_next.x()){_x++;}else if(_x>_next.x()){_x--;}
                if(_y<_next.y()-_h){_y++;}else if(_y>_next.y()-_h){_y--;}
            }

        }
        else if(_instructions.size()>0)
        {
            if( ((_floor+1)*_step)>_y+_h ){_floor--;}else
            if( ((_floor+1)*_step)<_y+_h ){_floor++;}

            _next=_instructions.pop();
            _moving=true;
        }
    }

    public void render(GameContainer game, Graphics g, Point pos)
    {
        g.setColor(Color.blue);
        g.drawRect(pos.x()+_x, pos.y()+_y, _w, _h);
    }

    public void addInstruction(Point p)
    {
        _instructions.push(p);
    }

    public int Floor(){return _floor;}
}
