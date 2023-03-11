package util;

import animals.general.Animal;
import area_and_cell.Area;
import lombok.Getter;
import lombok.Setter;
import plants.Plant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class MainSingleton {

    private Area area;
    private CopyOnWriteArrayList<Animal> animalList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Plant> plantList = new CopyOnWriteArrayList<>();
    private static long objectID = 0;

    private MainSingleton(){}

    public static synchronized long getObjectID() {
        return ++objectID;
    }

    private static class MainSingletonLoader {
        static final MainSingleton mainSingleton = new MainSingleton();
    }
    public static MainSingleton getInstance(){
        return MainSingletonLoader.mainSingleton;
    }

}
