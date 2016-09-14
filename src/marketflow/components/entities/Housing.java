package marketflow.components.entities;

import marketflow.econ.Stock;
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
		super(id, desc, st_ref, null, null, x, y);

		_home =city;
		_populationMax = maxpop;
		_taxRate =tax;
		_consumeOrder = consumes;
		_luxuryOrder = lux;
		//console=true;
	}
	City Home()
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
					i--;//Buy one more if you can!
				}
			}
		}
		if(excess)
		{//If you've got all the food you need. BUY LUXURY!
            for (String a_luxuryOrder : _luxuryOrder) {
                if (Buy(_home, a_luxuryOrder, _home.Price(a_luxuryOrder), 1)) {
                    log("Bought " + a_luxuryOrder + " for $" + _home.Price(a_luxuryOrder));
                    break;
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
			int amt = Integer.MAX_VALUE;
			String toEat = null;
            for (String a_consumeOrder : _consumeOrder) {
                if (Resource(a_consumeOrder) > 0 && amt > Resource(a_consumeOrder)) {
                    toEat = a_consumeOrder;
                    amt = Resource(toEat);
                }
            }
			if(toEat!=null) {
				incResource(toEat, -1);
				log("Ate 1 " + toEat);
			}
			else {
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
