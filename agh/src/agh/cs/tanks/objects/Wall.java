package agh.cs.tanks.objects;

import agh.cs.tanks.map.basic_logic.Vector2d;

import java.awt.*;

public class Wall extends MapObject{
    private int max_damage;

    public Wall(Vector2d position, int destruction_damage) {
        super(position, Color.WHITE, destruction_damage);
        this.max_damage = destruction_damage;
    }

    public Wall(int x, int y, int destruction_damage) {
        this(new Vector2d(x, y), destruction_damage);
    }

    @Override
    public void paint(Graphics g, int x, int y, int size) {
        g.setColor(color);
        if(!is_destructible){
            g.setColor(new Color(100,70,80));
        }
        g.fillRect(x, y, size, size);
    }

    @Override
    public void dealDamage(int d) {
        super.dealDamage(d);
        float val = (float)(destruction_damage)/max_damage;
        color = new Color(val, val, val);
    }
}
