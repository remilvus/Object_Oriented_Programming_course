package agh.cs.tanks.logic;
import agh.cs.tanks.map.Map;
import agh.cs.tanks.map.MapPainter;

import javax.swing.*;
import java.awt.*;

class Gui {
    private MapPainter mapPainter;
    private JFrame frame;

    public Gui(Map map){
        frame = new JFrame("Map");
        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapPainter = new MapPainter(map, 800, 800);
        frame.getContentPane().add(mapPainter, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public MapPainter getMapPainter() {
        return mapPainter;
    }

    public void close(){
        frame.dispose();
    }
}