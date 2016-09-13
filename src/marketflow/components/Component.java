package marketflow.components;

import engine.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * Created by Riggy on 9/10/2016.
 */
public class Component
{
    protected boolean hasImage;
    protected boolean hasHitbox;
    protected boolean renderable = false;

    protected int _posX, _posY;
    protected int _relX, _relY;
    protected int _width, _height;

    protected Image _img = null;
    protected Polygon _hitbox = null;

    public Component(String path, Polygon hitbox, int x, int y){
        if(path!=null){
            try {
                _img = new Image(path);
                _width=_img.getWidth();
                _height=_img.getHeight();
                hasImage=true;
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }else{
            hasImage=false;
            _width=0;
            _height=0;
        }

        if(hitbox!=null)
        {
            _hitbox=hitbox;
            hasHitbox=true;
        }else if(hasImage)
        {
            float[] hb_points = new float[]{0,0, _width,0, _width,_height,0,_height};
            _hitbox=new Polygon(hb_points);
            hasHitbox=true;
        }else
        {
            hasHitbox=false;
        }
        _posX=x;
        _posY=y;
    }

    public void update(int count)
    {
        _relX = _posX + Game.mf.Map().OffsetX() - _width / 2 + Game.mf.Map().PanX();
        _relY = _posY + Game.mf.Map().OffsetY() - _height / 2 + Game.mf.Map().PanY();
        if (hasHitbox)
        {
            _hitbox.setX(_relX);
            _hitbox.setY(_relY);
            renderable = Game.mf.Map().ViewPort().contains(_hitbox)||Game.mf.Map().ViewPort().intersects(_hitbox);
        }
    }

    public void render(GameContainer game, Graphics g)
    {
        if(renderable) {
            g.drawImage(_img, _hitbox.getX(), _hitbox.getY());
            g.draw(_hitbox);
        }
    }

    public int X(){return _posX;}
    public int Y(){return _posY;}
    public int relX(){return _relX;}
    public int relY(){return _relY;}
    public int Width(){return _width;}
    public int Height(){return _height;}
    public Polygon Hitbox(){return _hitbox;}
    public boolean Renderable(){return renderable;}
}
