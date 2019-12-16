package main;

import agh.cs.project.LivingBeingsLogic.SavannaAnimal;
import agh.cs.project.SavannaMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class World {
    public static void main(String[] args){
        try {
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

            SavannaMap map = new SavannaMap(width, height, ratio, startEnergy, moveEnergy, plantEnergy, startingAnimals);

            System.out.println("start");
            System.out.println(map.toString()); // print map at the start
            for (int i = 0; i < epochs; i++) {
                System.out.println("Day: " + Integer.toString(i + 1));
                map.nextDay();
                if (verbose == 1) {
                    System.out.println(map.toString()); // print map for each epoch
                    System.out.println(map.summarizeInformation());
                    for (SavannaAnimal a : map.animals) {
                        System.out.println("animal at " + a.getPosition().toString() + " has energy = " + Integer.toString(a.getEnergy()) + " and genes: " + a.getGenes());
                    }
                }
            }

            // print map at the end
            System.out.println(map.toString());
            System.out.println(map.summarizeInformation());
            for (SavannaAnimal a : map.animals) {
                System.out.println("animal at " + a.getPosition().toString() + " has energy = " + Integer.toString(a.getEnergy()) + " and genes: " + a.getGenes());
            }

        } catch (IllegalArgumentException | FileNotFoundException ex){
            System.out.println("Exception: " + ex.getMessage());
            return;
        }
    }
}
