package ui.tools;

import org.newdawn.slick.Font;
import org.newdawn.slick.geom.Rectangle;

public class Button extends Tool
{
    private String _action;

    public Button(Font font, String text, int activator, int x, int y, int marginx, int marginy, String action)
    {
        super(font, text, activator, x, y, marginx, marginy);
        _action=action;
        _w=(marginx*2)+font.getWidth(text);
        _h=(marginy*2)+font.getLineHeight();
        _mx=marginx;
        _my=marginy;
        _clickbox = new Rectangle(x,y,_w,_h);
    }
    public void update(float mx, float my, boolean[] down)
    {
        super.update(mx,my,down);
        if(_hovering&&down[_activator]){
            engine.Game.buttonmap.press(_action,_activator);
        }
        else
        {
            engine.Game.buttonmap.reset(_activator);
        }
    }
}
