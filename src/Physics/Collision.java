package Physics;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by James on 9/14/2016.
 */
public class Collision
{
    public Vector2f collCheck(Polygon poly1, Polygon poly2)
    {
        ArrayList<Vector2f> collPoints = new ArrayList<Vector2f>();
        Vector2f returnV = null;

        for(int c=0;c<poly2.getPointCount();c++)
        {
            if(poly1.contains(poly2.getPoint(c)[0],poly2.getPoint(c)[1]))
            {
                collPoints.add(new Vector2f(poly2.getNormal(c)[0],poly2.getNormal(c)[1]));
            }
        }
        if(collPoints.size()<3 && collPoints.size()>1)
        {
            for(int s=0; s<2; s++)// s<collPoints.size(); s++)
            {
                //prolly need to average all equally instead of 2 and 1 and 1 and etc
                returnV = new Vector2f(collPoints.get(s).dot(collPoints.get(s+1)));
                s++;
                System.out.println("COLLIDE!");
            }
        }else if(collPoints.size()==1){
            returnV = new Vector2f(collPoints.get(0));
        }else return null;
        return returnV;
    }
}
