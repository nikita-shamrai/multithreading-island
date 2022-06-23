package multithreading;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import controller.Controller;
import util.MainSingleton;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class AnimalHungryThread implements Runnable{
    private final Area area;
    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    public AnimalHungryThread(Area area) {
        this.area = area;
    }

    @Override
    public void run() {
        while (Controller.programIsRunning) {
            Thread.currentThread().setName("AnimalHungryThread");

            lowerAnimalHungerLevelOnOnePercent();

          //  System.out.println("Message from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

   /* private void lowerAnimalHungerInMainsingleton(){
        for (var animal:
        mainSingleton.getAnimalList()) {
            if (animal.getAnimalID() == 23) {
                System.out.println(animal.getName() + " " + animal.getAnimalID() + " hungry level: " + animal.getCurrentHungryPercent());
            }
            if (animal.getCurrentHungryPercent() > 1.0){
                animal.setCurrentHungryPercent(animal.getCurrentHungryPercent() - 1.0);
            }
            else {
                removeAnimal(animal);
                System.out.println(animal.getName() + " " + animal.getAnimalID() +
                        " died of hunger. \nTotal animals: " + mainSingleton.getAnimalList().size());
            }

        }
    }*/

   /* private void removeAnimal(Animal animal) {
        Cell[][] areaCells = area.getAreaCells();
        for (Cell[] areaCell : areaCells) {
            for (Cell cell : areaCell) {
                CopyOnWriteArrayList<Animal> animals = cell.getAnimals();
                for (int k = 0; k < animals.size(); k++) {
                    if (animals.get(k).equals(animal)){
                        animals.set(k, null);
                        mainSingleton.getAnimalList().remove(animals.get(k));
                    }
                }
                animals = animals.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
                cell.setAnimals(animals);
            }
        }
    }*/

    private void lowerAnimalHungerLevelOnOnePercent() {
        Cell[][] areaCells = area.getAreaCells();
        for (Cell[] areaCell : areaCells) {
            for (Cell cell : areaCell) {
                CopyOnWriteArrayList<Animal> animals = cell.getAnimals();
                for (int k = 0; k < animals.size(); k++) {
                    if (animals.get(k).getCurrentHungryPercent() > 1.0) {
                        animals.get(k).setCurrentHungryPercent(animals.get(k).getCurrentHungryPercent() - 1.0);
                       /* if (animals.get(k).getAnimalID() == 23) {
                            System.out.println(animals.get(k).getName() + " " + animals.get(k).getAnimalID() + " hungry level: " + animals.get(k).getCurrentHungryPercent());
                        }*/
                    } else {
                        animals.set(k, null);
                        mainSingleton.getAnimalList().remove(animals.get(k));
                        System.out.println(animals.get(k).getName() + " " + animals.get(k).getAnimalID() +
                                " died of hunger. \nTotal animals: " + mainSingleton.getAnimalList().size());

                    }
                }
                animals = animals.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
                cell.setAnimals(animals);
            }
        }
    }

}
