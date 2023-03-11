package multithreading;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import controller.Controller;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnimalThread implements Runnable{
    private final Area area;
    private static volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    public AnimalThread(Area area) {
        this.area = area;
    }

    @Override
    public void run() {
        while (Controller.programIsRunning) {
            Thread.currentThread().setName("AnimalThread");
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            animalActions();

           // System.out.println("Message from: " + Thread.currentThread().getName());
            System.out.println("Iteration #" + atomicInteger.incrementAndGet());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void animalActions(){
        Cell[][] cells = area.getAreaCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                CopyOnWriteArrayList<Animal> animals = cells[i][j].getAnimals();
                Collections.shuffle(animals);
                for (int k = 0; k < animals.size(); k++) {
                    if (animals.get(k) == null) {
                        continue;
                    }
                    Animal animal = animals.get(k);
                    int cordX = animal.getCordX();
                    int cordY = animal.getCordY();
                    animal.reproduction();
                    animal.eat();
                    animal.move();
                    int newCordX = animal.getCordX();
                    int newCordY = animal.getCordY();
                    if (cordX != newCordX || cordY != newCordY) {
                        animals.set(k, null);
                        cells[newCordY][newCordX].getAnimals().add(animal);
                    }
                }
                animals = animals.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
                cells[i][j].setAnimals(animals);
            }
        }
    }

}
