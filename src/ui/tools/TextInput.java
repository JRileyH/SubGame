package ui.tools;

import engine.Game;
import org.newdawn.slick.*;

public class TextInput extends Tool
{
    private boolean _active;
    private boolean _blink;
    private boolean _atmax;
    private int _blinkcount;
    private int _cursorposition;
    private int _charwidth;
    private int _max;

    public TextInput(Image[] imgset, Font font, String text, int activator, int x, int y, int mx, int my, int max) {
        super(imgset, font, text, activator, x, y, mx, my);
        _charwidth=_font.getWidth(" ");
        _max=max;
        _w=(mx*2)+_charwidth*max;
        _clickbox.setWidth(_w);
        _active=false;
        _blink=false;
        _blinkcount=0;
        _cursorposition=_txt.length();
    }

    public void update(float mx, float my, boolean[] down)
    {
        super.update(mx, my, down);
        if(_blinkcount%30==0)
        {
            _blinkcount=0;
            _blink=!_blink;
        }
        _blinkcount++;

        if (_hovering && down[_activator]) {
            _active=true;
            Game.keymap.setTextInput(this);
        }
        else if(down[0]||down[1]&&!_hovering)
        {
            _active=false;
            Game.keymap.setTextInput(null);
        }
        if (_cursorposition >= _max) {
            _atmax = true;
        } else if (_atmax) {
            _atmax = false;
        }
    }

    public void render(Graphics g)
    {
        super.render(g);
        if(_active&&_blink&&!_atmax)_font.drawString(_x+_mx+(_charwidth*_cursorposition),_y+_my,"|");
    }

    public void processKey(int c, boolean d)
    {
        String key = Input.getKeyName(c);
        int keylen = key.length();
        if(!d) {
            int txtlen = _txt.length();

            if(key.equals("LEFT"))
            {
                if(_cursorposition>0)_cursorposition--;
            }
            else if(key.equals("RIGHT"))
            {
                if(_cursorposition<txtlen)_cursorposition++;
            }
            else if(key.equals("BACK")){
                if(txtlen>0){
                    String beg = _txt.substring(0,_cursorposition-1);
                    String end = _txt.substring(_cursorposition,txtlen);
                    _txt = beg+end;
                    _cursorposition--;
                }
            }
            else if(key.equals("SPACE")&&!_atmax)
            {
                String beg = _txt.substring(0,_cursorposition);
                String end = _txt.substring(_cursorposition,txtlen);
                _txt=beg+" "+end;
                _cursorposition++;
            }
            else if(keylen==1&&!_atmax) {
                String beg = _txt.substring(0,_cursorposition);
                String end = _txt.substring(_cursorposition,txtlen);
                _txt=beg+key+end;
                _cursorposition++;
            }
        }
    }

    public String Text(){return _txt;}
}
