package main;

import agh.cs.project.LivingBeingsLogic.SavannaAnimal;
import agh.cs.project.SavannaMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;


public class World {
    static JPanel panel; 
    static final JFrame frame = new JFrame("World");
    static JTextArea text_area;

    public static void main(String[] args){
        try {

            // loading parameters from json
            JsonParser parser = new JsonParser();
            JsonObject res = parser.parse(new FileReader("agh\\src\\agh\\cs\\project\\input.json")).getAsJsonObject();
            int width = res.get("width").getAsInt();
            int height = res.get("height").getAsInt();
            float ratio = res.get("jungleRatio").getAsFloat();
            int startEnergy = res.get("startEnergy").getAsInt();
            int moveEnergy = res.get("moveEnergy").getAsInt();
            int plantEnergy = res.get("plantEnergy").getAsInt();
            int startingAnimals = res.get("startingAnimals").getAsInt();
            int epochs = res.get("epochs").getAsInt();
            int wait_time = res.get("waitTime").getAsInt();
            int font_size = res.get("fontSize").getAsInt();
            int log_file = res.get("logFile").getAsInt();

            // map logger
            BufferedWriter logger = null;
            if(log_file==1){
                logger = new BufferedWriter(new FileWriter("agh\\src\\agh\\cs\\project\\output.txt"));
            }

            int step_size = (int) (epochs / 20); // determines hot often map will be printed
            if(step_size==0) {step_size = 1;}

            SavannaMap map = new SavannaMap(width, height, ratio, startEnergy, moveEnergy, plantEnergy, startingAnimals);

            text_area = new JTextArea();
            text_area.setBackground(new Color(.0f, .1f, .0f));
            Font font = new Font("Verdana", Font.BOLD, font_size);
            text_area.setFont(new Font("monospaced", Font.PLAIN, font_size));
            text_area.setForeground(new Color(.2f, 1f, .0f));

            String string_map;
            if(log_file==1) {
                string_map = map.toString();
                logger.write("\nStarting state:\n");
                logger.write(string_map);
            }

            frame.setSize(2000,1000);
            for (int i = 0; i < epochs; i++) {
                map.nextDay();
                string_map = map.toString();

                if(log_file==1) {
                    logger.write("\nDay: " + Integer.toString(i + 1) + "\n");
                    logger.write(string_map);
                }
                showMap(string_map, i);

                TimeUnit.MILLISECONDS.sleep(wait_time);
            }

            if(log_file==1) {
                // save map state at the end
                string_map = map.toString();
                logger.write("\nEnd state:\n");
                logger.write(string_map);
                logger.write("\nInformation about animals:\n");
                for (SavannaAnimal a : map.animals) {
                    String animal_info = "animal at " + a.getPosition().toString() + " has energy = " + Integer.toString(a.getEnergy()) + " and genes: " + a.getGenes();
                    logger.write(animal_info + "\n");
                }
                logger.close();
            }
        } catch (IllegalArgumentException | JsonParseException | IOException | InterruptedException ex ){
            System.out.println("Exception: " + ex.getMessage());
            return;
        }
    }

    private static void showMap(String map, int day){
        text_area.setText("Day "+ Integer.toString(day + 1) + "\n" + map);
        panel = new JPanel();
        panel.add(text_area);
        frame.add(panel);
        frame.show();
    }

}
