package marketflow;

import java.util.ArrayList;
import java.util.Map;

public class Ship extends Entity
{
	private enum State
	{
		WAITING,//Just sitting around...
		ASSESSING,//Determines where to go based on global prices of goods compared to local goods until profit threshold is found
		LOADING,//Converts credit to resources until either credit reserve limit is reached or prices no longer match threshold
		ENROUTE,//Jacks off until distance of city is traversed
		REASSESSING,//Determines if the profit threshold is still valid for previous transactions or if there are better deals elsewhere now
		UNLOADING//Converts resources to credit until profit of transactions are no longer favorable
	}
	private State _state;
	private Map<String, City> _cityRef;
	private City _home;
	private City _dest;
	private City _dock;
	
	private int _speed = 0;
	private int _maxSpeed;
	
	public Ship(String id, String desc, int x, int y, int spd, City location, City home, Map<String, City> c_ref, Map<String, Stock> ref, int maxpop)
	{
		super(id, desc, ref, x, y,"res/ship.png");
		_state = State.ASSESSING;
		_populationMax =maxpop;
		_cityRef=c_ref;
		_home=home;
		_dock=location;
		_dest=null;
		_maxSpeed =spd;
		console=true;
	}

	public City Location()
	{
		return _dock;
	}
	public City Destination()
	{
		return _dest;
	}
	public City Home()
	{
		return _home;
	}

	public int X(){return _posX;}
	public int Y(){return _posX;}

	int stallCount = 0;
	int stallAmt = 200;
	ArrayList<Transaction> transactions = new ArrayList<>();
	int maxTransactions=100;

	public void update(int count)
	{
		if(_state==State.ENROUTE)
		{
			//Go go go!
			double angle = Math.atan2(_dest.Y() - _posY, _dest.X() - _posX);

			_posX += _speed * Math.cos(angle);
			_posY += _speed * Math.sin(angle);

			//hitbox.setLocation(_posX+Game.mf.mapOffsetX-img.getWidth()/2, _posY+Game.mf.mapOffsetY-img.getHeight()/2);

			double a = _dest.X()-_posX;
			double b = _dest.Y()-_posY;
			if(Math.sqrt(a*a+b*b)< _speed *10)
			{//you made it!
				_speed--;
				if(_speed ==0){_state=State.UNLOADING;}
			}
			else
			{
				_speed++;
				if(_speed >= _maxSpeed){
					_speed = _maxSpeed;}
			}
		}
	}

	public void tick(int count)
	{
		super.tick(count);

		int mostProfit = 0;
		switch(_state)
		{
			case WAITING:
				if(stallCount>=stallAmt)
				{
					stallCount = 0;
					_state = State.ASSESSING;
					break;
				}
				stallCount++;
				break;

			case ASSESSING:
				_dest=null;//The city you will be travelling to
				for (City city : _cityRef.values())
				{//check each city
					//Ignore the city you're in
					if(_dock._id.equals(city._id)){continue;}

					//TODO: prevent all traders going to the same place

					int profit=0;//max amount of money you could make off this city
					if(transactions.size()<maxTransactions)
					{//don't bother checking purchase prices if your cargo is full
						for(String stock : _stockRef.keySet())
						{//check each resource
							//ignore resources that your dock city doesn't have
							if(_dock.Resource(stock)<=0){continue;}
							//ignore resources you can't afford
							if(_dock.Price(stock)> _credit){continue;}

							//find the profit to be made
							int delta = city.Price(stock) - _dock.Price(stock);
							//add each resource profit to your max profit
							if (delta > 0) {profit += delta;}
						}}
					for (Transaction xact : transactions)
					{//check your record books! Maybe you can sell stuff you already have.
						//find profit to be made
						int delta = city.Price(xact.Resource()) - xact.Price();
						//add each resource profit to your max profit
						if (delta > 0) {profit += delta;}
					}
					if(profit>mostProfit)
					{//if you've found a better city to go to..
						mostProfit=profit;
						_dest=city;
						log("Decided to go to "+city._id +" for $"+mostProfit);
					}
				}
				if(_dest==null)
				{//if you haven't found a city to go to...
					if(_dock._id.equals(_home._id))
					{//if you're already home.. just chill.
						_state = State.WAITING;
						log("Could not find profit. Waiting in "+_home._id);
						break;
					}
					//other wise.. head home to kill time for a bit
					_dest = _home;
					_state = State.ENROUTE;
					log("Could not find profit. Heading home to "+_home._id);
				}
				else
				{//start loading up for the trip!
					_state=State.LOADING;
				}
				break;

			case LOADING:
				String Cargo = null;//The cargo you will buy this tick.
				//if you've got too much cargo already... just go!
				if(transactions.size()>=maxTransactions){
					_state = State.ENROUTE;
					break;
				}

				for(String stock : _stockRef.keySet())
				{//for each resource
					//ignore resources that your dock city doesn't have
					if(_dock.Resource(stock)<=0){continue;}
					//ignore resources you can't afford
					if(_dock.Price(stock)> _credit){continue;}

					//find the profit to be made
					int delta = _dest.Price(stock) - _dock.Price(stock);
					if (delta > mostProfit)
					{//If this is the best transaction you can make...
						mostProfit = delta;
						Cargo = stock;
					}
				}
				if(Cargo==null)
				{
					_state = State.ENROUTE;
					log("Setting sail for "+_dest._id +"!");
					break;
				}

				if(Buy(_dock, Cargo, _dock.Price(Cargo), 1))
				{
					transactions.add(new Transaction(count, Cargo, _dock.Price(Cargo)));
					log("Bought "+Cargo+" for $"+_dock.Price(Cargo)+" (+$"+(_dest.Price(Cargo)-_dock.Price(Cargo))+")");
				}
				else
				{
					log("Failed to buy "+Cargo);
				}
				break;
			case ENROUTE:
				log("Enroute to "+_dest._id);
				_state=State.UNLOADING;
				break;
			case UNLOADING:
				Transaction sale = null;//the good you will be selling!
				for(Transaction xact : transactions)
				{//for each transaction in your records
					//ignore transaction if the city can't afford it.
					if(_dest.Credit()<_dest.Price(xact.Resource())){continue;}

					int delta = _dest.Price(xact.Resource())-xact.Price();
					if(delta>mostProfit)
					{
						mostProfit=delta;
						sale=xact;
					}
				}
				if(sale==null)
				{//if you're all out of profitable transactions to be had..
					_dock=_dest;
					_dest=null;
					log("Time to assess profits for my next trip...");
					_state=State.ASSESSING;
					break;
				}

				if(Sell(_dest, sale.Resource(),_dest.Price(sale.Resource()),1))
				{//try to tell the stupid stuff
					transactions.remove(sale);
					log("Sold "+sale.Resource()+" for $"+_dest.Price(sale.Resource()));
				}
				else
				{//What? for some reason you couldn't sell that resource.. so don't keep trying.
					log("Failed to sell "+sale.Resource());
				}
				break;
			case REASSESSING:
				break;
			default:
				break;
		}
		logCol=oldLogCol;
	}
}
