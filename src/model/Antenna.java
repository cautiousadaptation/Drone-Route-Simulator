package model;

public class Antenna {
    private final int id;
    int iPosition, jPosition;

    public Antenna(int id, int iPosition, int jPosition) {
        this.id = id;
        this.iPosition = iPosition;
        this.jPosition = jPosition;
    }

    public int getiPosition() {
        return iPosition;
    }

    public void setiPosition(int iPosition) {
        this.iPosition = iPosition;
    }

    public int getjPosition() {
        return jPosition;
    }

    public void setjPosition(int jPosition) {
        this.jPosition = jPosition;
    }

    public int getId() {
        return id;
    }
}
