package subbattle;

import Physics.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

public class Vessel
{
    private Point _position;
    private Track _track;
    private ArrayList<Station> _stations = new ArrayList<>();
    private int _spw = 50, _sph = 25;//temporary spot width and spot height

    private Crew _crew;


    public Vessel(Point pos, Track track, ArrayList<Station> stations)
    {
        _position = pos;
        _track = track;
        _stations = stations;
        _crew = new Crew(0, 20, _track.StepSize());

        System.out.println(track.FindLadders(0));
        System.out.println(track.FindLadders(1));
        System.out.println(track.FindLadders(2));
    }

    public void update(int count)
    {
        _crew.update();
    }

    public void tick(int count)
    {

    }


    public void moveCrew(int crew, int station)
    {
        if(_stations.size()>station) {
            int sFloor = _stations.get(station).Floor();
            int sX = (int)_stations.get(station)._position.x();
            int cFloor = _crew.Floor();
            int cX = (int)_crew._x;

            int f2g = sFloor - cFloor;
            int x2g = sX - cX;

            boolean goingUp = (f2g<0);
            _crew.addInstruction(new Point(sX, (sFloor+1)*_track.StepSize()));

            if(f2g!=0)
            {
                int i = sFloor;
                while(i!=cFloor)
                {
                    //if(goingup);
                }
            }


        }
    }

    public void render(GameContainer game, Graphics g)
    {
        for(float i :_track.FindLadders(0)) {

        }

        _track.render(game, g, _position);
        for(Station s : _stations)
        {
            s.render(game,g,_position);
        }
        _crew.render(game, g, _position);
    }
}
