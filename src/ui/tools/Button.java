package ui.tools;

import org.lwjgl.Sys;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public class Button extends Tool
{
    private String _action;
    private boolean _clicked;

    public Button(Image[] imgset, int activator, int x, int y, int w, int h, String action)
    {
        super(imgset, activator, x, y, w, h);
        _action=action;
        _clicked=false;
    }

    public Button(Image[] imgset, Font font, String text, int activator, int x, int y, int marginx, int marginy, String action)
    {
        super(imgset, font, text, activator, x, y, marginx, marginy);
        _action=action;
        _clicked=false;
    }

    public void update(float mx, float my, boolean[] down)
    {
        super.update(mx,my,down);
        if(_hovering&&down[_activator]){
            if(_action!=null)engine.Game.buttonmap.press(_action,_activator);
            _clicked=true;
        }
        else
        {
            if(_action!=null)engine.Game.buttonmap.reset(_activator);
            _clicked=false;
        }
    }

    public boolean Clicked()
    {
        return _clicked;
    }
    public void reinit(){ _hovering=false;_clicked=false;}
}
