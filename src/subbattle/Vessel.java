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
        if(_stations.size()>station&&_crew._instructions.size()==0) {
            int sFloor = _stations.get(station).Floor();
            int sX = (int)_stations.get(station)._position.x();
            int cFloor = _crew.Floor();
            int cX = (int)_crew.Position().x();


            int f2g = sFloor - cFloor;
            int x2g = sX - cX;

            boolean goingUp = (f2g<0);
            _crew.addInstruction(new Point(sX, (sFloor+1)*_track.StepSize()));

            if(f2g!=0)
            {
                int i = sFloor;
                while(i!=cFloor)
                {
                    if(cFloor>sFloor)
                    {
                        float ladder = Integer.MAX_VALUE;
                        for(Float l : _track.FindLadders(i))
                        {
                            if(Math.abs(l-cX)+Math.abs(l-sX)<ladder)
                            {
                                ladder=l;
                            }
                        }
                        _crew._instructions.push(new Point(ladder, (i++ + 1)*_track.StepSize() ) );
                        _crew._instructions.push(new Point(ladder, (i + 1)*_track.StepSize() ) );
                    }
                    else
                    {
                        float ladder = Integer.MAX_VALUE;
                        for(Float l : _track.FindLadders(i-1))
                        {
                            if(Math.abs(l-cX)+Math.abs(l-sX)<ladder)
                            {
                                ladder=l;
                            }
                        }
                        _crew._instructions.push(new Point(ladder, (i-- + 1)*_track.StepSize() ) );
                        _crew._instructions.push(new Point(ladder, (i + 1)*_track.StepSize() ) );
                    }
                }
            }


        }
    }

    public void render(GameContainer game, Graphics g)
    {
        _track.render(game, g, _position);
        for(Station s : _stations)
        {
            s.render(game,g,_position);
        }
        _crew.render(game, g, _position);
    }
}
