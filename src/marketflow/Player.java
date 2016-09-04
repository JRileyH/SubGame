package marketflow;

import engine.Game;
import misc.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by Nark on 9/3/2016.
 */
public class Player extends Entity
{
    private boolean initUP = false;
    private boolean initDOWN = false;

    private boolean left = false;
    private boolean right = false;

    private Vector oldV = new Vector(0, Math.PI/2);        // current vector
    private Vector newV = new Vector(0, Math.PI/2);        // destination vector

    private int maxSpeed=14;
    private int speed=0;
    private int acc=1;
    private int dec=1;
    private int drag = 20;
    private double tack=0.01;
    public int maxViewDistance=300;

    private int destX=0,maxX=0;
    private int destY=0,maxY=0;


    public Player(String id, String desc, Map<String, Stock> st_ref, int x, int y)
    {
        super(id, desc, st_ref, x, y);
        img = Game.gfx.load("res/ship.png");
        //hitbox=new Rectangle(posX-img.getWidth()/2, posY-img.getHeight()/2, img.getWidth(), img.getHeight());
    }

    public void update(int count)
    {
        //Acceleration
        if(count%drag==0) {

        }

        //Vector Catch Up
        if ((newV.magnitude() > oldV.magnitude())) {
            oldV.incMagnitude(acc);
        } else if ((newV.magnitude() < oldV.magnitude())) {
            oldV.incMagnitude(-acc);
        }

        //Turn
        if(left){oldV().incAngle(-tack);}
        if(right){oldV().incAngle(tack);}

        //Keep ship centered
        posX = (Game.WIDTH/2)-Game.mf.map.OffsetX();
        posY = (Game.HEIGHT/2)-Game.mf.map.OffsetY();

        destX=(int)((Game.WIDTH/2)-10*oldV.magnitude()*Math.cos(oldV.angle()));
        destY=(int)((Game.HEIGHT/2)-10*oldV.magnitude()*Math.sin(oldV.angle()));

        maxX=(int)((Game.WIDTH/2)-10*maxSpeed*Math.cos(oldV.angle()));
        maxY=(int)((Game.HEIGHT/2)-10*maxSpeed*Math.sin(oldV.angle()));
    }

    public void tick(int count)
    {

    }

    public void render(Graphics g)
    {
        super.render(g);
        int x = (Game.WIDTH/2)+Game.mf.map.PanX();
        int y = (Game.HEIGHT/2)+Game.mf.map.PanY();

        g.setColor(Color.RED);
        g.drawLine(x,y,maxX+Game.mf.map.PanX(),maxY+Game.mf.map.PanY());
        g.setColor(Color.BLUE);
        g.drawLine(x,y,destX+Game.mf.map.PanX(),destY+Game.mf.map.PanY());
        g.setColor(Color.BLACK);
    }

    public void incSpeed()
    {
        if (newV.magnitude() < maxSpeed)
        {
            System.out.println(newV.magnitude());
            newV.incMagnitude(acc);
        }
    }

    public void decSpeed()
    {

        if (newV.magnitude() > -maxSpeed)
        {
            newV.incMagnitude(-acc);
        }
    }

    public void left(boolean l){left=l;}
    public boolean left(){return left;}
    public void right(boolean r){right=r;}
    public boolean right(){return right;}

    public Vector oldV(){return oldV;}
    public Vector newV(){return newV;}
}
