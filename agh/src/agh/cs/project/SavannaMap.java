package agh.cs.project;

import agh.cs.project.LivingBeingsLogic.Grass;
import agh.cs.project.LivingBeingsLogic.SavannaAnimal;
import agh.cs.project.LivingBeingsLogic.SavannaAnimalSet;
import agh.cs.project.MapBasicLogic.AbstractWorldMap;
import agh.cs.project.MapBasicLogic.LivingPlace;
import agh.cs.project.MapBasicLogic.MapDirection;
import agh.cs.project.MapBasicLogic.Vector2d;
import javafx.util.Pair;

import java.util.*;

public class SavannaMap extends AbstractWorldMap {
    private final Vector2d lower_left = new Vector2d(0, 0);
    private Vector2d upper_right;
    private Vector2d lower_left_jungle;
    private Vector2d upper_right_jungle;
    private Vector2d jungle_dim;
    private final Random rand = new Random();
    private Set<Grass> to_eat = new HashSet<Grass>();
    private final int plant_energy;
    final public List<SavannaAnimal> animals = new ArrayList<SavannaAnimal>();
    final protected HashMap<Vector2d, SavannaAnimalSet> animal_sets = new HashMap<>(); // for locations with more than 1 animal

    public SavannaMap(int width, int height, float jungle_ratio, int start_energy, int move_energy, int plant_energy,
                      int startingAnimals)  throws IllegalArgumentException{
        super();
        this.plant_energy = plant_energy;
        calculateJungleCoordinates(width, height, jungle_ratio);
        placeAnimals(startingAnimals, start_energy, start_energy, move_energy);
    }

    private void placeAnimals(int startingAnimals, int start_energy, int max_energy, int move_energy) {
        int counter = 0;
        for(; startingAnimals > 0; startingAnimals -= 1){
            Vector2d v = new Vector2d(rand.nextInt(upper_right.x + 1), rand.nextInt(upper_right.y + 1));
            counter += 1;
            if (counter == 1000){break;} // there probably isn't any space available for a new animal

            if(objectAt(v) != null){startingAnimals+=1; continue;}
            counter = 0;
            SavannaAnimal a = new SavannaAnimal(this, v, start_energy, max_energy, move_energy);
            place(a);
        }
    }

    private void calculateJungleCoordinates(int width, int height, float jungle_ratio){
        upper_right = new Vector2d(width-1, height-1);
        int jungle_width = (int) (jungle_ratio * width);
        int jungle_height = (int) (jungle_ratio * height);
        jungle_dim = new Vector2d(jungle_width, jungle_height);
        if (jungle_height == 0 || jungle_width == 0 || jungle_height >= height || jungle_width >= width){
            throw new IllegalArgumentException("Incorrect jungle_ratio. Jungle dimensions: " +
                    new Vector2d(jungle_width, jungle_height).toString() + " | World dimensions: " +
                    new Vector2d(width, height).toString());
        }
        int jungle_start_x = (int) ((1 - jungle_ratio) / 2 * width); // 1
        int jungle_start_y = (int) ((1 - jungle_ratio) / 2 * height); // 1
        lower_left_jungle = new Vector2d(jungle_start_x, jungle_start_y); // 1, 1
        upper_right_jungle = lower_left_jungle.add(new Vector2d(jungle_width - 1, jungle_height - 1)); //9, 9

    }

