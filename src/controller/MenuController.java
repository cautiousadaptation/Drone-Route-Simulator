package controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.EnvironmentMarshal;

import java.io.File;

public class MenuController {
    private Stage primaryStage;
    private AnchorPane rootAnchorPane;
    private FileChooser fileChooser = new FileChooser();

    public MenuController(AnchorPane rootAnchorPane, Stage primaryStage) {
        this.rootAnchorPane = rootAnchorPane;
        this.primaryStage = primaryStage;
        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);

        Menu menu = new Menu("File");
        MenuItem saveEnvironmentItemMenu = new MenuItem("Save Environment");
        MenuItem loadEnvironmentItemMenu = new MenuItem("Load Environment");
        MenuItem exitItemMenu = new MenuItem("Exit");
        menu.getItems().addAll(saveEnvironmentItemMenu,loadEnvironmentItemMenu,exitItemMenu);

        Menu menu1 = new Menu("Help");
        MenuItem aboutItemMenu = new MenuItem("About");
        menu1.getItems().addAll(aboutItemMenu);

        menuBar.getMenus().addAll(menu, menu1);
        rootAnchorPane.getChildren().add(menuBar);

        saveEnvironmentItemMenu.setOnAction(event -> {
            EnvironmentMarshal.serialize(new File("teste.xml"));
            //configureFileChooserSave();
           // openChooser();
        });

        loadEnvironmentItemMenu.setOnAction(event -> {
            configureFileChooserLoad();
            openChooser();
        });

        exitItemMenu.setOnAction(event -> {

        });

        aboutItemMenu.setOnAction(event -> {

        });

    }

    private void configureFileChooserSave() {
        fileChooser.setTitle("Choose text file with environment settings (.xml)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files","*.xml"));
    }


    private void configureFileChooserLoad() {
        fileChooser.setTitle("Choose text file with environment settings (.xml)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files","*.xml"));

    }

    private File openChooser() {
        return fileChooser.showOpenDialog(primaryStage);
    }

}
