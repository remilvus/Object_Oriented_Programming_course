package agh.cs.tanks.objects;

import agh.cs.tanks.map.basic_logic.Directions;
import agh.cs.tanks.map.Map;
import agh.cs.tanks.map.basic_logic.MapDirection;
import agh.cs.tanks.map.basic_logic.Vector2d;

import java.awt.*;

public abstract class DynamicObject extends MapObject {
    protected MapDirection direction;
    protected boolean is_immobilized = false;
    protected final Map map;

    protected DynamicObject(Vector2d position, MapDirection direction, Map map, Color color, int destruction_damage) {
        super(position, color, destruction_damage);
        this.direction = direction;
        this.map = map;
    }

    protected DynamicObject(int x, int y, MapDirection direction, Map map, Color color, int destruction_damage) {
        this(new Vector2d(x, y), direction, map, color, destruction_damage);
    }

    public boolean move(Directions direction){
        if (!is_immobilized){
            Vector2d v = null;
            switch (direction){
                case FORWARD:
                    v = this.direction.toUnitVector();
                    break;
                case BACK:
                    v = this.direction.toUnitVector().opposite();
                    break;
                case RIGHT:
                    this.direction = this.direction.previous();
                    break;
                case LEFT:
                    this.direction = this.direction.next();
            }
            if (v!=null && map.moveTo(this.position, this.position.add(v))){
                this.position = this.position.add(v);
                return true;
            }
        }
        return false;
    }

    public MapDirection getDirection(){
        return direction;
    }
}
