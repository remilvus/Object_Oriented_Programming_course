package agh.cs.tanks.logic;

import agh.cs.tanks.map.MapPainter;
import agh.cs.tanks.map.basic_logic.Directions;
import agh.cs.tanks.map.basic_logic.MapArea;
import agh.cs.tanks.map.basic_logic.Vector2d;
import agh.cs.tanks.map.Map;
import agh.cs.tanks.objects.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class GameOverseer {
    private Map map;
    private Gui gui;
    private int action_num;
    private static final int PLAYER_AREA_WIDTH = 3;
    private static final int PLAYER_AREA_HEIGHT = 3;
    private Vector<Player> players;
    private Player current_player;
    private int player_idx;
    private Tank current_tank;
    private int actions_left;

    public GameOverseer(int map_width, int map_height, int tanks_per_player, int actions, int wall_num, int hard_walls, int water, int player_number){
        this.players = new Vector<>();
        Color[] player_color = new Color[]{Color.RED, Color.YELLOW, Color.ORANGE, Color.MAGENTA};
        for(int i = 0; i< player_number; i++){
            Player p = new Player(tanks_per_player, player_color[i]);
            players.add(p);
        }

        this.map = new Map(map_width, map_height, reserveArea(player_number, map_width, map_height), water, wall_num, hard_walls);
        this.map.placeTanks(players);

        this.gui = new Gui(map);
        this.action_num = actions;

        startGame();

        setActions(this.gui.getMapPainter());
    }

    public GameOverseer(int map_side, int tanks_per_player, int action_num, int wall_num, int hard_walls, int water_num){
        this(map_side, map_side, tanks_per_player, action_num, wall_num, hard_walls, water_num, 2);
    }

    private void startGame(){
        current_player = players.firstElement();
        current_tank = current_player.getTanks().firstElement();
        current_tank.select();
        actions_left = action_num;
        player_idx = 0;
    }

    private Vector<MapArea> reserveArea(int PLAYER_NUMBER, int map_width, int map_height){
        Vector<MapArea> reserved = new Vector<>();
        reserved.add(new MapArea(new Vector2d(0, -map_height+1),
                new Vector2d(PLAYER_AREA_WIDTH-1, -map_height+PLAYER_AREA_HEIGHT)));
        reserved.add(new MapArea(new Vector2d(map_width-PLAYER_AREA_WIDTH, -PLAYER_AREA_HEIGHT+1),
                new Vector2d(map_width-1, 0)));
        return reserved;
    }

    private void startShooting(int player_idx){
        map.startShooting(players.get(player_idx));
        Player winner = findWinner();
        if (winner!=null){
            MapPainter painter = gui.getMapPainter();
            painter.setWinner(winner);
            painter.stopInput();
        }
    }

    private boolean checkActionCount(){
        if (actions_left==0){
            player_idx = (player_idx + 1) % players.size();
            current_player = players.get(player_idx);
            current_tank.deselect();
            startShooting(player_idx);
            if(current_player.getTankCount() > 0){
                current_tank = current_player.getTanks().firstElement();
                current_tank.select();
                actions_left = action_num;
            }
            return false;
        }
        return true;
    }

    private Player findWinner(){
        Player winner = null;
        for (Player p: players) {
            if (p.getTankCount() > 0) {
                if (winner != null) {
                    return null;
                } else {
                    winner = p;
                }
            }
        }
        return winner;
    }

    private void setActions(JComponent component){
        component.getInputMap().put(KeyStroke.getKeyStroke("W"), "w");
        component.getActionMap().put("w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current_tank.isTargeting()) {
                    Vector2d target = current_tank.getTarget().add(current_tank.getDirection().toUnitVector());
                    target = map.checkTarget(current_tank, target);
                    current_tank.setTarget(target);
                } else {
                    boolean success = current_tank.move(Directions.FORWARD);
                    if (success) {
                        actions_left -= 1;
                        GameOverseer.this.checkActionCount();
                    }
                }
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("S"), "s");
        component.getActionMap().put("s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current_tank.isTargeting()) {
                    Vector2d target = current_tank.getTarget().add(current_tank.getDirection().toUnitVector().opposite());
                    target = map.checkTarget(current_tank, target);
                    current_tank.setTarget(target);
                } else {
                    boolean success = current_tank.move(Directions.BACK);
                    if (success) {
                        actions_left -= 1;
                        GameOverseer.this.checkActionCount();
                    }
                }
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("D"), "d");
        component.getActionMap().put("d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_tank.move(Directions.RIGHT);
                if(current_tank.isTargeting()){current_tank.setTarget(current_tank.getPosition());}
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("A"), "a");
        component.getActionMap().put("a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_tank.move(Directions.LEFT);
                if(current_tank.isTargeting()){current_tank.setTarget(current_tank.getPosition());}
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("Q"), "select previous");
        component.getActionMap().put("select previous", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkActionCount()) {
                    component.repaint();
                    return;
                }
                Vector<Tank> tanks = current_player.getTanks();
                int next = tanks.indexOf(current_tank) + 1;
                current_tank.deselect();
                current_tank = tanks.get(next % tanks.size());
                current_tank.select();
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("E"), "select next");
        component.getActionMap().put("select next", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkActionCount()) {
                    component.repaint();
                    return;
                }
                Vector<Tank> tanks = current_player.getTanks();
                int next = tanks.indexOf(current_tank) - 1;
                if (next == -1){next = tanks.size() -1;}
                current_tank.deselect();
                current_tank = tanks.get(next);
                current_tank.select();
                component.repaint();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "start targeting");
        component.getActionMap().put("start targeting", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current_tank.isTargeting()){
                    actions_left += 1;
                    current_tank.stopTargeting();
                    component.repaint();
                } else{
                    actions_left -= 1;
                    current_tank.setTarget(current_tank.getPosition().add(current_tank.getDirection().toUnitVector()));
                    component.repaint();
                }
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "end");
        component.getActionMap().put("end", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions_left = 0;
                checkActionCount();
                component.repaint();
            }
        });
    }
}
