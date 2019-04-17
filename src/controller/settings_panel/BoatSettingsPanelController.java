package controller.settings_panel;

import controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Cell;
import model.entity.boat.Boat;
import view.CellView;
import view.SelectableView;
import view.boat.BoatView;


import java.io.IOException;

public class BoatSettingsPanelController extends SettingsPanelController<Boat> {

    private static BoatSettingsPanelController instance;
    private  Boat selectedBoat;
    private AnchorPane defaultPanelSettingsAnchorPane;
    private AnchorPane boatSettingsPanelAnchorPane;

    @FXML
    private ButtonBase saveButton;

    @FXML
    private TextField currentBoatTextField;

    @FXML
    private
    Label currentSourceCell, currentDestinyCell, sourceLabel, targetLabel;

    @FXML
    ImageView sourceSettingsImageView, destinySettingsImageView;
    private boolean clickedSourceSettings;
    private boolean clickedDestinySettings;
    private boolean waitForClickInCell =false;


    public static void init(AnchorPane defaultPanelSettingsAnchorPane) {

        if (!defaultPanelSettingsAnchorPane.getChildren().isEmpty()) {
            defaultPanelSettingsAnchorPane.getChildren().clear();
        }


        if (instance == null) {
            instance = new BoatSettingsPanelController(defaultPanelSettingsAnchorPane);
        }
    }

    private BoatSettingsPanelController(AnchorPane defaultPanelSettingsAnchorPane) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/res/boat/boat_settings_panel.fxml"));
        loader.setController(this);
        try {
            boatSettingsPanelAnchorPane = loader.load();
            this.defaultPanelSettingsAnchorPane = defaultPanelSettingsAnchorPane;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BoatSettingsPanelController getInstance() {
        return instance;
    }


    @Override
    void initialize() {
        saveButton.setOnAction(event -> {

            saveAttributesInEntity(selectedBoat);

            disableSettingsViews();


            MainController.getInstance().notitySaveFromPanelSettings();

        });

        sourceSettingsImageView.setOnMouseClicked(event -> {

            clickedSourceSettings = true;
            clickedDestinySettings = false;

            waitForClickInCell =true;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Source Cell View", ButtonType.OK);
            alert.showAndWait();


        });

        destinySettingsImageView.setOnMouseClicked(event -> {

            clickedSourceSettings = false;
            clickedDestinySettings = true;

            waitForClickInCell =true;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Destiny Cell View", ButtonType.OK);
            alert.showAndWait();


        });
    }

    @Override
    void disableSettingsViews() {
        saveButton.setDisable(true);
        sourceSettingsImageView.setDisable(true);
        sourceSettingsImageView.setOpacity(0.3);

        destinySettingsImageView.setDisable(true);
        destinySettingsImageView.setOpacity(0.3);
        sourceLabel.setDisable(true);
        targetLabel.setDisable(true);
    }

    @Override
    void enableSettingsViews() {
        sourceLabel.setDisable(false);
        targetLabel.setDisable(false);

        sourceSettingsImageView.setDisable(false);
        sourceSettingsImageView.setOpacity(1);

        destinySettingsImageView.setDisable(false);
        destinySettingsImageView.setOpacity(1);
        saveButton.setDisable(false);
    }

    @Override
    void saveAttributesInEntity(Boat boat) {
        int srcI = Integer.parseInt(currentSourceCell.getText().split(",")[0].replace("<",""));
        int srcJ = Integer.parseInt(currentSourceCell.getText().split(",")[1].replace(">",""));

        int destI = Integer.parseInt(currentDestinyCell.getText().split(",")[0].replace("<",""));
        int destJ = Integer.parseInt(currentDestinyCell.getText().split(",")[1].replace(">",""));


        boat.setSourceCell(CellController.getInstance().getCellFrom(srcI, srcJ));

        boat.setDestinyCell(CellController.getInstance().getCellFrom(destI, destJ));
    }

    @Override
    void updateSettingsViewsFromEntity(Boat boat) {

        String boatLabel = boat.getLabel();

        currentBoatTextField.setText(boatLabel);

        String currentSourceCellString =
                "<" + boat.getSourceCell().getRowPosition()
                        + "," + boat.getSourceCell().getColumnPosition() + ">";

        String currentDestinyCellString =
                "<" + boat.getDestinyCell().getRowPosition()
                        + "," + boat.getDestinyCell().getColumnPosition() + ">";

        currentSourceCell.setText(currentSourceCellString);
        currentDestinyCell.setText(currentDestinyCellString);

    }

    @Override
    void clearSettingView() {
        currentBoatTextField.setText("");
        currentSourceCell.setText("<0,0>");
        currentDestinyCell.setText("<0,0>");
    }

    @Override
    public void notifyMouseClick(SelectableView selectableView) {
        if(waitForClickInCell){
            if (selectableView instanceof CellView) {

                CellView cellview = (CellView) selectableView;
                Cell cell = CellController.getInstance().getCellFrom(cellview.getUniqueID());

                if (clickedSourceSettings) {
                    currentSourceCell.setText("<" + cell.getRowPosition() + "," + cell.getColumnPosition() + ">");
                    clickedSourceSettings = false;
                }

                if (clickedDestinySettings) {
                    currentDestinyCell.setText("<" + cell.getRowPosition() + "," + cell.getColumnPosition() + ">");
                    clickedDestinySettings = false;
                }

                waitForClickInCell = false;
            }
        }else {
            if (selectableView instanceof BoatView) {
                BoatView boatView = (BoatView) selectableView;
                Boat boat = BoatController.getInstance().getBoatFrom(boatView.getUniqueID());

                show();

                enableSettingsViews();
                updateSettingsViewsFromEntity(boat);

            }else {
                hide();
            }
        }




    }
    @Override
    public void hide() {
        if(defaultPanelSettingsAnchorPane.getChildren().contains(boatSettingsPanelAnchorPane)){
            defaultPanelSettingsAnchorPane.getChildren().remove(boatSettingsPanelAnchorPane);
        }

    }

    @Override
    public void show() {

        hide();

        defaultPanelSettingsAnchorPane.getChildren().add(boatSettingsPanelAnchorPane);
        enableSettingsViews();


        SelectableView selectableView = EnvironmentController.getInstance().getSelectedEntityView();
        BoatView boatView = (BoatView) selectableView;
        selectedBoat = BoatController.getInstance().getBoatFrom(boatView.getUniqueID());

        updateSettingsViewsFromEntity(selectedBoat);
    }
}
