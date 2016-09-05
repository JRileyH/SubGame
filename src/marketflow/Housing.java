package marketflow;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

public class Housing extends Entity
{

	private Map<String, City> cityRef;
	private City home;
	private int taxRate;
	private String[] consumeOrder;
	private String[] luxuryOrder;
	
	public Housing(String id, String desc, int x, int y, String[] consums, String[] lux, String location, Map<String, City> c_ref, Map<String, Stock> st_ref, int tax, int pm)
	{
		super(id, desc, st_ref, x, y);
		
		cityRef = c_ref;
		home=cityRef.get(location);
		PopulationMax = pm;

		taxRate=tax;
		consumeOrder = consums;
		luxuryOrder = lux;
	}
	public City Home()
	{
		return home;
	}

	private void log(String msg)
	{
		if(ID.equals("Sweat Bunks"))
		{
			System.out.println(ID+": "+msg);
		}
	}

	public void update(int count)
	{

	}

	public void tick(int count)
	{
		float multiplier = (float)Population/(float)PopulationMax;

		if(Resource("Water")<Population()) {
			Buy(home,"Water",home.Price("Water"),1);
		}

		boolean excess = true;
		for(int i = 0; i < consumeOrder.length; i++)
		{
			if(Resource(consumeOrder[i])<Population())
			{
				excess=false;
				if(Buy(home,consumeOrder[i],home.Price(consumeOrder[i]),1))
				{
					log(consumeOrder[i]+": "+Resource(consumeOrder[i])+" for $"+home.Price(consumeOrder[i]));
					log("Now has $"+Credit());
					i--;
					excess=true;
				}
				else
				{
					//log("Tried but couldn't buy "+consumeOrder[i]);
				}
			}
		}
		if(excess)
		{//If you've got all the food you need. BUY LUXURY!
			for (int i = 0; i < luxuryOrder.length; i++)
			{
				if(Buy(home,luxuryOrder[i],home.Price(luxuryOrder[i]),1))
				{
					log(luxuryOrder[i]+": "+Resource(luxuryOrder[i])+" for $"+home.Price(luxuryOrder[i]));
					log("Now has $"+Credit());
					break;
				}
				else
				{
					//log("Tried but couldn't buy "+luxuryOrder[i]);
				}
			}
		}

		if(count%(int)(10/multiplier)==0)
		{
			if(Resource("Water")<=0)
			{
				incPopulation(-1);
				log("Dying of thirst. "+Population);
			}
			boolean ateSomething = false;
			for(int i = 0; i < consumeOrder.length; i++) {
				if (Resource(consumeOrder[i])>0) {
					incResource(consumeOrder[i],-1);
					ateSomething=true;
					break;
				}
			}
			if(!ateSomething){
				incPopulation(-1);
				log("Dying of Hunger. "+Population);
			}
		}

		if(count%(int)(100/multiplier)==0)
		{
			incPopulation(1);
			log("Baby was born! "+Population);
		}

		if(count%100==0)
		{
			incCredit(Population*taxRate);
			log("Taxes Collected! $"+Credit+" (+$"+Population*taxRate+")");
		}
	}

	public void render(Graphics g){}
}
