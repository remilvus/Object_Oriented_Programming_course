package agh.cs.tanks.map.basic_logic;

import agh.cs.tanks.map.Map;

import java.util.Random;

public class MapArea {
    public final Vector2d lower_left;
    public final Vector2d upper_right;
    private int width;
    private int height;
    private Map map;
    private Random randomizer = new Random();

    public MapArea(Vector2d lower_left, Vector2d upper_right){
        this.lower_left = lower_left;
        this.upper_right = upper_right;
        width = Math.abs(upper_right.x - lower_left.x) + 1;
        height = Math.abs(upper_right.y - lower_left.y) + 1;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public boolean isInside(Vector2d position){
        return position.follows(lower_left) && position.precedes(upper_right);
    }

    public Vector2d getAvailablePosition(){
        Vector2d available = new Vector2d(randomizer.nextInt(width), randomizer.nextInt(height)).add(lower_left);
        int i=0;
        while(map.objectAt(available) != null){
            available = new Vector2d(randomizer.nextInt(width), randomizer.nextInt(height)).add(lower_left);
            i +=1;
            if (i==10000){
                return null;
            }
        }
        return available;
    }
}
