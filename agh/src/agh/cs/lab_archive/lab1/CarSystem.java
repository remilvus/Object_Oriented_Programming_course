package agh.cs.lab_archive.lab1;


import agh.cs.project.MapBasicLogic.Directions;

public class CarSystem {
    public static void main(String[] args) {
        System.out.println("Start");
        Directions[] d = new Directions[args.length];
        for(int i=0; i<args.length; i++){
            d[i] = Directions.fromString(args[i]);
        }
        run(d);
        System.out.println("Finish");
    }

    private static void run(Directions... d){
        for(Directions command: d){
            switch (command){
                case FORWARD:
                    System.out.println("moving forward");
                    break;
                case BACK:
                    System.out.println("moving backward");
                    break;
                case RIGHT:
                    System.out.println("turning right");
                    break;
                case LEFT:
                    System.out.println("turning left");
                    break;
            }
        }
    }
}
