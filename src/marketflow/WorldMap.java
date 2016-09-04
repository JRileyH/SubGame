package marketflow;

import engine.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Riggy on 9/4/2016.
 */
public class WorldMap
{
    public BufferedImage mapImg;
    public int _width;
    public int _height;
    public int _offsetX;
    public int _offsetY;
    public int _edgeX;
    public int _edgeY;
    Rectangle scrollUp = new Rectangle(0, 0, Game.WIDTH, 50);
    Rectangle scrollLeft = new Rectangle(0, 0, 50, Game.HEIGHT);
    Rectangle scrollRight = new Rectangle(Game.WIDTH-50, 0, 50, Game.HEIGHT);
    Rectangle scrollDown = new Rectangle(0, Game.HEIGHT-50, Game.WIDTH, 50);
    		/*if(me.getX() == 0)
		{
			System.out.println("IF YOUR HURTIN LAY IT ALL ON ME");
		}

		if(me.getX() == Game.WIDTH)
		{
			Game.mf.mapOffsetX++;
			System.out.println("that not where ur friend belongs!!");
		}*/



    public WorldMap(String path, int initX, int initY)
    {
        mapImg = Game.gfx.load(path);
        _width=mapImg.getWidth();
        _height=mapImg.getHeight();
        _offsetX=-initX;
        _offsetY=-initY;
        _edgeX = Game.WIDTH-_width;
        _edgeY = Game.HEIGHT-_height;
    }

    public void update(int count)
    {
        System.out.println("TITS!");
        if(scrollDown.contains(Game.mouse.x(), Game.mouse.y()))
        {
            System.out.println("bobsocks!");
            shift(0, -5);
        }
    }

    public BufferedImage Img()
    {
        return mapImg;
    }

    public int OffsetX(){return _offsetX;}//Gets the Offset in the X direction
    public void OffsetX(int x){_offsetX=x;}//Gets the Offset in the Y direction
    public int OffsetY(){return _offsetY;}//Sets the Offset in the X direction
    public void OffsetY(int y){_offsetY=y;}//Sets the Offset in the Y direction
    public void move(int x, int y){_offsetX=x;_offsetY=y;}//Sets both Offsets at once
    public void shift(int x, int y){_offsetX+=x;_offsetY+=y;}//Increments both Offsets
    public int EdgeX(){return _edgeX;}//Gets the far right edge of the map
    public int EdgeY(){return _edgeY;}//Gets the bottom edge of the map
}
