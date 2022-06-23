package controller;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import configuration.YamlParser;
import initialization.AnimalInitialization;
import initialization.AreaInitialization;
import multithreading.AnimalHungryThread;
import multithreading.AnimalThread;
import multithreading.PlantsThread;
import multithreading.VisualizationThread;
import plants.Plant;
import util.MainSingleton;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Controller {

    private Config config;
    private Area area;
    private final MainSingleton mainSingleton = MainSingleton.getInstance();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static volatile boolean programIsRunning = true;

    public void startApp() {
        YamlParser yamlParser = new YamlParser("src/configuration.yaml");
        this.config = yamlParser.configInitialize();

        AreaInitialization areaInitialization = new AreaInitialization(config);
        this.area = areaInitialization.areaInitialization();

        AnimalInitialization animalInitialization = new AnimalInitialization(config, area);
        animalInitialization.animalInitialize();
        animalInitialization.fillAreaWithAnimals();

        System.out.println("INIT:");
        temporaryCheckANIMALSMethod();

        System.out.println("STARTED:");

        Thread.currentThread().setName("MainThread");

        executorService.submit(new AnimalThread(area));
        executorService.submit(new PlantsThread(area, config));
        executorService.submit(new AnimalHungryThread(area));
        executorService.submit(new VisualizationThread(area));
        executorService.shutdown();

        while (programIsRunning) {
            /*System.out.println("Message from: " + Thread.currentThread().getName());
            System.out.println("TOTAL PLANTS - " + mainSingleton.getPlantList().size());
            System.out.println("TOTAL ANIMALS - " + mainSingleton.getAnimalList().size());*/
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            System.out.println("THREADSET - " + threadSet.toString());
            if (mainSingleton.getAnimalList().size() == 0) {
                programIsRunning = false;
                executorService.shutdownNow();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



        //TEMP CHECK
    private void temporaryCheckPLANTSMethod() {
        System.out.println("-PLANTS-");
        for (int i = 0; i < area.getAreaCells().length; i++) {
            System.out.println();
            for (int j = 0; j < area.getAreaCells()[i].length; j++) {

                if (area.getAreaCells()[i][j].getPlants().size() > 0) {
                    System.out.print("\t" + area.getAreaCells()[i][j].getPlants().size());
                } else System.out.print("\t" + "-");
            }
        }
        System.out.println();
    }
    //TEMP CHECK
    private void temporaryCheckANIMALSMethod() {
        System.out.println("-ANIMALS-");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < area.getAreaCells().length; i++) {
            System.out.println();
            for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                if (area.getAreaCells()[i][j].getAnimals().size() > 0) {
                    System.out.print("\t" + area.getAreaCells()[i][j].getAnimals().size());
                }
                else System.out.print("\t" + "-");
                }

            }
            System.out.println();
        }

}
