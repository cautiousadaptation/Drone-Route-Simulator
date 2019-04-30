package wrappers;

import controller.BoatAutomaticController;
import controller.CellController;
import controller.DroneController;
import model.entity.boat.Boat;
import model.entity.drone.Drone;
import view.boat.BoatView;
import view.drone.DroneView;

public aspect Wrapper4 {
    pointcut getCloserBoatFromDrone() : call ( BoatView wrappers.Wrapper3.getCloserBoatFromDrone(*));


    BoatView around(): getCloserBoatFromDrone()
            &&
            if(
            (((Drone)thisJoinPoint.getArgs()[0]).getWrapperId() == 6)
            ){


        Drone drone = (Drone) thisJoinPoint.getArgs()[0];

        DroneView droneView = DroneController.getInstance().getDroneViewFrom(drone.getUniqueID());

        BoatAutomaticController boatAutomaticController = BoatAutomaticController.getInstance();
        CellController cellController = CellController.getInstance();
        Double closerDistance = 99999999D;
        BoatView closerBoatView = null;

        for (BoatView boatView : boatAutomaticController.getBoatViewMap().values()) {
          //  Boat boat = BoatAutomaticController.getInstance().getBoatFrom(boatView.getUniqueID());

           // if(boat.isShitDown()){

                double tempDistance = cellController.calculeteDisplacementFrom(boatView, droneView);

                if (tempDistance < closerDistance) {
                    closerDistance = tempDistance;
                    closerBoatView = boatView;
                }
           // }

        }

        if(closerBoatView != null && CellController.getInstance().calculeteDisplacementFrom(droneView.getCurrentCellView(),
                closerBoatView.getCurrentCellView()) <= 150){
            return closerBoatView;
        }

        return null;

    }



}
