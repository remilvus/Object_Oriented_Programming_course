package agh.cs.lab_archive.lab_7;

import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.MapBasicLogic.AbstractWorldMap;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver {
    private Vector2d lower_left;
    private Vector2d upper_right;
    Comparator comparator_X = new ComparatorMapElement("x");
    Comparator comparator_Y = new ComparatorMapElement("y");
    private TreeSet<PositionAndElement> elements_x = new TreeSet<PositionAndElement>(comparator_X);
    private TreeSet<PositionAndElement> elements_y = new TreeSet<PositionAndElement>(comparator_Y);
    AbstractWorldMap map;

    public void add(AbstractWorldMap.MapElement element, AbstractWorldMap map){
        PositionAndElement posEm = new PositionAndElement(element.getPosition(), map);
        elements_x.add(posEm);
        elements_y.add(posEm);
        this.map = map;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        PositionAndElement posEm = new PositionAndElement(oldPosition, this.map);
        elements_x.remove(posEm);
        elements_y.remove(posEm);
        PositionAndElement posEm2 = new PositionAndElement(newPosition, this.map);
        elements_x.add(posEm2);
        elements_y.add(posEm2);
    }

    public Vector2d getLowerLeft(){
        return elements_x.first().position.lowerLeft(elements_y.first().position);
    }

    public Vector2d getUpper_right(){
        return elements_x.last().position.upperRight(elements_y.last().position);
    }
}
