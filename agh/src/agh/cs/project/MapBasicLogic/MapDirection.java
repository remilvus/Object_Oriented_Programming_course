package agh.cs.project.MapBasicLogic;

public enum MapDirection {
    NORTH, NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST, EAST, NORTH_EAST;

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
            case NORTH_WEST:
                return "NW";
            case SOUTH_EAST:
                return "SE";
            case SOUTH_WEST:
                return "SW";
            case NORTH_EAST:
                return "NE";
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
            case NORTH_EAST:
                return new Vector2d(1, 1);
            case SOUTH_WEST:
                return new Vector2d(-1, -1);
            case SOUTH_EAST:
                return new Vector2d(1, -1);
            case NORTH_WEST:
                return new Vector2d(-1, 1);

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
}
