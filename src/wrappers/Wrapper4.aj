package wrappers;

import controller.BoatAutomaticController;
import controller.CellController;
import controller.DroneController;
import model.entity.boat.Boat;
import model.entity.drone.Drone;
import view.boat.BoatView;
import view.drone.DroneView;

import java.util.ArrayList;
import java.util.List;

public aspect Wrapper4 {
    pointcut isEnable() : call ( boolean controller.BoatAutomaticController.isEnable(*));


    boolean around(): isEnable()
            &&
            if(
            (((Boat)thisJoinPoint.getArgs()[0]).getWrapperId() == 6)
            ){
        System.out.println("enable");
            //the boat wrapper will always be available to help drone, even if it is delivering a product.
        return true;

    }



}
