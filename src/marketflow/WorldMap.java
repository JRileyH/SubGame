package marketflow;

import engine.Game;
import marketflow.components.entities.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Polygon;

/**
 * Created by Riggy on 9/4/2016.
 */
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

    public WorldMap(Player player, int initX, int initY)
    //public WorldMap(PlayerAuto player, int initX, int initY)
    {
        float[] vp_points = new float[]{0,0, Game.WIDTH,0, Game.WIDTH,Game.HEIGHT, 0,Game.HEIGHT};
        _viewport=new Polygon(vp_points);
        try {
            _tiles = new TiledMap("res/marketflow/map/map.tmx");
            _overlay = new TiledMap("res/marketflow/map/overlay.tmx");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        _player=player;


        _width= _tiles.getWidth();
        _height=_tiles.getHeight();
        _offsetX=-initX;
        _offsetY=-initY;
        _panX=0;
        _panY=0;
        _waveX=0;
        _waveY=0;
        _edgeX = Game.WIDTH-_width;
        _edgeY = Game.HEIGHT-_height;

    }

    public void update(int count)
    {

        _offsetX+=(int)_player.Velocity().getX();
        _offsetY+=(int)_player.Velocity().getY();
        _waveX--;_waveY--;
        if(_waveX<-_overlay.getTileHeight()){_waveX=0;_waveY=0;}
    }

    public void tick(int count)
    {

    }

    public void render(GameContainer game, Graphics g)
    {
        _tiles.render(_offsetX+_panX,_offsetY+_panY);
    }

    public void overlay(GameContainer game, Graphics g)
    {
        _overlay.render(_offsetX%_overlay.getTileWidth()+_panX+_waveX-_overlay.getTileWidth(),_offsetY%_overlay.getTileHeight()+_panY+_waveY-_overlay.getTileHeight());
        g.draw(_viewport);
    }


    public Polygon ViewPort(){return _viewport;}
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

//===PANNING TESTING===\\

//private Map<String, Rectangle> _scrollBoxes;

        /*
        //in init
        _scrollBoxes = new HashMap<>();
        int w = Game.WIDTH/3;
        int h = Game.HEIGHT/3;
        int k = 50;
        int s = Game.WIDTH-k;
        int b = Game.HEIGHT-k;
        _scrollBoxes.put("NWY",new Rectangle(w*0, 0, w, k));
        _scrollBoxes.put("NY" ,new Rectangle(w*1, 0, w, k));
        _scrollBoxes.put("NEY",new Rectangle(w*2, 0, w, k));

        _scrollBoxes.put("SWY",new Rectangle(w*0, b, w, k));
        _scrollBoxes.put("SY" ,new Rectangle(w*1, b, w, k));
        _scrollBoxes.put("SEY",new Rectangle(w*2, b, w, k));

        _scrollBoxes.put("NWX",new Rectangle(0, h*0, k, h));
        _scrollBoxes.put("WX" ,new Rectangle(0, h*1, k, h));
        _scrollBoxes.put("SWX",new Rectangle(0, h*2, k, h));

        _scrollBoxes.put("NEX",new Rectangle(s, h*0, k, h));
        _scrollBoxes.put("EX" ,new Rectangle(s, h*1, k, h));
        _scrollBoxes.put("SEX",new Rectangle(s, h*2, k, h));
        */

        //in render
        /*for(Rectangle r : _scrollBoxes.values())
        {
            g.drawRect(r.x,r.y,r.width,r.height);
        }*/



        /*boolean centered = true;
        for(Map.Entry<String, Rectangle> r : _scrollBoxes.entrySet())
        {
            if(r.getValue().contains(Game.mouse.x(), Game.mouse.y()))
            {
                switch(r.getKey())
                {
                    case "NWY":
                        pan(2, 3);centered=false;
                        break;
                    case "NY":
                        pan(0, 5);centered=false;
                        break;
                    case "NEY":
                        pan(-2, 3);centered=false;
                        break;

                    case "SWY":
                        pan(2, -3);centered=false;
                        break;
                    case "SY":
                        pan(0, -5);centered=false;
                        break;
                    case "SEY":
                        pan(-2, -3);centered=false;
                        break;

                    case "NWX":
                        pan(3, 2);centered=false;
                        break;
                    case "WX":
                        pan(5, 0);centered=false;
                        break;
                    case "SWX":
                        pan(3, -2);centered=false;
                        break;

                    case "NEX":
                        pan(-3, 2);centered=false;
                        break;
                    case "EX":
                        pan(-5, 0);centered=false;
                        break;
                    case "SEX":
                        pan(-3, -2);centered=false;
                        break;
                }
            }
            if(_panY>_player.maxViewDistance){_panY=_player.maxViewDistance;}
            if(_panY<-_player.maxViewDistance){_panY=-_player.maxViewDistance;}
            if(_panX>_player.maxViewDistance){_panX=_player.maxViewDistance;}
            if(_panX<-_player.maxViewDistance){_panX=-_player.maxViewDistance;}
        }
        //TODO: make this elastic
        if(centered)
        {
            center(5, 5);
        }*/
