package agh.cs.project.LivingBeingsLogic;

import agh.cs.project.MapBasicLogic.AbstractWorldMap;
import agh.cs.project.MapBasicLogic.LivingPlace;
import agh.cs.project.MapBasicLogic.Vector2d;

public class Grass extends AbstractWorldMap.MapElement {

    public int energy = 10;

    public LivingPlace living_place = LivingPlace.UNSPECIFIED;

    public Grass(Vector2d position){
        this.position = position;
    }

    public String toString(){
        return "*";
    }
}
