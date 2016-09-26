package subbattle;

import Physics.Point;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;

public class Track
{
    Point[] _points;
    ArrayList<Float> _floors;
    int _step;

    public Track(ArrayList<Point> points, int step)
    {
        _points = new Point[points.size()];
        _points = points.toArray(_points);
        _step=step;

        _floors = new ArrayList<>();
        int level = 0;
        for(int i = 0; i < (_points.length/2)+2; i+=2)
        {
            if(_points[i].y()==_points[i+1].y()){
                _floors.add(level, _points[i].y());
                level++;
            }
            if(_points[i].x()==_points[i+1].x()){

            }
        }
    }

    public void render(GameContainer game, Graphics g, Point pos)
    {
        g.setColor(Color.pink);
        for(int i = 0; i < _points.length; i+=2)
        {
            g.drawLine(pos.x()+_points[i].x(), pos.y()+_points[i].y(), pos.x()+_points[i+1].x(), pos.y()+_points[i+1].y());
        }
    }

    public ArrayList<Float> FindLadders(int floor){
        ArrayList<Float> xs = new ArrayList<>();
        for(int i = 0; i < _points.length; i+=2)
        {
            if(_points[i].x()==_points[i+1].x()&&_points[i].y()==(floor+1)*_step)
            {
                xs.add(_points[i].x());
            }
        }
        return xs;
    }

    public int floorCount(){return _floors.size();}
    public int StepSize(){return _step;}
    public float floorPos(int floor){return _floors.get(floor);}
}
