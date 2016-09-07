package marketflow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import engine.Game;
import engine.XMLHandler;

public class Init
{
	public WorldMap map;

	public Player player;

	private Map<String, City> _cityColl;
	private Map<String, Stock> _cityResColl;
	
	private Map<String, Ship> _shipColl;
	private Map<String, Stock> _shipResColl;
	
	private Map<String, Generator> _genColl;
	private Map<String, Stock> _genResColl;
	
	private Map<String, Housing> _houseColl;
	private Map<String, Stock> _houseResColl;

	private int scrollSpeed = 1;

	public static HSSFWorkbook cityBook;
	public static HSSFWorkbook shipBook;
	public static HSSFWorkbook housingBook;
	public static HSSFWorkbook generatorBook;

	public Init(Game i, XMLHandler xmlh)
	{
		map = new WorldMap("res/map.jpg", 6000, 1000);

		player = new Player("Playername", "Riley is sexy", null, 0, 0);	//perfect center x, y

		cityBook = new HSSFWorkbook();
		shipBook = new HSSFWorkbook();
		housingBook = new HSSFWorkbook();
		generatorBook = new HSSFWorkbook();

		_cityColl = new HashMap<String, City>();
		_cityResColl = new HashMap<String, Stock>();
		
		_shipColl = new HashMap<String, Ship>();
		_shipResColl = new HashMap<String, Stock>();
		
		_genColl = new HashMap<String, Generator>();
		_genResColl = new HashMap<String, Stock>();
		
		_houseColl = new HashMap<String, Housing>();
		_houseResColl = new HashMap<String, Stock>();
		
		xmlh.processMarketFlow(_cityColl,_shipColl,_genColl,_cityResColl,_shipResColl,_genResColl,_houseColl,_houseResColl);
		for(City c : _cityColl.values())
		{
			c.update(0);
		}
	}

	/*public double rileyify(double val) {
		if (val > 0) {
			return Math.ceil(val);
		}
		return Math.floor(val);
	}*/

	public void update(int count)
	{
		map.update(count);

		player.update(count);

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
	}

	public void tick(int count)
	{
		player.tick(count);

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

	public void render(Graphics g)
	{
		map.render(g);

		player.render(g);

		for(City c : _cityColl.values())
		{
			c.render(g);
		}
		for(Ship s : _shipColl.values())
		{
			s.render(g);
		}
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


}
