package agh.cs.project.LivingBeingsLogic;

import agh.cs.project.MapBasicLogic.AbstractWorldMap;
import agh.cs.project.MapBasicLogic.Vector2d;
import javafx.util.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class SavannaAnimalSet extends AbstractWorldMap.MapElement {
    private final Collection<SavannaAnimal> animals = new HashSet<SavannaAnimal>();

    protected SavannaAnimalSet(Vector2d position){
        this.position = position;
    }

    public SavannaAnimalSet(Vector2d position, SavannaAnimal first, SavannaAnimal second) {
        this.position = position;
        assert !first.equals(second);
        animals.add(first);
        animals.add(second);
    }

    public Iterator<SavannaAnimal> iterator() {
        return animals.iterator();
    }

    public Object[] toArray() {
        return animals.toArray();
    }

    public void add(SavannaAnimal a) {
        animals.add(a);
    }

    public void remove(SavannaAnimal a) {
        animals.remove(a);
    }

    public int size(){return animals.size();}

    public Pair<SavannaAnimal, SavannaAnimal> getTopPair() { // returns animals with maximal energy
        SavannaAnimal a1 = null, a2 = null;
        for (SavannaAnimal a : animals) {
            if (a1 == null) {
                a1 = a;
                continue;
            }
            if (a2 == null) {
                a2 = a;
                continue;
            }
            if(a1.getEnergy() < a.getEnergy() || a2.getEnergy() < a.getEnergy()){
                if(a1.getEnergy() < a2.getEnergy()){a1 = a;}
                else {a2 = a;}
            }
        }
        return new Pair<SavannaAnimal, SavannaAnimal>(a1, a2);
    }

    public SavannaAnimalSet getAllWith(int energy){
        // use when animals in top_pair have the same amount of energy
        SavannaAnimalSet equal_energy = new SavannaAnimalSet(position);
        for (SavannaAnimal animal: animals){
            if (animal.getEnergy() == energy){equal_energy.add(animal);}
        }
        return equal_energy;
    }

    @Override
    public String toString() {
        return "#";
    }
}
