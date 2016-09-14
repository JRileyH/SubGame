package marketflow.components.entities;

import engine.Game;

import marketflow.econ.Stock;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import java.util.Map;

public class Player extends Entity
{
    private Vector2f _center;
    private Vector2f _trajectory;
    private Vector2f _carrot;
    private float _angle;
    private int _gear;
    private int _gearSize;
    private int _maxGear;
    private int _minGear;
    private int _yaw;
    private int _maxYaw;
    private float _drag;
    private float _handling;


    public Player(String id, String path, Polygon hitbox, String desc, Map<String, Stock> st_ref, int x, int y, int gear, int max_gear, int min_gear, int max_yaw, float drag, float handling)
    {
        super(id, desc, st_ref, path, hitbox, x, y);
        _center = new Vector2f((Game.WIDTH/2),(Game.HEIGHT/2));
        _trajectory = new Vector2f((Game.WIDTH/2),(Game.HEIGHT/2));
        _carrot = new Vector2f((Game.WIDTH/2),(Game.HEIGHT/2));
        _gear = 0;
        _gearSize=gear;
        _maxGear =max_gear;
        _minGear =min_gear;
        _angle = (float)Math.PI/2;
        _yaw = 0;
        _maxYaw = max_yaw;
        _drag=drag;
        _handling=handling;
        renderable=true;
    }



    public void update(int count) {
        //Determine yaw
        _angle += _handling * _yaw;
        _img.setRotation(_img.getRotation() + (float) Math.toDegrees(_handling * _yaw));

        _hitbox = (Polygon)_hitbox.transform(Transform.createRotateTransform(_handling * _yaw));
        _hitbox.setCenterX(_center.x);
        _hitbox.setCenterY(_center.y);

        //Set Carrot
        _carrot.x = (float) (_center.x - (Speed() * Math.cos(_angle)));
        _carrot.y = (float) (_center.y - (Speed() * Math.sin(_angle)));

        //Catch Up to Carrot
        double deltaX = _carrot.x - _trajectory.x;
        double deltaY = _carrot.y - _trajectory.y;
        float delta = (float) Math.atan2(deltaY, deltaX);
        _trajectory.x += (float) (_drag * Math.cos(delta));
        _trajectory.y += (float) (_drag * Math.sin(delta));

        _posX=Game.WIDTH/2;
        _posY=Game.HEIGHT/2;

        _relX = _posX + Game.mf.Map().OffsetX() - _width / 2 + Game.mf.Map().PanX();
        _relY = _posY + Game.mf.Map().OffsetY() - _height / 2 + Game.mf.Map().PanY();
    }

    @Override
    public void tick(int count)
    {

    }

    @Override
    public void render(GameContainer game, Graphics g)
    {
        //super.render(game, g);
        int panx = Game.mf.Map().PanX();
        int pany = Game.mf.Map().PanY();
        int x = (Game.WIDTH/2)+panx;
        int y = (Game.HEIGHT/2)+pany;

        g.drawImage(_img, x-_width/2, y-_height/2);
        g.draw(_hitbox);

        g.setColor(Color.green);
        g.drawOval(_center.x-3,_center.y-3,6,6);
        g.drawOval(_center.x-13,_center.y-13,26,26);
        g.setColor(Color.red);
        g.drawOval(_carrot.x-3,_carrot.y-3,6,6);
        g.drawOval(_carrot.x-13,_carrot.y-13,26,26);
        g.setColor(Color.blue);
        g.drawOval(_trajectory.x-3, _trajectory.y-3,6,6);
        g.drawOval(_trajectory.x-13, _trajectory.y-13,26,26);
        g.setColor(Color.black);

        g.drawString("Gear: "+ _gear , x+50,y+50);
        g.drawString("Speed: "+ Speed(), x+50,y+70);
        g.drawString("Velocity - X: "+ -(int)Velocity().x+" Y: "+ -(int)Velocity().y, x+50,y+90);
        g.drawString("Yaw: "+_yaw, x+50,y+130);
    }

    public void toggleLight()
    {
        _headlight.Toggle();
    }

    public void shiftUp()
    {
        if (_gear < _maxGear)
        {
            _gear++;
        }
    }

    public void shiftDown()
    {
        if (_gear > _minGear)
        {
            _gear--;
        }
    }

    public void tackLeft(boolean on)
    {
        /*if(_yaw>-_maxYaw)
        {
            _yaw--;
        }*/
        if(on){_yaw=-3;}else{_yaw=0;}
    }

    public void tackRight(boolean on)
    {
        /*if(_yaw<_maxYaw)
        {
            _yaw++;
        }*/
        if(on){_yaw=3;}else{_yaw=0;}
    }

    public void GoTo(float x, float y)
    {
        System.out.println(x+","+y);
    }

    @Override
    public int relX(){return (int)_center.x;}
    @Override
    public int relY(){return (int)_center.y;}

    private int Speed(){return _gear * _gearSize;}
    @SuppressWarnings("unused")
    public int Gear(){return _gear;}
    @SuppressWarnings("unused")
    public void GearSize(int amt){_gearSize =amt;}
    public Vector2f Velocity()
    {
        return new Vector2f(_center.x- _trajectory.x,_center.y- _trajectory.y);
    }
    public void Velocity(Vector2f v){ Velocity().set(v);}
    @SuppressWarnings("unused")
    public float Handling(){return _handling;}
    @SuppressWarnings("unused")
    public void Handling(int amt){_handling=amt;}
    @SuppressWarnings("unused")
    public int MaxGear(){return _maxGear;}
    @SuppressWarnings("unused")
    public int MinGear(){return _minGear;}
    @SuppressWarnings("unused")
    public int Yaw(){return _yaw;}
    @SuppressWarnings("unused")
    public int MaxYaw(){return _maxYaw;}
}
