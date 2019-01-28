package model;


import javafx.scene.shape.Rectangle;
import view.river.RiverView;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    private  Hospital sourceHospital;
    private  Hospital destinyHopistal;
    private  int id;
    private int currentPositionI, currentPositionJ;
    private int initialPosistionI, initialPositionJ;


    private List<Object> onTopOfList = new ArrayList<>();

    private double currentBattery =100;
    private double initialBattery = 100;


    private boolean isStarted = false;
    private boolean isTookOff = false;
    private Boolean isBadConnection = false;
    private boolean isEconomyMode = false;

    private List<Listener> listeners = new ArrayList<>();

    private double batteryPerBlock =1;
    private double batteryPerSecond =1;

    private double distanceHospitalSource;
    private double distanceHospitalDestiny;
    private boolean strongWind;
    private boolean isAutomatic;
    private boolean isManual;
    private boolean goingManualToDestiny;
    private boolean goingAutomaticToDestiny;
    private boolean returningToHome;
    private boolean safeland;
    private  boolean isAspect;



    public boolean isAspect() {
        return isAspect;
    }

    public void setAspect(boolean aspect) {
        isAspect = aspect;
    }

    public Boolean getBadConnection() {
        return isBadConnection;
    }

    public boolean isSafeland() {
        return safeland;
    }

    public void setSafeland(boolean safeland) {
        this.safeland = safeland;
    }

    synchronized public void setManual(boolean manual) {
        isManual = manual;
    }

    synchronized public void setReturningToHome(boolean returningToHome) {
        this.returningToHome = returningToHome;
    }

    synchronized public boolean isManual() {
        return isManual;
    }

    synchronized public void setIsManual(boolean manual) {
        isManual = manual;
    }

    synchronized public void setGoingAutomaticToDestiny(boolean goingAutomaticToDestiny) {
        this.goingAutomaticToDestiny = goingAutomaticToDestiny;
    }

    synchronized public int getInitialPosistionI() {
        return initialPosistionI;
    }

    synchronized public void setInitialPosistionI(int initialPosistionI) {
        this.initialPosistionI = initialPosistionI;
    }

    synchronized public int getInitialPositionJ() {
        return initialPositionJ;
    }

    synchronized public void setInitialPositionJ(int initialPositionJ) {
        this.initialPositionJ = initialPositionJ;
    }

    synchronized public double getInitialBattery() {
        return initialBattery;
    }

    synchronized public void setInitialBattery(double initialBattery) {
        this.initialBattery = initialBattery;
    }

    synchronized public boolean isStrongWind() {
        return strongWind;
    }

    synchronized public boolean isAutomatic() {
        return isAutomatic;
    }

    synchronized public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    synchronized public double getDistanceHospitalDestiny() {
        return distanceHospitalDestiny;
    }

    synchronized public void setDistanceHospitalDestiny(double distanceHospitalDestiny) {
        this.distanceHospitalDestiny = distanceHospitalDestiny;
    }

    synchronized public void setBAdConnection(boolean BAdConnection) {
        isBadConnection = BAdConnection;
    }


    synchronized public void setBatteryPerBlock(double batteryPerBlock) {
        this.batteryPerBlock = batteryPerBlock;
    }

    synchronized public void setBatteryPerSecond(double batteryPerSecond) {
        this.batteryPerSecond = batteryPerSecond;
    }

    synchronized public void setDistanceHospitalSource(double distanceHospitalSource) {
        this.distanceHospitalSource = distanceHospitalSource;
    }

    synchronized public double getDistanceHospitalSource() {
        return distanceHospitalSource;
    }

    synchronized public void setStrongWind(boolean strongWind) {
        this.strongWind = strongWind;
    }

    synchronized public void setIsAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    synchronized public boolean IsGoingADestiny() {
        return goingManualToDestiny;
    }

    synchronized public boolean isGoingAutomaticToDestiny() {
        return goingAutomaticToDestiny;
    }

    synchronized public boolean isReturningToHome() {
        return returningToHome;
    }

    public boolean isSafeLand() {
        return safeland;
    }

    public interface Listener{
        public void onChange(Drone drone);
    }

    public Drone(int id, int initialPositionI, int initialPositionJ, Hospital sourceHospital, Hospital destinyHopistal) {
        this.id = id;
        this.currentPositionI = initialPositionI;
        this.currentPositionJ = initialPositionJ;
        this.initialPosistionI = initialPositionI;
        this.initialPositionJ = initialPositionJ;

        this.sourceHospital = sourceHospital;
        this.destinyHopistal = destinyHopistal;
    }




    synchronized public int getCurrentPositionI() {
        return currentPositionI;
    }

    synchronized public void setCurrentPositionI(int currentPositionI) {
        this.currentPositionI = currentPositionI;
    }

    synchronized public int getCurrentPositionJ() {
        return currentPositionJ;
    }

    synchronized public void setCurrentPositionJ(int currentPositionJ) {
        this.currentPositionJ = currentPositionJ;
    }

    synchronized public List<Object> getOnTopOfList() {
        return onTopOfList;
    }

    synchronized public void addOnTopOfDroneList(Object onTopOf) {
        this.onTopOfList.add(onTopOf);
    }

    synchronized public List<Listener> getListeners() {
        return listeners;
    }

    synchronized public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }

    synchronized public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    synchronized public double getCurrentBattery() {
        return currentBattery;
    }

    synchronized public void setCurrentBattery(double currentBattery) {
        this.currentBattery = currentBattery;
    }

    synchronized public boolean isStarted() {
        return isStarted;
    }

    synchronized public void setStarted(boolean started) {
        isStarted = started;
    }

    synchronized public boolean isTookOff() {
        return isTookOff;
    }

    synchronized public void setTookOff(boolean tookOff) {
        isTookOff = tookOff;
    }

    synchronized public int getId() {
        return id;
    }

    synchronized public void setId(int id) {
        this.id = id;
    }

    synchronized public double getBatteryPerBlock() {
        return batteryPerBlock;
    }

    synchronized public double getBatteryPerSecond() {
        return batteryPerSecond;
    }

    synchronized public boolean isEconomyMode() {
        return isEconomyMode;
    }

    synchronized public void setEconomyMode(boolean economyMode) {
        isEconomyMode = economyMode;
    }

    synchronized public void setOnTopOfList(List<Object> onTopOfList) {
        this.onTopOfList = onTopOfList;
    }

    synchronized public Boolean isBadConnection() {
        return isBadConnection;
    }

    synchronized public void setBadConnection(Boolean BAdConnection) {
        isBadConnection = BAdConnection;
    }

    synchronized public Hospital getSourceHospital() {
        return sourceHospital;
    }

    synchronized public Hospital getDestinyHopistal() {
        return destinyHopistal;
    }

    synchronized public void setSourceHospital(Hospital sourceHospital) {
        this.sourceHospital = sourceHospital;
    }

    synchronized public void setDestinyHopistal(Hospital destinyHopistal) {
        this.destinyHopistal = destinyHopistal;
    }

    synchronized public boolean isBAdConnection() {
        return isBadConnection;
    }

    synchronized public boolean isGoingManualToDestiny() {
        return goingManualToDestiny;
    }

    synchronized public void setGoingManualToDestiny(boolean goingManualToDestiny) {
        this.goingManualToDestiny = goingManualToDestiny;
    }

    synchronized public boolean isOnWater(){

        if(onTopOfList.isEmpty()){
            return false;
        }
        for(Object object :onTopOfList){
            if(object instanceof RiverView){
                return true;
            }
        }

        return false;
    }

    private void notifiesListeners(){
        for (Listener listener : listeners){
            listener.onChange(this);
        }
    }
}