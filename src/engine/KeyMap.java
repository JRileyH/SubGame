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
		_keymap = new HashMap<Integer, String>();
		_keyFlags = new HashMap<Integer, Boolean>();
		mapKeys(Game.State.UI);
	}
	
	public void mapKeys(Game.State state)
	{
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


	//TODO: Add interface for changing keybindings
	
	public void press(int c, boolean d)
	{
		if(_keymap.containsKey(c))
		{
			switch(_keymap.get(c))
			{
			case "scrollLeft":
				Game.mf.player.left(d);
				break;
			case "scrollUp":
				//Game.mf.player.up(d);
				if(!_keyFlags.get(c)){Game.mf.player.incSpeed();}
				break;
			case "scrollRight":
				Game.mf.player.right(d);
				break;
			case "scrollDown":
				//Game.mf.player.down(d);
				if(!_keyFlags.get(c)){Game.mf.player.decSpeed();}
				break;
			
			case "SwitchToUI":
				Game.state(Game.State.UI);
				break;
			case "SwitchToMF":
				Game.state(Game.State.MARKETFLOW);
				break;
			case "SwitchToSB":
				Game.state(Game.State.SUBBATTLE);
				Game.mf.Report();
				break;
			case "SwitchToME":
				Game.state(Game.State.MYESTATE);
				break;

				//default:
				//	Game.mf.player.newV().incMagnitude(-1);
			}
			_keyFlags.put(c, d);
		}
		else
		{
			System.out.println("KeyNotBound: "+c);
		}
	}
}
