package agh.cs.project.LivingBeingsLogic;

import agh.cs.lab_archive.lab_7.IPositionChangeObserver;
import agh.cs.project.MapBasicLogic.*;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

public class Animal extends AbstractWorldMap.MapElement {
    protected MapDirection direction = MapDirection.NORTH;
    protected IWorldMap map;
    protected LinkedList<IPositionChangeObserver> observers = new LinkedList<IPositionChangeObserver>();
    protected int energy = 100;
    protected int max_energy = 100;

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    protected Animal(){};

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    protected void moveByVector(Vector2d vector){
        Vector2d end = this.position.add(vector);
        if (this.map.canMoveTo(end)){
            Object obj = map.objectAt(end);
            if (obj instanceof Grass){
                Grass g = (Grass) obj;
              //  this.energy = (this.energy + g.energy) % max_energy;
            }
            this.changePosition(this.position, end);
            this.position = end;
        }
    }

    public void move(MoveDirection move_direction) {
        switch (move_direction) {
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case FORWARD:
                this.moveByVector(this.direction.toUnitVector());
                break;
            case BACKWARD:
                this.moveByVector(this.direction.toUnitVector().opposite());
                break;
        }
    }

    public String toString(){
        switch (this.direction){
            case SOUTH_WEST:
                return "1";
            case SOUTH:
                return "2";
            case SOUTH_EAST:
                return "3";
            case WEST:
                return "4";
            case EAST:
                return "6";
            case NORTH_WEST:
                return "7";
            case NORTH:
                return "8";
            case NORTH_EAST:
                return "9";
            default:
                throw new IllegalSelectorException();
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    protected void changePosition(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver obs: observers){
            obs.positionChanged(oldPosition, newPosition);
        }
    }

    public int getEnergy(){return energy;}
}
