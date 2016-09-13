package marketflow;

import engine.Game;
import marketflow.components.lights.Light;
import org.newdawn.slick.*;

import java.util.ArrayList;

/**
 * Created by Riggy on 9/12/2016.
 */
public class LightMap {
    private final int RED=0,GREEN=1,BLUE=2;
    private ArrayList<Light> _lights;
    private float[][][] _lux;
    private float _shadow;
    private Image _backdrop;
    private int _lightBox;
    private int _lightMapX, _lightMapY;

    public LightMap(int lightBox, float shadow)
    {
        _lights = new ArrayList<>();
        try {
            _backdrop = new Image(Game.WIDTH,Game.HEIGHT);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _lightBox=lightBox;
        _lightMapX=(Game.WIDTH/_lightBox)+1;
        _lightMapY=(Game.HEIGHT/_lightBox)+1;
        _shadow=shadow;
        _lux = new float[_lightMapX+1][_lightMapY+1][3];
    }

    public void update(int count)
    {
        for(Light l : _lights)
        {
            l.update(count);
        }
        for (int y = 0; y< _lightMapY+1; y++)
        {
            for (int x = 0; x< _lightMapX+1; x++)
            {
                _lux[x][y][RED] = 0;
                _lux[x][y][GREEN] = 0;
                _lux[x][y][BLUE] = 0;

                for(Light l : _lights)
                {
                    if(l.Renderable()) {
                        float[] effect = l.getColorAt(x, y, _lightBox);
                        _lux[x][y][RED] += effect[RED];
                        _lux[x][y][BLUE] += effect[BLUE];
                        _lux[x][y][GREEN] += effect[GREEN];
                    }
                }
            }
        }
    }

    public void render(GameContainer game, Graphics g)
    {
        g.copyArea(_backdrop,0,0);

        _backdrop.startUse();
        for (int y=0;y<_lightMapY;y++) {
            for (int x=0;x<_lightMapX;x++) {
                Image lighting = _backdrop.getSubImage(x*_lightBox,y*_lightBox,_lightBox,_lightBox);

                lighting.setColor(Image.TOP_LEFT,       _lux[x][y][RED],     _lux[x][y][GREEN],       _lux[x][y][BLUE],        _shadow);
                lighting.setColor(Image.TOP_RIGHT,      _lux[x+1][y][RED],   _lux[x+1][y][GREEN],     _lux[x+1][y][BLUE],      _shadow);
                lighting.setColor(Image.BOTTOM_RIGHT,   _lux[x+1][y+1][RED], _lux[x+1][y+1][GREEN],   _lux[x+1][y+1][BLUE],    _shadow);
                lighting.setColor(Image.BOTTOM_LEFT,    _lux[x][y+1][RED],   _lux[x][y+1][GREEN],     _lux[x][y+1][BLUE],      _shadow);

                lighting.drawEmbedded(x*_lightBox,y*_lightBox,_lightBox,_lightBox);
            }
        }
        _backdrop.endUse();

        //Uncomment this if you want to view light source and radius
        /*for(Light l : _lights)
        {
            l.render(game,g);
        }*/
    }

    public void addLight(Light light)
    {
        _lights.add(light);
    }
    public float AbientShadow(){return _shadow;}
    public void AbientShadow(float amt){_shadow=amt;}
    public void incAbientShadow(float amt){_shadow+=amt;}


}
