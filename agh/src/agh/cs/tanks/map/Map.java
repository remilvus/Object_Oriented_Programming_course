package agh.cs.tanks.map;

import agh.cs.tanks.map.basic_logic.MapArea;
import agh.cs.tanks.map.basic_logic.MapDirection;
import agh.cs.tanks.map.basic_logic.Vector2d;
import agh.cs.tanks.objects.MapObject;
import agh.cs.tanks.objects.Water;
import agh.cs.tanks.objects.Tank;
import agh.cs.tanks.objects.Wall;
import agh.cs.tanks.logic.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class Map {
    public final int map_width;
    public final int map_height;
    private final Vector2d lower_left;
    private final Vector2d upper_right;
    private HashMap<Vector2d, MapObject> objects = new HashMap<>();
    private Vector<MapArea> player_area;
    private Random rand = new Random();
    private static final int WALL_DURABILITY = 200;

    public Map(int map_width, int map_height, Vector<MapArea> player_area, int rivers, int walls, int heavy_walls){
        this.map_width = map_width;
        this.map_height = map_height;
        this.lower_left = new Vector2d(0, -map_height+1);
        this.upper_right = new Vector2d(map_width-1, 0);
        this.player_area = player_area;
        placeWalls(walls, heavy_walls);
        placeRivers(rivers);
    }

    private void placeWalls(int normal, int heavy){
        placeWalls(heavy, false);
        placeWalls(normal, true);
    }

    private void placeWalls(int walls, boolean destructible){
        int i=0;
        while(walls>0 && i<1000){
            i++;
            Vector2d position = randomMapVector();
            if(isReserved(position) || objectAt(position)!=null) continue;
            Wall wall = new Wall(position, WALL_DURABILITY);
            wall.setDestructibility(destructible);
            objects.put(position, wall);
            walls-=1;
        }
    }

    private void placeRivers(int rivers){
        int i=0;
        int try_neighbour=0;
        Vector2d last_position=null;
        Vector2d position=null;
        while(rivers>0 && i<1000){
            i++;
            if (last_position != null && try_neighbour<3){
                last_position = position;
                position = last_position.add(MapDirection.randomDirection().toUnitVector());
                try_neighbour += 1;
            } else {
                position = randomMapVector();
            }

            if(isReserved(position) || objectAt(position)!=null || !isOnMap(position)) continue;
            last_position = position;
            objects.put(position, new Water(position));
            rivers-=1;
        }
    }

    public void startShooting(Player player){
        Vector<MapObject> destroyed = new Vector<>();
        for(Tank tank: player.getTanks()){
            if(tank.isTargeting()){
                Vector2d target = tank.getTarget();
                target = checkTarget(tank, target);
                int damage = tank.getDamage();
                MapArea area = new MapArea(target.subtract(new Vector2d(1, 1)), target.add(new Vector2d(1, 1)));
                for(MapObject o: this.getObjectsOnArea(area)){
                    o.dealDamage(damage);
                    if(o.isDestroyed()){
                        destroyed.add(o);
                    }
                    tank.stopTargeting();
                }
            }
        }
        for(MapObject o: destroyed){
            this.objects.remove(o.getPosition());
            if(o instanceof Tank){
                ((Tank) o).getPlayer().removeTank((Tank) o);
            }
        }
    }

    private boolean isReserved(Vector2d position){
        for(MapArea area: player_area){
            if(area.isInside(position)) return true;
        }
        return false;
    }

    public Vector2d checkTarget(Tank tank, Vector2d target){
        MapObject map_object = this.rayDetect(tank.getPosition(), tank.getDirection());
        if(map_object == null) return target;
        Vector2d tank_position = tank.getPosition();
        Vector2d vec_object = map_object.getPosition().subtract(tank_position);
        Vector2d vec_target = target.subtract(tank_position);
        int distance_object = Math.max(Math.abs(vec_object.x), Math.abs(vec_object.y));
        int distance_target = Math.max(Math.abs(vec_target.x), Math.abs(vec_target.y));
        if(distance_object < distance_target) target = map_object.getPosition();
        return target;
    }

    public void placeTanks(Vector<Player> players){
        for (int i=0; i < players.size(); i++){
            Player p = players.get(i);
            MapArea area = player_area.get(i);
            area.setMap(this);
            for(int j=0; j < p.tank_num; j++){
                Vector2d position = area.getAvailablePosition();
                if (position==null) break;
                Tank tank = new Tank(position, MapDirection.randomDirection(), this, p.color);
                p.addTank(tank);
                objects.put(position, tank);
            }
        }
    }

    private boolean canMoveTo(Vector2d position){
        return isOnMap(position) && objectAt(position)==null;
    }

    public boolean moveTo(Vector2d old_position, Vector2d new_position){
        if(canMoveTo(new_position)){
            MapObject map_object = objects.get(old_position);
            assert map_object != null;
            objects.remove(old_position);
            objects.put(new_position, map_object);
            return true;
        }
        return false;
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(this.lower_left) && position.precedes(this.upper_right);
    }

    public MapObject objectAt(Vector2d position){
        return objects.get(position);
    }

    private MapObject rayDetect(Vector2d from, MapDirection direction){
        Vector2d increment = direction.toUnitVector();
        Vector2d check = from.add(increment);
        while(isOnMap(check)){
            MapObject object_at = objectAt(check);
            if(object_at != null && !object_at.isPenetrable()){
                return object_at;
            }
            check = check.add(increment);
        }
        return null;
    }

    public Collection<MapObject> getObjects(){
        return this.objects.values();
    }

    private Vector<MapObject> getObjectsOnArea(MapArea area){
        Vector<MapObject> on_area = new Vector<>();
        for (MapObject o: objects.values()){
            if(area.isInside(o.getPosition())){
                on_area.add(o);
            }
        }
        return on_area;
    }

    private Vector2d randomMapVector(){
        return new Vector2d(rand.nextInt(map_width), -rand.nextInt(map_height));
    }
}
