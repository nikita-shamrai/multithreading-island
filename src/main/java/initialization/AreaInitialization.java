package initialization;

import area_and_cell.Area;
import area_and_cell.Cell;
import configuration.Config;
import util.MainSingleton;

public class AreaInitialization {

    private final MainSingleton mainSingleton = MainSingleton.getInstance();
    private final Config config;
    private Area area;

    public AreaInitialization(Config config) {
        this.config = config;
    }


    public Area areaInitialization() {
        this.area = Area.getInstance(config.getAreaHeight(), config.getAreaWidth());
        cellObjectCreation(area.getAreaCells());
        mainSingleton.setArea(area);
        return area;
    }

    private void cellObjectCreation(Cell[][] cell) {
        for (int i = 0; i < area.getAreaCells().length; i++) {
            for (int j = 0; j < area.getAreaCells()[i].length; j++) {
                cell[i][j] = new Cell();
            }
        }
    }
}
