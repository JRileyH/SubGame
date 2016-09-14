
package marketflow.components.entities;

import marketflow.econ.Stock;
import org.newdawn.slick.*;

import java.util.Map;

public class Generator extends Entity
{
	private enum State
	{
		LOADING,//Buying up the input ingredients
		PRODUCING,//Workers are creating the product until conditions are no longer favorable for production
		UNLOADING,//Bringing goods to market
	}
	private State _state;
	private int _cost;							//cost per worker to produce product
	private int _output;						//how many product you can output per time unit
	private int _time;							//how long it takes to produce output
	private String[] _inputs;					//list of required input resources to produce
	private String _product;					//output resource name

	private City _home;							//
	
	public Generator(String id, String desc, int x, int y, int cost, int output, String[] inputs, String product, City home, Map<String, Stock> st_ref, int time, int maxpop)
	{
		super(id, desc, st_ref, null, null, x, y);
		_state = State.PRODUCING;
		_inputs = inputs;
		_output = output;
		_product = product;
		_home = home;
		_cost = cost;
		_time = time;
		_populationMax = maxpop;
		//console=true;
	}

	private String Product()
	{
		return _product;
	}
    @SuppressWarnings("unused")
	public City Home() {return _home;}

	private boolean Procure()
	{//Buy input goods from city market
		//Find most needed input
		String Cargo = null;//What cargo will you buy today?
		int amt = Integer.MAX_VALUE;//How much are you holding?

        for (String _input : _inputs) {
            if (Resource(_input) < amt) {//Buy the resource you have the least of
                Cargo = _input;
                amt = Resource(_input);
            } else if (Resource(_input) == amt && _home.Price(_input) < _home.Price(Cargo)) {//If you have a tie then buy the cheaper one.
                Cargo = _input;
                amt = Resource(_input);
            }
        }

		if(Resource(Cargo)> _population)
		{//You've got enough already start producing
			_state = State.PRODUCING;
			return false;
		}
		else if(Buy(_home, Cargo, _home.Price(Cargo), 1))
		{//Buy cargo
			log("Bought " + Cargo + " for $" + _home.Price(Cargo) + " (" + Resource(Cargo) + ")");
			return true;
		}
		else
		{//if you can't buy for some reason start selling goods (maybe you're out of money?)
			log("Can't buy " + Cargo + " for $" + _home.Price(Cargo));
			_state=State.UNLOADING;
			return false;
		}
	}

	private boolean Produce()
	{//spend credit and input goods on producing product
		//Check if cost of production is available
		if(_credit < _cost * _population)
		{
			_state=State.UNLOADING;
			if(Population()>1){incPopulation(-1);}
			log("Not enough funds, firing employee, _population: " + Population());
			return false;
		}
        for (String _input : _inputs) {
            //Check for sufficient ingredients
            if (Resource(_input) <= 0) {
                //don't have enough ingredients
                _state = State.LOADING;
                log("Not enough Resources to produce");
                return false;
            }
        }
		//Spend cost of production
		incCredit(-_cost * _population);
        for (String _input : _inputs) {
            //Spend each ingredient resource
            incResource(_input, -1);
        }
		//produce output product
		incResource(_product, _output);

		if(Resource(_product)>= _population){_state = State.UNLOADING;}
		return true;
	}

	private boolean Vend()
	{//sell produce at city market
		if(Resource(_product)<=0)
		{//Out of Goods
			log("Out of stock");
			_state=State.PRODUCING;
			return false;
		}
		if(Sell(_home, _product,_home.Price(_product),1))
		{
			log("Sold "+ _product +" for $"+_home.Price(_product));
			return true;
		}
		else
		{
			log("Couldn't sell"+ _product +" for $"+_home.Price(_product));
		}
		return true;
	}

	public void tick(int count)
	{//One Second Logic loop
		super.tick(count);

		float multiplier = (float) _population / (float) _populationMax;
		if(count%(int)(_time /multiplier)==0)
		{
			switch (_state) {
				case LOADING:
					if(Procure()){
                        //do nothing
					}
					break;
				case PRODUCING:
					if(Produce()){
						log("Produced 1 "+Product() + ".");
					}
					break;
				case UNLOADING:
					if(Vend()){

					}
					break;
			}

		}
		logCol=oldLogCol;
	}

	@Override
	public void render(GameContainer game, Graphics g) {
		//super.render(game, g);
	}
}
