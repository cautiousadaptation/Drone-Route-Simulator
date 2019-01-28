package view.antenna;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;

import java.util.ArrayList;
import java.util.List;

public abstract class AntennaView extends Group implements SelectableView {
    public static List<AntennaView> antennaViewList = new ArrayList<>();

    /*public static void cleanAntennaViewList() {}

    public static List<AntennaView> getAntennaViewList() {
        return antennaViewList;
    }

    public static void removeAntennaViewFromList(AntennaView antenaView) {

    }

    public static void addAntennaViewFromList(AntennaView antenaView){

    }*/

    Object getAntenna() {
        return null;
    }

    public Node getNode() {
        return null;
    }

    @Override
    public void removeStyleSelected() {

    }

    @Override
    public void applyStyleSelected() {

    }
}
