package engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

public class ImageMap
{
    public static final int PLAIN = 0;
    public static final int HOVER = 1;
    public static final int CLICK = 2;

    private static Map<String, Image[]> _imageMap;
    public ImageMap()
    {
        _imageMap = new HashMap<>();
        //TODO:Load this from xml?
        try {
            _imageMap.put("Standard", new Image[]{
                    new Image("res/ui/button.png"),
                    new Image("res/ui/hover.png"),
                    new Image("res/ui/click.png")
            });
            _imageMap.put("Close", new Image[]{
                    new Image("res/ui/x.png"),
                    new Image("res/ui/x.png"),
                    new Image("res/ui/x.png")
            });
            _imageMap.put("Minimize", new Image[]{
                    new Image("res/ui/minimize.png"),
                    new Image("res/ui/minimize.png"),
                    new Image("res/ui/minimize.png")
            });
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static Image[] get(String set)
    {
        return _imageMap.get(set);
    }
}
