package multithreading;

import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import controller.Controller;
import plants.Plant;
import randomizer.Randomizer;
import util.MainSingleton;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PlantsThread implements Runnable{
    private final Area area;
    private final Config config;
    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    public PlantsThread(Area area, Config config) {
        this.area = area;
        this.config = config;
    }

    @Override
    public void run() {
        while (Controller.programIsRunning) {
            Thread.currentThread().setName("PlantsThread");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            plantLifeTime();
            randomPlacePlantGenerator();

       //     System.out.println("Message from: " + Thread.currentThread().getName());
        }
    }

    public void plantLifeTime(){
        Cell[][] cells = area.getAreaCells();
        for (Cell[] cell : cells) {
            for (Cell value : cell) {
                CopyOnWriteArrayList<Plant> plants = value.getPlants();
                for (int k = 0; k < plants.size(); k++) {
                    Plant plant = plants.get(k);
                    double lifetime = ChronoUnit.SECONDS.between(plant.getPlantCreationTime(), LocalTime.now());
                    if (lifetime >= config.getPlantLiveTime()) {
                        plants.set(k, null);
                        mainSingleton.getPlantList().remove(plant);
                    }
                }
                plants = plants.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
                value.setPlants(plants);
            }
        }
    }

    public void randomPlacePlantGenerator() {
        if (mainSingleton.getPlantList().size() < config.getPlantQuantity()) {
            int randomPlantQuantity = Randomizer.randomNumberGenerator(config.getPlantQuantity() - mainSingleton.getPlantList().size());
            int count = 0;
            while (count < randomPlantQuantity) {
                int cordX = Randomizer.randomNumberGenerator(area.getAreaWidth());
                int cordY = Randomizer.randomNumberGenerator(area.getAreaHeight());
                Cell cell = area.getAreaCells()[cordY][cordX];
                if (cell.getPlants().size() < config.getPlantsMaxQuantityForCell()) {
                    Plant plant = new Plant();
                    cell.getPlants().add(plant);
                    mainSingleton.getPlantList().add(plant);
                    count++;
                }
            }
        }
    }




}
