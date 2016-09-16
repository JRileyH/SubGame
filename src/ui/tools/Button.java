package ui.tools;


import org.newdawn.slick.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Button
{
    private Image _img;
    private Image _plain;
    private Image _hover;
    private Image _click;
    private Font _font;
    private String _txt;
    private String _action;
    private int _x, _y, _w, _h, _mx, _my;
    private Rectangle _clickbox;

    public Button(Font font, String text, String action, int x, int y, int marginx, int marginy)
    {
        try {
            _plain = new Image("res/ui/button.png");
            _hover = new Image("res/ui/hover.png");
            _click = new Image("res/ui/click.png");
            _img=_plain;
            _font = font;
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _txt=text;
        _action=action;
        _x=x;
        _y=y;
        _w=(marginx*2)+font.getWidth(text);
        _h=(marginy*2)+font.getLineHeight();
        _mx=marginx;
        _my=marginy;
        _clickbox = new Rectangle(x,y,_w,_h);
    }
    public void update(float mx, float my, Boolean down)
    {
        boolean hover = false;
        if(_clickbox.contains(mx,my)){
            _img=_hover;
            hover = true;
        }
        else{
            _img=_plain;
        }
        if(hover&&down){
            _img=_click;
            engine.Game.buttonmap.press(_action);
        }
    }
    public void render(Graphics g)
    {
        _img.draw(_x,_y,_w,_h);
        _font.drawString(_x+_mx,_y+_my,_txt);
        g.drawRect(_clickbox.getX(),_clickbox.getY(),_clickbox.getWidth(),_clickbox.getHeight());
    }
}
