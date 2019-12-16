package agh.cs.project.MapBasicLogic;

public enum Directions {
    FORWARD, BACK, RIGHT, LEFT;

    public static Directions fromString(String s){
            switch(s) {
                case "f":
                    return FORWARD;
                case "b":
                    return BACK;
                case "l":
                    return LEFT;
                case "r":
                    return RIGHT;
                default:
                    throw new IllegalArgumentException("Wrong command");
            }
    }
}
