Code written for laboratories at AGH.
  
  
[Project 1:](https://github.com/remilvus/Object_Oriented_Programming_course/tree/master/agh/src/agh/cs/project) a simple wild life simulation. It can be run from [World.java](https://github.com/remilvus/Object_Oriented_Programming_course/blob/master/agh/src/main/World.java)

Input file variables:
* width - map width
* height - map height
* startEnergy - all animals' starting energy level
* moveEnergy - energy used every day per move
* plantEnergy - energy an animal gets by eating a plant
* startingAnimals - number of animals at the beggining
* jungleRatio - % of every side of map wchich will be taken by a jungle. Possible range=(0, 1)
* epochs - how long to run the simulation for
* waitTime - ms of time to wait between epochs
* fontSize - size of font for map visualization
* logFile - `1` for logging map every day to `output.txt`; antyhing other than `1` disables logging
