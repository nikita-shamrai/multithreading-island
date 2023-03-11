package animals.general;

import plants.Plant;
import util.MainSingleton;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Herbivore extends Animal{
    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    public void eat(){
        if (this.getCurrentHungryPercent() <= 80.0){
            CopyOnWriteArrayList<Plant> plantsOnCell = this.getArea().getAreaCells()[this.getCordY()][this.getCordX()].getPlants();
            if (plantsOnCell != null && plantsOnCell.size() > 0){
                while (plantsOnCell.size() > 0 && this.getCurrentHungryPercent() < 100.0) {
                    double requiredMeal = (this.getFullSatietyWeight()) * (100 - this.getCurrentHungryPercent()) / 100;
                    plantsOnCell.remove(0);
                    mainSingleton.getPlantList().remove(0);
                    /*TEMP
                    System.out.println(this.getName() + " " +this.getAnimalID() + " ATE PLANT at cell "
                    +this.getCordX()+ " "+this.getCordY()+" plants left on cell - " + plantsOnCell.size());
                    /*TEMP */
                    if (requiredMeal <= 1) {
                        this.setCurrentHungryPercent(100.0);
                        break;
                    } else {
                        double onePlantPercentage = (100 - this.getCurrentHungryPercent()) / requiredMeal;
                        this.setCurrentHungryPercent(this.getCurrentHungryPercent() + onePlantPercentage);
                    }
                }
            }

        }
    }
}
