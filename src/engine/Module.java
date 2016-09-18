package engine;

import org.newdawn.slick.*;
import ui.tools.Modal;

public class Module
{
    protected Font _font;
    protected Modal _menumodal = null;
    protected Input _input;

    public Module(Input input)
    {
        try {
            _font = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png").getScaledCopy(0.5f),16, 32), ' ');
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _input=input;
    }

    public void reinit()
    {

    }

    public void update(GameContainer game, int count)
    {//Fast Update Loop
        if(_menumodal!=null) {
            if (_menumodal.Active()) {
                boolean[] buttonStates = new boolean[5];
                buttonStates[Input.MOUSE_LEFT_BUTTON] = _input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
                buttonStates[Input.MOUSE_RIGHT_BUTTON] = _input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
                buttonStates[Input.MOUSE_MIDDLE_BUTTON] = _input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
                buttonStates[3] = _input.isMouseButtonDown(3);
                buttonStates[4] = _input.isMouseButtonDown(4);
                _menumodal.update(_input.getMouseX(), _input.getMouseY(), buttonStates);
            }
        }
    }

    private int _time = 0;
    public void tick(int count)
    {//One Second Update Loop
        if(_menumodal!=null) {
            if (!_menumodal.Active()) {
                _time++;
            }
        }
    }

    public void render(GameContainer game, Graphics g)
    {
        if(_menumodal!=null) {
            _menumodal.render(g);
        }
    }
}
