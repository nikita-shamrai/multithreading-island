package initialization;

import animals.general.Animal;
import configuration.animalConfigurations;
import configuration.Config;

import java.util.List;

public class AnimalFactory {

    private final Config config;

    public AnimalFactory(Config config) {
        this.config = config;
    }

    public Animal createAnimal (String className){
        List<animalConfigurations> animalConfigurationsList = config.getAnimalConfigurations();



        return null;
    }
}
