package misc;

public class Vector
{
    private int magnitude;
    private double angle;

    public Vector(int mag, double ang)
    {
        magnitude=mag;
        angle=ang;
    }

    public void incMagnitude(int m){magnitude += m;}
    public void magnitude(int m){magnitude = m;}
    public int magnitude(){return magnitude;}

    public void incAngle(double a){angle += a;}
    public void angle(double a){angle = a;}
    public double angle(){return angle;}
}
