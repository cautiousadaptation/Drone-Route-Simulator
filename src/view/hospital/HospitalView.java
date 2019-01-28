package view.hospital;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;


import java.util.ArrayList;
import java.util.List;

public abstract class HospitalView  extends Group implements SelectableView {
    public static List<HospitalView> hospitalViewList = new ArrayList<>();
    public Object getHospital() {
        return null;
    }

    public Node getNode() {
        return null;
    }
/*
    public static void cleanHospitalViewList() {}

    public static List<HospitalView> getHospitalViewList() {
        return hospitalViewList;
    }

    public static void removeHospitalViewFromList(HospitalView hospitalView) {

    }

    public static void addHospitalViewFromList(HospitalView hospitalView){

    }*/


    @Override
    public void removeStyleSelected() {

    }

    @Override
    public void applyStyleSelected() {

    }
}
