package ui.tools;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

public class Modal extends Tool
{
    private int _labelH, _bodyH;
    private ArrayList<Tool> _internals;
    private Rectangle _bodybox;
    private float _oldMouseX, _oldMouseY;

    public Modal(Font font, String text, int activator, int x, int y, int marginx, int marginy, int w, int h, ArrayList<Tool> internals)
    {
        super(font, text, activator, x, y, marginx, marginy);

        _w=w;
        _h=h;
        _labelH = (marginy*2)+font.getLineHeight();
        _bodyH = _h-_labelH;
        _clickbox = new Rectangle(_x, _y, _w, _labelH);
        _bodybox = new Rectangle(_x, _y+_labelH, _w, _bodyH);
        _internals = internals;
        for(Tool t : _internals)
        {
            t.move(_x+t.X(), _y+t.Y()+ _labelH);
        }
    }

    public void update(float mx, float my, boolean[] down)
    {
        super.update(mx,my,down);
        if(_hovering&&down[_activator]){
            int deltaX = (int)(mx-_oldMouseX);
            int deltaY = (int)(my-_oldMouseY);
            shift(deltaX, deltaY);
            _bodybox.setX(_x);
            _bodybox.setY(_y+ _labelH);
            for(Tool t : _internals)
            {
                t.shift(deltaX, deltaY);
            }
        }
        _oldMouseX=mx;
        _oldMouseY=my;
        for(Tool t : _internals)
        {
            t.update(mx, my, down);
        }
    }

    public void render(Graphics g)
    {
        super.render(g);

        _img.draw(_bodybox.getX(),_bodybox.getY(),_w,_labelH);
        g.drawRect(_bodybox.getX(),_bodybox.getY(),_w,_bodyH);

        for(Tool t : _internals)
        {
            t.render(g);
        }
    }
}
