package engine;

import java.util.HashMap;
import java.util.Map;

class MouseMap
{
    private Map<Integer, String> _mouseMap;
    private Map<Integer, Boolean> _mouseFlags;
    private XMLHandler _xmlh;
    MouseMap(XMLHandler xmlh)
    {
        _xmlh = xmlh;
        _mouseMap = new HashMap<>();
        _mouseFlags = new HashMap<>();
        mapMouse(Game.state);
    }

    void mapMouse(Game.State state)
    {//Initialized each module with its stored key bindings from XML
        switch (state)
        {
            case UI:
                _xmlh.setMouseMap("data/userinterface/InputMap.xml", _mouseMap, _mouseFlags);
                break;
            case MARKETFLOW:
                _xmlh.setMouseMap("data/marketflow/InputMap.xml", _mouseMap, _mouseFlags);
                break;
            case SUBBATTLE:
                _xmlh.setMouseMap("data/subbattle/InputMap.xml", _mouseMap, _mouseFlags);
                break;
            case MYESTATE:
                _xmlh.setMouseMap("data/myestate/InputMap.xml", _mouseMap, _mouseFlags);
                break;
        }
    }
    void press(int b, boolean d, int x, int y)
    {
        if(_mouseMap.containsKey(b))
        {
            switch(_mouseMap.get(b))
            {
                case "GoTo":
                    if(!_mouseFlags.get(b)){Game.mf.Player().GoTo(x,y);}
                    break;
            }
            _mouseFlags.put(b, d);
        }
        else
        {
            System.out.println("MouseButtonNotBound: "+b);
        }
    }
}