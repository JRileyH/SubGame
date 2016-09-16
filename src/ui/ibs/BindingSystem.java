package ui.ibs;

import engine.XMLHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class BindingSystem
{
    private List<BindingModule> _modules;
    BindingModule _active = null;
    public BindingSystem(XMLHandler xmlh)
    {
        _modules = new ArrayList<>();
        _modules.add(new BindingModule("MarketFlow",xmlh.read("data/marketflow/InputMap.xml")));
        _modules.add(new BindingModule("SubBattle",xmlh.read("data/marketflow/InputMap.xml")));
        _modules.add(new BindingModule("MyEstate",xmlh.read("data/marketflow/InputMap.xml")));
    }

    public void render(GameContainer game, Graphics g)
    {
        if(_active!=null)
        {
            _active.render(game,g);
        }
    }
}
