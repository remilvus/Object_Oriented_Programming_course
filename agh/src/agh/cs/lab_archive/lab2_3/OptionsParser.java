package agh.cs.lab_archive.lab2_3;

import agh.cs.project.MapBasicLogic.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String... directions) throws IllegalArgumentException{
        MoveDirection[] result = new MoveDirection[directions.length];
        int empty_idx = 0;
        for (String elem: directions){
            switch (elem){
                case "forward":
                case "f":
                    result[empty_idx] = MoveDirection.FORWARD;
                    empty_idx+=1;
                    break;
                case "backward":
                case "b":
                    result[empty_idx] = MoveDirection.BACKWARD;
                    empty_idx+=1;
                    break;
                case "left":
                case "l":
                    result[empty_idx] = MoveDirection.LEFT;
                    empty_idx+=1;
                    break;
                case "right":
                case "r":
                    result[empty_idx] = MoveDirection.RIGHT;
                    empty_idx+=1;
                    break;
                default:
                    throw new IllegalArgumentException(elem + " is not legal move specification");
            }
        }
        MoveDirection[] output = Arrays.copyOfRange(result, 0, empty_idx);
        return output;
    }
}
