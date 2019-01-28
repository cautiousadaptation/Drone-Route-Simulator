package view;


import javafx.scene.layout.Pane;
import view.res.EnvironmentView;

public class CellView extends Pane {
    private final EnvironmentView environmentView;
    private int i,j;
    private boolean isBadConnection;

    public CellView(int i, int j, EnvironmentView environmentView) {
        this.environmentView = environmentView;
        this.i = i;
        this.j = j;
    }

    public boolean isBadConnection() {
        return isBadConnection;
    }

    public CellView setBadConnection(boolean badConnection) {
        isBadConnection = badConnection;
        return this;
    }

    public int getI() {
        return i;
    }

    public CellView setI(int i) {
        this.i = i;
        return this;
    }

    public int getJ() {
        return j;
    }

    public CellView setJ(int j) {
        this.j = j;
        return this;
    }

    public EnvironmentView getEnvironmentView() {
        return environmentView;
    }
}
