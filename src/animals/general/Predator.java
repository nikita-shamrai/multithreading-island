package animals.general;

import randomizer.Randomizer;
import util.AnimalsUTIL;
import util.MainSingleton;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Predator extends Animal {
    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    //ELEMENT IN LIST IS NULL!!!
    public void eat() {
        if (this.getCurrentHungryPercent() <= 80.0) {
            CopyOnWriteArrayList<Animal> animalsOnCell = this.getArea().getAreaCells()[this.getCordY()][this.getCordX()].getAnimals();
            if (/*animalsOnCell != null && */animalsOnCell.size() > 1) {
                for (int i = 0; i < animalsOnCell.size(); i++) {
                    Animal animal = animalsOnCell.get(i);
                    if (animal == null) {
                        continue;
                    }
                    int killingChance = AnimalsUTIL.chanceToKill(this.getName(), animal.getName());
                    if (killingChance > 0) {
                        int fightResult = Randomizer.randomNumberGenerator(100);
                        if (fightResult < killingChance) {

                            /*TEMP */ System.out.println(this.getName()+" "+this.getAnimalID() + " KILLED "
                                    + animal.getName() +" "+animal.getAnimalID() + " at cell " + animal.getCordX()+ " " + animal.getCordY()); /*TEMP */

                            double victimWeight = animal.getWeight();
                            mainSingleton.getAnimalList().remove(animal);

                            animalsOnCell.set(i, null);
                            double requiredMeal = (this.getFullSatietyWeight()) * (100 - this.getCurrentHungryPercent()) / 100;
                            if (victimWeight >= requiredMeal) {
                                this.setCurrentHungryPercent(100.0);
                                break;
                            } else {
                                this.setCurrentHungryPercent(100 - (100 * (requiredMeal - victimWeight) / this.getFullSatietyWeight()));
                            }
                        }
                    }
                }
            }
        }
    }

    ;
}
