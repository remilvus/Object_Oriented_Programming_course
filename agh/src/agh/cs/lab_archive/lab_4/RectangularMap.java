package agh.cs.lab_archive.lab_4;

import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.MapBasicLogic.AbstractWorldMap;
import agh.cs.project.MapVisualizer;

public class RectangularMap extends AbstractWorldMap {
    private final static Vector2d lower_left = new Vector2d(0,0);
    private final Vector2d upper_right;

    public RectangularMap(int width, int height){
        this.upper_right = new Vector2d(width, height);
        this.visualizer = new MapVisualizer(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(this.lower_left) && position.precedes(this.upper_right) && super.canMoveTo(position));
    }

    public String toString(){
        String result = this.visualizer.draw(this.lower_left, this.upper_right);
        return result;
    }

    public Vector2d get_lower_left(){
        return this.lower_left;
    }

    public Vector2d get_upper_right(){
        return this.upper_right;
    }
}
