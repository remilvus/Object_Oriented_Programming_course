package agh.cs.tanks.map;

import agh.cs.tanks.map.basic_logic.Vector2d;
import agh.cs.tanks.objects.MapObject;
import agh.cs.tanks.objects.Tank;
import agh.cs.tanks.logic.Player;

import javax.swing.*;
import java.awt.*;

public class MapPainter extends JPanel {
    private Map map;
    private int width;
    private int height;
    private int tile_side;
    private int MARGIN = 10;
    private Player winner = null;

    public MapPainter(Map map, int width, int height) {
        super();
        this.map = map;
        this.width = width;
        this.height = height;
        this.tile_side = Math.min(width/map.map_width, height/map.map_height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setSize(width + MARGIN, height + MARGIN);

        g.setColor(new Color(0,60,0));
        g.fillRect(0, 0, width, height);
        drawMapElements(g);

        if(winner != null){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Comic sans", Font.BOLD, 142));
            g.drawString(">winner", width/10+1, height/2-1);
            g.setFont(new Font("Comic sans", Font.BOLD, 142));
            g.drawString(">winner", width/10-1, height/2+1);
            g.setColor(winner.color);
            g.setFont(new Font("Comic sans", Font.BOLD, 142));
            g.drawString(">winner", width/10, height/2);
        }
    }

    private void drawMapElements(Graphics g){
        for(MapObject map_object: map.getObjects()){
            Vector2d position = map_object.getPosition();
            int tank_x = Math.abs(position.x) * tile_side;
            int tank_y = Math.abs(position.y) * tile_side;
            map_object.paint(g, tank_x, tank_y, tile_side);
            if (map_object instanceof Tank) {
                Tank tank = (Tank) map_object;
                if (tank.isTargeting()) {
                    Vector2d target = tank.getTarget();
                    int x = target.x * tile_side;
                    int y = (-1)*target.y * tile_side;
                    tank.paintTarget(g, tank_x, tank_y, x, y, tile_side);
                }
            }
        }
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void stopInput(){
        this.getInputMap().clear();
        this.getActionMap().clear();
    }
}
