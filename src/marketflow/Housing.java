package marketflow;

import java.awt.*;
import java.util.Map;

public class Housing extends Entity
{

	private Map<String, City> cityRef;
	private City home;
	private float taxRate = 1.5f;
	
	public Housing(String id, String desc, int x, int y, String location, Map<String, City> c_ref, Map<String, Stock> st_ref, int pm)
	{
		super(id, desc, st_ref, x, y);
		
		cityRef = c_ref;
		home=cityRef.get(location);
		PopulationMax = pm;
	}
	public City Home()
	{
		return home;
	}

	public void update(int count)
	{

	}

	public void tick(int count)
	{
		float multiplier = (float)Population/(float)PopulationMax;

		//System.out.println(Population + " - " + multiplier);
	}

	public void render(Graphics g){}
}
