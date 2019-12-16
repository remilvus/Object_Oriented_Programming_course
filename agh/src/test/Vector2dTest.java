package test;

import agh.cs.project.MapBasicLogic.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Vector2dTest {

    @Test
    public void equality(){
        Vector2d v1 = new Vector2d(3, 4);
        Vector2d v2 = new Vector2d(3, 4);
        Vector2d v3 = new Vector2d(1, 4);
        Vector2d v4 = new Vector2d(3, 5);

        assertTrue(v1.equals(v2));
        assert !v1.equals(v3);
        assert !v1.equals(v4);
        assert !v3.equals(v4);

        Integer i = 4;
        String s = "aaa";

        assert  !v1.equals(i);
        assert  !v1.equals(s);
    }

    @Test
    public void str(){
        Vector2d v = new Vector2d(2,3);
        String s = v.toString();
        assert  s.equals("(2, 3)");
    }

    @Test
    public void hash(){
        HashSet<Vector2d> set = new HashSet<Vector2d>();
        for (int i=0; i<1000; i++){
            for (int j=0; j<1000; j++){
                Vector2d v = new Vector2d(i, j);
                assertFalse(set.contains(v));
                set.add(v);
            }
        }
    }

    @Test
    public void precedes(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 4);
        assert v1.precedes(v2);
    }

    @Test
    public void follows(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 4);
        assert v2.follows(v1);
    }

    @Test
    public void upperRight(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 2);
        Vector2d v3 = new Vector2d(3, 4);
        assert v3.equals(v1.upperRight(v2));
    }

    @Test
    public void lowerLeft(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 2);
        Vector2d v3 = new Vector2d(1, 2);
        assert v3.equals(v1.lowerLeft(v2));
    }

    @Test
    public void add(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 2);
        Vector2d v3 = new Vector2d(4, 6);
        assert v3.equals(v1.add(v2));
    }

    @Test
    public void subtract(){
        Vector2d v1 = new Vector2d(1, 4);
        Vector2d v2 = new Vector2d(3, 2);
        Vector2d v3 = new Vector2d(-2, 2);
        assert v3.equals(v1.subtract(v2));
    }

    @Test
    public void opposite(){
        Vector2d v1 = new Vector2d(1, -4);
        Vector2d v2 = new Vector2d(-1, 4);
        assert v1.equals(v2.opposite());
    }
}
