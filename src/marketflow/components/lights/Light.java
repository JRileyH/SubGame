package marketflow.components.lights;

import engine.Game;
import marketflow.components.Component;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Light extends Component
{
    private Color _tint;
    float _radius;

    Light(int x, int y, float radius)
    {
        super(null, null, x,y);
        _radius=radius;
        _tint=Color.white;
        _hitbox= new Polygon(new float[]{
                -radius,-radius,
                -radius,radius,
                radius,radius,
                radius,-radius
        });
        hasHitbox=true;
    }
    public Light(int x, int y, float radius, Color tint)
    {
        super(null, null, x,y);
        _radius=radius;
        _tint=tint;
        _hitbox= new Polygon(new float[]{
                -radius,-radius,
                -radius,radius,
                radius,radius,
                radius,-radius
        });
        hasHitbox=true;
    }

    public void update(int count)
    {
        _relX = _posX + Game.mf.Map().OffsetX() - _width / 2 + Game.mf.Map().PanX();
        _relY = _posY + Game.mf.Map().OffsetY() - _height / 2 + Game.mf.Map().PanY();
        if (hasHitbox)
        {
            _hitbox.setX(_relX-_radius);
            _hitbox.setY(_relY-_radius);
            renderable = Game.mf.Map().ViewPort().contains(_hitbox)||Game.mf.Map().ViewPort().intersects(_hitbox);
        }
    }

    public void render(GameContainer game, Graphics g)
    {
        if(renderable) {
            g.setColor(_tint);
            g.fillOval(_relX - 5, _relY - 5, 10, 10);
            g.drawLine(_relX, _relY, _relX + _radius, _relY);
            g.drawLine(_relX, _relY, _relX, _relY + _radius);
            g.drawRect(_hitbox.getX(), _hitbox.getY(), _hitbox.getWidth(), _hitbox.getHeight());
        }
    }
    @SuppressWarnings("unused")
    public float Radius(){return _radius;}
    @SuppressWarnings("unused")
    public void Radius(float radius){_radius=radius;}
    @SuppressWarnings("unused")
    public Color Tint(){return _tint;}
    @SuppressWarnings("unused")
    public void Tint(Color tint){_tint=tint;}

    public float[] getColorAt(float x, float y, int box) {
        float dx = (x - _relX/box);
        float dy = (y - _relY/box);
        float hypotenuse = (dx*dx)+(dy*dy);
        float value = 1-(hypotenuse/((_radius*_radius)/(box*box)));
        if(value<0){value=0;}
        return new float[] {value*_tint.r,value*_tint.g,value*_tint.b};
    }
}
