package ui;

import engine.XMLHandler;
import org.newdawn.slick.*;
import ui.ibs.BindingSystem;

public class Init
{
    BindingSystem _ibs;
    Font _font;
	public Init(XMLHandler xmlh)
	{
        try {
            _font = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png"),37, 37), 'a');
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _ibs = new BindingSystem(xmlh);
	}
    @SuppressWarnings("unused")
	public void update(int count)
	{

	}
    @SuppressWarnings("unused")
    public void tick(int count)
    {

    }
    @SuppressWarnings("unused")
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("UserInterface", 100, 10);
        _ibs.render(game,g);

        _font.drawString(500,500,"abc");
	}
}
