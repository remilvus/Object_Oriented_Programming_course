package agh.cs.tanks.map.basic_logic;

import java.util.Random;

public enum MapDirection {
    NORTH, WEST, SOUTH, EAST;

    private static MapDirection[] values = values();

    public String toString(){
        switch (this){
            case EAST:
                return "E";
            case WEST:
                return "W";
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            default:
                throw new IllegalArgumentException("Illegal argument");
        }
    }

    public MapDirection next() {
        return values[(this.ordinal()+1) % values.length];
    }

    public MapDirection previous() {
        return values[(this.ordinal() + values.length - 1) % values.length];
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTH:
                return new Vector2d(0, -1);
            case WEST:
                return new Vector2d(-1, 0);
            default:
                throw new IllegalArgumentException("Illegal argument");
        }
    }

    public static MapDirection fromInt(int num){
        return values[num % values().length];
    }

    public static int toInt(MapDirection direction){
        for(int i=0; i< values().length; i+=1){
            if (values[i] == direction){return i;}
        }
        throw new IllegalStateException("Direction from MapDirection is missing from MapDirection.values (shouldn't happen)");
    }

    public static MapDirection randomDirection(){
        return fromInt(new Random().nextInt(values().length));
    }
}
