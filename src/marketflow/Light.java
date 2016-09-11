package marketflow;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Riggy on 9/11/2016.
 */
public class Light extends Obstacle
{
    private Color _tint;
    private float _diameter;
    private float _alpha;

    public Light(int x, int y, float diameter, Color tint)
    {
        super("res/marketflow/light.png", null, x,y);
        _diameter=diameter;
        _tint=tint;
    }
    public Light(int x, int y, float diameter)
    {
        super("res/marketflow/light.png", null, x,y);
        _diameter=diameter;
        _tint=Color.white;
    }
}