    private void placeGrass(LivingPlace living_place){
        Vector2d v;
        int try_counter = 0;
        while(true){
            if (living_place == LivingPlace.JUNGLE){ // placing in jungle
                v = new Vector2d(rand.nextInt(jungle_dim.x), rand.nextInt(jungle_dim.y)).add(lower_left_jungle);
                assert (v.follows(lower_left_jungle) && v.precedes(upper_right_jungle));
            } else { // placing outside of jungle
                v = new Vector2d(rand.nextInt(upper_right.x + 1), rand.nextInt(upper_right.y + 1));
                assert v.follows(lower_left) && v.precedes(upper_right);
                if ((v.follows(lower_left_jungle) && v.precedes(upper_right_jungle))){continue;}
            }
            if (objectAt(v) == null){
                try_counter = 0;
                Grass grass = new Grass(v);
                grass.living_place = living_place;
                grass.energy = plant_energy;
                this.place(grass);
                break;
            }
            try_counter += 1;
            if(try_counter == 1000){break;} // there probably isn't any space available for a new grass
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d new_position, Object object) {
        // remove animal from old position (hashmap or set)
        SavannaAnimal animal = (SavannaAnimal) object;
        Object old_object = objectAt(oldPosition);
        if (old_object instanceof SavannaAnimal){ // removing animal from old position
            animalHashMap.remove(oldPosition);
        } else if (old_object instanceof SavannaAnimalSet){  // removing animal from set at old position
            SavannaAnimalSet set = (SavannaAnimalSet) old_object;
            removeFromSet(animal, set);
        } else {
            throw new IllegalStateException("nothing at old position");
        }

        // move to new position
        Object new_object = objectAt(new_position);
        if (new_object == null || new_object instanceof Grass) { // place animal at new position
            animalHashMap.put(new_position, animal);
        }
        else if (new_object instanceof SavannaAnimal) // create set
        {
            SavannaAnimalSet new_set = new SavannaAnimalSet(new_position, animal, (SavannaAnimal) new_object);
            animal_sets.put(new_position, new_set);
        } else if (new_object instanceof SavannaAnimalSet){ // add animal to set
            ((SavannaAnimalSet) new_object).add(animal);
        } else {
            throw new IllegalStateException("object at " + new_position.toString() + " has illegal state " + new_object.toString());
        }
    }

    private void removeFromSet(SavannaAnimal animal, SavannaAnimalSet set){
        set.remove(animal);
        if(set.size() == 1){ // remove set
            SavannaAnimal last_animal = set.iterator().next();
            animal_sets.remove(set.getPosition());
            animalHashMap.put(last_animal.getPosition(), last_animal);
        }
    }

    public Vector2d checkAndCorrectPosition(Vector2d position){
        // called when animal intends to go to `position`
        // when `position` is outside of map boundaries it is corrected
        // if there is grass at `position` it is added to `to_eat` set
        Vector2d end = correctPosition(position);
        Object object = objectAt(end);
        if (object instanceof Grass){
            to_eat.add((Grass) object);
        }
        return end;
    }

    protected Vector2d correctPosition(Vector2d position){
        // when `position` is outside of map boundaries it is corrected
        if (!(position.follows(lower_left) && position.precedes(upper_right))) { // position is not on the map
            // teleportation to the other side of the map
            position = new Vector2d(lower_left.x + (upper_right.x + 1 - lower_left.x + position.x) % (upper_right.x - lower_left.x + 1),
                    lower_left.y + (upper_right.y + 1 - lower_left.y + position.y) % (upper_right.y - lower_left.y + 1));
        }
        return position;
    }
    
    public void nextDay(){
        // moving animals
        for (SavannaAnimal a: animals){
            a.moveOneself();
        }
        
        feedAnimals();
        reproduceAnimals();
        removeDead();

        // growing grass
        placeGrass(LivingPlace.JUNGLE);
        placeGrass(LivingPlace.SAVANNA);
    }

    private void removeDead() {
        List<SavannaAnimal> to_remove = new LinkedList<>();
        for (SavannaAnimal a : animals) {
            if (!a.is_alive()) {
                to_remove.add(a);
                animalHashMap.remove(a.getPosition());
                Object at_animal = objectAt(a.getPosition());
                if (at_animal instanceof SavannaAnimalSet){
                    removeFromSet(a, (SavannaAnimalSet) at_animal);
                }
            }
        }
        animals.removeAll(to_remove);
    }

    private void reproduceAnimals() {
        for (SavannaAnimalSet set: animal_sets.values()){
            Pair<SavannaAnimal, SavannaAnimal> top = set.getTopPair();
            if (top.getKey().canReproduce() && top.getValue().canReproduce()){
                // selecting random position for new animal
                Vector2d end_position = null;
                Integer[] intArray = {0, 1, 2, 3, 4, 5, 6, 7};
                List<Integer> directions = Arrays.asList(intArray);
                Collections.shuffle(directions);
                for(int d: directions){
                    Vector2d to_check = correctPosition(set.getPosition().add(MapDirection.fromInt(d).toUnitVector()));
                    if (objectAt(to_check) == null){ // searching for first available space
                        end_position = to_check;
                    }
                }
                if (end_position != null){ // if there is a place for a new animal
                    // create and place the animal
                    SavannaAnimal young = new SavannaAnimal(top.getKey(), top.getValue(), end_position, rand.nextInt(8));
                    assert !animals.contains(young);
                    place(young);
                }
            }
        }
    }

    private void feedAnimals() {
        for (Grass g: to_eat){
            Object obj = objectAt(g.getPosition());
            if (obj instanceof SavannaAnimal){((SavannaAnimal)obj).feed(g.energy);}
            else{
                assert obj instanceof SavannaAnimalSet;
                SavannaAnimalSet set = (SavannaAnimalSet) obj;
                Pair<SavannaAnimal, SavannaAnimal> top = set.getTopPair();
                if (top.getValue().isStronger(top.getKey())) {top.getValue().feed(g.energy);}
                else if (top.getKey().isStronger(top.getValue())) {top.getKey().feed(g.energy);}
                else // both animals are equally strong -> divide grass among them
                {
                    SavannaAnimalSet eating_animals = set.getAllWith(top.getKey().getEnergy());
                    int portion = g.energy / eating_animals.size();
                    Iterator animal_set_it = eating_animals.iterator();
                    while (animal_set_it.hasNext()){
                        ((SavannaAnimal)animal_set_it.next()).feed(portion);
                    }
                }
            }
            grassHashMap.remove(g.getPosition());
        }
        to_eat.clear();
    }

    @Override
    public String toString() {
        return this.visualizer.draw(lower_left, upper_right);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object o = animal_sets.get(position);
        if(o != null){return o;}
        return super.objectAt(position);
    }

    @Override
    public boolean place(MapElement element) {
        if (element instanceof SavannaAnimal && this.canMoveTo(element.getPosition())) {
            SavannaAnimal animal = (SavannaAnimal) element;
            animal.addObserver(this);
            this.animalHashMap.put(element.getPosition(), animal);
            assert !animals.contains(animal);
            animals.add(animal);
            return true;
        } else if(element instanceof Grass && this.canMoveTo(element.getPosition())){
            Grass grass = (Grass) element;
            grassHashMap.put(grass.getPosition(),  grass);
            return true;
        }
        throw new IllegalArgumentException("There is wrong type of element (not SavannaAnimal and not Grass) at "+ element.getPosition().toString());
    }

    @Override
    public boolean canMoveTo(Vector2d v){
        return true;
    }

    public String summarizeInformation(){
        // returns basic map information
        return "animals: " + Integer.toString(animals.size()) + " | grass: " + Integer.toString(grassHashMap.size());
    }
}
