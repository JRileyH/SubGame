package ui.tools;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import static engine.ImageMap.*;

public class Tool
{
    protected Image _img;
    protected Image[] _imgset;

    protected Font _font;
    protected String _txt;
    protected int _x, _y, _w, _h, _mx, _my;
    protected int _activator;
    protected Rectangle _clickbox;
    protected Boolean _hovering;
    protected boolean _clicking;

    public Tool(Image[] imgset, int activator, int x, int y, int w, int h)
    {
        _img=imgset[PLAIN];
        _imgset=imgset;
        _font=null;
        _txt=null;
        _activator=activator;
        _x=x;
        _y=y;
        _w=w;
        _h=h;
        _clickbox = new Rectangle(x,y,_w,_h);
        _hovering = false;
        _clicking =false;
    }

    public Tool(Image[] imgset, Font font, String text, int activator, int x, int y, int mx, int my)
    {
        _img=imgset[PLAIN];
        _imgset=imgset;
        _font=font;
        _txt=text;
        _activator=activator;
        _x=x;
        _y=y;
        _mx=mx;
        _my=my;
        _w=(mx*2)+font.getWidth(text);
        _h=(my*2)+font.getLineHeight();
        _clickbox = new Rectangle(x,y,_w,_h);
        _hovering = false;
        _clicking = false;
    }

    public void update(float mx, float my, boolean[] down)
    {
        if(_clickbox.contains(mx,my)){
            if(!_hovering) {
                _hovering = true;
                _img = _imgset[HOVER];
            }
        }
        else if(_hovering&&!down[_activator]){
            _hovering=false;
            _img=_imgset[PLAIN];
        }
        if(_hovering&&down[_activator]) {
            if(!_clicking) {
                _img = _imgset[CLICK];
                _clicking = true;
            }
        }
        else if(_clicking)
        {
            _clicking =false;
            _img=_imgset[PLAIN];
        }
    }

    public void render(Graphics g)
    {
        _img.draw(_clickbox.getX(),_clickbox.getY(),_clickbox.getWidth(),_clickbox.getHeight());
        g.drawRect(_clickbox.getX(),_clickbox.getY(),_clickbox.getWidth(),_clickbox.getHeight());
        if(_font!=null)_font.drawString(_x+_mx,_y+_my,_txt);
    }

    public int Activator(){return _activator;}
    public int X(){return _x;}
    public void X(int x){_x=x;_clickbox.setX(x);}
    public int Y(){return _y;}
    public void Y(int y){_y=y;_clickbox.setY(y);}
    public void move(int x, int y){X(x);Y(y);}
    public void shift(int x, int y){
        _x+=x;_clickbox.setX(_clickbox.getX()+x);
        _y+=y;_clickbox.setY(_clickbox.getY()+y);
    }
}
