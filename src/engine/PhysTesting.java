package engine;

import javafx.scene.Camera;
import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jbox2d.dynamics.*;
import org.jbox2d.common.*;
import org.jbox2d.collision.*;


public class PhysTesting extends BasicGame
{

    AABB aabb;
    Vec2 gravity;
    Camera camera;
    boolean doSleep;
    World world;

    public PhysTesting(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer game) throws SlickException {
        game.setTargetFrameRate(60);

    }

    @Override
    public void update(GameContainer game, int i) throws SlickException {
        System.out.println("pickle");
    }

    @Override
    public void render(GameContainer game, Graphics g) throws SlickException {

    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer agc2;
            agc2 = new AppGameContainer(new PhysTesting("Physics"));
            agc2.setDisplayMode(1600, 900, false);
            agc2.start();


        }
        catch (SlickException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}