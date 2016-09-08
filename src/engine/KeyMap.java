package engine;

import java.util.HashMap;
import java.util.Map;

public class KeyMap
{
	private Map<Integer, String> _keymap;
	private Map<Integer, Boolean> _keyFlags;
	private XMLHandler _xmlh;
	public KeyMap(XMLHandler xmlh)
	{
		_xmlh = xmlh;
		_keymap = new HashMap<>();
		_keyFlags = new HashMap<>();
		mapKeys(Game.state);
	}
	
	public void mapKeys(Game.State state)
	{//Initialized each module with its stored key bindings from XML
		switch (state)
		{
		case UI:
			_xmlh.setKeyMap("data/userinterface/KeyMap.xml", _keymap, _keyFlags);
			break;
		case MARKETFLOW:
			_xmlh.setKeyMap("data/marketflow/KeyMap.xml", _keymap, _keyFlags);
			break;
		case SUBBATTLE:
			_xmlh.setKeyMap("data/subbattle/KeyMap.xml", _keymap, _keyFlags);
			break;
		case MYESTATE:
			_xmlh.setKeyMap("data/myestate/KeyMap.xml", _keymap, _keyFlags);
			break;
		}
	}
	
	public void press(int c, boolean d)
	{//registers a key action d: true=pressed, false=released
		if(_keymap.containsKey(c))
		{
			switch(_keymap.get(c))
			{
			case "tackLeft":
				if(!_keyFlags.get(c)){Game.mf.Player().tackLeft();}
				break;
			case "tackRight":
				if(!_keyFlags.get(c)){Game.mf.Player().tackRight();}
				break;
			case "Forward":
				if(!_keyFlags.get(c)){Game.mf.Player().shiftUp();}
				break;
			case "Reverse":
				if(!_keyFlags.get(c)){Game.mf.Player().shiftDown();}
				break;
			
			case "SwitchToUI":
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
