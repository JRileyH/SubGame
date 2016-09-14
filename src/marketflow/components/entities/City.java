package marketflow.components.entities;

import marketflow.econ.Stock;

import java.util.Map;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class City extends Entity
{
	public List<String> ships = new ArrayList<>();
	public List<String> generators = new ArrayList<>();
	public List<String> housing = new ArrayList<>();
	
	private Map<String, Ship> _shipRef;
	//private float shipTax = 0.1f;
	private Map<String, Generator> _genRef;
	//private float genTax = 0.05f;
	private Map<String, Housing> _houseRef;
	private float houseTax;
	private Map<String, Integer> _prices;
	private Map<String, Integer> _basePrices;

	public City(String id, String desc, int x, int y, Map<String, Ship> sh_ref, Map<String, Generator> g_ref, Map<String, Housing> h_ref, Map<String, Stock> st_ref)
	{
		super(id, desc, st_ref,"res/marketflow/entities/city.png", null, x, y);
		_shipRef =sh_ref;
		_genRef =g_ref;
		_houseRef =h_ref;
		_prices = new HashMap<>();
        houseTax = 0.01f;
	}

	public void tick(int count)
	{
		super.tick(count);

		if(count%100==0)
		{
			int total = 0;
			for(Housing h : _houseRef.values())
			{
				int tax = (int)(h.Credit()*houseTax);
				h.incCredit(-tax);
				total+=tax;
			}
			incCredit(total);
			log("Collected Taxes: $"+Credit()+" (+$"+total+")");
		}
		for(Stock s : _stockRef.values())
		{//Find appropriate prices.
			float amt = (float)s.Resource(_id);
			float ttl = (float)s.Total();
			float max = (float) _basePrices.get(s.Name());
			float mlt = 0.0f;
			if(amt>0){mlt = amt/ttl;}
			mlt=mlt+1.0f;
			float fin = max/mlt;
			int price = Math.round(fin);
			_prices.put(s.Name(), price);
		}
		logCol=oldLogCol;
	}

	public void BasePrices(Map<String, Integer> in){_basePrices =in;}
	public Map<String, Integer> BasePrices()
	{
		return _basePrices;
	}
	public void BasePrice(String rid, int amt){ _basePrices.put(rid,amt);}
	public void incBasePrice(String rid, int amt){ int inc = _basePrices.get(rid)+amt; _basePrices.put(rid,inc);}

	int Price(String rid){return _prices.get(rid);}

	@SuppressWarnings("unused")
	public Ship Ship(String sid){return _shipRef.get(sid);}
	@SuppressWarnings("unused")
	public Generator Generator(String gid){return _genRef.get(gid);}
	@SuppressWarnings("unused")
	public Housing House(String hid){return _houseRef.get(hid);}

	public void Population(int amt){System.out.println("Cannot Change _population Via City Method.");}
	public int Population()
	{
		int pop = 0;
		for(Housing h : _houseRef.values())
		{
			if(h.Home().ID().equals(ID()))
			{
				pop=pop+h.Population();
			}
		}
		return pop;
	}
	public void incPopulation(int amt){System.out.println("Cannot Change _population Via City Method.");}
	public int PopulationMax(){
		int pop = 0;
		for(Housing h : _houseRef.values())
		{
			if(h.Home().ID().equals(ID()))
			{
				pop=pop+h.PopulationMax();
			}
		}
		return pop;}
}
