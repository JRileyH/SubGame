package marketflow;

import engine.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Riggy on 9/4/2016.
 */
public class WorldMap
{
    public BufferedImage _img;
    public int _width;
    public int _height;
    public int _offsetX;
    public int _offsetY;
    public int _panX;
    public int _panY;
    public int _edgeX;
    public int _edgeY;
    Rectangle scrollBoxUp = new Rectangle(0, 0, Game.WIDTH, 50);
    Rectangle scrollBoxLeft = new Rectangle(0, 0, 50, Game.HEIGHT);
    Rectangle scrollBoxRight = new Rectangle(Game.WIDTH-50, 0, 50, Game.HEIGHT);
    Rectangle scrollBoxDown = new Rectangle(0, Game.HEIGHT-50, Game.WIDTH, 50);


    public WorldMap(String path, int initX, int initY)
    {
        _img = Game.gfx.load(path);
        _width=_img.getWidth();
        _height=_img.getHeight();
        _offsetX=-initX;
        _offsetY=-initY;
        _panX=0;
        _panY=0;
        _edgeX = Game.WIDTH-_width;
        _edgeY = Game.HEIGHT-_height;
    }

    public void update(int count)
    {
        boolean centeredX = true;
        boolean centeredY = true;
        if(scrollBoxUp.contains(Game.mouse.x(), Game.mouse.y()))
        {
            pan(0, 5);
            if(_panY>Game.mf.player.maxViewDistance){_panY=Game.mf.player.maxViewDistance;}
            centeredY=false;
        }
        if(scrollBoxDown.contains(Game.mouse.x(), Game.mouse.y()))
        {
            pan(0, -5);
            if(_panY<-Game.mf.player.maxViewDistance){_panY=-Game.mf.player.maxViewDistance;}
            centeredY=false;
        }
        if(scrollBoxLeft.contains(Game.mouse.x(), Game.mouse.y()))
        {
            pan(5, 0);
            if(_panX>Game.mf.player.maxViewDistance){_panX=Game.mf.player.maxViewDistance;}
            centeredX=false;
        }
        if(scrollBoxRight.contains(Game.mouse.x(), Game.mouse.y()))
        {
            pan(-5, 0);
            if(_panX<-Game.mf.player.maxViewDistance){_panX=-Game.mf.player.maxViewDistance;}
            centeredX=false;
        }
        //TODO: make this elastic
        if(centeredX)
        {
            center(5, 0);
        }
        if(centeredY)
        {
            center(0, 5);
        }

    }

    public void render(Graphics g)
    {
        g.drawImage(_img, _offsetX+_panX, _offsetY+_panY, null, null);

        g.drawString("MarketFlow " + _offsetX + ", " + _offsetY, 100, 10);

        g.drawRect(scrollBoxUp.x, scrollBoxUp.y, scrollBoxUp.width, scrollBoxUp.height);
        g.drawRect(scrollBoxRight.x, scrollBoxRight.y, scrollBoxRight.width, scrollBoxRight.height);
        g.drawRect(scrollBoxLeft.x, scrollBoxLeft.y, scrollBoxLeft.width, scrollBoxLeft.height);
        g.drawRect(scrollBoxDown.x, scrollBoxDown.y, scrollBoxDown.width, scrollBoxDown.height);
    }

    public BufferedImage Img()
    {
        return _img;
    }

    public int OffsetX(){return _offsetX;}//Gets the Offset in the X direction
    public void OffsetX(int x){_offsetX=x;}//Gets the Offset in the Y direction
    public int OffsetY(){return _offsetY;}//Sets the Offset in the X direction
    public void OffsetY(int y){_offsetY=y;}//Sets the Offset in the Y direction
    public void move(int x, int y){_offsetX=x;_offsetY=y;}//Sets both Offsets at once
    public void shift(int x, int y){_offsetX+=x;_offsetY+=y;}//Increments both Offsets
    public void snap(int x, int y){_panX=x;_panY=y;}//snaps the Camera to place
    public void center(int x, int y){
        if(_panX>x){_panX-=x;}else if(_panX<-x){_panX+=x;}else{_panX=0;}
        if(_panY>y){_panY-=y;}else if(_panY<-y){_panY+=y;}else{_panY=0;}
    }
    public void pan(int x, int y){_panX+=x;_panY+=y;}//Pans Camera
    public int PanX(){return _panX;}//Gets the far right edge of the map
    public int PanY(){return _panY;}//Gets the bottom edge of the map
    public int EdgeX(){return _edgeX;}//Gets the far right edge of the map
    public int EdgeY(){return _edgeY;}//Gets the bottom edge of the map
}
