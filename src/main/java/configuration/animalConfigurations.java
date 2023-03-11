package configuration;

import lombok.Getter;

@Getter
//@Setter
public class animalConfigurations {

    private String className;
    private String name;
    private double weight;
    private int maxQuantityForCell;
    private int cellsPerMove;
    private int fullSatietyWeight;
    private double currentHungryPercent;
    private int quantityOnInit;
}
