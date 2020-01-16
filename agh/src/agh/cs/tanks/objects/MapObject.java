package agh.cs.tanks.objects;

import agh.cs.tanks.map.basic_logic.Vector2d;

import java.awt.*;

public abstract class MapObject {
    protected boolean is_penetrable = false;
    protected boolean is_destructible = true;
    protected Vector2d position;
    protected Color color = Color.BLACK;
    protected int destruction_damage;

    protected MapObject(Vector2d position, Color color, int destruction_damage){
        this.position = position;
        this.color = color;
        this.destruction_damage = destruction_damage;
    }

    protected MapObject(int x, int y, Color color, int destruction_damage){
        this(new Vector2d(x, y), color, destruction_damage);
    }

    public void setPenetrability(boolean value){
        is_penetrable = value;
    }

    public void setDestructibility(boolean value){
        is_destructible = value;
    }

    public boolean isPenetrable(){
        return is_penetrable;
    }

    public boolean isDestructible(){
        return is_destructible;
    }

    public Vector2d getPosition(){return position;}

    public abstract void paint(Graphics g, int x, int y, int size);

    public void dealDamage(int d){
        if (is_destructible){
            this.destruction_damage -=d;
        }
    }

    public boolean isDestroyed(){
        return destruction_damage <= 0;
    }
}
