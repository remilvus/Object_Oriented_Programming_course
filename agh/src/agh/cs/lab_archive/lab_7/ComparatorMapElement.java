package agh.cs.lab_archive.lab_7;

import agh.cs.project.LivingBeingsLogic.Animal;
import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.LivingBeingsLogic.Grass;

import java.util.Comparator;

public class ComparatorMapElement implements Comparator<PositionAndElement> {
    int compareBy;
    ComparatorMapElement (String axis){
        if (axis.equals("y")){
            compareBy = 1;
        } else {
            compareBy = 0;
        }
    }

    @Override
    public int compare(PositionAndElement o1, PositionAndElement o2) {
        if (o1 == null || o2 == null){return 0;};
        if (o1.element == o2.element) {return 0;}
        Vector2d p1 = o1.position;
        Vector2d p2 = o2.position;
        if (compareBy == 0){
            if (p1.x < p2.x){
                return -1;
            }
            if (p1.x > p2.x){
                return 1;
            }
            if (p1.y < p2.y){
                return -1;
            }
            if (p1.y > p2.y){
                return 1;
            }
            if (o1.element instanceof Grass && o2.element instanceof Animal){
                return 1;
            }
            if (o2.element instanceof Grass && o1.element instanceof Animal){
                return -1;
            }
            throw new IllegalStateException("Two instances of MapElement at " + p1.toString());
        } else {
            if (p1.y < p2.y){
                return -1;
            }
            if (p1.y > p2.y){
                return 1;
            }
            if (p1.x < p2.x){
                return -1;
            }
            if (p1.x > p2.x){
                return 1;
            }
            if (o1.element instanceof Grass && o2.element instanceof Animal){
                return 1;
            }
            if (o2.element instanceof Grass && o1.element instanceof Animal){
                return -1;
            }
             throw new IllegalStateException("Two instances of MapElement at " + p1.toString());
        }
    }
}
