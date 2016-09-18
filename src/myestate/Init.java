package myestate;

import engine.Module;
import engine.XMLHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import ui.tools.Button;
import ui.tools.Modal;
import ui.tools.TextInput;
import ui.tools.Tool;

import java.util.HashMap;

import static engine.Game.imagemap;

public class Init extends Module
{
	public Init(XMLHandler xmlh, Input input)
	{
        super(input);
        HashMap<String, Tool> internals = new HashMap<>();
        internals.put("UiButton", new Button(imagemap.get("Standard"), _font, "MAIN MENU", Input.MOUSE_LEFT_BUTTON, 50,50,25,10, "UI"));
        internals.put("MfButton", new Button(imagemap.get("Standard"), _font, "MARKET FLOW", Input.MOUSE_LEFT_BUTTON, 50,100,25,10, "MarketFlow"));
        internals.put("SbButton", new Button(imagemap.get("Standard"), _font, "SUB BATTLE", Input.MOUSE_LEFT_BUTTON, 50,150,25,10, "SubBattle"));
        _menumodal = new Modal(imagemap.get("Standard"), _font, "MAIN MENU", Input.MOUSE_LEFT_BUTTON, 300, 300, 25, 10, 400, 500, internals);

		xmlh.processMyEstate();
	}

    @SuppressWarnings("unused")
	public void update(GameContainer game, int count)
	{
        super.update(game, count);
	}
    @SuppressWarnings("unused")
    public void tick(int count) {

    }
    @SuppressWarnings("unused")
	public void render(GameContainer game, Graphics g)
	{
        super.render(game, g);
		g.setColor(Color.orange);
		g.drawString("MyEstate", 100, 10);
	}
}
