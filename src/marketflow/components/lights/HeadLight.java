package marketflow.components.lights;

import engine.Game;
import marketflow.components.entities.Entity;
import org.newdawn.slick.Color;

public class HeadLight extends Light
{
    private Entity _owner;
    @SuppressWarnings("unused")
    public HeadLight(Entity owner, int x, int y, float radius) {
        super(x, y, radius);
        _owner=owner;
        _relX=_owner.relX()+_owner.Width()/2;
        _relY=_owner.relY()+_owner.Height()/2;
    }

    public HeadLight(Entity owner, int x, int y, float radius, Color tint) {
        super(x, y, radius, tint);
        _owner=owner;
        _relX=_owner.relX()+_owner.Width()/2;
        _relY=_owner.relY()+_owner.Height()/2;
    }

    public void update(int count)
    {
        _relX=_owner.relX()+_owner.Width()/2;
        _relY=_owner.relY()+_owner.Height()/2;

        if (hasHitbox)
        {
            _hitbox.setX(_relX-_radius);
            _hitbox.setY(_relY-_radius);
            renderable = Game.mf.Map().ViewPort().contains(_hitbox)||Game.mf.Map().ViewPort().intersects(_hitbox);
        }
    }
}
