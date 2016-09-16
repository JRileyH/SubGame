package engine;

import java.util.HashMap;
import java.util.Map;

public class ButtonMap
{
	private Map<String, Boolean> _buttonFlags;
	ButtonMap()
	{
		_buttonFlags = new HashMap<>();
	}
	
	public void press(String action)
	{//registers a button action d: true=pressed, false=released
        switch(action)
        {
        case "StartGame":
            if(Game.state!=Game.State.MARKETFLOW)Game.setState(Game.State.MARKETFLOW);
            break;
        }
	}
}
