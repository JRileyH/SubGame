package engine;

import ui.tools.TextInput;

import java.util.HashMap;
import java.util.Map;

public class KeyMap
{
	private Map<Integer, String> _keyMap;
	private Map<Integer, Boolean> _keyFlags;
	private XMLHandler _xmlh;
    private TextInput _textinput;

	KeyMap(XMLHandler xmlh)
	{
		_xmlh = xmlh;
		_keyMap = new HashMap<>();
		_keyFlags = new HashMap<>();
		mapKeys(Game.state);
        _textinput = null;
	}
	
	void mapKeys(Game.State state)
	{//Initialized each module with its stored key bindings from XML
		switch (state)
		{
		case UI:
			_xmlh.setKeyMap("data/userinterface/InputMap.xml", _keyMap, _keyFlags);
			break;
		case MARKETFLOW:
			_xmlh.setKeyMap("data/marketflow/InputMap.xml", _keyMap, _keyFlags);
			break;
		case SUBBATTLE:
			_xmlh.setKeyMap("data/subbattle/InputMap.xml", _keyMap, _keyFlags);
			break;
		case MYESTATE:
			_xmlh.setKeyMap("data/myestate/InputMap.xml", _keyMap, _keyFlags);
			break;
		}
	}

	public void setTextInput(TextInput ti)
    {
        _textinput =ti;
    }

	void press(int c, boolean d)
	{//registers a key action d: true=pressed, false=released
		if(_keyMap.containsKey(c)&& _textinput ==null)
		{
			switch(_keyMap.get(c))
			{

            case "Menu":
                switch (Game.state){
                    case UI:
                        Game.ui._menumodal.Activate(true);
                        break;
                    case MARKETFLOW:
                        Game.mf._menumodal.Activate(true);
                        break;
                    case SUBBATTLE:
                        Game.sb._menumodal.Activate(true);
                        break;
                    case MYESTATE:
                        Game.me._menumodal.Activate(true);
                        break;
                }
                break;

			case "TackLeft":
				Game.mf.Player().tackLeft(d);
				break;
			case "TackRight":
				Game.mf.Player().tackRight(d);
				break;
			case "Forward":
				if(!_keyFlags.get(c)){Game.mf.Player().shiftUp();}
				break;
			case "Reverse":
				if(!_keyFlags.get(c)){Game.mf.Player().shiftDown();}
				break;
			case "ToggleLight":
				if(!_keyFlags.get(c)){Game.mf.Player().toggleLight();}
				break;

			case "Report":
				if(d){Game.mf.Report();}
				break;

            case "Station0":
                if(!d) Game.sb._vessel.moveCrew(0,0);
                break;
            case "Station1":
                if(!d) Game.sb._vessel.moveCrew(0,1);
                break;
            case "Station2":
                if(!d) Game.sb._vessel.moveCrew(0,2);
                break;
            case "Station3":
                if(!d) Game.sb._vessel.moveCrew(0,3);
                break;
            case "Station4":
                if(!d) Game.sb._vessel.moveCrew(0,4);
                break;
            case "Station5":
                if(!d) Game.sb._vessel.moveCrew(0,5);
                break;
            case "Station6":
                if(!d) Game.sb._vessel.moveCrew(0,6);
                break;
            case "Station7":
                if(!d) Game.sb._vessel.moveCrew(0,7);
                break;
            case "Station8":
                if(!d) Game.sb._vessel.moveCrew(0,8);
                break;
            case "Station9":
                if(!d) Game.sb._vessel.moveCrew(0,9);
                break;
			}
			_keyFlags.put(c, d);
		}
		else if(_textinput!=null)
        {
            _textinput.processKey(c, d);
        }
	}
}
