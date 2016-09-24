package subbattle;

import Physics.Point;
import engine.Module;
import engine.XMLHandler;
import org.newdawn.slick.*;
import ui.tools.Button;
import ui.tools.Modal;
import ui.tools.TextInput;
import ui.tools.Tool;

import java.util.HashMap;

import static engine.Game.imagemap;

public class Init extends Module
{
    Vessel _vessel;
	public Init(XMLHandler xmlh, Input input)
	{
        super(input);
        HashMap<String, Tool> internals = new HashMap<>();
        internals.put("UiButton", new Button(imagemap.get("Standard"), _font, "MAIN MENU", Input.MOUSE_LEFT_BUTTON, 50,50,25,10, "UI"));
        internals.put("MfButton", new Button(imagemap.get("Standard"), _font, "MARKET FLOW", Input.MOUSE_LEFT_BUTTON, 50,100,25,10, "MarketFlow"));
        internals.put("MeButton", new Button(imagemap.get("Standard"), _font, "MY ESTATE", Input.MOUSE_LEFT_BUTTON, 50,150,25,10, "MyEstate"));
        _menumodal = new Modal(imagemap.get("Standard"), _font, "MAIN MENU", Input.MOUSE_LEFT_BUTTON, 300, 300, 25, 10, 400, 500, internals);

        _vessel = xmlh.processSubBattle("A1");

	}

    @SuppressWarnings("unused")
	public void update(GameContainer game, int count)
	{
        super.update(game, count);

        _vessel.update(count);
	}
    @SuppressWarnings("unused")
    public void tick(int count)
    {
        _vessel.tick(count);
    }
    @SuppressWarnings("unused")
	public void render(GameContainer game, Graphics g)
	{
        super.render(game,g);
        g.setColor(Color.orange);
        g.drawString("SubBattle", 100, 10);

        _vessel.render(game, g);
	}
}
