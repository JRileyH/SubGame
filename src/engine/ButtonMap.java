package engine;

import org.newdawn.slick.Input;

import java.util.HashMap;
import java.util.Map;

public class ButtonMap
{
    private boolean[] _down;

	ButtonMap()
	{
        _down=new boolean[5];
	}
	
	public void press(String action, int button)
	{
        switch(action)
        {
        case "StartGame":
            if(Game.state!=Game.State.MARKETFLOW)Game.setState(Game.State.MARKETFLOW);
            break;
        case "Nothing":
            if(!_down[button])System.out.println("Does Nothing");
            break;
        }
        _down[button]=true;
	}
	public void reset(int button){
        _down[button]=false;
    }
}
