package engine;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import marketflow.components.entities.City;
import marketflow.components.entities.Generator;
import marketflow.components.entities.Housing;
import marketflow.components.entities.Ship;
import marketflow.econ.Stock;

public class XMLHandler
{
	private DocumentBuilderFactory _factory;
	private static DocumentBuilder _builder;
	private static StringBuilder _xmlSB;

	XMLHandler()
	{
		_factory = DocumentBuilderFactory.newInstance();
		try
		{
			_builder = _factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	private static Document read(String path)
	{
		_xmlSB = new StringBuilder();
		_xmlSB.append("<?xml version=\"1.0\"?> <class> </class>");
		try
		{
			File input = new File(path);
			Document doc = _builder.parse(input);
			doc.getDocumentElement().normalize();
			return doc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	void setKeyMap(String path, Map<Integer, String> map, Map<Integer, Boolean> flags)
	{
		Document doc = read(path);
		map.clear();
		if (doc == null) throw new AssertionError();
		Node keys = doc.getElementsByTagName("Keys").item(0);
		Element keys_elem = (Element) keys;
		NodeList bindings = keys_elem.getElementsByTagName("Binding");
		for(int i = 0; i < bindings.getLength(); i++)
		{
			Node binding = bindings.item(i);
			Element binding_elem = (Element) binding;
			map.put(Integer.parseInt(binding_elem.getAttribute("id")), binding_elem.getAttribute("action"));
			flags.put(Integer.parseInt(binding_elem.getAttribute("id")), false);
		}
	}
	public void setMouseMap(String path, Map<Integer, String> map, Map<Integer, Boolean> flags)
	{
		Document doc = read(path);
		map.clear();
		Node mouse = doc.getElementsByTagName("Mouse").item(0);
		Element mouse_elem = (Element) mouse;
		NodeList bindings = mouse_elem.getElementsByTagName("Binding");
		for(int i = 0; i < bindings.getLength(); i++)
		{
			Node binding = bindings.item(i);
			Element binding_elem = (Element) binding;
			map.put(Integer.parseInt(binding_elem.getAttribute("id")), binding_elem.getAttribute("action"));
			flags.put(Integer.parseInt(binding_elem.getAttribute("id")), false);
		}
	}
	
//===___|CUSTOM FUNCTIONS|___===//

	public Map<String, Integer> setBasePrices()
	{
		Map<String, Integer> out = new HashMap<>();
		Document doc = read("data/marketflow/PriceInfo.xml");
		NodeList nodes = doc.getElementsByTagName("Price");
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			Element elem = (Element) node;
			out.put(elem.getAttribute("id"),Integer.parseInt(node.getTextContent()));
		}
		//System.out.println(out);
		return out;
	}
	public void processMarketFlow(Map<String, City> cc, Map<String, Ship> sc, Map<String, Generator> gc, Map<String, Stock> crc, Map<String, Stock> src, Map<String, Stock> grc, Map<String, Housing> hc, Map<String, Stock> hrc)
	{//Loads resource holdings for ships, cities, generators, etc for world map mode.

		Map<String, Integer> stockPrices = setBasePrices();

		String city;
		String ship;
		String gen;
		String res;
		String restype;
		String house;
		String desc;
		Map<String, Integer> basePrices;

		Document doc = read("data/marketflow/ResInfo.xml");
		NodeList c_nodes = doc.getElementsByTagName("City");
		for(int i = 0; i < c_nodes.getLength(); i++)
		{
			Node c_node = c_nodes.item(i);
			Element c_elem = (Element) c_node;
			
			//sets city name
			city = c_elem.getAttribute("id");
			//get description of city
			desc = c_elem.getElementsByTagName("Description").item(0).getTextContent();

			//initializes city
			cc.put(city, new City(city,							//City Name
					desc,										//_description of City
					Integer.parseInt(c_elem.getAttribute("x")),	//X Coordinate
					Integer.parseInt(c_elem.getAttribute("y")),	//Y Coordinate
					sc,											//Ship Collection
					gc,											//Generator Collection
					hc,											//Housing Collection
					crc											//City Resource Collection
			));
			//initializes city credit amount
			cc.get(city).Credit(Integer.parseInt(c_elem.getAttribute("cred")));

			//gets basePrice map ready to build
			basePrices = new HashMap<String, Integer>();

			NodeList cr_nodes = c_elem.getElementsByTagName("cRes");
			for(int j = 0; j < cr_nodes.getLength(); j++)
			{
				Node cr_node = cr_nodes.item(j);
				Element cr_elem = (Element) cr_node;
				
				//sets city resource product type
				res = cr_elem.getAttribute("id");
				//sets the cargo type
				restype = cr_elem.getAttribute("type");
				//set the base price
				basePrices.put(res, 0);

				//initializes city resource stock (if not already)
				if(crc.get(res)==null){crc.put(res, new Stock(res, restype));}
				//initializes city's resource stock amount
				crc.get(res).initStock(city, Integer.parseInt(cr_elem.getTextContent()));
			}

			//set city base prices
			cc.get(city).BasePrices(basePrices);
		//Ships
			NodeList s_nodes = c_elem.getElementsByTagName("Ship");
			for(int j = 0; j < s_nodes.getLength(); j++)
			{
				Node s_node = s_nodes.item(j);
				Element s_elem = (Element) s_node;
				
				//sets ship name
				ship = s_elem.getAttribute("id");
				
				//Get last known location for loading in
				String docked = s_elem.getAttribute("dock");

				//Get description of ship
				desc = s_elem.getElementsByTagName("Description").item(0).getTextContent();

				//initializes ship
				sc.put(ship, new Ship(ship,								//Ship Name
						desc,
						Integer.parseInt(s_elem.getAttribute("x")),		//X Coordinate
						Integer.parseInt(s_elem.getAttribute("y")),		//Y Coordinate
						Integer.parseInt(s_elem.getAttribute("speed")),	//Max Speed
						cc.get(docked),									//Starting Location
						cc.get(city),									//Home City
						cc,												//City Collection Reference
						src,											//Ship Resource Collection
						Integer.parseInt(s_elem.getAttribute("max"))	//Max Population
				));
				//initializes ship credit amount
				sc.get(ship).Credit(Integer.parseInt(s_elem.getAttribute("cred")));
				//initializes ship crew count
				sc.get(ship).Population(Integer.parseInt(s_elem.getAttribute("pop")));
				//adds ship to respective city's ship list
				cc.get(city).ships.add(ship);
				
				NodeList sr_nodes = s_elem.getElementsByTagName("sRes");
				for(int k = 0; k < sr_nodes.getLength(); k++)
				{
					Node sr_node = sr_nodes.item(k);
					Element sr_elem = (Element) sr_node;
					
					//sets ship resource product type
					res = sr_elem.getAttribute("id");
					//set cargo type
					restype = sr_elem.getAttribute("type");

					//initializes ship resource stock (if not already)
					if(src.get(res)==null){src.put(res, new Stock(res, restype));}
					//initializes ship's resource stock amount
					src.get(res).initStock(ship, Integer.parseInt(sr_elem.getTextContent()));
				}
			}
		//Generators
			NodeList g_nodes = c_elem.getElementsByTagName("Generator");
			for(int j = 0; j < g_nodes.getLength(); j++)
			{
				Node g_node = g_nodes.item(j);
				Element g_elem = (Element) g_node;
				
				//sets generator name
				gen = g_elem.getAttribute("id");
				//get generator type
				String gentype = g_elem.getAttribute("type");

				gc.put(gen, makeGenerator(gentype,
						gen,
						Integer.parseInt(c_elem.getAttribute("x")),		//X Coordinate
						Integer.parseInt(c_elem.getAttribute("y")),		//Y Coordinate
						cc.get(city),									//Home City
						grc,											//reference to Generator Resource Stock
						stockPrices										//For setting Base Prices
				));

				//initializes generator credit amount
				gc.get(gen).Credit(Integer.parseInt(g_elem.getAttribute("cred")));
				//initializes generator worker population amount
				gc.get(gen).Population(Integer.parseInt(g_elem.getAttribute("pop")));
				//adds generator to respective city's generator list
				cc.get(city).generators.add(gen);
				
				NodeList gr_nodes = g_elem.getElementsByTagName("gRes");
				for(int k = 0; k < gr_nodes.getLength(); k++)
				{
					Node gr_node = gr_nodes.item(k);
					Element gr_elem = (Element) gr_node;

					//sets generator resource product type
					res = gr_elem.getAttribute("id");
					//set cargo type
					restype = gr_elem.getAttribute("type");

					//initializes generator resource stock (if not already)
					if(grc.get(res)==null){grc.put(res, new Stock(res, restype));}
					//initializes generator's resource stock amount
					grc.get(res).initStock(gen, Integer.parseInt(gr_elem.getTextContent()));
				}
			}

		//Housing
			NodeList h_nodes = c_elem.getElementsByTagName("Housing");
			for(int j = 0; j < h_nodes.getLength(); j++) {
				Node h_node = h_nodes.item(j);
				Element h_elem = (Element) h_node;

				//sets housing unit name
				house = h_elem.getAttribute("id");

				//sets housing type
				String housetype = h_elem.getAttribute("type");

				//initialized housing unit
				hc.put(house, makeHouse(housetype,
						house,
						Integer.parseInt(c_elem.getAttribute("x")),		//X Coordinate
						Integer.parseInt(c_elem.getAttribute("y")),		//Y Coordinate
						cc.get(city),									//Home City
						hrc,											//reference to Generator Resource Stock
						stockPrices										//For Setting Base Prices
				));

				//initializes housing unit's credit amount
				hc.get(house).Credit(Integer.parseInt(h_elem.getAttribute("cred")));
				//initializes housing resident population
				hc.get(house).Population(Integer.parseInt(h_elem.getAttribute("pop")));
				//adds using unit to respective city's housing list
				cc.get(city).housing.add(house);

				NodeList hr_nodes = h_elem.getElementsByTagName("hRes");
				for (int k = 0; k < hr_nodes.getLength(); k++) {
					Node hr_node = hr_nodes.item(k);
					Element hr_elem = (Element) hr_node;

					//sets housing resource type
					res = hr_elem.getAttribute("id");
					//set cargo type
					restype = hr_elem.getAttribute("type");

					//initializes housing resource stock (if not already)
					if (hrc.get(res) == null) {
						hrc.put(res, new Stock(res, restype));
					}
					//initializes housing unit's resource stock amount
					hrc.get(res).initStock(house, Integer.parseInt(hr_elem.getTextContent()));
				}
			}


			//Print out the base prices to make sure they are right....
			/*System.out.println(cc.get(city).ID()+":");
			for(Map.Entry<String, Integer> print : cc.get(city).BasePrices().entrySet())
			{
				System.out.println(print.getKey()+": "+print.getValue());
			}
			System.out.println("=======================");*/

		}
	}

	private Generator makeGenerator(
		String type,
		String id,
		int x,
		int y,
		City city,
		Map<String, Stock> grc,
		Map<String, Integer> stockPrices
	)
	{//This function received data from ResInfo.xml and pulls data from GenInfo.xml to initialize Generator Objects
		Document doc = read("data/marketflow/GenInfo.xml");
		NodeList nodes = doc.getElementsByTagName("Gen");
		Element elem = null;
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			elem = (Element) node;
			if(elem.getAttribute("id").equals(type)){break;}
		}

		String[] inputs = new String[]{};
		try{NodeList input_nodes = elem.getElementsByTagName("Input");
		inputs = new String[input_nodes.getLength()];
		for(int k = 0; k < input_nodes.getLength(); k++)
		{//find inputs (ingredients) required for creating product...?
			Node input_node = input_nodes.item(k);
			Element input_elem = (Element) input_node;

			//adjust city base price and set inputs ^^^
			String inputRes = input_elem.getTextContent();
			//If you dont have a base price yet then set it. Other wise make it MORE valuable since you will be buying it for generator inputs
			if(city.BasePrices().get(inputRes)==0){
				city.BasePrice(inputRes,stockPrices.get(inputRes));
			}else{
				city.incBasePrice(inputRes,1);
			}
			inputs[k]=inputRes;

		}}catch(Exception e){}

		//If you don't have a base price set then set it. Other wise make it LESS valuable since you have a home course of it.
		String product = elem.getAttribute("product");
		if(city.BasePrices().get(product)==0){
			city.BasePrice(product,stockPrices.get(product));
		}else{
			city.incBasePrice(product,-1);
		}

		return new Generator(id,
				elem.getElementsByTagName("Description").item(0).getTextContent(),
				x,
				y,
				Integer.parseInt(elem.getAttribute("cost")),
				Integer.parseInt(elem.getAttribute("out")),
				inputs,
				product,
				city,
				grc,
				Integer.parseInt(elem.getAttribute("time")),
				Integer.parseInt(elem.getAttribute("max"))
		);
	}

	private Housing makeHouse(
			String type,
			String id,
			int x,
			int y,
			City city,
			Map<String, Stock> hrc,
			Map<String, Integer> stockPrices
	)
	{//This function received data from ResInfo.xml and pulls data from HouseInfo.xml to initialize Housing Objects
		Document doc = read("data/marketflow/HouseInfo.xml");
		NodeList nodes = doc.getElementsByTagName("House");
		Element elem = null;
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			elem = (Element) node;
			if(elem.getAttribute("id").equals(type)){break;}
		}

		NodeList consumables = elem.getElementsByTagName("Consumable");
		String[] consumeOrder = new String[consumables.getLength()];
		for(int i = 0; i < consumables.getLength(); i++)
		{
			consumeOrder[i]=consumables.item(i).getTextContent();

			//If base prices isn't set then set it because you have atleast one house that will buy this consumable.
			//Other wise increase the price because you have ANOTHER housing pod that will buy this consumable.
			if(city.BasePrices().get(consumeOrder[i])==0){
				city.BasePrice(consumeOrder[i],stockPrices.get(consumeOrder[i]));
			}else{
				city.incBasePrice(consumeOrder[i],1);
			}
		}

		NodeList luxury = elem.getElementsByTagName("Luxury");
		String[] luxOrder = new String[luxury.getLength()];
		for(int i = 0; i < luxury.getLength(); i++)
		{
			luxOrder[i]=luxury.item(i).getTextContent();

			//If base prices isn't set then set it because you have atleast one house that will buy this lux.
			//Other wise increase the price because you have ANOTHER housing pod that will buy this lux.
			if(city.BasePrices().get(luxOrder[i])==0){
				city.BasePrice(luxOrder[i],stockPrices.get(luxOrder[i]));
			}else{
				city.incBasePrice(luxOrder[i],1);
			}
		}

		//Hard coded for water because everyone needs water!
		//Each housing unit will increase the base price since you'll be drinking more of it.
		if(city.BasePrices().get("Water")==0){
			city.BasePrice("Water",stockPrices.get("Water"));
		}else{
			city.incBasePrice("Water",1);
		}

		return new Housing(id,
				elem.getElementsByTagName("Description").item(0).getTextContent(),
				x,
				y,
				consumeOrder,
				luxOrder,
				city,
				hrc,
				Integer.parseInt(elem.getAttribute("tax")),
				Integer.parseInt(elem.getAttribute("max"))
		);
	}


	public void processSubBattle()
	{
		// TODO Auto-generated method stub
	}
	public void processMyEstate()
	{
		// TODO Auto-generated method stub
	}
}
