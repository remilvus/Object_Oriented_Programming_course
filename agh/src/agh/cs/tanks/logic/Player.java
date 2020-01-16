package agh.cs.tanks.logic;

import agh.cs.tanks.objects.Tank;

import java.awt.*;
import java.util.Vector;

public class Player {
    private Vector<Tank> tanks = new Vector<>();
    public int tank_num;
    public Color color;

    public Player(int tank_num, Color color){
        this.tank_num = tank_num;
        this.color = color;
    }

    public void addTank(Tank tank){
        tanks.add(tank);
        tank.setPlayer(this);
    }

    public Vector<Tank> getTanks(){
        return tanks;
    }

    public int getTankCount(){return tanks.size();}

    public void removeTank(Tank tank){
        tanks.remove(tank);
    }


}
