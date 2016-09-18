package ui.tools;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

import static engine.Game.imagemap;

public class Modal extends Tool
{
    private int _labelH, _bodyH;
    private ArrayList<Tool> _internals;
    private Rectangle _bodybox;
    private float _oldMouseX, _oldMouseY;
    private boolean _active;
    private boolean _minimized;
    private Button _closeButton;
    private Button _minButton;

    public Modal(Image[] imgset, Font font, String text, int activator, int x, int y, int marginx, int marginy, int w, int h, ArrayList<Tool> internals)
    {
        super(imgset, font, text, activator, x, y, marginx, marginy);
        _closeButton = new Button(imagemap.get("Close"), _activator, (x+w)-marginx-_font.getLineHeight(), y+marginy, _font.getLineHeight(), _font.getLineHeight(), null);
        _minButton = new Button(imagemap.get("Minimize"), _activator, (x+w)-marginx-(_font.getLineHeight()*2), y+marginy, _font.getLineHeight(), _font.getLineHeight(), null);
        _w=w;
        _h=h;
        _labelH = (marginy*2)+font.getLineHeight();
        _bodyH = _h-_labelH;
        _clickbox = new Rectangle(_x, _y, _w, _labelH);
        _bodybox = new Rectangle(_x, _y+_labelH, _w, _bodyH);
        _internals = internals;
        _active = false;
        _minimized=false;

        for(Tool t : _internals)
        {
            t.move(_x+t.X(), _y+t.Y()+ _labelH);
        }
    }

    public void update(float mx, float my, boolean[] down)
    {
        if(_active) {
            super.update(mx, my, down);
            if(_closeButton.Clicked()){_active=false;}
            if(_minButton.Clicked()&&!_reset){_minimized=!_minimized;}

            if (_hovering && down[_activator]) {
                int deltaX = (int) (mx - _oldMouseX);
                int deltaY = (int) (my - _oldMouseY);
                shift(deltaX, deltaY);
                _bodybox.setX(_x);
                _bodybox.setY(_y + _labelH);
                _closeButton.shift(deltaX, deltaY);
                _minButton.shift(deltaX, deltaY);
                for (Tool t : _internals) {
                    t.shift(deltaX, deltaY);
                }
            }
            _oldMouseX = mx;
            _oldMouseY = my;
            _closeButton.update(mx,my,down);
            _minButton.update(mx,my,down);
            if(!_minimized) {
                for (Tool t : _internals) {
                    t.update(mx, my, down);
                }
            }

        }
    }

    public void render(Graphics g)
    {
        if(_active) {
            super.render(g);

            if(!_minimized) {
                _img.draw(_bodybox.getX(), _bodybox.getY(), _w, _bodyH);
                g.drawRect(_bodybox.getX(), _bodybox.getY(), _w, _bodyH);
                for (Tool t : _internals) {
                    t.render(g);
                }
            }
            _closeButton.render(g);
            _minButton.render(g);
        }
    }

    public void Activate(boolean active)
    {
        _active=active;
    }
    public void Minimize(boolean min)
    {
        _minimized=min;
    }
}
