package ui;

import engine.Module;
import engine.XMLHandler;
import org.newdawn.slick.*;
import ui.tools.Button;
import ui.tools.TextInput;

import static engine.Game.imagemap;

public class Init extends Module
{
    TextInput _name;
    public Button _startMF;
    public Button _startSB;
    public Button _startME;
	public Init(XMLHandler xmlh, Input input)
	{
        super(input);
        _name = new TextInput(imagemap.get("Standard"), _font, "", Input.MOUSE_LEFT_BUTTON,50,50,25,10,16);
        _startMF = new Button(imagemap.get("Standard"), _font, "MARKET FLOW", Input.MOUSE_LEFT_BUTTON, 50,100,25,10, "MarketFlow");
        _startSB = new Button(imagemap.get("Standard"), _font, "SUB BATTLE", Input.MOUSE_LEFT_BUTTON, 50,150,25,10, "SubBattle");
        _startME = new Button(imagemap.get("Standard"), _font, "MY ESTATE", Input.MOUSE_LEFT_BUTTON, 50,200,25,10, "MyEstate");
	}

    public void reinit()
    {
        _startMF.reinit();
        _startSB.reinit();
        _startME.reinit();
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
        _startMF.update(_input.getMouseX(),_input.getMouseY(), buttonStates);
        _startSB.update(_input.getMouseX(),_input.getMouseY(), buttonStates);
        _startME.update(_input.getMouseX(),_input.getMouseY(), buttonStates);

    }
    @SuppressWarnings("unused")
    public void tick(int count) {

    }
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("UserInterface", 100, 10);
        _name.render(g);
        _startMF.render(g);
        _startSB.render(g);
        _startME.render(g);
	}
}
