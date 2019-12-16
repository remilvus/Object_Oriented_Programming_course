package agh.cs.project.LivingBeingsLogic;

import agh.cs.lab_archive.lab_7.IPositionChangeObserver;
import agh.cs.project.MapBasicLogic.MapDirection;
import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.SavannaMap;

public class SavannaAnimal extends Animal {
    private int energy;
    private final int max_energy;
    private int move_energy;
    private Genes genes;
    private final SavannaMap map;
    private final int ENERGY_PER_NEW;

    public SavannaAnimal(SavannaMap map) {
        this(map, new Vector2d(2, 2), 100,100, 1);
    }

    public SavannaAnimal(SavannaMap map, Vector2d v) {
        this(map, v, 100, 100, 1);
    }

    public SavannaAnimal(SavannaMap map, Vector2d initialPosition, int energy, int max_energy, int move_energy){
        super();
        this.map = map;
        this.position = initialPosition;
        genes = new Genes();
        assert energy > 0;
        this.energy = energy;
        this.max_energy = max_energy;
        this.move_energy = move_energy;
        ENERGY_PER_NEW = max_energy/4;
    }

    public SavannaAnimal(SavannaAnimal first, SavannaAnimal second, Vector2d initialPosition, int direction){
        this(first.map, initialPosition, 2 * first.ENERGY_PER_NEW, first.max_energy, first.move_energy);
        assert first != second;
        first.energy -= first.ENERGY_PER_NEW;
        second.energy -= second.ENERGY_PER_NEW;
        this.genes = first.genes.combine(second.genes);
        this.direction = MapDirection.fromInt(direction);
    }

    public boolean canReproduce(){
        return energy >= max_energy * 0.5;
    }

    public void moveOneself(){
        int decision = genes.decision();
        int current_direction = MapDirection.toInt(this.direction);
        this.direction = MapDirection.fromInt(current_direction + decision);
        moveByVector(this.direction.toUnitVector());
    }
    public boolean is_alive(){
        if (energy <= 0) {
            return false;
        }
        return true;
    }

    public void feed(int food_energy){
        energy += food_energy;
        if (energy > max_energy) {energy = max_energy;}
    }

    @Override
    protected void moveByVector(Vector2d vector){
        Vector2d end = this.position.add(vector);
        end = map.checkAndCorrectPosition(end);
        if (this.map.canMoveTo(end)){
            energy -= move_energy;
            this.changePosition(this.position, end);
            this.position = end;
        }
    }

    public int getEnergy(){return energy;}

    public void setEnergy(int energy){this.energy = energy;}

    public boolean isStronger(SavannaAnimal other){
        return this.energy > other.energy;
    }

    @Override
    protected void changePosition(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver obs: observers){
            obs.positionChanged(oldPosition, newPosition, this);
        }
    }

    public String getGenes(){
        return genes.toString();
    }
}
