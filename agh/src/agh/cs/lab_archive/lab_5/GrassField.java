package agh.cs.lab_archive.lab_5;

import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.MapBasicLogic.AbstractWorldMap;
import agh.cs.project.LivingBeingsLogic.Grass;

import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    private Grass[] grass;
    final private int max_Grass;
    private int current_Grass;;

    public GrassField(int Grass_num){
        super();
        this.max_Grass = Grass_num;
        this.current_Grass = 0;
        
        int max = (int) sqrt(10*Grass_num);
        Random rand = new Random();
        while(this.current_Grass < max_Grass){
            Vector2d v = new Vector2d(rand.nextInt(max), rand.nextInt(max));
            if (!(objectAt(v) instanceof Grass)){
                Grass grass = new Grass(v);
                this.place(grass);
                this.grassHashMap.put(grass.getPosition(), grass);
                this.current_Grass += 1;
            }
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object o = this.animalHashMap.get(position);
        if (o != null) return o;
        return this.grassHashMap.get(position);
    }

    @Override
    public String toString() {
        int animals_size = this.animalHashMap.size();
        if (animals_size==0){return "";}
        Vector2d lower_left = boundary.getLowerLeft();
        Vector2d upper_right = boundary.getUpper_right();
        return this.visualizer.draw(lower_left, upper_right);
    }
}
