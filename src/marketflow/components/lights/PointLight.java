package marketflow.components.lights;

import org.newdawn.slick.Color;

public class PointLight extends Light
{
    @SuppressWarnings("unused")
    PointLight(int x, int y, float radius, float lux) {
        super(x, y, radius, lux);

    }
    @SuppressWarnings("unused")
    PointLight(int x, int y, float radius, float lux, Color tint) {
        super(x, y, radius, lux, tint);
    }



}
