package agh.cs.project.MapBasicLogic;

import agh.cs.lab_archive.lab_7.IPositionChangeObserver;
import agh.cs.lab_archive.lab_7.MapBoundary;
import agh.cs.project.*;
import agh.cs.project.LivingBeingsLogic.Animal;
import agh.cs.project.LivingBeingsLogic.Grass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    final protected List<Animal> animals = new ArrayList<>();
    protected MapVisualizer visualizer;
    protected HashMap<Vector2d, Animal> animalHashMap = new HashMap<Vector2d, Animal>();
    protected HashMap<Vector2d, Grass> grassHashMap = new HashMap<Vector2d, Grass>();
    protected MapBoundary boundary = new MapBoundary();

    public AbstractWorldMap(){
        visualizer = new MapVisualizer(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return this.objectAt(position) instanceof Grass || this.objectAt(position) == null;
    }


    @Override
    public void run(MoveDirection[] directions) {
        int i = 0;
        for (Animal a: animals){
            a.move(directions[i]);
            i++;
            if (i == directions.length) {break;}
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    @Override
    public boolean place(MapElement element) {
        if (element instanceof Animal && this.canMoveTo(element.getPosition())) {
            Animal animal = (Animal) element;
            animal.addObserver(this);
            animal.addObserver(this.boundary);
            this.animalHashMap.put(element.getPosition(), animal);
            this.boundary.add(animal, this);
            return true;
        } else if(element instanceof Grass && this.canMoveTo(element.getPosition())){
            Grass grass = (Grass) element;
            grassHashMap.put(grass.getPosition(),  grass);
            this.boundary.add(grass, this);
            return true;
        }
        throw new IllegalArgumentException("There is already an object at "+ element.getPosition().toString());
    }


    @Override
    public Object objectAt(Vector2d position) {
        Object anim = this.animalHashMap.get(position);
        if (anim != null) {return anim;}
        return this.grassHashMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal a = animalHashMap.get(oldPosition);
        animalHashMap.remove(oldPosition);
        animalHashMap.put(newPosition, a);
        if (objectAt(newPosition) instanceof Grass){
            LivingPlace place = ((Grass) objectAt(newPosition)).living_place;
            grassHashMap.remove(newPosition);
        }
    }

    public abstract static class MapElement {
        protected Vector2d position;

        public Vector2d getPosition() {
            return this.position.clone();
        }

    }
}
