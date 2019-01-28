import javafx.application.Platform;
import model.Drone;
import view.drone.DroneViewImpl;

import java.util.Timer;
import java.util.TimerTask;

public aspect Aspect {
    private boolean isMoveAside; pointcut safeLanding() : call (void view.drone.DroneViewImpl.safeLanding());


    void around() : safeLanding() && if(
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getCurrentBattery()>5
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalDestiny() <=60
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isStrongWind()
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isAspect()
            ){

        System.out.println(" Start Aspect around");
        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
        Drone drone = (Drone) droneView.getDrone();

        System.out.println("drone.isOnWater() "+drone.isOnWater());
        System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
        System.out.println("drone.isStrongWind() "+drone.isStrongWind());
        System.out.println("drone.isAutomatic() "+drone.isAutomatic());


        keepFlying(droneView);


        System.out.println("finish Aspect");
    }


    void around() : safeLanding() && if(
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isOnWater()
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isAspect()
            ) {

        System.out.println(" Start Aspect around");
        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
        Drone drone = (Drone) droneView.getDrone();


        System.out.println("drone.isOnWater() "+drone.isOnWater());
        System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
        System.out.println("drone.isStrongWind() "+drone.isStrongWind());
        System.out.println("drone.isAutomatic() "+drone.isAutomatic());


        moveASide(droneView);


        System.out.println("finish Aspect");

    }


    private void moveASide(DroneViewImpl droneView) {

        if(isMoveAside){
            return;
        }

        Drone drone = (Drone) droneView.getDrone();

        if(drone.isGoingAutomaticToDestiny()  || drone.isAutomatic()){
            droneView.stopGoAutomaticDestiny();
        }

        if(drone.isReturningToHome() || drone.isBadConnection()){
            droneView.stopReturnToHome();
        }


        System.out.println("Drone[" + drone.getId() + "] " + "Move aside aspect");
        droneView.loggerController.print("Drone[" + drone.getId() + "] " + "Move aside aspect");


        /*Get off the river*/
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {
                    droneView.flyingUp();

                    droneView.updadePositionDroneView();
                    droneView.updateItIsOver();



                    if (!drone.isOnWater()) {
                        safeLandingAspect(droneView);
                        isMoveAside=false;
                        cancel();
                        return;
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);


    }

    /*This specific aspect method was created to avoid infinite loop*/
    private void safeLandingAspect(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        drone.setTookOff(false);

        System.out.println("Drone["+drone.getId()+"] "+"SafeLand aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"SafeLand aspect");

        droneView.applyStyleLanded();

        System.out.println("Drone["+drone.getId()+"] "+"Landed aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Landed aspect");

        shutDown(droneView);

        if(drone.isOnWater()){
            System.out.println("Drone["+drone.getId()+"] "+"Drone landed on water aspect");
            droneView.loggerController.print("Drone["+drone.getId()+"] "+"Drone landed on water aspect");
        }else {
            System.out.println("Drone["+drone.getId()+"] "+"Drone landed successfully aspect");
            droneView.loggerController.print("Drone["+drone.getId()+"] "+"Drone landed successfully aspect");
        }

    }

    /*This particular aspect method was created to differentiate the management*/
    private void shutDown(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        drone.setStarted(false);

        droneView.removeStyleSelected();

        System.out.println("Drone["+drone.getId()+"] "+"shutdown aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"shutdown aspect");

    }

    /*Continues flying towards the destination hospital*/
    private void keepFlying(DroneViewImpl droneView) {

        Drone drone = (Drone) droneView.getDrone();

        if(drone.isGoingAutomaticToDestiny()){
            droneView.stopGoAutomaticDestiny();
        }


        System.out.println("Drone[" + drone.getId() + "] " + "keep Flying aspect");
        droneView.loggerController.print("Drone[" + drone.getId() + "] " + "keep Flying aspect");


        Timer timer = new Timer();
        TimerTask goAutomaticDestinyTimerTask = new TimerTask() {
            @Override
            public void run() {

                if (!drone.isAutomatic()) {
                    cancel();
                    return;
                }

                if (drone.isBadConnection()) {
                    droneView.stopReturnToHome();

                }

                if (drone.getDistanceHospitalDestiny() == 0) {
                    landing(droneView);

                    shutDown(droneView);

                    System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination aspect");
                    droneView.loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination aspect");
                    cancel();
                    return;

                }

                /*Flying towards the destination hospital*/
                Platform.runLater(() -> {
                    try {


                        int oldI = drone.getCurrentPositionI();
                        int oldJ = drone.getCurrentPositionJ();
                        double newDistanceDestiny = 999999;
                        String mustGO = null;

                        double tempDistance = droneView.distanceDroneWentRight(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "->";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentLeft(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "<-";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentUp(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "/\\";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentDown(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "\\/";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);


                        if (drone.getDistanceHospitalDestiny() != 0) {
                            droneView.goTo(mustGO);
                            droneView.updadePositionDroneView();

                            droneView.updateBattery();

                            droneView.updateItIsOver();


                        }

                        if (drone.getDistanceHospitalDestiny() == 0) {
                            droneView.landing();

                            droneView.shutDown();

                            System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination aspect");
                            droneView.loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination aspect");
                            cancel();
                            return;

                        }


                    } catch (Exception e) {
                        System.out.println();
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(goAutomaticDestinyTimerTask,0,1000);

    }

    /*This particular aspect method was created to differentiate the management*/
    private void landing(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        drone.setTookOff(false);

        System.out.println("Drone["+drone.getId()+"] "+"Landing aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Landing aspect");

        droneView.applyStyleLanded();

        System.out.println("Drone["+drone.getId()+"] "+"Landed aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Landed aspect");
    }



    pointcut applyEconomyMode() : call (void view.drone.DroneViewImpl.applyEconomyMode());
    void around() : applyEconomyMode() &&
            if(((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isAspect())
            {


                System.out.println(" Start Aspect");
                DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
                Drone drone = (Drone) droneView.getDrone();
                System.out.println("drone.isOnWater() "+drone.isOnWater());
                System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
                System.out.println("drone.isStrongWind() "+drone.isStrongWind());
                System.out.println("drone.isAutomatic() "+drone.isAutomatic());

                normalMode(droneView);

                System.out.println(" Finish Aspect");
            }


    /*Continues in the normal mode of battery consumption*/
    private void normalMode(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        System.out.println("Drone["+drone.getId()+"] "+"Continue Normal Mode aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Continue Normal Mode aspect");
        drone.setEconomyMode(false);

    }




    pointcut returnToHome() : call (void view.drone.DroneViewImpl.returnToHome());
    void around() : returnToHome() &&
            if(
            (
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalDestiny()
            <
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalSource()
            )
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getCurrentBattery()
            >
            10
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isAspect()
            ) {

        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();

        glide(droneView);


    }

    private void glide(DroneViewImpl droneView) {

        Drone drone = (Drone) droneView.getDrone();
        System.out.println("Drone["+drone.getId()+"] "+" Glide aspect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Glide aspect");

        if(drone.isGoingAutomaticToDestiny()){
            droneView.stopGoAutomaticDestiny();
            drone.setGoingAutomaticToDestiny(false);
        }
    }
}