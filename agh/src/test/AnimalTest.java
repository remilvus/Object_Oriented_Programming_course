package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {
//    @BeforeAll
//    public static void startingPosition(){
//        Animal animal = new Animal();
//        assertTrue(animal.getPosition().equals(new Vector2d(2, 2)));
//        assertTrue(animal.getDirection().equals(MapDirection.NORTH));
//    }
//
//    @Test
//    public void basicMovement(){
//        Animal animal = new Animal();
//        animal.move(MoveDirection.FORWARD);
//        assertTrue(animal.getPosition().equals(new Vector2d(2, 3)));
//        animal.move(MoveDirection.BACKWARD);
//        assertTrue(animal.getPosition().equals(new Vector2d(2, 2)));
//    }

//    @Test
//    public void turning(){
//        Animal animal = new Animal();
//        animal.move(MoveDirection.RIGHT);
//        assertTrue(animal.getDirection().equals(MapDirection.EAST));
//        animal.move(MoveDirection.RIGHT);
//        assertTrue(animal.getDirection().equals(MapDirection.SOUTH));
//        animal.move(MoveDirection.RIGHT);
//        assertTrue(animal.getDirection().equals(MapDirection.WEST));
//        animal.move(MoveDirection.LEFT);
//        assertTrue(animal.getDirection().equals(MapDirection.SOUTH));
//    }

//    @Test
//    public void mapBoundaries(){
//        Animal animal = new Animal();
//        animal.move(MoveDirection.BACKWARD);
//        animal.move(MoveDirection.BACKWARD);
//        animal.move(MoveDirection.BACKWARD);
//        animal.move(MoveDirection.LEFT);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        assertTrue(animal.getPosition().equals(new Vector2d(0, 0)));
//        assertTrue(animal.getDirection().equals(MapDirection.WEST));
//        for (int j=0; j < 6; j++){
//            animal.move(MoveDirection.RIGHT);
//            for (int i=0; i<6; i+=1) {
//                animal.move(MoveDirection.FORWARD);
//            }
//        }
//        assertTrue(animal.getPosition().equals(new Vector2d(4, 4)));
//        assertTrue(animal.getDirection().equals(MapDirection.EAST));
//    }

//    @Test
//    public void moving_with_parser(){
//        String[] args = new String[]{"f", "forward", "f", "f", "f", "b", "b", "b"};
//        MoveDirection[] result = OptionsParser.parse(args);
//        Animal animal = new Animal();
//        for (MoveDirection d: result){
//            animal.move(d);
//        }
//        assertTrue(animal.getPosition().equals(new Vector2d(2,1)));
//    }
}
