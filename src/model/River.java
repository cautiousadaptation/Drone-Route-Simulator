package model;



public class River {

    int iPoisition, JPoisition;

    public River(int iPoisition, int JPoisition) {
        this.iPoisition = iPoisition;
        this.JPoisition = JPoisition;
    }

    public int getJPoisition() {
        return JPoisition;
    }

    public void setJPoisition(int JPoisition) {
        this.JPoisition = JPoisition;
    }

    public int getiPoisition() {
        return iPoisition;
    }

    public void setiPoisition(int iPoisition) {
        this.iPoisition = iPoisition;
    }
}
