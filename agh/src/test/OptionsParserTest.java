package test;

import agh.cs.project.MapBasicLogic.MoveDirection;
import agh.cs.lab_archive.lab2_3.OptionsParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionsParserTest {
    @Test
    public void parsing(){
        String[] args = new String[]{"f", "forward", "b", "backward", "l", "left", "r", "right"};
        MoveDirection[] result = OptionsParser.parse(args);
        MoveDirection[] expected = new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.RIGHT};
        assertTrue(Arrays.equals(expected, result));
        args = new String[]{"f", "b"};
        result = OptionsParser.parse(args);
        expected = new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.BACKWARD,};
        assertTrue(Arrays.equals(expected, result));
    }
}
