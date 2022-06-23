package controller;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import configuration.YamlParser;
import initialization.AnimalInitialization;
import initialization.AreaInitialization;
import initialization.PlantsInitialization;
import util.MainSingleton;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class backupControllerDELETE {


  /*  private Config config;
    private Area area;
    private final MainSingleton mainSingleton = MainSingleton.getInstance();


    public void startApp() {
        YamlParser yamlParser = new YamlParser("src/configuration.yaml");
        this.config = yamlParser.configInitialize();

        AreaInitialization areaInitialization = new AreaInitialization(config);
        this.area = areaInitialization.areaInitialization();

        PlantsInitialization plantsInitialization = new PlantsInitialization(config, area);
        plantsInitialization.fillAreaWithPlants();

        AnimalInitialization animalInitialization = new AnimalInitialization(config, area);
        animalInitialization.animalInitialize();
        animalInitialization.fillAreaWithAnimals();

        animalActions();

        System.out.println("TOTAL PLANTS - " + mainSingleton.getPlantList().size());
        System.out.println("TOTAL ANIMALS - " + mainSingleton.getAnimalList().size());
    }

    //TEMP CHECK
    private void temporaryCheckPLANTSMethod() {
        for (int i = 0; i < area.getAreaCells().length; i++) {
            System.out.println();
            for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                System.out.print("\t" + area.getAreaCells()[i][j].getPlants().size());
            }
        }
        System.out.println();
    }
    //TEMP CHECK
    private void temporaryCheckANIMALSMethod() {
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
    }*/

    /*private void areaInitialization() {
        this.area = Area.getInstance(config.getAreaHeight(), config.getAreaWidth());
        cellObjectCreation(area.getAreaCells());
        mainSingleton.setArea(area);

    }

    private void cellObjectCreation(Cell[][] cell) {
        for (int i = 0; i < area.getAreaCells().length; i++) {
            for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                cell[i][j] = new Cell();
            }
        }
    }

    private void fillAreaWithPlants() {
        Cell[][] cell = area.getAreaCells();
        while (config.getPlantQuantity() > 0) {
            for (int i = 0; i < area.getAreaCells().length; i++) {
                for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                    if (cell[i][j].getPlants().size() < config.getPlantsMaxQuantityForCell()) {
                        addPlantToCellOrSkip(cell[i][j]);
                        //addPlantsToCell(cell[i][j], plantsQuantityOnCellNow);
                    }
                }
            }
        }
    }

    private void addPlantToCellOrSkip(Cell cell) {
        int result = Randomizer.randomNumberGenerator(2);
        switch (result) {
            case 0:
                return;
            case 1:
                addPlantsToCell(cell, true);
                break;
            case 2:
                addPlantsToCell(cell, false);
                break;
        }
    }

    private void addPlantsToCell(Cell cell, boolean addOnePlant) {
        int plantsAddedOnCell = 0;
        int numberOfPlantsToAdd = Randomizer.randomNumberGenerator(3);
        if (addOnePlant) {
            numberOfPlantsToAdd = 1;
        }
        if (config.getPlantQuantity() - numberOfPlantsToAdd >= 0) {
            for (int i = 0; i < numberOfPlantsToAdd; i++) {
                if (cell.getPlants().size() < config.getPlantsMaxQuantityForCell()) {
                    Plant plant = new Plant();
                    cell.getPlants().add(plant);
                    mainSingleton.getPlantList().add(plant);
                    plantsAddedOnCell++;
                } else break;
            }
        }
        config.setPlantQuantity(config.getPlantQuantity() - plantsAddedOnCell);
    }

    private void animalInitialize() {
        List<animalConfigurations> animalConfigurations = config.getAnimalConfigurations();
        String path = "animals.types.";
        for (var animalConfig :
                animalConfigurations) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(path + animalConfig.getClassName());
                Object animalObjectCheckInstance = clazz.getDeclaredConstructor().newInstance();
                if (animalObjectCheckInstance instanceof Animal) {
                    for (int i = 0; i < animalConfig.getQuantityOnInit(); i++) {
                        Object animalObject = clazz.getDeclaredConstructor().newInstance();
                        Animal animal = setAnimalFields(animalConfig, (Animal) animalObject);
                        mainSingleton.getAnimalList().add(animal);
                    }
                }
            } catch (ClassNotFoundException | InvocationTargetException
                     | InstantiationException | IllegalAccessException
                     | NoSuchMethodException e) {
                throw new RuntimeException("animalInitialize ERROR");
            }
        }
        System.out.println("HashCode singletone - "+mainSingleton.hashCode());
        System.out.println(mainSingleton.getAnimalList().size());
        System.out.println(mainSingleton.getPlantList().size());
    }

    private Animal setAnimalFields(animalConfigurations animalConfig, Animal animalObject) {
        animalObject.setAnimalID(MainSingleton.getObjectID());
        animalObject.setArea(this.area);
        animalObject.setName(animalConfig.getName());
        animalObject.setWeight(animalConfig.getWeight());
        animalObject.setMaxQuantityForCell(animalConfig.getMaxQuantityForCell());
        animalObject.setCellsPerMove(animalConfig.getCellsPerMove());
        animalObject.setFullSatietyWeight(animalConfig.getFullSatietyWeight());
        animalObject.setCurrentHungryPercent(animalConfig.getCurrentHungryPercent());
        return animalObject;
    }

    private void fillAreaWithAnimals() {
        List<Animal> animals = new ArrayList<>(mainSingleton.getAnimalList());
        Cell[][] cell = area.getAreaCells();
        while (animals.size()>0) {
            for (int i = 0; i < area.getAreaCells().length; i++) {
                for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                    if (animals.size() > 0) {
                        Animal animal = getAnimalFromList(animals);
                        if (AnimalsUTIL.possibilityToAddAnimalOnCell(animal, cell[i][j])) {
                            addAnimalToCellOrSkip(cell[i][j], animal, animals, j, i);
                        }
                    }
                }
            }
        }
    }

    private Animal getAnimalFromList(List<Animal> animals) {
        int randomAnimalIndex = Randomizer.randomNumberGenerator(animals.size());
        return animals.get(randomAnimalIndex);
    }

    private void addAnimalToCellOrSkip(Cell cell, Animal animal, List<Animal> animals, int j, int i) {
        int result = Randomizer.randomNumberGenerator(2);
        if (result == 0) {
            animal.setCordX(j);
            animal.setCordY(i);
            cell.getAnimals().add(animal);
            animals.remove(animal);
        }
    }*/

}
