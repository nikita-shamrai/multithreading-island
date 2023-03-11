package multithreading;

import animals.general.Animal;
import area_and_cell.Area;
import area_and_cell.Cell;
import controller.Controller;
import plants.Plant;
import util.MainSingleton;

import java.util.List;

public class VisualizationThread implements Runnable {
    private final Area area;
    private final MainSingleton mainSingleton = MainSingleton.getInstance();

    public VisualizationThread(Area area) {
        this.area = area;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("VisualizationThread");

        while (Controller.programIsRunning){
            visualArea();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void visualArea() {
        clearConsole();
        Cell[][] areaCells = area.getAreaCells();
        for (int i = 0; i < areaCells.length; i++) {
            for (int j = 0; j < areaCells[i].length; j++) {
                Cell cell = areaCells[i][j];
                List<Animal> animalList = cell.getAnimals();
                StringBuilder currentState = new StringBuilder();
                if (animalList.size() > 1) {
                    currentState.append("\uD83D\uDC3E");
                } else if (animalList.size() == 1) {
                    currentState.append(animalList.get(0).getName());
                } else if (cell.getPlants().size() == 1) {
                    currentState.append(Plant.plantEmoji);
                } else if (cell.getPlants().size() > 1) {
                    currentState.append("\uD83C\uDF3F");
                } else {
                    currentState.append("▫️");
                }
                System.out.print("\t" + currentState);
            }
            System.out.println();
        }
        System.out.println("TOTAL PLANTS - " + mainSingleton.getPlantList().size());
        System.out.println("TOTAL ANIMALS - " + mainSingleton.getAnimalList().size());
        System.out.println("-------------------------------------------------------------");
    }

    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }


}

