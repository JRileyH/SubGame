package ui.tools;


import engine.Game;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.awt.event.KeyListener;

public class TextInput extends Tool
{
    private boolean _active;
    private Input _input;

    public TextInput(Image[] imgset, Font font, String text, int activator, int x, int y, int mx, int my, int h) {
        super(imgset, font, text, activator, x, y, mx, my);
        _h=h;
        _clickbox.setWidth((my*2)+h);
        _active=false;
        _input = new Input(0);
        _input.addKeyListener(new org.newdawn.slick.KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                System.out.println(c);
            }

            @Override
            public void keyReleased(int i, char c) {

            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return false;
            }

            @Override
            public void inputEnded() {

            }

            @Override
            public void inputStarted() {

            }
        });
        _input.pause();
    }

    public void update(float mx, float my, boolean[] down)
    {
        super.update(mx, my, down);
        if (_hovering && down[_activator]) {
            _active=true;
            Game.keymap.Disable(true);
            _input.resume();
        }
        else if(down[0]||down[1]&&!_hovering)
        {
            _active=false;
            Game.keymap.Disable(false);
            _input.pause();
        }
        if(_active)
        {
            if(_input.isKeyDown(32))
            {
                System.out.println("fart");
            }
        }
    }
}
