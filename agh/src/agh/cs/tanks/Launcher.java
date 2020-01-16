package agh.cs.tanks;

import agh.cs.tanks.logic.GameOverseer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Launcher {
    private static JSlider hard_wall_slider;
    private static JSlider tank_slider;
    private static JSlider size_slider;
    private static JSlider wall_slider;
    private static JSlider water_slider;
    private static JSlider action_slider;
    private static int default_map_size = 10;
    private static int max_objects = default_map_size*default_map_size-18;

    public static void main(String[] args){
        JFrame frame = new JFrame("Launcher");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(13,1));
        JButton button = new JButton("New Game");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int hard_walls = (wall_slider.getValue() * hard_wall_slider.getValue()) / 100;
                int normal_walls = wall_slider.getValue() - hard_walls;
                new GameOverseer(size_slider.getValue(), tank_slider.getValue(),
                        action_slider.getValue(), normal_walls, hard_walls, water_slider.getValue());
            }
        });
        frame.add(button);

        add_sliders(frame);
        frame.setVisible(true);
    }

    private static void add_sliders(JFrame frame){
        frame.add(new JLabel("map size", SwingConstants.CENTER));
        size_slider = new JSlider(6,40,10);
        size_slider.setLabelTable(size_slider.createStandardLabels(size_slider.getMaximum()/10));
        size_slider.setPaintLabels(true);
        frame.add(size_slider);

        frame.add(new JLabel("number of tanks", SwingConstants.CENTER));
        tank_slider = new JSlider(1, 9, 3);
        tank_slider.setLabelTable(tank_slider.createStandardLabels(1));
        tank_slider.setPaintLabels(true);
        frame.add(tank_slider);

        frame.add(new JLabel("number of water fields", SwingConstants.CENTER));
        water_slider = new JSlider(0,(max_objects-18)/2,3);
        water_slider.setLabelTable(water_slider.createStandardLabels(max_objects/20));
        water_slider.setPaintLabels(true);
        frame.add(water_slider);

        frame.add(new JLabel("number of walls", SwingConstants.CENTER));
        wall_slider = new JSlider(0,(max_objects-18)/2,10);
        wall_slider.setLabelTable(wall_slider.createStandardLabels(max_objects/20));
        wall_slider.setPaintLabels(true);
        frame.add(wall_slider);

        frame.add(new JLabel("% of indestructible walls", SwingConstants.CENTER));
        hard_wall_slider = new JSlider(0, 100, 10);
        hard_wall_slider.setLabelTable(hard_wall_slider.createStandardLabels(10));
        hard_wall_slider.setPaintLabels(true);
        frame.add(hard_wall_slider);

        frame.add(new JLabel("Actions per turn", SwingConstants.CENTER));
        action_slider = new JSlider(1, 13, 5);
        action_slider.setLabelTable(action_slider.createStandardLabels(1));
        action_slider.setPaintLabels(true);
        frame.add(action_slider);

        size_slider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int size = size_slider.getValue();
                        max_objects = size*size-18;
                        resetLabels();
                    }
                }
        );
    }

    private static void resetLabels(){
        water_slider.setMaximum((max_objects-18)/2);
        wall_slider.setMaximum((max_objects-18)/2);
        water_slider.setLabelTable(water_slider.createStandardLabels(
                Math.max((water_slider.getMaximum()-water_slider.getMinimum())/10, 1)));
        wall_slider.setLabelTable(wall_slider.createStandardLabels(
                Math.max((wall_slider.getMaximum()-wall_slider.getMinimum())/10, 1)));
    }


}
