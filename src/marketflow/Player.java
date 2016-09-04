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
    private boolean up = false;
    private boolean down = false;
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
            if (up && newV.magnitude() < maxSpeed) {
                newV.incMagnitude(acc);
            }
            if (down && newV.magnitude() > -maxSpeed) {
                newV.incMagnitude(-acc);
            }
        }

        //Vector Catch Up
        if ((newV.magnitude() > oldV.magnitude())) {
            oldV.incMagnitude(acc);
        } else if ((newV.magnitude() < oldV.magnitude())) {
            oldV.incMagnitude(-acc);
        }

        //Deccelartion
        /*if(count%drag==0) {
            if (!up && !down) {
                if (newV.magnitude() > dec) {
                    newV.incMagnitude(-dec);
                } else if (newV.magnitude() < -dec) {
                    newV.incMagnitude(dec);
                } else {
                    newV.magnitude(0);
                }
            }
        }*/

        //Turn
        if(left){oldV().incAngle(-tack);}
        if(right){oldV().incAngle(tack);}

        //Keep ship centered
        posX = (Game.WIDTH/2)-Game.mf.mapOffsetX;
        posY = (Game.HEIGHT/2)-Game.mf.mapOffsetY;

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
        g.setColor(Color.RED);
        g.drawLine((Game.WIDTH/2),(Game.HEIGHT/2),maxX,maxY);
        g.setColor(Color.BLUE);
        g.drawLine((Game.WIDTH/2),(Game.HEIGHT/2),destX,destY);
        g.setColor(Color.BLACK);
    }

    public void up(boolean u){up=u;}
    public boolean up(){return up;}
    public void down(boolean d){down=d;}
    public boolean down(){return down;}
    public void left(boolean l){left=l;}
    public boolean left(){return left;}
    public void right(boolean r){right=r;}
    public boolean right(){return right;}

    public Vector oldV(){return oldV;}
    public Vector newV(){return newV;}
}
