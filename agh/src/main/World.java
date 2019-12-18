package main;

import agh.cs.project.LivingBeingsLogic.SavannaAnimal;
import agh.cs.project.SavannaMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import java.io.*;


public class World {
    public static void main(String[] args){
        try {
            // map logger
            BufferedWriter logger = new BufferedWriter(new FileWriter("C:\\Users\\adam\\IdeaProjects\\lab_0_\\agh\\src\\agh\\cs\\project\\output.txt"));

            // loading parameters from json
            JsonParser parser = new JsonParser();
            JsonObject res = parser.parse(new FileReader("C:\\Users\\adam\\IdeaProjects\\lab_0_\\agh\\src\\agh\\cs\\project\\input.json")).getAsJsonObject();
            int width = res.get("width").getAsInt();
            int height = res.get("height").getAsInt();
            float ratio = res.get("jungleRatio").getAsFloat();
            int startEnergy = res.get("startEnergy").getAsInt();
            int moveEnergy = res.get("moveEnergy").getAsInt();
            int plantEnergy = res.get("plantEnergy").getAsInt();
            int startingAnimals = res.get("startingAnimals").getAsInt();
            int epochs = res.get("epochs").getAsInt();
            int verbose = res.get("verbose").getAsInt();
            int step_size = (int) (epochs / 20); // determines hot often map will be printed
            if(step_size==0) {step_size = 1;}


            SavannaMap map = new SavannaMap(width, height, ratio, startEnergy, moveEnergy, plantEnergy, startingAnimals);

            System.out.println("Starting state:");
            String string_map = map.toString();
            System.out.println(string_map); // print map at the start

            logger.write("\nStarting state:\n");
            logger.write(string_map);

            for (int i = 0; i < epochs; i++) {
                map.nextDay();
                logger.write("\nDay: " + Integer.toString(i + 1) + "\n");
                string_map = map.toString();
                logger.write(string_map);
                if (verbose == 1 && i % step_size == 0) {
                    System.out.println("Day: " + Integer.toString(i + 1));
                    System.out.println(map.toString()); // print map for each epoch
                    System.out.println(map.summarizeInformation());
//                    for (SavannaAnimal a : map.animals) {
//                        System.out.println("animal at " + a.getPosition().toString() + " has energy = " + Integer.toString(a.getEnergy()) + " and genes: " + a.getGenes());
//                    }
                }
            }

            // print map at the end
            string_map = map.toString();
            System.out.println(string_map);
            System.out.println(map.summarizeInformation());
            logger.write("\nEnd state:\n");
            logger.write(string_map);

            System.out.println("\nInformation about animals:");
            logger.write("\nInformation about animals:\n");
            for (SavannaAnimal a : map.animals) {
                String animal_info = "animal at " + a.getPosition().toString() + " has energy = " + Integer.toString(a.getEnergy()) + " and genes: " + a.getGenes();
                System.out.println(animal_info);
                logger.write(animal_info + "\n");
            }


            logger.close();

        } catch (IllegalArgumentException | JsonParseException | IOException ex ){
            System.out.println("Exception: " + ex.getMessage());
            return;
        }
    }
}
