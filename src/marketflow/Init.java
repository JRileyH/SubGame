package marketflow;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Physics.Collision;
import engine.*;
import marketflow.components.entities.*;
import marketflow.components.lights.*;
import marketflow.econ.Stock;
import marketflow.components.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class Init
{
	private WorldMap _map;
	private LightMap _lmap;
	private Player _player;
	private Map<String, City> _cityColl;
	private Map<String, Ship> _shipColl;
	private Map<String, Generator> _genColl;
	private Map<String, Housing> _houseColl;

	private Collision coll = new Collision();

	private ArrayList<Component> _obstacles;

	public static HSSFWorkbook cityBook;
	public static HSSFWorkbook shipBook;
	public static HSSFWorkbook housingBook;
	public static HSSFWorkbook generatorBook;

	public Init(XMLHandler xmlh)
	{
        float[] p_points = new float[]{
                32,0,
                17,25,
                3,73,
                0,117,
                23,100,
                32,125,
                41,100,
                63,117,
                60,73,
                46,25
        };
		_player = new Player("Player Name",										//Player Name
                "res/marketflow/entities/player2.png",                          //Image Path
                new Polygon(p_points),                                          //hitbox
				"Your Ship. There are many like it but this one is yours.",		//Description
				null,															//Stock Reference
				0,																//X
				0,																//Y
				3,																//Gear Size
				7,																//Max Forward Gears
				-3,																//Max Reverse Gears
				4,																//Max Yaw
				0.3f,															//Drag
				0.01f															//Turning Speed
		);

		_map = new WorldMap(_player, 0, 0);
		_lmap = new LightMap(15,1f);

		cityBook = new HSSFWorkbook();
		shipBook = new HSSFWorkbook();
		housingBook = new HSSFWorkbook();
		generatorBook = new HSSFWorkbook();

		_cityColl = new HashMap<>();
		Map<String, Stock> _cityResColl = new HashMap<>();
		
		_shipColl = new HashMap<>();
		Map<String, Stock> _shipResColl = new HashMap<>();
		
		_genColl = new HashMap<>();
		Map<String, Stock> _genResColl = new HashMap<>();
		
		_houseColl = new HashMap<>();
		Map<String, Stock> _houseResColl = new HashMap<>();

		_obstacles = new ArrayList<>();

		_lmap.addLight(new Light(500,500,150,0.5f,Color.red));
		_lmap.addLight(new Light(650,500,150,0.5f,Color.blue));
		_lmap.addLight(new Light(575,600,150,0.5f,Color.green));
		_lmap.addLight(new HeadLight(_player,500,600,550,0.5f,Color.white));


		//TESTING OBSTACLES
		float[] o_points=new float[]{
				46,50-36,
				157,0,
				247,128-36,
				239,154-36,
				205,196-36,
				125,220-36,
				58,193-36,
				0,121-36
		};
		_obstacles.add(new Component("res/marketflow/rock.png",new Polygon(o_points),400,400));

		xmlh.processMarketFlow(_cityColl,_shipColl,_genColl, _cityResColl, _shipResColl, _genResColl,_houseColl, _houseResColl);
	}

	public void update(int count)
	{//Fast Update Loop
		_map.update(count);
		_lmap.update(count);
		_player.update(count);

		for(City c : _cityColl.values())
		{
			c.update(count);
		}
		for(Ship s : _shipColl.values())
		{
			s.update(count);
		}
		for(Generator g : _genColl.values())
		{
			g.update(count);
		}
		for(Housing h : _houseColl.values())
		{
			h.update(count);
		}
		for(Component o : _obstacles)
		{
			o.update(count);
		}

		for(int c=0; c<_obstacles.size();c++)
		{
			if(coll.collCheck(_player.Hitbox(),_obstacles.get(c).Hitbox()) != null)
			{
				_player.Velocity(coll.collCheck(_player.Hitbox(),_obstacles.get(c).Hitbox()));
			}
		}
	}

	private int _time = 0;
	public void tick(int count)
	{//One Second Update Loop

		_player.tick(count);

		for(City c : _cityColl.values())
		{
			c.tick(count);
		}
		for(Generator g : _genColl.values())
		{
			g.tick(count);
		}
		for(Housing h : _houseColl.values())
		{
			h.tick(count);
		}
		for(Ship s : _shipColl.values())
		{
			s.tick(count);
		}
		_time++;
	}

	public void render(GameContainer game, Graphics g)
	{
		_map.render(game,g);

		_player.render(game,g);

		for(City c : _cityColl.values())
		{
			c.render(game,g);
		}
		for(Ship s : _shipColl.values())
		{
			s.render(game,g);
		}
		for(Component o : _obstacles)
		{
			o.render(game,g);
		}

		_lmap.render(game,g);

		_map.overlay(game,g);

		g.setColor(Color.orange);
		g.drawString("MarketFlow " + _map.OffsetX() + ", " + _map.OffsetY() + " : " + _time, 100, 10);
		g.setColor(Color.black);
	}
	
	public void Report()
	{
		FileOutputStream out;
		String reportPath = "C:\\Users\\Riggy\\IdeaProjects\\SubGame\\data\\marketflow\\reports\\";
		try
		{

			out = new FileOutputStream(new File(reportPath+"CityReport.xls"));
			cityBook.write(out);
			out.close();
			System.out.println("City Report Generated..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			out = new FileOutputStream(new File(reportPath+"ShipReport.xls"));
			shipBook.write(out);
			out.close();
			System.out.println("Ship Report Generated..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			out = new FileOutputStream(new File(reportPath+"HousingReport.xls"));
			housingBook.write(out);
			out.close();
			System.out.println("Housing Report Generated..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			out = new FileOutputStream(new File(reportPath+"GeneratorReport.xls"));
			generatorBook.write(out);
			out.close();
			System.out.println("Generator Report Generated..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public WorldMap Map(){return _map;}
	public Player Player(){return _player;}
}
