package subbattle;

import Physics.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class Vessel
{
    private Point _position;
    private Track _track;
    private ArrayList<Station> _stations = new ArrayList<>();
    private int _spw = 50, _sph = 25;//temporary spot width and spot height

    private Crew _crew;


    public Vessel(Point pos, Track track, ArrayList<Point> stations)
    {
        _position = pos;
        _track = track;
        for(int i = 0; i < stations.size(); i++)
        {
            _stations.add(new Station(i, stations.get(i), new Point(_spw,_sph)));
        }
        //_crew = new Crew();
    }

    public void update(int count)
    {

    }

    public void tick(int count)
    {

    }

    public void render(GameContainer game, Graphics g)
    {
        _track.render(game, g, _position);
        for(Station s : _stations)
        {
            s.render(game,g,_position);
        }

        g.setColor(Color.blue);
        g.drawRect(_position.x(),_position.y(),10,20);
    }
}
