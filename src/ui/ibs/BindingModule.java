package ui.ibs;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.w3c.dom.*;

public class BindingModule {
    String _name;
    Document _doc;
    public BindingModule(String name, Document doc)
    {
        _name=name;
        _doc=doc;
    }

    public void render(GameContainer game, Graphics g) {
        g.drawString(_name, 500, 500);
    }
}
