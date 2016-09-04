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
		int x = me.getX()-instance.mf.map.OffsetX();
		int y = me.getY()-instance.mf.map.OffsetY();
		//TODO: Remove this.. temporary
		for(Map.Entry<marketflow.Entity, Rectangle> e : Game.clickables.entrySet())
		{
			if(e.getValue().contains(x, y))
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
		/*if(me.getX() == 0)
		{
			System.out.println("IF YOUR HURTIN LAY IT ALL ON ME");
		}

		if(me.getX() == Game.WIDTH)
		{
			Game.mf.mapOffsetX++;
			System.out.println("that not where ur friend belongs!!");
		}

		Rectangle scrollUp = new Rectangle(0, 0, Game.WIDTH, 50);
		Rectangle scrollLeft = new Rectangle(0, 0, 50, Game.HEIGHT);
		Rectangle scrollRight = new Rectangle(Game.WIDTH-50, 0, 50, Game.HEIGHT);
		Rectangle scrollDown = new Rectangle(0, Game.HEIGHT-50, Game.WIDTH, 50);*/
	}
}