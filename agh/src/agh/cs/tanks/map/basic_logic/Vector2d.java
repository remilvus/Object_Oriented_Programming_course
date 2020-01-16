package agh.cs.tanks.map.basic_logic;

public class Vector2d implements Cloneable{
    final public int x;
    final public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("(%d, %d)", this.x, this.y);
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        int x = this.x;
        int y = this.y;
        if (other.x > x){
            x = other.x;
        }
        if (other.y > y){
            y = other.y;
        }
        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int x = this.x;
        int y = this.y;
        if (other.x < x){
            x = other.x;
        }
        if (other.y < y){
            y = other.y;
        }
        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other) {return true;}
        if (!(other instanceof Vector2d)) {return false;}
        Vector2d other_v = (Vector2d) other;
        return this.x == other_v.x && this.y == other_v.y;
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode() {
        int hash = 42 + 1;
        hash += this.x * 2137;
        hash += this.y * 2139;
        return hash;
    }

}

