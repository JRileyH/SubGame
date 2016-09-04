package misc;

/**
 * Created by James on 9/3/2016.
 */
public class Vector
{
    private int magnitude = 0;
    private double angle = 0;

    public void magDrop()
    {
        if(magnitude>0)
        {
            magnitude-=1;
        }
        if(magnitude<0)
        {
            magnitude+=1;
        }
    }
    public void incMagnitude(int m){magnitude += m;}
    public void magnitude(int m){magnitude = m;}
    public int magnitude(){return magnitude;}

    public void incAngle(double a){angle += a;}
    public void angle(double a){angle = a;}
    public double angle(){return angle;}
}
