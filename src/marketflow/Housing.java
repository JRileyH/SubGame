package marketflow;

import org.newdawn.slick.*;

import java.util.Map;

public class Housing extends Entity
{
	private City _home;
	private int _taxRate;
	private String[] _consumeOrder;
	private String[] _luxuryOrder;
	
	public Housing(String id, String desc, int x, int y, String[] consumes, String[] lux, City city, Map<String, Stock> st_ref, int tax, int maxpop)
	{
		super(id, desc, st_ref, x, y,null);

		_home =city;
		_populationMax = maxpop;
		_taxRate =tax;
		_consumeOrder = consumes;
		_luxuryOrder = lux;
		//console=true;
	}
	public City Home()
	{
		return _home;
	}
	@Override
	public void update(int count) {
		//super.update(count);
	}
	@Override
	public void tick(int count) {
		super.tick(count);

		float multiplier = (float) _population /(float) _populationMax;

		if(Resource("Water")<Population()) {
			if(Buy(_home,"Water", _home.Price("Water"),1)) {
				log("Bought Water for $" + _home.Price("Water"));
			}
		}

		boolean excess = true;
		for(int i = 0; i < _consumeOrder.length; i++)
		{
			if(Resource(_consumeOrder[i])<Population())
			{
				excess=false;
				if(Buy(_home, _consumeOrder[i], _home.Price(_consumeOrder[i]),1))
				{
					log("Bought "+ _consumeOrder[i]+" for $"+ _home.Price(_consumeOrder[i]));
					i--;
					excess=true;
				}
				else
				{
					//log("Tried but couldn't buy "+_consumeOrder[i]);
				}
			}
		}
		if(excess)
		{//If you've got all the food you need. BUY LUXURY!
			for (int i = 0; i < _luxuryOrder.length; i++)
			{
				if(Buy(_home, _luxuryOrder[i], _home.Price(_luxuryOrder[i]),1))
				{
					log("Bought "+ _luxuryOrder[i]+" for $"+ _home.Price(_luxuryOrder[i]));
					break;
				}
				else
				{
					//log("Tried but couldn't buy "+_luxuryOrder[i]);
				}
			}
		}

		if(count%(int)(10/multiplier)==0)
		{
			if(Resource("Water")<=0)
			{
				incPopulation(-1);
				log("Dying of thirst. "+ _population);
			}
			else
			{
				incResource("Water",-1);
				log("Drank 1 Water.");
			}
			boolean ateSomething = false;
			for(int i = 0; i < _consumeOrder.length; i++) {
				if (Resource(_consumeOrder[i])>0) {
					incResource(_consumeOrder[i],-1);
					log("Ate 1 "+ _consumeOrder[i]);
					ateSomething=true;
					break;
				}
			}
			if(!ateSomething){
				incPopulation(-1);
				log("Dying of Hunger. "+ _population);
			}
		}

		if(count%(int)(100/multiplier)==0&& _population < _populationMax)
		{
			incPopulation(1);
			log("Baby was born! "+ _population);
		}

		if(count%100==0)
		{
			incCredit(_population * _taxRate);
			log("Taxes Collected! $"+ _credit +" (+$"+ _population * _taxRate +")");
		}
		logCol=oldLogCol;
	}
	@Override
	public void render(GameContainer game, Graphics g) {
		//super.render(game, g);
	}
}
