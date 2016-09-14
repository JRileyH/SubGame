package marketflow.components.entities;

import java.util.Map;

import marketflow.Init;
import marketflow.components.Component;
import marketflow.econ.Stock;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

public class Entity extends Component
{
	int _credit;
	String _id;
	Map<String, Stock> _stockRef;
	int _population;
	int _populationMax = 1;
	private String _description;
	private HSSFSheet _reportSheet;
	
	Entity(String id, String desc, Map<String, Stock> st_ref, String path, Polygon hitbox, int x, int y)
	{
		super(path,hitbox,x,y);
		_id = id;
		_description = desc;
		_stockRef = st_ref;

		if(this.getClass().getName().equals("marketflow.components.entities.City"))
		{
			_reportSheet = Init.cityBook.createSheet(_id +" Report");
		}
		else if(this.getClass().getName().equals("marketflow.components.entities.Ship"))
		{
			_reportSheet = Init.shipBook.createSheet(_id +" Report");
		}
		else if(this.getClass().getName().equals("marketflow.components.entities.Housing"))
		{
			_reportSheet = Init.housingBook.createSheet(_id +" Report");
		}
		else if(this.getClass().getName().equals("marketflow.components.entities.Generator"))
		{
			_reportSheet = Init.generatorBook.createSheet(_id +" Report");
		}

		if(_reportSheet != null)
		{//create excel sheet for outputting actions
			Row row = _reportSheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("Time");
			cell = row.createCell(1);
			cell.setCellValue("Credit");
			cell = row.createCell(2);
			cell.setCellValue("Population");
			int colNum=3;
			for(String name : st_ref.keySet())
			{
				cell = row.createCell(colNum);
				cell.setCellValue(name);
				colNum++;
			}
			logCol=colNum;
			oldLogCol=colNum;
			cell = row.createCell(colNum);
			cell.setCellValue("Action");
		}
	}

//===Reporting================================//
    private boolean console = false;
	private int time = 0;
	int logCol = 0;
	int oldLogCol = 0;
	public void print(){
		String toPrint = "";
		toPrint+="/=================================\\\n";
		toPrint+=makeLine(_id, ""+time);
		toPrint+=makeLine("Cred: "+Credit(), "Pop: "+Population());
		toPrint+="|--Consumables--------------------|\n";
		toPrint+=makeLine("Water:    "+Resource("Water"), 		"Fish:     "+Resource("Fish"));
		toPrint+=makeLine("Seaweed:  "+Resource("Seaweed"), 	"Soylent:  "+Resource("Soylent"));
		toPrint+=makeLine("Decapoda: "+Resource("Decapoda"),	"Jellies:  "+Resource("Jellies"));
		toPrint+=makeLine("Voles:    "+Resource("Water"), 	".");
		toPrint+="|--Goods--------------------------|\n";
		toPrint+=makeLine("Oil:      "+Resource("Oil"), 		"Fuel:     "+Resource("Fuel"));
		toPrint+=makeLine("Metal:    "+Resource("Hard Metals"),	"Parts:    "+Resource("Parts"));
		toPrint+=makeLine("Energy:   "+Resource("Energy"), ".");
		toPrint+="|--Luxuries-----------------------|\n";
		toPrint+=makeLine("Shells:   "+Resource("Shells"), 		"Coral:    "+Resource("Coral"));
		toPrint+=makeLine("Pearls:   "+Resource("Pearls"), 		"Spirits:  "+Resource("Spirits"));
		toPrint+="\\=================================/\n";
		System.out.print(toPrint);
	}
	private String makeLine(String first, String second)
	{
		String line = "| "+first;
		int blanks = 17-line.length();
		if(blanks>0)
		{
			for(int i = 0; i < blanks; i++)
			{
				line+=" ";
			}
		}
		line+="| "+second;
		blanks = 34-line.length();
		if(blanks>0)
		{
			for(int i = 0; i < blanks; i++)
			{
				line+=" ";
			}
		}
		line+="|";
		return line+"\n";
	}
	void log(String msg)
	{
		if(console){System.out.println(_id +": "+msg);}
		if(_reportSheet != null && row != null) {
			Cell cell = row.createCell(logCol);
			cell.setCellValue(msg);
		}
		logCol++;
	}
//===================================================================//

	public void update(int count)
	{//Fast Logic Loop
		super.update(count);
	}

	private int rowNum = 1;
	private Row row;
	public void tick(int count)
	{//One Second Loop
		if(_reportSheet !=null) {
			row = _reportSheet.createRow(rowNum);
			Cell cell = row.createCell(0);
			cell.setCellValue(count);
			cell = row.createCell(1);
			cell.setCellValue(Credit());
			cell = row.createCell(2);
			cell.setCellValue(Population());
			int colNum = 3;
			for (Stock val : _stockRef.values()) {
				cell = row.createCell(colNum);
				cell.setCellValue(val.Resource(_id));
				colNum++;
			}
			rowNum++;
		}
	}

	public void render(GameContainer game, Graphics g)
	{
		super.render(game,g);
		if(renderable) g.drawString(_id, _relX, _relY);
	}

	@SuppressWarnings("unused")
	public boolean Immigrate(Entity source, int amt)
	{
		if(source.Population()>amt&& _population <(_populationMax -amt)){
			source.incPopulation(-amt);
			incPopulation(amt);
		}else{
			return false;
		}
		return true;
	}
    @SuppressWarnings("unused")
	public boolean Emigrate(Entity destination, int amt)
	{
		if(Population()>amt&&destination.Population()<(destination.PopulationMax()-amt)){
			destination.incPopulation(amt);
			incPopulation(-amt);
		}else{
			return false;
		}
		return true;
	}
	boolean Buy(Entity seller, String res, int pricePer, int amt)
	{
		int cost=pricePer*amt;
		if(_credit >=cost&&seller.Resource(res)>=amt){
			seller.incCredit(cost);
			incCredit(-cost);
			seller.incResource(res, -amt);
			incResource(res, amt);
		}else{
			return false;
		}
		return true;
	}
	boolean Sell(Entity buyer, String res, int pricePer, int amt)
	{
		int cost=pricePer*amt;
		if(buyer.Credit()>=cost&&Resource(res)>=amt){
			incCredit(cost);
			buyer.incCredit(-cost);
			incResource(res, -amt);
			buyer.incResource(res, amt);
		}else{
			return false;
		}
		return true;
	}

	String ID(){return _id;}
    @SuppressWarnings("unused")
	public void Description(String str){ _description = str;}
    @SuppressWarnings("unused")
	public String Description(){return _description;}
	public void Credit(int amt){ _credit = amt;}
	int Credit(){return _credit;}
	void incCredit(int amt){ _credit += amt;}

	public void Population(int amt){ _population = amt;}
	public int Population(){return _population;}
	public int PopulationMax(){return _populationMax;}
	public void incPopulation(int amt){ _population +=amt;}
    @SuppressWarnings("unused")
	void Resource(String rid, int amt){_stockRef.get(rid).Resource(_id, amt);}
	int Resource(String rid){return _stockRef.get(rid).Resource(_id);}
	void incResource(String rid, int amt){_stockRef.get(rid).incResource(_id, amt);}
}
