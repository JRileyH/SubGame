package marketflow;

import engine.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

import java.util.Map;

/**
 * Created by Nark on 9/3/2016.
 */
public class Player extends Entity
{
    private Vector2f _velocity = new Vector2f(0,0);
    private Vector2f _carrot = new Vector2f(0,0);
    private float _angle;
    private int _gear;
    private int _gearSize;
    private int _maxGear;
    private int _minGear;
    private int _yaw;
    private int _maxYaw;
    private float _drag;
    private float _handling;


    public Player(String id, String desc, Map<String, Stock> st_ref, int x, int y, int gear, int max_gear, int min_gear, int max_yaw, float drag, float handling)
    {
        super(id, desc, st_ref, x, y, "res/player.png");
        _gear = 0;
        _gearSize=gear;
        _maxGear =max_gear;
        _minGear =min_gear;
        _angle = (float)Math.PI/2;
        _yaw = 0;
        _maxYaw = max_yaw;
        _drag=drag;
        _handling=handling;
    }

    public void update(int count)
    {
        //Determine yaw
        _angle +=_handling*_yaw;
        _img.setRotation(_img.getRotation()+(float)Math.toDegrees(_handling*_yaw));

        //Set Carrot
        _carrot.x = (float)(Speed() * Math.cos(_angle));
        _carrot.y = (float)(Speed() * Math.sin(_angle));

        //Catch Up to Carrot
        double deltaX = _carrot.x - _velocity.x;
        double deltaY = _carrot.y - _velocity.y;
        float delta = (float)Math.atan2(deltaY, deltaX);
        _velocity.x += (float)(_drag * Math.cos(delta));
        _velocity.y += (float)(_drag * Math.sin(delta));


        //Keep ship centered
        _posX = (Game.WIDTH/2)-Game.mf.Map().OffsetX();
        _posY = (Game.HEIGHT/2)-Game.mf.Map().OffsetY();
    }

    public void tick(int count)
    {

    }

    public void render(GameContainer game, Graphics g)
    {
        super.render(game, g);
        int panx = Game.mf.Map().PanX();
        int pany = Game.mf.Map().PanY();
        int x = (Game.WIDTH/2)+panx;
        int y = (Game.HEIGHT/2)+pany;

        g.setColor(Color.red);
        g.drawLine(x,y,x- _carrot.x*5,y- _carrot.y*5);
        g.setColor(Color.blue);
        g.drawLine(x,y,x- _velocity.x*5,y- _velocity.y*5);
        g.setColor(Color.black);

        g.drawString("Gear: "+ _gear , x+50,y+50);
        g.drawString("Speed: "+ Speed(), x+50,y+70);
        g.drawString("X: "+ -(int)_velocity.x+" Y: "+ -(int)_velocity.y, x+50,y+90);
        g.drawString("Yaw: "+_yaw, x+50,y+110);
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

    public void tackLeft()
    {
        if(_yaw>-_maxYaw)
        {
            _yaw--;
        }
    }

    public void tackRight()
    {
        if(_yaw<_maxYaw)
        {
            _yaw++;
        }
    }

    public int Speed(){return _gear * _gearSize;}
    public int Gear(){return _gear;}
    public void GearSize(int amt){_gearSize =amt;}
    public Vector2f Velocity(){return _velocity;}
    public float Handling(){return _handling;}
    public void Handling(int amt){_handling=amt;}
    public int MaxGear(){return _maxGear;}
    public int MinGear(){return _minGear;}
    public int Yaw(){return _yaw;}
    public int MaxYaw(){return _maxYaw;}
}
