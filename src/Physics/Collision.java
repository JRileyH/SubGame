package Physics;

import marketflow.components.entities.Player;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by James on 9/14/2016.
 */
public class Collision
{
    Player _owner;
    boolean _cleared;
    /*Point collPoint = new Point(0,0);
    Vector2f testV = new Vector2f();
    ArrayList<Vector2f> collPointNorms = new ArrayList<>();*/




    public Collision(Player owner)
    {
        _owner=owner;
        _cleared = true;
    }

    public void pointToLine(Polygon poly2)
    {
        if(poly2.intersects(_owner.Hitbox()))
        {
            float[] pointdata = _owner.Hitbox().getPoints();
            Point[] points = new Point[pointdata.length/2];
            for(int i = 0; i < pointdata.length; i+=2)
            {
                points[i/2]=new Point(pointdata[i],pointdata[i+1]);
            }
            //System.out.print("[");
            for(Point p : points)
            {
                if(poly2.contains(p.x(),p.y()))
                {
                    //System.err.print(p.toString() + ", ");
                }
                else {
                    //System.out.print(p.toString() + ", ");
                }
            }
            //System.out.print("]");
            //System.out.println();

            if(_cleared){_owner.FlipTrajectory();_cleared=false;}




                    /*System.out.println("intersects!");
            for(int c=0;c<_owner.Hitbox().getPointCount();c++)
            {
                if(poly2.contains(_owner.Hitbox().getPoint(c)[0],_owner.Hitbox().getPoint(c)[1]))
                {
                    System.out.println("C: "+c+" | Size: "+_owner.Hitbox().getPointCount());
                    System.out.println("points: " + _owner.Hitbox().getPoint(c)[0] + "," + _owner.Hitbox().getPoint(c)[1]);
                    collPoint.x(_owner.Hitbox().getPoint(c)[0]);
                    collPoint.y(_owner.Hitbox().getPoint(c)[1]);*/
                    //collPointNorms.add(new Vector2f(poly2.getNormal(c)[0],poly2.getNormal(c)[1]));
                    /*System.out.print(poly2.getNormal(c)[0]);
                    System.out.println(" + "+poly2.getNormal(c)[1]);
                    testV.set(_owner.Hitbox().getPoint(c)[0],_owner.Hitbox().getPoint(c)[1]);
                    System.out.println(testV.getNormal().getX()+" + "+testV.getNormal().getY());*/

                    //_owner.Velocity().setTheta(_owner.Velocity().dot(new Vector2f(poly2.getNormal(c)[0],poly2.getNormal(c)[1])));
                //}
            //}
        }
        else if(!_cleared)
        {
            _cleared=true;
        }
    }

    /*
    public Vector2f collCheck(Polygon poly1, Polygon poly2)
    {
        ArrayList<Vector2f> collPoints = new ArrayList<>();
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
    }*/


}
