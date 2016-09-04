package engine;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
	Game instance;

	int x;
	int y;

	public Mouse(Game instance)
	{
		this.instance = instance;
		instance.addMouseListener(this);
		instance.addMouseWheelListener(this);
		instance.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent me)
	{
		int xWorld = me.getX()-instance.mf.map.OffsetX();
		int yWorld = me.getY()-instance.mf.map.OffsetY();
		//TODO: Remove this.. temporary
		for(Map.Entry<marketflow.Entity, Rectangle> e : Game.clickables.entrySet())
		{
			if(e.getValue().contains(xWorld, yWorld))
			{
				e.getKey().print();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent me)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent me)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent me)
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent me)
	{

	}

	@Override
	public void mouseMoved(MouseEvent me)
	{
		x = me.getX();
		y = me.getY();
	}


	public void x(int setX){x=setX;}
	public int x(){return x;}
	public void y(int setY){y=setY;}
	public int y(){return y;}
}