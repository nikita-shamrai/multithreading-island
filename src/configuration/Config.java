package configuration;

import lombok.Getter;

import java.util.List;

@Getter
//@Setter
public class Config {

    private int areaHeight;
    private int areaWidth;
    private int plantsMaxQuantityForCell;
    private int plantQuantity;
    private double plantLiveTime;
    private int simulationDuration;

    private List<animalConfigurations> animalConfigurations; //List animal or animalConfig? Check yaml

    public void setPlantQuantity(int plantQuantity) {
        this.plantQuantity = plantQuantity;
    }


}
