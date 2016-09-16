package engine;

import java.util.HashMap;
import java.util.Map;

class KeyMap
{
	private Map<Integer, String> _keyMap;
	private Map<Integer, Boolean> _keyFlags;
	private XMLHandler _xmlh;

	KeyMap(XMLHandler xmlh)
	{
		_xmlh = xmlh;
		_keyMap = new HashMap<>();
		_keyFlags = new HashMap<>();
		mapKeys(Game.state);
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
	
	void press(int c, boolean d)
	{//registers a key action d: true=pressed, false=released
		if(_keyMap.containsKey(c))
		{
			switch(_keyMap.get(c))
			{
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
			
			case "SwitchToUI":
			    System.out.println("OMG");
				if(Game.state!=Game.State.UI)Game.setState(Game.State.UI);
				break;
			case "SwitchToMF":
				if(Game.state!=Game.State.MARKETFLOW)Game.setState(Game.State.MARKETFLOW);
				break;
			case "SwitchToSB":
				if(Game.state!=Game.State.SUBBATTLE)Game.setState(Game.State.SUBBATTLE);
				break;
			case "SwitchToME":
				if(Game.state!=Game.State.MYESTATE)Game.setState(Game.State.MYESTATE);
				break;

			case "Report":
				if(d){Game.mf.Report();}
				break;
			}
			_keyFlags.put(c, d);
		}
		else
		{
			System.out.println("KeyNotBound: "+c);
		}
	}
}
