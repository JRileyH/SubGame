package marketflow;

import engine.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.Map;

public class PlayerAuto extends Entity
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

    public PlayerAuto(String id, String desc, Map<String, Stock> st_ref, int x, int y, int gear, int max_gear, int min_gear, int max_yaw, float drag, float handling)
    {
        super(id, desc, st_ref, x, y, "res/player.png");
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
    }

    public void update(int count)
    {
        double deltaX = _carrot.x - _trajectory.x;
        double deltaY = _carrot.y - _trajectory.y;
        float angle = (float) Math.atan2(deltaY, deltaX);
        _trajectory.x += (float) (_drag * Math.cos(angle));
        _trajectory.y += (float) (_drag * Math.sin(angle));

        _carrot.x+=Velocity().x;
        _carrot.y+=Velocity().y;

        //Keep ship centered
        _posX = (Game.WIDTH / 2) - Game.mf.Map().OffsetX();
        _posY = (Game.HEIGHT / 2) - Game.mf.Map().OffsetY();
    }

    public void tick(int count)
    {

    }

    public void render(GameContainer game, Graphics g)
    {
        super.render(game, g);
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
    }

    public void shiftUp()
    {

    }

    public void shiftDown()
    {

    }

    public void tackLeft()
    {

    }

    public void tackRight()
    {

    }

    public void GoTo(float x, float y)
    {
        _carrot.x = x;
        _carrot.y = y;
    }

    public void toggleAutoPilot()
    {

    }

    public int Speed(){return _gear * _gearSize;}
    public int Gear(){return _gear;}
    public void GearSize(int amt){_gearSize =amt;}
    public Vector2f Velocity()
    {
        return new Vector2f(_center.x- _trajectory.x,_center.y- _trajectory.y);
    }
    public float Handling(){return _handling;}
    public void Handling(int amt){_handling=amt;}
    public int MaxGear(){return _maxGear;}
    public int MinGear(){return _minGear;}
    public int Yaw(){return _yaw;}
    public int MaxYaw(){return _maxYaw;}
}
