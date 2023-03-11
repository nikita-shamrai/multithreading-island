package util;

import animals.general.Animal;
import area_and_cell.Cell;

import java.util.HashMap;
import java.util.Map;

public class AnimalsUTIL {

    public static boolean possibilityToAddAnimalOnCell(Animal animal, Cell cell) {
        int countOfAnimals = 0;
        Class<? extends Animal> providedAnimalType = animal.getClass();
        for (Animal currentAnimalInList :
                cell.getAnimals()) {
            if (currentAnimalInList == null){
                continue;
            }
            if (currentAnimalInList.getClass().equals(providedAnimalType)) {
                countOfAnimals++;
            }
        }
        return countOfAnimals < animal.getMaxQuantityForCell();
    }

    public static int chanceToKill(String killerName, String victimName){
        int killingChance = 0;
        Map <String, Map<String, Integer>> killingTable = new HashMap<>();
        initializeKillingTable(killingTable);
        if (killingTable.containsKey(killerName)) {
            Map<String, Integer> victimsMap = killingTable.get(killerName);
            if (victimsMap.containsKey(victimName)) {
                killingChance = victimsMap.get(victimName);
            }
        }
        return killingChance;
    }

    private static void initializeKillingTable(Map<String, Map<String, Integer>> killingTable) {
        killingTable.put("🐺", new HashMap<String, Integer>());
        killingTable.get("🐺").put("🐎", 10);
        killingTable.get("🐺").put("🦌", 15);
        killingTable.get("🐺").put("🐇", 60);
        killingTable.get("🐺").put("🐁", 80);
        killingTable.get("🐺").put("🐐", 60);
        killingTable.get("🐺").put("🐑", 70);
        killingTable.get("🐺").put("🐗", 15);
        killingTable.get("🐺").put("🐃", 10);
        killingTable.get("🐺").put("🦆", 40);

        killingTable.put("🐍", new HashMap<String, Integer>());
        killingTable.get("🐍").put("🦊", 15);
        killingTable.get("🐍").put("🐇", 20);
        killingTable.get("🐍").put("🐁", 40);
        killingTable.get("🐍").put("🦆", 10);

        killingTable.put("🦊", new HashMap<String, Integer>());
        killingTable.get("🦊").put("🐇", 70);
        killingTable.get("🦊").put("🐁", 90);
        killingTable.get("🦊").put("🦆", 60);
        killingTable.get("🦊").put("🐛", 40);

        killingTable.put("🐻", new HashMap<String, Integer>());
        killingTable.get("🐻").put("🐍", 80);
        killingTable.get("🐻").put("🐎", 40);
        killingTable.get("🐻").put("🦌", 80);
        killingTable.get("🐻").put("🐇", 80);
        killingTable.get("🐻").put("🐁", 90);
        killingTable.get("🐻").put("🐐", 70);
        killingTable.get("🐻").put("🐑", 70);
        killingTable.get("🐻").put("🐗", 50);
        killingTable.get("🐻").put("🐃", 20);
        killingTable.get("🐻").put("🦆", 10);

        killingTable.put("🦅", new HashMap<String, Integer>());
        killingTable.get("🦅").put("🦊", 10);
        killingTable.get("🦅").put("🐇", 90);
        killingTable.get("🦅").put("🐁", 90);
        killingTable.get("🦅").put("🦆", 80);

        killingTable.put("🐁", new HashMap<String, Integer>());
        killingTable.get("🐁").put("🐛", 90);

        killingTable.put("🐗", new HashMap<String, Integer>());
        killingTable.get("🐗").put("🐁", 50);
        killingTable.get("🐗").put("🐛", 90);

        killingTable.put("🦆", new HashMap<String, Integer>());
        killingTable.get("🦆").put("🐛", 90);
    }

   /* public static double requiredWeightForFullSatiety(String animalName) {
        Map <String, Double> satietyTable = new HashMap<>();
        satietyTableInitialize(satietyTable);
        return satietyTable.get(animalName);
    }

    private static void satietyTableInitialize(Map<String, Double> satietyTable) {
        satietyTable.put("🐺", 8.0);
        satietyTable.put("🐍", 3.0);
        satietyTable.put("🦊", 2.0);
        satietyTable.put("🐻", 80.0);
        satietyTable.put("🦅", 1.0);
        satietyTable.put("🐎", 60.0);
        satietyTable.put("🦌", 50.0);
        satietyTable.put("🐇", 0.45);
        satietyTable.put("🐁", 0.01);
        satietyTable.put("🐐", 10.0);
        satietyTable.put("🐑", 15.0);
        satietyTable.put("🐗", 50.0);
        satietyTable.put("🐃", 100.0);
        satietyTable.put("🦆", 0.15);
        satietyTable.put("🐛", 0.0);
    }*/


}
