package ui;

import engine.Module;
import engine.XMLHandler;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Text;
import ui.ibs.BindingSystem;
import ui.tools.Button;
import ui.tools.Modal;
import ui.tools.TextInput;
import ui.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

import static engine.Game.imagemap;
import static org.newdawn.slick.Game.*;

public class Init extends Module
{
    TextInput _name;
    public Button _start;
	public Init(XMLHandler xmlh, Input input)
	{
        super(input);
        _name = new TextInput(imagemap.get("Standard"), _font, "", Input.MOUSE_LEFT_BUTTON,50,50,25,10,16);
        _start = new Button(imagemap.get("Standard"), _font, "StartGame", Input.MOUSE_LEFT_BUTTON, 50,100,25,10, "MarketFlow");
	}

    public void reinit()
    {
        _start.reinit();
    }

    @SuppressWarnings("unused")
    public void update(GameContainer game, int count)
    {
        boolean[] buttonStates = new boolean[5];
        buttonStates[Input.MOUSE_LEFT_BUTTON]=_input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        buttonStates[Input.MOUSE_RIGHT_BUTTON]=_input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
        buttonStates[Input.MOUSE_MIDDLE_BUTTON]=_input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
        buttonStates[3]=_input.isMouseButtonDown(3);
        buttonStates[4]=_input.isMouseButtonDown(4);

        _name.update(_input.getMouseX(),_input.getMouseY(), buttonStates);
        _start.update(_input.getMouseX(),_input.getMouseY(), buttonStates);
    }
    @SuppressWarnings("unused")
    public void tick(int count) {

    }
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("UserInterface", 100, 10);
        _name.render(g);
        _start.render(g);
	}
}
