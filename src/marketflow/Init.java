package marketflow;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import engine.XMLHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

public class Init
{
	private WorldMap _map;
	private Player _player;
	//private PlayerAuto _player;
	private Map<String, City> _cityColl;
	private Map<String, Stock> _cityResColl;
	private Map<String, Ship> _shipColl;
	private Map<String, Stock> _shipResColl;
	private Map<String, Generator> _genColl;
	private Map<String, Stock> _genResColl;
	private Map<String, Housing> _houseColl;
	private Map<String, Stock> _houseResColl;

	private ArrayList<Obstacle> _obstacles;
	private ArrayList<Light> _lights;
	private Graphics _shading;
	private Image _shadow;


	public static HSSFWorkbook cityBook;
	public static HSSFWorkbook shipBook;
	public static HSSFWorkbook housingBook;
	public static HSSFWorkbook generatorBook;

	public Init(XMLHandler xmlh)
	{
		_player = new Player("Player Name",										//Player Name
		//_player = new PlayerAuto("Player Name",								//Player Name
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

		cityBook = new HSSFWorkbook();
		shipBook = new HSSFWorkbook();
		housingBook = new HSSFWorkbook();
		generatorBook = new HSSFWorkbook();

		_cityColl = new HashMap<>();
		_cityResColl = new HashMap<>();
		
		_shipColl = new HashMap<>();
		_shipResColl = new HashMap<>();
		
		_genColl = new HashMap<>();
		_genResColl = new HashMap<>();
		
		_houseColl = new HashMap<>();
		_houseResColl = new HashMap<>();

		_obstacles = new ArrayList<>();
		_lights = new ArrayList<>();
		try {
			_shadow = new Image("res/marketflow/shadow.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
		_obstacles.add(new Obstacle("res/marketflow/rock.png",new Polygon(o_points),400,400));

		//TESTING LIGHTING
		_lights.add(new Light(500,500,1.0f));

		xmlh.processMarketFlow(_cityColl,_shipColl,_genColl,_cityResColl,_shipResColl,_genResColl,_houseColl,_houseResColl);
	}

	public void update(int count)
	{//Fast Update Loop
		_map.update(count);

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
		for(Obstacle o : _obstacles)
		{
			o.update(count);
		}
		for(Light l : _lights)
		{
			l.update(count);
		}
	}

	public void tick(int count)
	{//One Second Update Loop
		_map.tick(count);

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
		for(Obstacle o : _obstacles)
		{
			o.render(game,g);
		}



		//GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_ONE,GL11.GL_ONE_MINUS_SRC_ALPHA);
		_shadow.startUse();
		GL11.glColorMask(false,false,false,true);
		for(Light l : _lights)
		{
			_shadow.drawEmbedded(l.X(),l.Y(),l.Width(),l.Height());
		}
		//g.drawImage(_shadow, 0,0,(float)game.getWidth(),(float)game.getHeight(),0,0,(float)game.getWidth(),(float)game.getHeight());
		_shadow.endUse();



		_map.overlay(game,g);

		g.setColor(Color.orange);
		g.drawString("MarketFlow " + _map.OffsetX() + ", " + _map.OffsetY(), 100, 10);
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
	//public PlayerAuto Player(){return _player;}
}
