package Physics;
public class Point
{
    private float _x, _y;
    public Point(int x, int y)
    {
        _x=x;
        _y=y;
    }
    public Point(float x, float y)
    {
        _x=x;
        _y=y;
    }
    public Point(double x, double y)
    {
        _x=(float)x;
        _y=(float)y;
    }
    public Point(Point p)
    {
        _x=p.x();
        _y=p.y();
    }
    public float x(){return _x;}
    public void x(int x){_x=x;}
    public void x(float x){_x=x;}
    public void x(double x){_x=(float)x;}
    public float y(){return _y;}
    public void y(int y){_y=y;}
    public void y(float y){_y=y;}
    public void y(double y){_y=(float)y;}

    public void move(int x, int y){
        _x=x;
        _y=y;
    }
    public void move(float x, float y){
        _x=x;
        _y=y;
    }
    public void move(double x, double y){
        _x=(float)x;
        _y=(float)y;
    }

    public void shift(int x, int y){
        _x+=x;
        _y+=y;
    }
    public void shift(float x, float y){
        _x+=x;
        _y+=y;
    }
    public void shift(double x, double y){
        _x+=(float)x;
        _y+=(float)y;
    }


    @Override
    public String toString(){
        return "["+_x+","+_y+"]";
    }
}
