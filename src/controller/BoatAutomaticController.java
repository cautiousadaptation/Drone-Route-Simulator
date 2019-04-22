package controller;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import model.Cell;
import model.entity.boat.Boat;
import model.entity.boat.BoatBusinessObject;
import util.StopWatch;
import view.CellView;
import view.SelectableView;
import view.boat.BoatView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoatAutomaticController {

    private static BoatAutomaticController instance;

    private Map<String, BoatView> boatViewMap = new HashMap<>();
    private Map<String, Boat> boatMap = new HashMap<>();
    private StopWatch automaticExecutionStopWatch;
    private StopWatch alertNavigateStopWatch;
    private StopWatch navigateStopWatch;
    private boolean mustStopAlertNavigateStopWatch;
    private boolean mustStopNavigateStopWatch;


    private BoatAutomaticController() {
    }

    public static BoatAutomaticController getInstance(){
        if(instance == null){

            instance = new BoatAutomaticController();
        }

        return instance;
    }

    public Boat createBoat(String uniqueID, String boatLabel, CellView currentCellView){

        BoatView boatView  = new BoatView(uniqueID, boatLabel, currentCellView);


        boatViewMap.put(uniqueID, boatView);

        Cell currentCell = CellController.getInstance().getCellFrom(currentCellView.getUniqueID());

        Boat boat = new Boat(uniqueID, boatLabel, currentCell);

        BoatBusinessObject.updateDistances(boat);

        boat.addListener(boatView);

        boatMap.put(uniqueID, boat);

        BoatBusinessObject.updateDistances(boat);

        boat.setSelected(true);

        return boat;
    }



    public BoatView getBoatViewFrom(String identifierBoat) {

        return boatViewMap.get(identifierBoat);
    }

    public Boat getBoatFrom(String identifierBoat) {
        return boatMap.get(identifierBoat);
    }

    public void consumeReset() {
        for (Boat boat : boatMap.values()) {
            BoatBusinessObject.resetSettingsBoat(boat);

        }
        mustStopAlertNavigateStopWatch = true;
        mustStopNavigateStopWatch = true;
        lastCellViewMap.clear();
    }

    public void consumeClickEvent(SelectableView selectedEntityView ) {
        if(selectedEntityView instanceof BoatView){
            Boat boat =  getBoatFrom(selectedEntityView.getUniqueID());
            boat.setSelected(true);
        }
    }

    public void consumeOnKeyPressed(SelectableView selectedEntityView, KeyEvent keyEvent) {
        if(!(selectedEntityView instanceof BoatView)){
            return;
        }

    }


    public void consumeRunEnviroment() {
        mustStopAlertNavigateStopWatch = false;
        mustStopNavigateStopWatch = false;

     alertNavigateStopWatch  = new StopWatch(0, 5000) {
            @Override
            public void task() {
                for(Boat boat : boatMap.values()){
                    if(boat.isShitDown()){

                        Random random = new Random();
                        double randomDouble = random.nextDouble();

                        if(randomDouble>0.6){
                        Platform.runLater(() -> {
                            BoatBusinessObject.notifyRunEnviroment(boat);

                            BoatBusinessObject.start(boat);
                            BoatBusinessObject.normalDestiny(boat);
                            BoatBusinessObject.stocked(boat);

                            //pog

                            BoatView boatView = getBoatViewFrom(boat.getUniqueID());

                            lastCellViewMap.remove(boatView);



                        });
                        }

                    }

                }
            }

            @Override
            public boolean conditionStop() {

                return mustStopAlertNavigateStopWatch;
            }
        };


        navigateStopWatch  = new StopWatch(0, 1000) {
            @Override
            public void task() {
                Platform.runLater(() -> {
                for(Boat boat : boatMap.values()){
                    if(boat.isStarted()){
                        navigate(boat);
                    }


                }
                });
            }

            @Override
            public boolean conditionStop() {
                return mustStopNavigateStopWatch;
            }
        };



    }

    private void navigate(Boat boat) {
        BoatView boatView = getBoatViewFrom(boat.getUniqueID());
        System.out.println("entrou no navegation");

            if(boat.getDistanceDestiny() == 0){
                BoatBusinessObject.returnToHome(boat);
                BoatBusinessObject.shortage(boat);
                //pog
                lastCellViewMap.remove(boatView);

            }else if(boat.getDistanceSource() == 0 && boat.isReturnToHome()){
                BoatBusinessObject.shutDown(boat);

            }

            if(boat.isReturnToHome()){
                CellView cellView = CellController.getInstance().getCellViewFrom(boat.getSourceCell());
                goDestinyAutomatic(boatView, cellView);
                //   System.out.println(boat.getLabel()+":"+cellView.getRowPosition()+","+cellView.getCollunmPosition());
            }else {
                CellView cellView = CellController.getInstance().getCellViewFrom(boat.getDestinyCell());
                goDestinyAutomatic(boatView, cellView);
                //  System.out.println(boat.getLabel()+":"+cellView.getRowPosition()+","+cellView.getCollunmPosition());
            }


            BoatBusinessObject.updateDistances(boat);




    }


    public void consumeCleanEnvironment() {
        boatMap.clear();
        boatViewMap.clear();
        Boat.restartCount();
    }


    public void cleanSelections() {
        for(Boat boat : boatMap.values()){
            boat.setSelected(false);
        }
    }
    //todo pog
    Map<BoatView, CellView > lastCellViewMap = new HashMap<>();

    public void goDestinyAutomatic(BoatView boatView, CellView dstCellView) {

        Boat boat = getBoatFrom(boatView.getUniqueID());
        if(boat.isShitDown()){
            return;
        }

        CellView boatCellView = boatView.getCurrentCellView();

        int oldRownPosition = boatCellView.getRowPosition();
        int oldCollunmPosition = boatCellView.getCollunmPosition();
        CellView oldCellView =  boatCellView;

        double newDistanceDestiny = 999999;
        String mustGO = null;

        double tempDistance = BoatBusinessObject.distanceDroneWentRight(boatCellView, dstCellView);

        //avoid return last cellView
        Cell cell = CellController.getInstance().getCellFrom(oldRownPosition, oldCollunmPosition+1);
        CellView cellView = null;

        if(cell != null){
            cellView = CellController.getInstance().getCellViewFrom(cell);
        }else {
            tempDistance = 999999;
        }


        if(lastCellViewMap.get(boatView) == cellView){
            tempDistance = 999999;
        }

        if (tempDistance < newDistanceDestiny) {
            newDistanceDestiny = tempDistance;
            mustGO = "->";
        }

        tempDistance = BoatBusinessObject.distanceDroneWentLeft(boatCellView, dstCellView);

         cell = CellController.getInstance().getCellFrom(oldRownPosition, oldCollunmPosition-1);

         if(cell != null){
             cellView = CellController.getInstance().getCellViewFrom(cell);
         }else {
             tempDistance = 999999;
         }


        if(lastCellViewMap.get(boatView) == cellView){
            tempDistance = 999999;
        }

        if (tempDistance < newDistanceDestiny) {
            newDistanceDestiny = tempDistance;
            mustGO = "<-";
        }


        tempDistance = BoatBusinessObject.distanceDroneWentUp(boatCellView, dstCellView);

        cell = CellController.getInstance().getCellFrom(oldRownPosition-1, oldCollunmPosition);

        if(cell != null){
            cellView = CellController.getInstance().getCellViewFrom(cell);
        }else {
            tempDistance = 999999;
        }


        if(lastCellViewMap.get(boatView) == cellView){
            tempDistance = 999999;
        }

        if (tempDistance < newDistanceDestiny) {
            newDistanceDestiny = tempDistance;
            mustGO = "/\\";

        }

        tempDistance = BoatBusinessObject.distanceDroneWentDown(boatCellView, dstCellView);

        cell = CellController.getInstance().getCellFrom(oldRownPosition+1, oldCollunmPosition);

        if(cell != null){
            cellView = CellController.getInstance().getCellViewFrom(cell);
        }else {
            tempDistance = 999999;
        }


        if(lastCellViewMap.get(boatView) == cellView){
            tempDistance = 999999;
        }

        if (tempDistance < newDistanceDestiny) {
            newDistanceDestiny = tempDistance;
            mustGO = "\\/";

        }

        boat = getBoatFrom(boatView.getUniqueID());
        lastCellViewMap.put(boatView, oldCellView);

        if (mustGO != null) {
            BoatBusinessObject.goTo(boat, mustGO);
        }


//        DroneBusinessObject.getInstance().checkStatus(drone);

    }

    public Map<String, BoatView> getBoatViewMap() {
        return boatViewMap;
    }

    public void setBoatViewMap(Map<String, BoatView> boatViewMap) {
        this.boatViewMap = boatViewMap;
    }

    public Map<String, Boat> getBoatMap() {
        return boatMap;
    }

    public void setBoatMap(Map<String, Boat> boatMap) {
        this.boatMap = boatMap;
    }
}
