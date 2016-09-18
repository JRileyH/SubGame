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

        case "UI":
            if(Game.state!=Game.State.UI||!_down[button]){
                Game.setState(Game.State.UI);
                closeAllModals();
                Game.ui.reinit();
            }
            break;
        case "MarketFlow":
            if(Game.state!=Game.State.MARKETFLOW||!_down[button]){
                Game.setState(Game.State.MARKETFLOW);
                closeAllModals();
                Game.mf.reinit();
            }
            break;
        case "SubBattle":
            if(Game.state!=Game.State.SUBBATTLE||!_down[button]){
                Game.setState(Game.State.SUBBATTLE);
                closeAllModals();
                Game.sb.reinit();
            }
            break;
        case "MyEstate":
            if(Game.state!=Game.State.MYESTATE||!_down[button]){
                Game.setState(Game.State.MYESTATE);
                closeAllModals();
                Game.me.reinit();
            }
            break;
        case "Nothing":
            if(!_down[button])System.out.println("Does Nothing");
            break;
        }
        _down[button]=true;
	}
	private void closeAllModals(){
        Game.mf._menumodal.Activate(false);
        Game.sb._menumodal.Activate(false);
        Game.me._menumodal.Activate(false);
    }
	public void reset(int button){
        _down[button]=false;
    }
}
