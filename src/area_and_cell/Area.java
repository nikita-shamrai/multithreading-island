package area_and_cell;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Area {

    private Cell[][] areaCells;
    private int areaHeight;
    private int areaWidth;

//    private Area(int areaHeight, int areaWidth) {
//        this.areaHeight = areaHeight;
//        this.areaWidth = areaWidth;
//        this.area = new Cell[areaHeight][areaWidth];
//    }

    private Area (){}

   /* public static Area createInstance(int areaHeight, int areaWidth) {
        Area area = AreaLoader.area;
        area.setAreaHeight(areaHeight);
        area.setAreaWidth(areaWidth);
        area.setAreaCells(new Cell[areaHeight][areaWidth]);

    }*/

    private static class AreaLoader {
        static final Area area = new Area();
    }

    public static Area getInstance (int areaHeight, int areaWidth){
        Area area = AreaLoader.area;
        area.setAreaHeight(areaHeight);
        area.setAreaWidth(areaWidth);
        area.setAreaCells(new Cell[areaHeight][areaWidth]);
        return area;
    }

}