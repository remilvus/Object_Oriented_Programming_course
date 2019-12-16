package agh.cs.lab_archive.lab_7;

import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.MapBasicLogic.AbstractWorldMap;

public class PositionAndElement {
    public Vector2d position;
    public AbstractWorldMap.MapElement element;

    public PositionAndElement(Vector2d position, AbstractWorldMap map){
        this.position = position;
        this.element = (AbstractWorldMap.MapElement) map.objectAt(position);
    }
}
