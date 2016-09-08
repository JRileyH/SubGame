package subbattle;

import engine.XMLHandler;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Init
{
	private Image star;
	private Polygon starBox;
    private Polygon collisionBox;
	private int x = 200;
	private int y = 200;
    float[] points;
    boolean collision = false;

	public Init(XMLHandler xmlh)
	{
		xmlh.processSubBattle();
		try {
			star = new Image("res/star.png");
			points = new float[]{
				x+255, y+0,
				x+334, y+163,
				x+510, y+187,
				x+382, y+310,
				x+412, y+485,
				x+255, y+402,
				x+98, y+485,
				x+129, y+310,
				x+0, y+187,
				x+177, y+163,
			};
			starBox = new Polygon(points);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		points= new float[]{
		    700,300,
            1100,300,
            1100,500,
            700,500
        };
        collisionBox = new Polygon(points);
	}

	public void update(int count)
	{
		star.setRotation(star.getRotation()+1);
		starBox = (Polygon) starBox.transform(Transform.createRotateTransform((float)Math.PI/180, x+255, y+242));
        if(collisionBox.intersects(starBox)){collision=true;}else{collision=false;}
	}
	
	public void render(GameContainer game, Graphics g)
	{
		g.setColor(Color.orange);
		g.drawString("SubBattle", 100, 10);

        g.setColor(Color.red);
		g.drawImage(star, 200, 200);
        if(!collision)g.setColor(Color.green);
        g.draw(starBox);
        if(!collision)g.setColor(Color.blue);
        g.draw(collisionBox);
        g.setColor(Color.black);
	}

	public void tick(int count) {

	}
}
