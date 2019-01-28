package view;

import javafx.scene.Group;
import javafx.scene.Node;

public  interface  SelectableView  {

    Node getNode();

    void removeStyleSelected();

    void applyStyleSelected();
}
