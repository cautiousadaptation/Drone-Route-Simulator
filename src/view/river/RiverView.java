package view.river;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;

import java.util.ArrayList;
import java.util.List;

public abstract class RiverView  extends Group implements SelectableView {
    public static List<RiverView> riverViewList = new ArrayList<>();
    Object getRiver() {
        return null;
    }

    public Node getNode() {
        return null;
    }

   /* public static void cleanRiverViewList() {}

    public static List<RiverView> getRiverViewList() {
        return riverViewList;
    }

    public static void removeRiverViewFromList(RiverView riverView) {

    }

    public static void addRiverViewFromList(RiverView riverView){

    }
*/

    @Override
    public void removeStyleSelected() {

    }

    @Override
    public void applyStyleSelected() {

    }
}
