package test;

import agh.cs.project.MapBasicLogic.Vector2d;
import agh.cs.project.LivingBeingsLogic.SavannaAnimal;
import agh.cs.project.LivingBeingsLogic.SavannaAnimalSet;
import agh.cs.project.SavannaMap;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalSetTest {

    @Test
    void getTopPair() {
        Vector2d v = new Vector2d(2,2);
        SavannaMap map = new SavannaMap(0, 0, (float) 0.5, 100,100,10, 0);
        SavannaAnimal a1=new SavannaAnimal(map, v), a2=new SavannaAnimal(map, v, 10, 10, 1);
        SavannaAnimal a3=new SavannaAnimal(map, v);
        SavannaAnimalSet set = new SavannaAnimalSet(v, a1, a2);
        set.add(a3);
        Pair<SavannaAnimal, SavannaAnimal> top = set.getTopPair();
        assertTrue(top.getKey().getEnergy() == top.getValue().getEnergy() &&
                !top.getValue().equals(top.getKey()));
    }
}