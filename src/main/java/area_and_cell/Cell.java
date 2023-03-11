package area_and_cell;

import animals.general.Animal;
import lombok.Getter;
import lombok.Setter;
import plants.Plant;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Cell implements Runnable{

    private final String emptyEmoji = "▪️";
    private String currentEmoji;
    private CopyOnWriteArrayList<Plant> plants = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();

    @Override
    public void run() {

    }
}
