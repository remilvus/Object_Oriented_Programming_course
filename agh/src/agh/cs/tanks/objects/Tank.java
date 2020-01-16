package agh.cs.tanks.objects;

import agh.cs.tanks.map.Map;
import agh.cs.tanks.map.basic_logic.MapDirection;
import agh.cs.tanks.map.basic_logic.Vector2d;
import agh.cs.tanks.logic.Player;
import com.sun.prism.GraphicsPipeline;

import java.awt.*;
import java.util.Random;

public class Tank extends DynamicObject {
    private final float BARREL_WIDTH = 0.1f;
    private final float BARREL_LENGTH = 0.45f;
    private final float TANK_SIZE = 0.8f;
    private final float MARGIN = (1 - TANK_SIZE)/2;
    private final float BAR_HEIGHT = 0.07f;
    private  boolean is_selected = false;
    private Vector2d target;
    private boolean is_targeting = false;
    private int damage;
    private int max_destruction_points;
    private Player player;
    private int target_shift_y;
    private int target_shift_x;

    public Tank(int x, int y, MapDirection direction, Map map, Color color, int destruction_damage, int damage) {
        this(new Vector2d(x, y), direction, map, color, destruction_damage, damage);
    }

    public Tank(Vector2d position, MapDirection direction, Map map, Color color) {
        this(position, direction, map, color, 100, 50);
        max_destruction_points = destruction_damage;
    }

    public Tank(Vector2d position, MapDirection direction, Map map, Color color, int destruction_damage, int damage) {
        super(position, direction, map, color, destruction_damage);
        this.damage=damage;
        Random rand = new Random();
        target_shift_x = (rand.nextInt(5)-1);
        target_shift_y = (rand.nextInt(5)-1);
    }

    @Override
    public void paint(Graphics g, int x, int y, int tile_size) {
        g.setColor(color);
        int margin = (int) (tile_size * MARGIN);
        g.fillOval(x + margin, y + margin, tile_size -2*margin, tile_size-2*margin); // main body

        // barrel
        g.setColor(Color.BLACK);
        int width = barrel_width(tile_size);
        int heigt = barrel_height(tile_size);
        int bx = barrel_x(tile_size);
        int by = barrel_y(tile_size);
        g.fillRect(x + bx,y + by, width, heigt);
        if(is_selected){
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100 ));
            g.fillRect(x, y, tile_size, tile_size);
        }

        // health bar
        g.setColor(Color.GREEN.darker().darker());
        width = (int) (tile_size * TANK_SIZE * destruction_damage / max_destruction_points);
        heigt = (int) (tile_size * BAR_HEIGHT);
        g.fillRect(x + margin, y + tile_size - 3*margin, width, heigt);
    }

    public void paintTarget(Graphics g,int tank_x, int tank_y, int x, int y, int tile_side){
        g.setColor(Color.RED);
        int half_tile = tile_side/2;
        x += target_shift_x; // avoiding overlapping targets
        y += target_shift_y;
        g.drawLine(x-half_tile, y-half_tile, x+half_tile+tile_side, y+half_tile+tile_side);
        g.drawLine(x+half_tile+tile_side, y-half_tile, x-half_tile, y+half_tile+tile_side);
        g.drawLine(tank_x + half_tile, tank_y + half_tile, x + half_tile, y + half_tile);
    }

    private int barrel_width(int tile_size){
        if (direction == MapDirection.EAST || direction == MapDirection.WEST){
            return (int)(tile_size * BARREL_LENGTH);
        } else {
            return (int)(tile_size * BARREL_WIDTH);
        }
    }

    private int barrel_height(int tile_size){
        if (direction == MapDirection.EAST || direction == MapDirection.WEST){
            return (int)(tile_size * BARREL_WIDTH);
        } else {
            return (int)(tile_size * BARREL_LENGTH);
        }
    }

    private int barrel_x(int tile_size){
        switch(direction){
            case WEST:
                return (int)(tile_size/2 - tile_size*BARREL_LENGTH);
            case NORTH:
            case SOUTH:
                return (int)(tile_size/2 - tile_size*BARREL_WIDTH/2);
            case EAST:
                return (int)(tile_size/2);
            default:
                throw new IllegalArgumentException("Wrong direction");
        }
    }

    private int barrel_y(int tile_size){
        switch(direction){
            case SOUTH:
                return (int)(tile_size/2);
            case NORTH:
                return (int)(tile_size/2 - tile_size*BARREL_LENGTH);
            case EAST:
            case WEST:
                return (int)(tile_size/2 - tile_size*BARREL_WIDTH/2);
            default:
                throw new IllegalArgumentException("Wrong direction");
        }
    }

    public void select(){
        is_selected = true;
    }

    public void deselect(){
        is_selected = false;
    }

    public void setTarget(Vector2d target){
        is_targeting = true;
        if (target.equals(position)) target=target.add(direction.toUnitVector());
        this.target = target;
    }

    public void stopTargeting(){
        target = null;
        is_targeting = false;
    }

    public boolean isTargeting(){
        return is_targeting;
    }

    public Vector2d getTarget(){
        return target;
    }

    public int getDamage(){
        return damage;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
