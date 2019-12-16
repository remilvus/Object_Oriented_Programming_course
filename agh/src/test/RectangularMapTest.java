package test;

import agh.cs.project.MapBasicLogic.IWorldMap;
import agh.cs.lab_archive.lab_4.RectangularMap;
import agh.cs.project.LivingBeingsLogic.Animal;
import agh.cs.project.MapBasicLogic.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    @Test
    public void placeTest(){
        IWorldMap map = new RectangularMap(4,4);
        map.place(new Animal(map, new Vector2d(2,2)));
        assertThrows(IllegalArgumentException.class, ()->map.place(new Animal(map, new Vector2d(2,2))));
        assertTrue(map.place(new Animal(map, new Vector2d(3,3))));
        assertThrows(IllegalArgumentException.class, ()->map.place(new Animal(map, new Vector2d(10,10))));
    }

    @Test
    public void basicTests(){
        IWorldMap map = new RectangularMap(4,4);
        Animal animal = new Animal(map, new Vector2d(2,2));
        map.place(animal);
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertFalse(map.isOccupied(new Vector2d(3,3)));
        assertTrue(map.objectAt(new Vector2d(2, 2)).equals(animal));
        assertTrue(map.objectAt(new Vector2d(3, 3))==null);
        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
    }

}
