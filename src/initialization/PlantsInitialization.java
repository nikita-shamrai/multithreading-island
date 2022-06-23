package initialization;

import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import plants.Plant;
import randomizer.Randomizer;
import util.MainSingleton;

public class PlantsInitialization {

    private final MainSingleton mainSingleton = MainSingleton.getInstance();
    private final Config config;
    private final Area area;


    public PlantsInitialization(Config config, Area area) {
        this.config = config;
        this.area = area;
    }


    public void fillAreaWithPlants(){
        int totalPlants = config.getPlantQuantity();
        while (totalPlants > 0) {
            int cordX = Randomizer.randomNumberGenerator(area.getAreaWidth());
            int cordY = Randomizer.randomNumberGenerator(area.getAreaHeight());
            Cell cell = area.getAreaCells()[cordY][cordX];
            if (cell.getPlants().size() >= config.getPlantsMaxQuantityForCell()) {
                continue;
            }
            cell.getPlants().add(new Plant());
            totalPlants--;
        }
    }

    /*public void fillAreaWithPlants() {
        Cell[][] cell = area.getAreaCells();
        int initPlantQuantity = config.getPlantQuantity();
        while (initPlantQuantity > 0) {
            for (int i = 0; i < area.getAreaCells().length; i++) {
                for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                    if (cell[i][j].getPlants().size() < config.getPlantsMaxQuantityForCell()) {
                        addPlantToCellOrSkip(cell[i][j], initPlantQuantity);
                        //addPlantsToCell(cell[i][j], plantsQuantityOnCellNow);
                    }
                }
            }
        }
    }

    private void addPlantToCellOrSkip(Cell cell, int initPlantQuantity) {
        int result = Randomizer.randomNumberGenerator(2);
        switch (result) {
            case 0:
                return;
            case 1:
                addPlantsToCell(cell, true, initPlantQuantity);
                break;
            case 2:
                addPlantsToCell(cell, false, initPlantQuantity);
                break;
        }
    }

    private void addPlantsToCell(Cell cell, boolean addOnePlant, int initPlantQuantity) {
        int plantsAddedOnCell = 0;
        int numberOfPlantsToAdd = Randomizer.randomNumberGenerator(3);
        if (addOnePlant) {
            numberOfPlantsToAdd = 1;
        }
        if (initPlantQuantity - numberOfPlantsToAdd >= 0) {
            for (int i = 0; i < numberOfPlantsToAdd; i++) {
                if (cell.getPlants().size() < config.getPlantsMaxQuantityForCell()) {
                    Plant plant = new Plant();
                    cell.getPlants().add(plant);
                    mainSingleton.getPlantList().add(plant);
                    plantsAddedOnCell++;
                } else break;
            }
        }
        initPlantQuantity -= plantsAddedOnCell;
    }*/
}
