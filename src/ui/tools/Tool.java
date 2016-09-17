package ui.tools;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Tool
{
    protected Image _img;
    protected Image _plain;
    protected Image _hover;
    protected Image _click;

    protected Font _font;
    protected String _txt;
    protected int _x, _y, _w, _h, _mx, _my;
    protected int _activator;
    protected Rectangle _clickbox;
    protected Boolean _hovering;

    public Tool(Font font, String text, int activator, int x, int y, int mx, int my)
    {
        try {
            _plain = new Image("res/ui/button.png");
            _hover = new Image("res/ui/hover.png");
            _click = new Image("res/ui/click.png");
            _img=_plain;
        } catch (SlickException e) {
            e.printStackTrace();
        }

        _font=font;
        _txt=text;
        _activator=activator;
        _x=x;
        _y=y;
        _mx=mx;
        _my=my;
        _hovering = false;
    }

    public void update(float mx, float my, boolean[] down)
    {
        if(_clickbox.contains(mx,my)){
            _hovering = true;
            _img=_hover;
        }
        else if(_hovering&&!down[_activator]){
            _hovering=false;
            _img=_plain;
        }
        if(_hovering&&down[_activator]) {
            _img=_click;
        }
    }

    public void render(Graphics g)
    {
        _img.draw(_clickbox.getX(),_clickbox.getY(),_clickbox.getWidth(),_clickbox.getHeight());
        g.drawRect(_clickbox.getX(),_clickbox.getY(),_clickbox.getWidth(),_clickbox.getHeight());
        _font.drawString(_x+_mx,_y+_my,_txt);
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
