package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Drone;
import model.Hospital;
import view.CellView;
import view.SelectableView;
import view.antenna.AntennaView;
import view.antenna.AntennaViewImpl;
import view.drone.DroneView;
import view.drone.DroneViewImpl;
import view.hospital.HospitalViewImpl;
import view.hospital.HospitalView;
import view.res.EnvironmentView;
import util.DroneAnalyzerLog;
import view.river.RiverView;
import view.river.RiverViewImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class MainController extends Application {

    @FXML
    AnchorPane environmentAnchorPane;

    @FXML
    AnchorPane settingsAnchorPane;

    @FXML
    AnchorPane loggerAnchorPane;

    @FXML
    TextField initialBatteryTextView, consumptionPerBlockTextView, consumptionPerSecondTextView, currentDroneTextField;

    @FXML
    Label initialBatteryLabel, consumptionPerBlockLabel, consumptionPerSecondLabel/*, badConectionLabel*/, currentDroneLabel, sourceLabel, targetLabel;

    @FXML
    ToggleButton startToggleButton, restartToggleButton;

    @FXML
    ToggleButton riverToggleButton, hospitalToggleButton, droneToggleButton, antennaToggleButton;

    /*@FXML
    RadioButton trueBadConnectionRadioButton*//*, randomBadConnectionRadioButton*//*, noBadConnectionRadioButton;
     */
    @FXML
    RadioButton trueStrongWindRadioButton, randomStrongWindRadioButton, noStrongWindRadioButton;

    @FXML
    TextArea loggerTextArea;

    @FXML
    CheckBox automaticCheckBox, wrapperCheckBox;

    @FXML
    Button saveButton, deleteButton, cleanButton;

    @FXML
    ComboBox<String> sourceComboBox, targetComboBox;


    private AnchorPane rootAnchorPane;

    private List<SelectableView> selectableViews = new ArrayList<>();
    private SelectableView selectedSelectableView = null;

    private boolean running = false;
    private DroneView droneViewSelected;
    private boolean droneToggleButtonIsSelected = false;
    private boolean badConnection;
    private LoggerController loggerController = LoggerController.getInstance();
    private EnvironmentView environmentView;
    private Timer ramdomStrongWind;
    private ScheduledExecutorService executor;


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/res/MainPanes.fxml"));
        loader.setController(this);
        rootAnchorPane = loader.load();
        primaryStage.setTitle("Drone Simulator");

        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);
        Menu menuExamples = new Menu("Menu");
        MenuItem exampleLodonMenuItem = new MenuItem("London Example");
        menuExamples.getItems().add(exampleLodonMenuItem);
        menuBar.getMenus().add(menuExamples);
        rootAnchorPane.getChildren().add(menuBar);

        exampleLodonMenuItem.setOnAction(event -> {
         clearEnverionment();

            running = false;

            enableEnvironmentSettingViews();

            addExampleLondon();
        });


        Scene scene = new Scene(rootAnchorPane, 903, 683);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void clearEnverionment() {

        for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
            droneView.notifyReset();
        }

        for(CellView cellView : environmentView.getCellViews()){
            if(cellView.getChildren().size()!=0){
                cellView.getChildren().clear();
            }

        }





        selectableViews.clear();
        droneViewSelected = null;
        selectedSelectableView = null;
        DroneViewImpl.cleanDroneViewList();
        HospitalViewImpl.cleanHospitalViewList();
        RiverViewImpl.cleanRiverViewList();
        AntennaViewImpl.cleanAntennaViewList();

        loggerController.clear();

        DroneViewImpl.COUNT_DRONE = 0;

        HospitalViewImpl.COUNT_HOSPITAL =0;

        AntennaViewImpl.COUNT_ANTENNA =0;
    }

    private void addExampleLondon() {

        AntennaViewImpl antenaView1 = new AntennaViewImpl(environmentView.getCellFrom(3, 15));
        AntennaViewImpl.addAntennaViewFromList(antenaView1);
        selectableViews.add(antenaView1);

        HospitalViewImpl hospitalView = new HospitalViewImpl(environmentView.getCellFrom(3, 0));
        HospitalViewImpl.addHospitalViewFromList(hospitalView);
        selectableViews.add(hospitalView);

        hospitalView = new HospitalViewImpl(environmentView.getCellFrom(3, 29));
        HospitalViewImpl.addHospitalViewFromList(hospitalView);
        selectableViews.add(hospitalView);

        RiverViewImpl riverView1 = null;
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 2));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 3));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 2));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 3));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 2));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 3));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 4));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 5));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 6));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 7));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 3));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 4));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 5));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 6));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 7));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 8));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 7));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 8));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 9));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 10));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 11));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 12));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 8));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 9));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 10));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 11));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 11));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 12));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 13));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 12));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 13));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 14));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 15));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 16));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 17));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 13));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 14));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 15));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 16));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 16));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 17));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 18));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 17));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 18));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 19));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 20));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 21));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 22));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 18));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 19));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 20));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 21));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 21));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 22));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 23));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 22));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 23));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 24));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 25));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 26));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 27));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 23));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 24));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 25));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 26));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 26));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 27));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 28));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 28));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 26));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 27));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 28));
        RiverViewImpl.addRiverViewFromList(riverView1);
        selectableViews.add(riverView1);

    }

    @FXML
    private void initialize() {


        loggerController.setTextArea(loggerTextArea);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem logAnalyzerMenuItem = new MenuItem("Drone Logs Analyzer");
        contextMenu.getItems().add(logAnalyzerMenuItem);
        loggerTextArea.setContextMenu(contextMenu);
        logAnalyzerMenuItem.setOnAction(event -> {

            try {

                new DroneAnalyzerLog().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        ToggleGroup toggleGroup1 = new ToggleGroup();
        riverToggleButton.setToggleGroup(toggleGroup1);
        hospitalToggleButton.setToggleGroup(toggleGroup1);
        droneToggleButton.setToggleGroup(toggleGroup1);
        antennaToggleButton.setToggleGroup(toggleGroup1);

        ToggleGroup toggleGroup3 = new ToggleGroup();
        trueStrongWindRadioButton.setToggleGroup(toggleGroup3);
        noStrongWindRadioButton.setToggleGroup(toggleGroup3);
        randomStrongWindRadioButton.setToggleGroup(toggleGroup3);

        ToggleGroup toggleGroup4 = new ToggleGroup();
        startToggleButton.setToggleGroup(toggleGroup4);
        restartToggleButton.setToggleGroup(toggleGroup4);


        environmentView = new EnvironmentView(12, 30, environmentAnchorPane);


        trueStrongWindRadioButton.setOnMouseClicked(event -> {
            stopRandomStrongWind();
            environmentView.addStrongWind();

            for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                droneView.notifyStrongWind();
            }
        });

        noStrongWindRadioButton.setOnMouseClicked(event -> {
            stopRandomStrongWind();
            environmentView.removeStrongWind();

            for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                droneView.notifyNoStrongWind();
            }
        });

        randomStrongWindRadioButton.setOnAction(event -> {
            ramdomStrongWind = new Timer();
            ramdomStrongWind.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Random random = new Random();
                    boolean bool = random.nextBoolean();

                    if (bool) {

                        Platform.runLater(() -> {
                            environmentView.addStrongWind();
                        });


                        for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                            droneView.notifyStrongWind();
                        }

                    } else {


                        Platform.runLater(() -> {
                            environmentView.removeStrongWind();
                        });

                        for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                            droneView.notifyNoStrongWind();
                        }
                    }

                }
            }, 0, 1000);
        });

        droneToggleButton.setOnMouseClicked(event -> droneToggleButtonIsSelected = !droneToggleButtonIsSelected);

        /*    trueBadConnectionRadioButton.setOnAction(event -> {

         *//*    if(running){
                if(droneViewSelected!= null){
                    droneViewSelected.notifyBadConnection();
                }
            }else {
                badConnection = true;
            }*//*


        });

        noBadConnectionRadioButton.setOnAction(event -> {
           *//* if(running){

                if(droneViewSelected!= null){
                    droneViewSelected.notifyNormalConnection();
                    badConnection = false;
                }else {
                    badConnection = false;
                }
            }*//*

        });*/


        saveButton.setOnAction(event -> {

            startToggleButton.setDisable(false);
            restartToggleButton.setDisable(false);

            saveAttributesDrone();
            updateDroneSettingsViews();


            disableDroneSettingsViews();

            droneToggleButton.setSelected(false);

            environmentView.getGridpane().requestFocus();
        });

        startToggleButton.setOnAction(event -> {

            disableEnvironmentSettingViews();


            for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                droneView.notifyRunEnviroment();
            }


            running = true;

            environmentView.getGridpane().requestFocus();

        });

        restartToggleButton.setOnAction(event -> {

            for (DroneView droneView : DroneViewImpl.getDroneViewList()) {
                droneView.notifyReset();
            }

            loggerController.clear();


            running = false;

            enableEnvironmentSettingViews();


        });


        environmentView.getGridpane().setOnMouseClicked(event1 -> {

            if (riverToggleButton.isSelected()) {

                createRiver();

            } else if (hospitalToggleButton.isSelected()) {
                createHospital();

            } else if (antennaToggleButton.isSelected()) {
                createAntenna();
            } else if (droneToggleButton.isSelected()) {

                if(HospitalViewImpl.getHospitalViewList().size()<2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You must add at 2 hospitals \n(first as target and second as target)", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }



                DroneViewImpl droneView = (DroneViewImpl) createDrone();
                updateSelectedDrone(droneView);
                updateDroneSettingsViews();
                enableDroneSettingsViews();

            }


            SelectableView selectableView = (SelectableView) getViewFromCell(environmentView.getCellViewSelected());

            for (SelectableView selectableView1 : selectableViews) {
                selectableView1.removeStyleSelected();
            }

            if(selectableView != null){
                selectedSelectableView = selectableView;
                selectableView.applyStyleSelected();

                if(selectableView instanceof DroneView){
                    DroneView droneView = (DroneView) selectableView;
                    droneViewSelected = droneView;

                    updateSelectedDrone((DroneViewImpl) droneView);
                    updateDroneSettingsViews();

                    enableDroneSettingsViews();
                }else {

                    droneViewSelected = null;
                    updateDroneSettingsViews();
                    disableDroneSettingsViews();

                }
            }else {
                selectedSelectableView = null;
            }




        });

        environmentView.getGridpane().setOnKeyPressed(event -> {

            if (!running) {
                return;
            }

            if (droneViewSelected != null) {
                droneViewSelected.eventKey(event.getCode());


            }

        });

        cleanButton.setOnAction(event -> {

            if(selectableViews.size()==0){
                return;
            }

            if(running){
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clean this environment?", ButtonType.YES ,ButtonType.CANCEL);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES){
                riverToggleButton.setSelected(false);
                droneToggleButton.setSelected(false);
                antennaToggleButton.setSelected(false);
                hospitalToggleButton.setSelected(false);

                clearEnverionment();
            }




        });

        deleteButton.setOnAction(event -> {

            if(selectableViews.size()==0){
                return;
            }

            if(selectedSelectableView==null){
                return;
            }

            if(running){
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this view?", ButtonType.YES ,ButtonType.CANCEL);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES){
                for(CellView cellView : environmentView.getCellViews()){
                    if(cellView.getChildren().contains(selectedSelectableView)){
                        cellView.getChildren().remove(selectedSelectableView);
                    }
                }



                selectableViews.remove(selectedSelectableView);


                if(selectedSelectableView == droneViewSelected){
                    droneViewSelected = null;
                }

                Method removeMethed = null;
                try {
                    removeMethed = selectedSelectableView.getClass()
                            .getMethod("remove"+selectedSelectableView.getClass().getSimpleName()
                                    .replace("Impl","")+"FromList",selectedSelectableView.getClass().getSuperclass());
                    removeMethed.invoke(selectedSelectableView, selectedSelectableView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                if(selectedSelectableView instanceof HospitalView){
                    for(DroneView droneView : new ArrayList<>(DroneViewImpl.getDroneViewList())){
                        if(((Drone)droneView.getDrone()).getSourceHospital()
                                == ((HospitalView)selectedSelectableView).getHospital()){
                            DroneViewImpl.removeDroneViewFromList(droneView);
                        }

                        if(((Drone)droneView.getDrone()).getDestinyHopistal()
                                == ((HospitalView)selectedSelectableView).getHospital()){
                            DroneViewImpl.removeDroneViewFromList(droneView);
                        }
                    }
                }


                //  DroneView.removeDroneViewFromList(selectedSelectableView);

              /*  if(DroneView.getDroneViewList().contains(selectedSelectableView)){
                    DroneView.getDroneViewList().remove(selectedSelectableView);
                }

                if(antennaViews.contains(selectedSelectableView)){
                    antennaViews.remove(selectedSelectableView);
                }

                if(riverViews.contains(selectedSelectableView)){
                    riverViews.remove(selectedSelectableView);
                }

                if(hospitalViews.contains(selectedSelectableView)){
                    hospitalViews.remove(selectedSelectableView);
                }*/

                selectedSelectableView = null;
            }




        });


    }


    private void stopRandomStrongWind() {
        try {
            ramdomStrongWind.cancel();
        } catch (Exception e) {

        }

    }


    private void saveAttributesDrone() {

        Drone selectedDrone = (Drone) droneViewSelected.getDrone();

        if (!running) {
            selectedDrone.setInitialBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setCurrentBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setBatteryPerBlock(Double.parseDouble(consumptionPerBlockTextView.getText()));
            selectedDrone.setBatteryPerSecond(Double.parseDouble(consumptionPerSecondTextView.getText()));

            selectedDrone.setAspect(wrapperCheckBox.isSelected());

            selectedDrone.setSourceHospital((Hospital) HospitalViewImpl.getHospitalViewList().get(sourceComboBox.getSelectionModel().getSelectedIndex()).getHospital());
            selectedDrone.setDestinyHopistal((Hospital) HospitalViewImpl.getHospitalViewList().get(targetComboBox.getSelectionModel().getSelectedIndex()).getHospital());

        }


        if (!randomStrongWindRadioButton.isSelected()) {
            selectedDrone.setStrongWind(trueStrongWindRadioButton.isSelected());
        }


        selectedDrone.setIsAutomatic(automaticCheckBox.isSelected());
        selectedDrone.setIsManual(!selectedDrone.isAutomatic());

        /* selectedDrone.setBadConnection(trueBadConnectionRadioButton.isSelected());*/


        if (running) {
            if (droneViewSelected != null) {
                if (selectedDrone.isBadConnection()) {
                    droneViewSelected.notifyBadConnection();
                } else {
                    droneViewSelected.notifyNormalConnection();
                }


            }
        }
    }

    private void updateDroneSettingsViews() {

        if (droneViewSelected == null) {
            consumptionPerBlockTextView.setText("");
            consumptionPerSecondTextView.setText("");
            initialBatteryTextView.setText("");
            currentDroneTextField.setText("");
            automaticCheckBox.setSelected(false);
            wrapperCheckBox.setSelected(false);
            sourceComboBox.getSelectionModel().clearSelection();
            targetComboBox.getSelectionModel().clearSelection();

            /* trueBadConnectionRadioButton.setSelected(false);*/
        } else {
            Drone selectedDrone = (Drone) droneViewSelected.getDrone();
            currentDroneTextField.setText(String.valueOf(selectedDrone.getId()));
            consumptionPerBlockTextView.setText(String.valueOf(selectedDrone.getBatteryPerBlock()));
            consumptionPerSecondTextView.setText(String.valueOf(selectedDrone.getBatteryPerSecond()));
            initialBatteryTextView.setText(String.valueOf(selectedDrone.getInitialBattery()));
            automaticCheckBox.setSelected(selectedDrone.isAutomatic());
            wrapperCheckBox.setSelected(selectedDrone.isAspect());

            List<String> nameHospitals = new ArrayList<>(HospitalViewImpl.getHospitalViewList().size());
            for(HospitalView hospitalView : HospitalViewImpl.getHospitalViewList()){
                nameHospitals.add(String.valueOf(((Hospital)hospitalView.getHospital()).getId()));
            }

            ObservableList<String> options =
                    FXCollections.observableArrayList(nameHospitals);
            sourceComboBox.setItems(options);


            targetComboBox.setItems(options);

            sourceComboBox.getSelectionModel().select(String.valueOf(selectedDrone.getSourceHospital().getId()));



            targetComboBox.getSelectionModel().select(String.valueOf(selectedDrone.getDestinyHopistal().getId()));





          /*  trueBadConnectionRadioButton.setSelected(selectedDrone.isBadConnection());

            noBadConnectionRadioButton.setSelected(!selectedDrone.isBadConnection());*/
        }


    }

    private void updateSelectedDrone(DroneViewImpl droneView) {
        droneViewSelected = droneView;
        droneView.applyStyleSelected();
    }

    private DroneView createDrone() {
        DroneViewImpl droneView = new DroneViewImpl((CellView) environmentView.getCellViewSelected(),
                (Hospital) HospitalViewImpl.getHospitalViewList().get(0).getHospital(),
                (Hospital) HospitalViewImpl.getHospitalViewList().get(1).getHospital());

        DroneViewImpl.addDroneViewFromList(droneView);
        selectableViews.add(droneView);

        Drone drone = (Drone) droneView.getDrone();

        System.out.println("Drone" + drone.getId() + ": Source Hospital " + drone.getSourceHospital().getId() + " Destiny Hospital " + drone.getDestinyHopistal().getId());

        return droneView;
    }

    private HospitalView createHospital() {
        HospitalViewImpl hospitalView = new HospitalViewImpl((CellView) environmentView.getCellViewSelected());
        HospitalViewImpl.addHospitalViewFromList(hospitalView);
        selectableViews.add(hospitalView);
        return hospitalView;
    }

    private RiverView createRiver() {
        RiverViewImpl riverView = new RiverViewImpl((CellView) environmentView.getCellViewSelected());
        RiverViewImpl.addRiverViewFromList(riverView);
        selectableViews.add(riverView);
        return riverView;
    }

    private AntennaView createAntenna() {
        AntennaViewImpl antenaView = new AntennaViewImpl((CellView) environmentView.getCellViewSelected());
        AntennaViewImpl.addAntennaViewFromList(antenaView);
        selectableViews.add(antenaView);
        return antenaView;
    }

    private void disableEnvironmentSettingViews() {
        riverToggleButton.setDisable(true);
        hospitalToggleButton.setDisable(true);
        droneToggleButton.setDisable(true);
        antennaToggleButton.setDisable(true);
        deleteButton.setDisable(true);
        cleanButton.setDisable(true);
    }

    private void enableEnvironmentSettingViews() {
        settingsAnchorPane.requestFocus();
        riverToggleButton.setDisable(false);
        hospitalToggleButton.setDisable(false);
        droneToggleButton.setDisable(false);
        antennaToggleButton.setDisable(false);
        deleteButton.setDisable(false);
        cleanButton.setDisable(false);
    }

    private void disableDroneSettingsViews() {

        consumptionPerBlockLabel.setDisable(true);
        consumptionPerBlockTextView.setDisable(true);

        consumptionPerSecondLabel.setDisable(true);
        consumptionPerSecondTextView.setDisable(true);

        initialBatteryLabel.setDisable(true);
        initialBatteryTextView.setDisable(true);
        wrapperCheckBox.setDisable(true);
        saveButton.setDisable(true);
        sourceComboBox.setDisable(true);
        sourceLabel.setDisable(true);
        targetComboBox.setDisable(true);
        targetComboBox.setDisable(true);
        /*       badConectionLabel.setDisable(true);*/
   /*     trueBadConnectionRadioButton.setDisable(true);
        noBadConnectionRadioButton.setDisable(true);
       *//* randomBadConnectionRadioButton.setDisable(true);*/

        automaticCheckBox.setDisable(true);

    }

    private void enableDroneSettingsViews() {


        if (!running) {
            consumptionPerBlockLabel.setDisable(false);
            consumptionPerBlockTextView.setDisable(false);
            consumptionPerBlockTextView.requestFocus();

            consumptionPerSecondLabel.setDisable(false);
            consumptionPerSecondTextView.setDisable(false);

            initialBatteryLabel.setDisable(false);
            initialBatteryTextView.setDisable(false);
            wrapperCheckBox.setDisable(false);
            sourceComboBox.setDisable(false);
            sourceLabel.setDisable(false);
            targetLabel.setDisable(false);
            targetComboBox.setDisable(false);
        }

        if (droneViewSelected != null && ((Drone) droneViewSelected.getDrone()).isTookOff()) {
            /* badConectionLabel.setDisable(false);*/
          /*  trueBadConnectionRadioButton.setDisable(false);
            noBadConnectionRadioButton.setDisable(false);*/
            /*  randomBadConnectionRadioButton.setDisable(false);*/
        }


        automaticCheckBox.setDisable(false);
        wrapperCheckBox.setDisable(false);

        saveButton.setDisable(false);

    }


    private DroneViewImpl getDroneViewFromCell(Pane cellSelected) {
        if (cellSelected.getChildren().isEmpty()) {
            return null;
        }

        Node node = cellSelected.getChildren().get(cellSelected.getChildren().size() - 1);

        if (node instanceof DroneView) {
            return (DroneViewImpl) node;
        }

        return null;

    }

    private Node getViewFromCell(Pane cellSelected) {

        if (cellSelected.getChildren().isEmpty()) {
            return null;
        }

        Node node = cellSelected.getChildren().get(cellSelected.getChildren().size() - 1);



        return node;

    }


}