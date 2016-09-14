package marketflow;

import engine.Game;
import marketflow.components.entities.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Polygon;

public class WorldMap
{
    private int _width,_height;
    private Polygon _viewport;
    private int _offsetX,_offsetY;
    private int _panX,_panY;
    private int _edgeX,_edgeY;
    private int _waveX,_waveY;
    //private PlayerAuto _player;
    private Player _player;
    private TiledMap _tiles;
    private TiledMap _overlay;

    WorldMap(Player player, int initX, int initY)
    //public WorldMap(PlayerAuto player, int initX, int initY)
    {
        float[] vp_points = new float[]{0, 0, Game.WIDTH, 0, Game.WIDTH, Game.HEIGHT, 0, Game.HEIGHT};
        _viewport = new Polygon(vp_points);
        try {
            _tiles = new TiledMap("res/marketflow/map/map.tmx");
            _overlay = new TiledMap("res/marketflow/map/overlay.tmx");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _player = player;


        _width = _tiles.getWidth();
        _height = _tiles.getHeight();
        _offsetX = -initX;
        _offsetY = -initY;
        _panX = 0;
        _panY = 0;
        _waveX = 0;
        _waveY = 0;
        _edgeX = Game.WIDTH - _width;
        _edgeY = Game.HEIGHT - _height;

    }

    @SuppressWarnings("unused")
    public void update(int count)
    {
        _offsetX+=(int)_player.Velocity().getX();
        _offsetY+=(int)_player.Velocity().getY();
        _waveX--;_waveY--;
        if(_waveX<-_overlay.getTileHeight()){_waveX=0;_waveY=0;}
    }

    @SuppressWarnings("unused")
    public void render(GameContainer game, Graphics g)
    {
        _tiles.render(_offsetX+_panX,_offsetY+_panY);
    }
    @SuppressWarnings("unused")
    void overlay(GameContainer game, Graphics g)
    {
        _overlay.render(_offsetX%_overlay.getTileWidth()+_panX+_waveX-_overlay.getTileWidth(),_offsetY%_overlay.getTileHeight()+_panY+_waveY-_overlay.getTileHeight());
        g.draw(_viewport);
    }

    @SuppressWarnings("unused")public int Width(){return _width;}
    @SuppressWarnings("unused")public int Height(){return _height;}
    @SuppressWarnings("unused")public Polygon ViewPort(){return _viewport;}
    @SuppressWarnings("unused")public int OffsetX(){return _offsetX;}//Gets the Offset in the X direction
    @SuppressWarnings("unused")public void OffsetX(int x){_offsetX=x;}//Gets the Offset in the Y direction
    @SuppressWarnings("unused")public int OffsetY(){return _offsetY;}//Sets the Offset in the X direction
    @SuppressWarnings("unused")public void OffsetY(int y){_offsetY=y;}//Sets the Offset in the Y direction
    @SuppressWarnings("unused")public void move(int x, int y){_offsetX=x;_offsetY=y;}//Sets both Offsets at once
    @SuppressWarnings("unused")public void shift(int x, int y){_offsetX+=x;_offsetY+=y;}//Increments both Offsets
    @SuppressWarnings("unused")public void snap(int x, int y){_panX=x;_panY=y;}//snaps the Camera to place
    @SuppressWarnings("unused")public void center(int x, int y){
        if(_panX>x){_panX-=x;}else if(_panX<-x){_panX+=x;}else{_panX=0;}
        if(_panY>y){_panY-=y;}else if(_panY<-y){_panY+=y;}else{_panY=0;}
    }
    @SuppressWarnings("unused")public void pan(int x, int y){_panX+=x;_panY+=y;}//Pans Camera
    @SuppressWarnings("unused")public int PanX(){return _panX;}//Gets the far right edge of the map
    @SuppressWarnings("unused")public int PanY(){return _panY;}//Gets the bottom edge of the map
    @SuppressWarnings("unused")public int EdgeX(){return _edgeX;}//Gets the far right edge of the map
    @SuppressWarnings("unused")public int EdgeY(){return _edgeY;}//Gets the bottom edge of the map
}