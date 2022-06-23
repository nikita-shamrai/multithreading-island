package animals.general;

import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.animalConfigurations;
import lombok.*;
import randomizer.Randomizer;
import util.AnimalsUTIL;
import util.MainSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@ToString

public abstract class Animal implements Cloneable {

    private long animalID;
    private Area area;
    private String name;
    private double weight;
    private int maxQuantityForCell;
    private int cellsPerMove;
    private double fullSatietyWeight;
    private double currentHungryPercent;
    private int cordX;
    private int cordY;
    private boolean justBorn = false;
    private boolean reproducedOnCurrentMove = false;
    private MainSingleton mainSingleton = MainSingleton.getInstance();
    private List<Long> parentsID = new ArrayList<>();

    public Animal(animalConfigurations animalConfig) {
        this.animalID = MainSingleton.getObjectID();
        this.name = animalConfig.getName();
        this.weight = animalConfig.getWeight();
        this.maxQuantityForCell = animalConfig.getMaxQuantityForCell();
        this.cellsPerMove = animalConfig.getCellsPerMove();
        this.fullSatietyWeight = animalConfig.getFullSatietyWeight();
        this.currentHungryPercent = 100.0;
    }

    public Animal() {
    }

    public abstract void eat();

    public void move() {
        int countOfMoves = cellsPerMove;
        while (countOfMoves > 0) {
            moveOnSingleCell();
            countOfMoves--;
        }
        while (true) {
            Cell newCell = area.getAreaCells()[cordY][cordX];
            if (AnimalsUTIL.possibilityToAddAnimalOnCell(this, newCell)) {
                this.setJustBorn(false);
                this.setReproducedOnCurrentMove(false);
                break;
            } else {
                moveOnSingleCell();
            }
        }
    }


    private void moveOnSingleCell() {
        boolean inUpperLeftCornerCell = cordX == 0 && cordY == 0;
        boolean inUpperRightCornerCell = cordX == area.getAreaWidth() - 1 && cordY == 0;
        boolean inLowerLeftCornerCell = cordX == 0 && cordY == area.getAreaHeight() - 1;
        boolean inLowerRightCornerCell = cordX == area.getAreaWidth() - 1 && cordY == area.getAreaHeight() - 1;
        boolean inUpperBorderRow = cordY == 0;
        boolean inLowerBorderRow = cordY == area.getAreaHeight() - 1;
        boolean inLeftBorderColumn = cordX == 0;
        boolean inRightBorderColumn = cordX == area.getAreaWidth() - 1;

        int moveFromCorner0or1 = Randomizer.randomNumberGenerator(2);
        int moveFromBorder0or1or2 = Randomizer.randomNumberGenerator(3);
        int moveFromCentralCell0or1or2or3 = Randomizer.randomNumberGenerator(4);

        if (inUpperLeftCornerCell) {
            switch (moveFromCorner0or1) {
                case 0 -> cordX += 1;
                case 1 -> cordY += 1;
            }
        } else if (inUpperRightCornerCell) {
            switch (moveFromCorner0or1) {
                case 0 -> cordX -= 1;
                case 1 -> cordY += 1;
            }
        } else if (inLowerLeftCornerCell) {
            switch (moveFromCorner0or1) {
                case 0 -> cordX += 1;
                case 1 -> cordY -= 1;
            }
        } else if (inLowerRightCornerCell) {
            switch (moveFromCorner0or1) {
                case 0 -> cordX -= 1;
                case 1 -> cordY -= 1;
            }
        } else if (inUpperBorderRow/* && !inUpperLeftCornerCell && !inUpperRightCornerCell*/) {
            switch (moveFromBorder0or1or2) {
                case 0 -> cordX -= 1;
                case 1 -> cordX += 1;
                case 2 -> cordY += 1;
            }
        } else if (inLowerBorderRow/* && !inLowerLeftCornerCell && !inLowerRightCornerCell*/) {
            switch (moveFromBorder0or1or2) {
                case 0 -> cordX -= 1;
                case 1 -> cordX += 1;
                case 2 -> cordY -= 1;
            }
        } else if (inLeftBorderColumn/* && !inUpperLeftCornerCell && !inLowerLeftCornerCell*/) {
            switch (moveFromBorder0or1or2) {
                case 0 -> cordY -= 1;
                case 1 -> cordY += 1;
                case 2 -> cordX += 1;
            }
        } else if (inRightBorderColumn/* && !inUpperRightCornerCell && !inLowerRightCornerCell*/) {
            switch (moveFromBorder0or1or2) {
                case 0 -> cordY -= 1;
                case 1 -> cordY += 1;
                case 2 -> cordX -= 1;
            }
        } else {
            switch (moveFromCentralCell0or1or2or3) {
                case 0 -> cordY -= 1;
                case 1 -> cordX += 1;
                case 2 -> cordY += 1;
                case 3 -> cordX -= 1;
            }
        }
    }

    public void reproduction(){
        CopyOnWriteArrayList<Animal> animalsOnCell = new CopyOnWriteArrayList<>(area.getAreaCells()[cordY][cordX].getAnimals());
        for (var animal:
             animalsOnCell) {
            if (animal == null){
                continue;
            }
            if (animal.getName().equals(this.getName()) && (animal != this) && !animalsAreRelatives(animal, this)
            && !animal.isJustBorn() && !animal.isReproducedOnCurrentMove() && !this.isReproducedOnCurrentMove()){
                int randomReproductionChance = Randomizer.randomNumberGenerator(10);
                if (randomReproductionChance == 0) {
                    Animal childAnimal = animal.clone();
                    animal.setReproducedOnCurrentMove(true);
                    this.setReproducedOnCurrentMove(true);
                    childAnimal.setJustBorn(true);
                    childAnimal.setAnimalID(MainSingleton.getObjectID());
                    childAnimal.getParentsID().add(this.animalID);
                    childAnimal.getParentsID().add(animal.animalID);
                    childAnimal.setCurrentHungryPercent(100);
                    animal.getArea().getAreaCells()[animal.getCordY()][animal.getCordX()].getAnimals().add(childAnimal);
                    mainSingleton.getAnimalList().add(childAnimal);

            /*TEMP!! */       /*System.out.println(animal.getName()+" "+animal.getAnimalID() + " reproducted with " + this.getName()
                    +" "+this.getAnimalID() + " in CELL - "+ animal.getCordX()+" "+animal.getCordY()+
                            " - CHILD:  "+ childAnimal.getName()+" "+childAnimal.getAnimalID());*/

                    break;
                }
            }
        }
    }

    private boolean animalsAreRelatives(Animal animal1, Animal animal2){
        boolean result = false;
        boolean compareOne = animal1.getParentsID().contains(animal2.animalID);
        boolean compareTwo = animal2.getParentsID().contains(animal1.animalID);
        if (compareOne || compareTwo){
            result = true;
        }
        return result;
    }

    @Override
    public Animal clone() {
        try {
            Animal clone = (Animal) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    //protected abstract void eat();
}
