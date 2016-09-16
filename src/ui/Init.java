package ui;

import engine.XMLHandler;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import ui.ibs.BindingSystem;
import ui.tools.Button;

public class Init
{
    //private BindingSystem _ibs;
    private Font _font_xl;
    private Font _font_lg;
    private Font _font;
    private Font _font_sm;
    private Font _font_xs;

    private Input _input;

    private Button _button;

	public Init(XMLHandler xmlh, Input input)
	{
        _input = input;

        try {
            _font_xl = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png").getScaledCopy(2f),64, 128), ' ');
            _font_lg = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png"),32, 64), ' ');
            _font = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png").getScaledCopy(0.5f),16, 32), ' ');
            _font_sm = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png").getScaledCopy(0.25f),8, 16), ' ');
            _font_xs = new SpriteSheetFont(new SpriteSheet(new Image("res/ui/font.png").getScaledCopy(0.125f),4, 8), ' ');
        } catch (SlickException e) {
            e.printStackTrace();
        }
        //_ibs = new BindingSystem(xmlh);
        _button = new Button(_font, "START GAME", "StartGame", 700,400,10,10);
	}
    @SuppressWarnings("unused")
	public void update(GameContainer game, int count)
	{
        _button.update(_input.getMouseX(),_input.getMouseY(), _input.isMouseButtonDown(0));
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

        _font_xl.drawString(100, 128, "String _boobs = \"HOT\";");
        _font_lg.drawString(100, 256, "The rain in Spain falls mainly on the plain.");
        _font.drawString(100, 320, "Can we get smaller?");
        _font_sm.drawString(100, 352, "I THINK WE CAN!");
        _font_xs.drawString(100, 368, "THIS IS JUST TOO MUCH.");

        _button.render(g);
	}
}
