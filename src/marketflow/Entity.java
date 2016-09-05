package marketflow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import engine.Game;

public class Entity
{
	protected int Credit;
	protected String ID;
	protected Map<String, Stock> stockRef;
	protected int Population;
	protected int PopulationMax = 1;
	protected int posX, posY;
	protected String Description;
	protected BufferedImage img;
	protected Rectangle hitbox;
	protected HSSFSheet reportSheet;
	
	public Entity(String id, String desc, Map<String, Stock> st_ref, int x, int y)
	{
		ID = id;
		Description=desc;
		stockRef = st_ref;
		posX=x;
		posY=y;

		hitbox = new Rectangle(x,y, 0, 0);

		if(this.getClass().getName().equals("marketflow.City"))
		{
			reportSheet = Init.cityBook.createSheet(ID+" Report");
		}
		else if(this.getClass().getName().equals("marketflow.Ship"))
		{
			reportSheet = Init.shipBook.createSheet(ID+" Report");
		}
		else if(this.getClass().getName().equals("marketflow.Housing"))
		{
			reportSheet = Init.housingBook.createSheet(ID+" Report");
		}
		else if(this.getClass().getName().equals("marketflow.Generator"))
		{
			reportSheet = Init.generatorBook.createSheet(ID+" Report");
		}

		if(reportSheet!=null)
		{//create excel sheet for outputting actions
			Row row = reportSheet.createRow(0);
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
	protected boolean console = false;
	private int time = 0;
	protected int logCol = 0;
	protected int oldLogCol = 0;
	public void print(){
		String toPrint = "";
		toPrint+="/=================================\\\n";
		toPrint+=makeLine(ID, ""+time);
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
	protected void log(String msg)
	{
		if(console){System.out.println(ID+": "+msg);}
		if(reportSheet != null && row != null) {
			Cell cell = row.createCell(logCol);
			cell.setCellValue(msg);
		}
		logCol++;
	}
//===================================================================//

	public void update(int count)
	{

	}

	protected int rowNum = 1;
	protected Row row;
	public void tick(int count)
	{
		if(reportSheet!=null) {
			row = reportSheet.createRow(rowNum);
			Cell cell = row.createCell(0);
			cell.setCellValue(count);
			cell = row.createCell(1);
			cell.setCellValue(Credit());
			cell = row.createCell(2);
			cell.setCellValue(Population());
			int colNum = 3;
			for (Stock val : stockRef.values()) {
				cell = row.createCell(colNum);
				cell.setCellValue(val.Resource(ID));
				colNum++;
			}
			rowNum++;
		}
	}

	public void render(Graphics g)
	{
		int x = posX+Game.mf.map.OffsetX()-img.getWidth()/2+Game.mf.map.PanX();
		int y = posY+Game.mf.map.OffsetY()-img.getHeight()/2+Game.mf.map.PanY();

		g.drawImage(img, x, y, null);
		g.drawString(ID, x-30, y-20);
	}
	
	public boolean Immigrate(Entity source, int amt)
	{
		if(source.Population()>amt&&Population<(PopulationMax-amt)){
			source.incPopulation(-amt);
			incPopulation(amt);
		}else{
			return false;
		}
		return true;
	}
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
	public boolean Buy(Entity seller, String res, int pricePer, int amt)
	{
		int cost=pricePer*amt;
		if(Credit>=cost&&seller.Resource(res)>=amt){
			seller.incCredit(cost);
			incCredit(-cost);
			seller.incResource(res, -amt);
			incResource(res, amt);
		}else{
			return false;
		}
		return true;
	}
	public boolean Sell(Entity buyer, String res, int pricePer, int amt)
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

	public void Description(String str){Description=str;}
	public String Description(){return Description;}
	public void Credit(int amt){Credit=amt;}
	public int Credit(){return Credit;}
	public void incCredit(int amt){Credit+=amt;}
	
	public void Population(int amt){Population=amt;}
	public int Population(){return Population;}
	public int PopulationMax(){return PopulationMax;}
	public void incPopulation(int amt){Population+=amt;}
	
	public void Resource(String rid, int amt){stockRef.get(rid).Resource(ID, amt);}
	public int Resource(String rid){return stockRef.get(rid).Resource(ID);}
	public void incResource(String rid, int amt){stockRef.get(rid).incResource(ID, amt);}
}
