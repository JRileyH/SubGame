package subbattle;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import java.util.Map;

/**
 * Created by Riggy on 9/8/2016.
 */
public class Star
{
    private String _id;
    private Image _img;
    private Polygon _box;
    private int _x = 200;
    private int _y = 200;
    private float[] _points;
    private boolean _spin;
    private Map<String,Star> _stars;
    private boolean _colliding = false;

    public Star(String id, int x, int y, Map<String, Star> stars, boolean autospin)
    {
        _id=id;
        _x=x;
        _y=y;
        _stars = stars;
        _spin=autospin;
        try {
            _img = new Image("res/star.png");
            _points = new float[]{
                _x+255, _y+0,
                _x+334, _y+163,
                _x+510, _y+187,
                _x+382, _y+310,
                _x+412, _y+485,
                _x+255, _y+402,
                _x+98, _y+485,
                _x+129, _y+310,
                _x+0, _y+187,
                _x+177, _y+163,
            };
            _box = new Polygon(_points);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    public void update(int count)
    {
        if(_spin){
            _img.setRotation(_img.getRotation()+1);
            _box = (Polygon) _box.transform(Transform.createRotateTransform((float)Math.PI/180, _x+255, _y+242));
        }
        _colliding=false;
        for(Map.Entry<String, Star> e : _stars.entrySet())
        {
            if(!e.getKey().equals(_id))
            {
                if(_box.intersects(e.getValue().Box()))
                {
                    _colliding = true;
                }
            }
        }
    }
    public void render(GameContainer game, Graphics g)
    {
        if(!_colliding){g.drawImage(_img, _x, _y);}else{g.setColor(Color.red);g.draw(_box);}
    }

    public Polygon Box(){return _box;}
}
