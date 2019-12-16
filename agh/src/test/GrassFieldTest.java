package test;

import agh.cs.lab_archive.lab2_3.*;
import agh.cs.project.MapBasicLogic.IWorldMap;
import agh.cs.lab_archive.lab_5.GrassField;
import agh.cs.project.LivingBeingsLogic.Animal;
import agh.cs.project.MapBasicLogic.MoveDirection;
import agh.cs.project.MapBasicLogic.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    public void placeTest(){
        IWorldMap map = new GrassField(1);
        map.place(new Animal(map, new Vector2d(2,2)));
        boolean exc = false;
        assertThrows(IllegalArgumentException.class , () -> map.place(new Animal(map, new Vector2d(2,2))));
        assertTrue(map.place(new Animal(map, new Vector2d(3,3))));
        assertTrue(map.place(new Animal(map, new Vector2d(10,10))));
    }

    @Test
    public void basicTests(){
        IWorldMap map = new GrassField(4);
        Animal animal = new Animal(map, new Vector2d(2,2));
        map.place(animal);
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertThrows(IllegalArgumentException.class , () -> map.place(new Animal(map, new Vector2d(2, 2))));
        assertThrows(IllegalArgumentException.class , () -> map.place(new Animal(map, new Vector2d(2, 2))));

        assertTrue(map.objectAt(new Vector2d(2, 2)).equals(animal));
        assertTrue(map.objectAt(new Vector2d(3, 3))==null);

        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));

        //Grass Grass = new Grass(new Vector2d(3,4));
       // map.place(Grass);
        //assertTrue(map.objectAt(new Vector2d(3, 4)) == Grass);
    }

    @Test
    public void runTest(){
        final String[] args_error = "f b r asfasfasf f f f f f".split(" ");
        assertThrows(IllegalArgumentException.class , () -> new OptionsParser().parse(args_error));

        String[] args = "f b r l f f r r f f f f f f f f".split(" ");
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new GrassField(4);
        Animal a1 = new Animal(map);
        Animal a2 = new Animal(map,new Vector2d(3,4));
//        Vector2d[] Grasss_pos = new Vector2d[]{new Vector2d(-4, -4), new Vector2d(7, 7),
//                new Vector2d(3, 6), new Vector2d(2, 0)};
//        for (Vector2d p: Grasss_pos){
//            map.place(new Grass(p));
//        }
        map.place(a1);
        map.place(a2);
        map.run(directions);
    //    assertTrue(a1.getPosition().equals(new Vector2d(2,-1)));
      //  assertTrue(a1.getDirection().equals(MapDirection.SOUTH));
       // assertTrue(a2.getPosition().equals(new Vector2d(3,7)));
    //    assertTrue(a2.getDirection().equals(MapDirection.NORTH));
    }

//    @Test
//    public void Grass_placing(){
//        GrassField map = new GrassField(3);
//        assertTrue(map.place(new Grass(new Vector2d(1, 1))));
//        assertFalse(map.place(new Grass(new Vector2d(1, 1))));
//        assertTrue(map.place(new Grass(new Vector2d(2, 2))));
//        assertTrue(map.place(new Grass(new Vector2d(3, 3))));
//        assertFalse(map.place(new Grass(new Vector2d(4, 4))));
//        assertFalse(map.isOccupied(new Vector2d(4, 4)));
//        assertTrue(map.isOccupied(new Vector2d(3, 3)));
//    }
}
