package agh.cs.tanks.objects;

import agh.cs.tanks.map.basic_logic.Vector2d;

import java.awt.*;

public class Water extends MapObject {
    public Water(Vector2d position) {
        super(position, Color.blue, 1);
        is_destructible = false;
        is_penetrable = true;
    }

    public Water(int x, int y){
        this(new Vector2d(x, y));
    }

    @Override
    public void paint(Graphics g, int x, int y, int size) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }
}
