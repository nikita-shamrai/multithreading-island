package initialization;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import configuration.animalConfigurations;
import randomizer.Randomizer;
import util.AnimalsUTIL;
import util.MainSingleton;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AnimalInitialization {

    private final MainSingleton mainSingleton = MainSingleton.getInstance();
    private final Config config;
    private final Area area;

    public AnimalInitialization(Config config, Area area) {
        this.config = config;
        this.area = area;
    }

    public void animalInitialize() {
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

    public void fillAreaWithAnimals(){
        List<Animal> animals = new ArrayList<>(mainSingleton.getAnimalList());
        while (animals.size() > 0) {
            int cordX = Randomizer.randomNumberGenerator(area.getAreaWidth());
            int cordY = Randomizer.randomNumberGenerator(area.getAreaHeight());
            Cell cell = area.getAreaCells()[cordY][cordX];
            Animal animal = animals.get(Randomizer.randomNumberGenerator(animals.size()));
            if (!AnimalsUTIL.possibilityToAddAnimalOnCell(animal, cell)) {
                continue;
            }
            animal.setCordX(cordX);
            animal.setCordY(cordY);
            cell.getAnimals().add(animal);
            animals.remove(animal);
        }
    }

   /* public void fillAreaWithAnimals() {
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
